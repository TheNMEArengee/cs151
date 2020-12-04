package application.EventHandlers;

import application.Card;
import application.Unit;
import application.CardContainers.Hand;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

// Used for unit movement (moving the selected unit to a tile)
public class MouseReleasedAction implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;
	private Stage primaryStage;
	private Scene welcomeScene;
	private GraphicsContext gc;
	private Unit unitToRemove;
	private Checkerboard checkerboard;
	private Hand currentHand;
	private Card cardSelected;

	// Constructor
	public MouseReleasedAction(CheckerboardPane checkerboardPane, Stage primaryStage, Scene welcomeScene) {
		this.checkerboardPane = checkerboardPane;
		this.primaryStage = primaryStage;
		this.welcomeScene = welcomeScene;
		this.gc = checkerboardPane.getGraphicsContext();
	}

	// Main Handler
	@Override
	public void handle(MouseEvent e) {
		checkerboardPane.drawBoard();
		checkerboard = checkerboardPane.getCheckerBoard();
		int tileSize = checkerboard.getTileSize();
		// Location of mouse release, translated to grid
		int releasedX = (int) ((e.getX() - checkerboard.getInitX()) / (tileSize));
		int releasedY = (int) ((e.getY() - checkerboard.getInitY()) / (tileSize));
		// Used to check logic and keep track of which unit to remove
		boolean validMove = true;
		unitToRemove = null;
		// Check what card is selected
		currentHand = null;
		cardSelected = setCardSelected();
		int movement = setCardSelectedMovement();

		// Check all units to see which one is selected, then determine validity of
		// movement with game logic
		for (Unit u : checkerboardPane.getUnits()) {
			if (u.isSelected()) { // unit is selected
				Unit unitAtReleasedCoords = unitExistsAtCoords(releasedX, releasedY, checkerboardPane);
				if (checkerboard.getCurrPlayer() == u.getPlayer()) { // unit moved belongs to current player
					// If the player did not just click and release on the same tile
					if (!((u.getX() == releasedX) && (u.getY() == releasedY))) { // They moved
						if (releasedX < 8 && releasedY < 8) { // If in bounds
							if (u.getRole() == 0) {
								switch (movement) {
								case 0: // Pawn: Move 1 forward
									validMove = pawnLogic(u, unitAtReleasedCoords, releasedX, releasedY);
									break;
								case 1: // King: Move 1 anywhere
									validMove = kingLogic(u, releasedX, releasedY);
									break;
								case 2: // Rook: Move infinite X or infinite Y
									validMove = rookLogic(u, releasedX, releasedY);
									break;
								case 3: // Bishop: Move infinite diagonal
									validMove = bishopLogic(u, releasedX, releasedY);
									break;
								case 4: // Knight: Move in L shapes
									validMove = knightLogic(u, releasedX, releasedY);
									break;
								case 5: // Queen: Combo of Rook + Bishop
									validMove = queenLogic(u, releasedX, releasedY);
								}
							} else if (u.getRole() == 1) {
								validMove = kingLogic(u, releasedX, releasedY);
							}
						} else { // Out of bounds
							validMove = false;
						}
						// If game logic passes, check if logic is valid at released coords
						if (validMove) {
							validMove = logicAtReleasedCoords(u, unitAtReleasedCoords);
						}
						// If released coord logic is valid, then we can update the boord with the move
						if (validMove) {
							if (cardSelected != null && u.getRole() != 1) { // Used card, remove?
								cardSelected.setSelected(false);
								currentHand.removeCard(cardSelected);
								currentHand.addCard(checkerboardPane.getDeck().drawCard());
								checkerboardPane.drawCards();
							}
							u.setX(releasedX);
							u.setY(releasedY);
							checkerboard.changePlayerTurn();
							checkerboardPane.resetCurrentMoveUI();
						}
					}
				}
				u.setSelected(false);
			}
		}
		updateGameView();
	}

	// Basically an update UI function
	private void updateGameView() {
		checkerboardPane.getUnits().remove(unitToRemove);
		gc.clearRect(0, 0, 480, 480);
		checkerboardPane.drawUnits();
		checkerboardPane.drawCurrentPlayerUI();
		checkerboardPane.drawCurrentMoveUI();
	}

	// Used to find movement card that is selected
	private Card setCardSelected() {
		if (checkerboard.getCurrPlayer() == 0) {
			currentHand = checkerboardPane.getPlayer0Hand();
		} else if (checkerboard.getCurrPlayer() == 1) {
			currentHand = checkerboardPane.getPlayer1Hand();
		}
		if (currentHand != null) {
			for (Card c : currentHand.getHand()) {
				if (c.isSelected()) {
					return c;
				}
			}
		}
		return null;
	}

	// Used to translate the card selected to a movement type
	private int setCardSelectedMovement() {
		if (cardSelected != null) {
			return cardSelected.getMovementTypeID();
		}
		return 0; // default pawn
	}

	// Checks if unit exists at specified coordinates
	public Unit unitExistsAtCoords(int x, int y, CheckerboardPane cp) {
		Unit unitAtCoords = null;
		for (Unit u : cp.getUnits()) {
			if ((u.getX() == x) && (u.getY() == y)) {
				unitAtCoords = u;
			}
		}
		return unitAtCoords;
	}

	// Check game logic for specified released location
	private boolean logicAtReleasedCoords(Unit currentUnit, Unit releasedUnit) {
		if (releasedUnit != null && (releasedUnit.getPlayer() != currentUnit.getPlayer())) {
			if (releasedUnit.isKing()) {
				System.out.println("Game Over!");
				primaryStage.setScene(welcomeScene);
				primaryStage.show();
			} else {
				System.out.println("Player " + checkerboard.getEnemyPlayer() + " ("
						+ checkerboard.getEnemyPlayerToString() + ")" + " unit down!");
			}
			unitToRemove = releasedUnit;
		} else if (releasedUnit != null) { // Valid move if null, moving to empty spot
			System.out.println("Invalid Move.");
			return false;
		}
		return true;
	}

	private boolean pawnLogic(Unit currentUnit, Unit releasedUnit, int releasedX, int releasedY) {
		int validMovement = (currentUnit.getPlayer() == 0 ? 1 : -1); // Player 0: +1. Player 1: -1
		if (currentUnit.getY() + validMovement != releasedY) { // If not one move forward...
			System.out.println("Invalid Move: Pawn's move one forward.");
			return false;
		} else { // Move is valid
			if (currentUnit.getX() == releasedX) { // Moving forward
				if (releasedUnit != null) {
					return false;
				}
				// Moving Diagonal Legal
			} else if (currentUnit.getX() + 1 == releasedX || currentUnit.getX() - 1 == releasedX) {
				if (releasedUnit == null) { // No unit there
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean kingLogic(Unit currentUnit, int releasedX, int releasedY) {
		// If not one move any direction...
		if ((Math.abs(releasedY - currentUnit.getY()) > 1) || (Math.abs(releasedX - currentUnit.getX()) > 1)) {
			System.out.println("Invalid Move: The fat king cannot move more than one step.");
			return false;
		}
		return true;
	}

	private boolean rookLogic(Unit currentUnit, int releasedX, int releasedY) {
		// Check path
		int x_movement = releasedX - currentUnit.getX();
		int y_movement = releasedY - currentUnit.getY();
		Unit unitAtPath;
		if (x_movement != 0 && y_movement != 0) {
			return false;
		} else if (x_movement != 0) { // Moving right/left
			if (currentUnit.getX() > releasedX) {
				for (int x = currentUnit.getX() - 1; x > releasedX; x--) {
					unitAtPath = unitExistsAtCoords(x, currentUnit.getY(), checkerboardPane);
					if (unitAtPath != null) {
						System.out.println("No clear path to released location!");
						return false;
					}
				}
			} else if (currentUnit.getX() < releasedX) {
				for (int x = currentUnit.getX() + 1; x < releasedX; x++) {
					unitAtPath = unitExistsAtCoords(x, currentUnit.getY(), checkerboardPane);
					if (unitAtPath != null) {
						System.out.println("No clear path to released location!");
						return false;
					}
				}
			}
		} else if (y_movement != 0) { // Moving forward/back
			if (currentUnit.getY() > releasedY) {
				for (int y = currentUnit.getY() - 1; y > releasedY; y--) {
					unitAtPath = unitExistsAtCoords(currentUnit.getX(), y, checkerboardPane);
					if (unitAtPath != null) {
						System.out.println("No clear path to released location!");
						return false;
					}
				}
			} else if (currentUnit.getY() < releasedY) {
				for (int y = currentUnit.getY() + 1; y < releasedY; y++) {
					unitAtPath = unitExistsAtCoords(currentUnit.getX(), y, checkerboardPane);
					if (unitAtPath != null) {
						System.out.println("No clear path to released location!");
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean bishopLogic(Unit currentUnit, int releasedX, int releasedY) {
		// Check path
		int x_movement = releasedX - currentUnit.getX(); // end - start
		int y_movement = releasedY - currentUnit.getY(); // end - start
		Unit unitAtPath;
		// If not diagonal movement
		if ((x_movement != 0 && y_movement == 0) || (x_movement == 0 && y_movement != 0)) {
			return false;
		} else if (Math.abs(x_movement) == Math.abs(y_movement)) { // Moving diagonal
			int yCounter;
			if (x_movement > 0) { // To the right
				if (y_movement > 0) { // To the bottom
					yCounter = 1;
					for (int x = currentUnit.getX() + 1; x < releasedX; x++) {
						unitAtPath = unitExistsAtCoords(x, currentUnit.getY() + yCounter, checkerboardPane);
						if (unitAtPath != null) {
							System.out.println("No clear path to released location!");
							return false;
						}
						yCounter++;
					}
				} else if (y_movement < 0) {
					yCounter = -1;
					for (int x = currentUnit.getX() + 1; x < releasedX; x++) {
						unitAtPath = unitExistsAtCoords(x, currentUnit.getY() + yCounter, checkerboardPane);
						if (unitAtPath != null) {
							System.out.println("No clear path to released location!");
							return false;
						}
						yCounter--;
					}
				}
			} else if (x_movement < 0) { // To the left
				if (y_movement > 0) { // To the bottom
					yCounter = 1;
					for (int x = currentUnit.getX() - 1; x > releasedX; x--) {
						unitAtPath = unitExistsAtCoords(x, currentUnit.getY() + yCounter, checkerboardPane);
						if (unitAtPath != null) {
							System.out.println("No clear path to released location!");
							return false;
						}
						yCounter++;
					}
				} else if (y_movement < 0) { // To the top
					yCounter = -1;
					for (int x = currentUnit.getX() - 1; x > releasedX; x--) {
						unitAtPath = unitExistsAtCoords(x, currentUnit.getY() + yCounter, checkerboardPane);
						if (unitAtPath != null) {
							System.out.println("No clear path to released location!");
							return false;
						}
						yCounter--;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean knightLogic(Unit currentUnit, int releasedX, int releasedY) {
		int x_movement = Math.abs(releasedX - currentUnit.getX()); // end - start
		int y_movement = Math.abs(releasedY - currentUnit.getY()); // end - start
		Unit unitAtPath;
		// Essentially we are just checking if the piece goes one direction
		// 2 spots and 1 direction another, then we are okay
		if ((x_movement == 1 && y_movement == 2) || (x_movement == 2 && y_movement == 1)) {
			// Valid, check to if unit at path is ally or opponent
			unitAtPath = unitExistsAtCoords(releasedX, releasedY, checkerboardPane);
			if (unitAtPath != null) {
				if (unitAtPath.getColor() == currentUnit.getColor()) { // If unit at path is the player's
					// own unit
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean queenLogic(Unit currentUnit, int releasedX, int releasedY) {
		int x_movement = releasedX - currentUnit.getX();
		int y_movement = releasedY - currentUnit.getY();
		if (x_movement != 0 && y_movement == 0 || x_movement == 0 && y_movement != 0) {
			return rookLogic(currentUnit, releasedX, releasedY);
		} else if (Math.abs(x_movement) == Math.abs(y_movement)) {
			return bishopLogic(currentUnit, releasedX, releasedY);
		} else {
			return false;
		}
	}
}
