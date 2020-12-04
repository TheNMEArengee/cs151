package application.EventHandlers;

import application.Card;
import application.Unit;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

// Used for unit movement (selecting unit to move)
public class MousePressedAction implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;
	private GraphicsContext gc;
	private Card currentSelectedMovement;
	private int currentPlayer;

	// Constructor
	public MousePressedAction(CheckerboardPane checkerboardPane) {
		this.checkerboardPane = checkerboardPane;
		this.gc = checkerboardPane.getGraphicsContext();
	}

	// Main handler
	@Override
	public void handle(MouseEvent e) {
		Checkerboard checkerboard = checkerboardPane.getCheckerBoard();
		int tileSize = checkerboard.getTileSize();
		// For the units, Location of mouse press, translated to grid
		int boardX = (int) e.getX() / tileSize;
		int boardY = (int) e.getY() / tileSize;
		// Co-ords for the cards
		int mouseX = (int) e.getX();
		int mouseY = (int) e.getY();

		currentPlayer = checkerboard.getCurrPlayer();
		Card c = checkerboardPane.getCardAt(mouseX, mouseY);
		Unit u = getUnitSelected(boardX, boardY);

		if (u != null) { // Unit is selected
			// Traverse 'units' set in checkerboardPane to redraw/update units
			checkerboardPane.getUnits().forEach(unit -> {
				// If unit's coordinates match calculated mouse press coordinates
				if (unit.getX() == boardX && unit.getY() == boardY) {
					int movement = 0;
					if (currentSelectedMovement != null && unit.getRole() == 0) {
						movement = currentSelectedMovement.getMovementTypeID();
					} else if (unit.getRole() == 1) {
						movement = 1;
					}
					Unit movingUnit = new Unit(unit.getX(), unit.getY(), unit.getPlayer(), movement);
					colorValidSquares(movingUnit);
					unit.setSelected(true);
					checkerboardPane.drawUnits();
				}
			});
		} else if (c != null) { // Card is selected
			// if else used to reset selected, then set selected to that card
			int cardOwner = (c.getAffiliation().toString() == "WHITE") ? 0 : 1;
			if (currentPlayer == 0 && currentPlayer == cardOwner) {
				checkerboardPane.getPlayer0Hand().resetSelectedCards();
				c.setSelected(true);
			} else if (currentPlayer == 1 && currentPlayer == cardOwner) {
				checkerboardPane.getPlayer1Hand().resetSelectedCards();
				c.setSelected(true);
			}
			setSelectedMovement();
		} else {
			currentSelectedMovement = null;
			checkerboardPane.resetCurrentMoveUI();
		}

	}

	private Unit getUnitSelected(int boardX, int boardY) {
		checkerboardPane.getUnits();
		for (Unit u : checkerboardPane.getUnits()) {
			if (boardX == u.getX() && boardY == u.getY()) {
				return u;
			}
		}
		return null;
	}

	private void setSelectedMovement() {
		if (currentPlayer == 0) {
			for (Card c : checkerboardPane.getPlayer0Hand().getHand()) {
				if (c.isSelected()) {
					currentSelectedMovement = c;
					break;
				}
			}
		} else if (currentPlayer == 1) {
			for (Card c : checkerboardPane.getPlayer1Hand().getHand()) {
				if (c.isSelected()) {
					currentSelectedMovement = c;
					break;
				}
			}
		}
	}

	// Highlights all the valid squares the selected unit can move to
	private void colorValidSquares(Unit unit) {
		if (unit.getPlayer() == checkerboardPane.getCheckerBoard().getCurrPlayer()) {
			gc.setFill(Color.rgb(235, 0, 27));
			gc.fillRect(unit.getX() * 60, unit.getY() * 60, 60, 60);
			if (unit.getRole() == 0) {
				colorPawnSquares(unit);
			} else if (unit.getRole() == 1) {
				colorKingSquares(unit);
			} else if (unit.getRole() == 2) {
				colorRookSquares(unit);
			} else if (unit.getRole() == 3) {
				colorBishopSquares(unit);
			} else if (unit.getRole() == 4) {
				colorKnightSquares(unit);
			} else {
				colorQueenSquares(unit);
			}
		}
	}

	// Highlights all the valid squares a queen can move
	private void colorQueenSquares(Unit unit) {
		// Queen is just Rook + Bishop, so call those methods
		colorRookSquares(unit);
		colorBishopSquares(unit);
	}

	// Highlights all the valid squares a Knight can move
	private void colorKnightSquares(Unit unit) {
		// For the 2x2 area around it
		for (int x = unit.getX() - 2; x <= unit.getX() + 2; x++) {
			for (int y = unit.getY() - 2; y <= unit.getY() + 2; y++) {
				// If the square is in bounds
				if (x >= 0 && x <= 7 && y >= 0 & y <= 7) {
					Unit otherUnit = getUnitAt(x, y);
					// If the square is null or an enemy piece is present, fill it
					if (otherUnit == null || areOpposingUnits(unit, otherUnit)) {
						tiledFillRect(x, y);
					}
				}
				unhighlight(unit);
			}
		}
	}

	// Un-highlight method for colorKnightSquares()
	private void unhighlight(Unit unit) {
		// For the 2x2 area around it
		for (int x = unit.getX() - 2; x <= unit.getX() + 2; x++) {
			for (int y = unit.getY() - 2; y <= unit.getY() + 2; y++) {
				// If the square is in bounds
				if (x >= 0 && x <= 7 && y >= 0 & y <= 7) {
					// Un-highlight immediate area
					for (int i = unit.getX() - 1; i <= unit.getX() + 1; i++) {
						for (int j = unit.getY() - 1; j <= unit.getY() + 1; j++) {
							if (i >= 0 && i <= 7 && j >= 0 & j <= 7) {
								gc.clearRect(i * 60, j * 60, 60, 60);
							}
						}
					}

					// Re-fill current square
					gc.setFill(Color.rgb(235, 0, 27));
					tiledFillRect(unit.getX() * 60, unit.getY() * 60);

					// Un-highlight corners
					int cornerX = Math.abs(unit.getX() - x);
					int cornerY = Math.abs(unit.getY() - y);
					if (cornerX > 1 && cornerY > 1 && cornerX >= 0 && x >= 0 && x <= 7 && y >= 0 & y <= 7) {
						gc.clearRect(x * 60, y * 60, 60, 60);
					}

					// Un-highlight N/S/E/W
					if ((x == unit.getX() || y == unit.getY()) && x >= 0 && x <= 7 && y >= 0 & y <= 7) {
						gc.clearRect(x * 60, y * 60, 60, 60);
					}
				}
			}
		}
	}

	// Highlights all the valid squares a Bishop can move
	private void colorBishopSquares(Unit unit) {
		colorBishopSquaresTopLeft(unit);
		colorBishopSquaresTopRight(unit);
		colorBishopSquaresBottomLeft(unit);
		colorBishopSquaresBottomRight(unit);
	}

	// Check top left diagonal squares for colorBishopSquares() method
	private void colorBishopSquaresTopLeft(Unit unit) {
		int i = 1;

		// While checked square doesn't exceed game board
		while (unit.getX() - i >= 0 && unit.getY() - i >= 0) {
			Unit otherUnit = getUnitAt(unit.getX() - i, unit.getY() - i);
			// If a unit exists at target square
			if (otherUnit != null) {
				// If its a unit of the same side, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color that square and then break
				else {
					tiledFillRect(unit.getX() - i, unit.getY() - i);
					break;
				}
			}
			// Else, no enemy units. Color and check next square
			else {
				tiledFillRect(unit.getX() - i, unit.getY() - i);
			}
			i++;
		}
	}

	// Check top right diagonal squares for colorBishopSquares() method
	private void colorBishopSquaresTopRight(Unit unit) {
		// Check top right diagonal
		int j = 1;
		while (unit.getX() + j <= 7 && unit.getY() - j >= 0) {
			Unit otherUnit = getUnitAt(unit.getX() + j, unit.getY() - j);
			// If a unit exists at target square
			if (otherUnit != null) {
				// If its a unit of the same side, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color that square and then break
				else {
					tiledFillRect(unit.getX() + j, unit.getY() - j);
					break;
				}
			}
			// Else, no enemy units. Color and check next square
			else {
				tiledFillRect(unit.getX() + j, unit.getY() - j);
			}
			j++;
		}
	}

	// Check bottom left diagonal squares for colorBishopSquares() method
	private void colorBishopSquaresBottomLeft(Unit unit) {
		// Check bottom left diagonal
		int k = 1;
		while (unit.getX() - k >= 0 && unit.getY() + k <= 7) {
			Unit otherUnit = getUnitAt(unit.getX() - k, unit.getY() + k);
			// If a unit exists at target square
			if (otherUnit != null) {
				// If its a unit of the same side, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color that square and then break
				else {
					tiledFillRect(unit.getX() - k, unit.getY() + k);
					break;
				}
			}
			// Else, no enemy units. Color and check next square
			else {
				tiledFillRect(unit.getX() - k, unit.getY() + k);
			}
			k++;
		}
	}

	// Check bottom right diagonal squares for colorBishopSquares() method
	private void colorBishopSquaresBottomRight(Unit unit) {
		// Check bottom right diagonal
		int l = 1;
		while (unit.getX() + l <= 7 && unit.getY() + l <= 7) {
			Unit otherUnit = getUnitAt(unit.getX() + l, unit.getY() + l);
			// If a unit exists at target square
			if (otherUnit != null) {
				// If its a unit of the same side, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color that square and then break
				else {
					tiledFillRect(unit.getX() + l, unit.getY() + l);
					break;
				}
			}
			// Else, no enemy units. Color and check next square
			else {
				tiledFillRect(unit.getX() + l, unit.getY() + l);
			}
			l++;
		}
	}

	// Highlights all the valid squares a Rook can move
	private void colorRookSquares(Unit unit) {
		colorRookSquaresLeft(unit);
		colorRookSquaresRight(unit);
		colorRookSquaresTop(unit);
		colorRookSquaresBottom(unit);
	}

	// Check left squares for colorBishopSquares() method
	private void colorRookSquaresLeft(Unit unit) {
		// While in bounds
		for (int x = unit.getX() - 1; x >= 0; x--) {
			Unit otherUnit = getUnitAt(x, unit.getY());
			// If a unit is on the path
			if (otherUnit != null) {
				// If its a friendly unit, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color and then break
				else {
					tiledFillRect(x, unit.getY());
					break;
				}
			}
			// Else. nothing in path. Color and check next square
			else {
				tiledFillRect(x, unit.getY());
			}
		}
	}

	// Check right squares for colorBishopSquares() method
	private void colorRookSquaresRight(Unit unit) {
		// While in bounds
		for (int x = unit.getX() + 1; x <= 7; x++) {
			Unit otherUnit = getUnitAt(x, unit.getY());
			// If a unit is on the path
			if (otherUnit != null) {
				// If its a friendly unit, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color and then break
				else {
					tiledFillRect(x, unit.getY());
					break;
				}
			}
			// Else. nothing in path. Color and check next square
			else {
				tiledFillRect(x, unit.getY());
			}
		}
	}

	// Check top squares for colorBishopSquares() method
	private void colorRookSquaresTop(Unit unit) {
		// While in bounds
		for (int y = unit.getY() - 1; y >= 0; y--) {
			Unit otherUnit = getUnitAt(unit.getX(), y);
			// If a unit is on the path
			if (otherUnit != null) {
				// If its a friendly unit, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color and then break
				else {
					tiledFillRect(unit.getX(), y);
					break;
				}
			}
			// Else. nothing in path. Color and check next square
			else {
				tiledFillRect(unit.getX(), y);
			}
		}
	}

	// Check bottom squares for colorBishopSquares() method
	private void colorRookSquaresBottom(Unit unit) {
		// While in bounds
		for (int y = unit.getY() + 1; y <= 7; y++) {
			Unit otherUnit = getUnitAt(unit.getX(), y);
			// If a unit is on the path
			if (otherUnit != null) {
				// If its a friendly unit, break immediately
				if (!areOpposingUnits(unit, otherUnit)) {
					break;
				}
				// Else, its an enemy unit. Color and then break
				else {
					tiledFillRect(unit.getX(), y);
					break;
				}
			}
			// Else. nothing in path. Color and check next square
			else {
				tiledFillRect(unit.getX(), y);
			}
		}
	}

	// Highlights all the valid squares a King can move
	private void colorKingSquares(Unit unit) {
		// For the immediate area around it
		for (int x = unit.getX() - 1; x <= unit.getX() + 1; x++) {
			for (int y = unit.getY() - 1; y <= unit.getY() + 1; y++) {
				// If the square is in bounds
				if (x >= 0 && x <= 7 && y >= 0 & y <= 7) {
					Unit otherUnit = getUnitAt(x, y);
					// If there is no unit or an enemy unit, color the square
					if (otherUnit == null || areOpposingUnits(unit, otherUnit)) {
						tiledFillRect(x, y);
					}
				}
			}
		}
	}

	// Highlights all the valid squares a Pawn can move
	private void colorPawnSquares(Unit unit) {
		// Allows code to work for both white and black pieces
		int sideMultiplier = 1;
		if (unit.getPlayer() == 1) {
			sideMultiplier = -1;
		}

		Unit otherUnit = getUnitAt(unit.getX(), unit.getY() + (1 * sideMultiplier));
		// Checking straight ahead
		if (otherUnit == null) {
			tiledFillRect(unit.getX(), (unit.getY() + (1 * sideMultiplier)));
		}

		// Checking diagonals
		otherUnit = getUnitAt(unit.getX() + 1, unit.getY() + (1 * sideMultiplier));
		if (otherUnit != null && areOpposingUnits(unit, otherUnit)) {
			tiledFillRect((unit.getX() + 1), (unit.getY() + (1 * sideMultiplier)));
		}
		otherUnit = getUnitAt(unit.getX() - 1, unit.getY() + (1 * sideMultiplier));
		if (otherUnit != null && areOpposingUnits(unit, otherUnit)) {
			tiledFillRect((unit.getX() - 1), (unit.getY() + (1 * sideMultiplier)));
		}
	}

	// Retrieves the unit at the given x, y coordinate
	public Unit getUnitAt(int x, int y) {
		CheckerboardPane cp = checkerboardPane;
		Unit unitAtCoords = null;
		for (Unit u : cp.getUnits()) {
			if ((u.getX() == x) && (u.getY() == y)) {
				unitAtCoords = u;
			}
		}
		return unitAtCoords;
	}

	// Checks if the two input units are opposing sides. Return true if so and false
	// otherwise
	public boolean areOpposingUnits(Unit unit1, Unit unit2) {
		if (unit1.getPlayer() != unit2.getPlayer()) {
			return true;
		}
		return false;
	}

	// Color method for the colorValidSquares() method
	public void tiledFillRect(int x, int y) {
		// Alternate Colors
		if ((x + y) % 2 == 0) {
			gc.setFill(Color.DARKORANGE);
		} else {
			gc.setFill(Color.ORANGE);
		}
		gc.fillRect(x * 60, y * 60, 60, 60);
	}
}