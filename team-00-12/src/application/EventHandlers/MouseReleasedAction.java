package application.EventHandlers;

import application.Unit;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

// Used for unit movement (moving the selected unit to a tile)
public class MouseReleasedAction implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;
	private Stage primaryStage;
	private Scene welcomeScene;

	// Constructor
	public MouseReleasedAction(CheckerboardPane checkerboardPane, Stage primaryStage, Scene welcomeScene) {
		this.checkerboardPane = checkerboardPane;
		this.primaryStage = primaryStage;
		this.welcomeScene = welcomeScene;
	}

	// Main Handler
	@Override
	public void handle(MouseEvent e) {
		checkerboardPane.drawBoard();
		Checkerboard checkerboard = checkerboardPane.getCheckerBoard();
		int tileSize = checkerboard.getTileSize();

		// Location of mouse release, translated to grid
		int releasedX = (int) ((e.getX() - checkerboard.getInitX()) / (tileSize));
		int releasedY = (int) ((e.getY() - checkerboard.getInitY()) / (tileSize));

		boolean validMove = true;
		Unit unitToRemove = null;

		// Check all units to see which one is selected, then determine validity of
		// movement with game logic
		for (Unit u : checkerboardPane.getUnits()) {
			if (u.isSelected()) { // unit is selected
				if (checkerboard.getCurrPlayer() == u.getPlayer()) { // unit moved belongs to current player
					// If the player did not just click and release on the same tile
					if (!((u.getX() == releasedX) && (u.getY() == releasedY))) {
						// Means they moved, set the unit's location to new coordinates
						if (releasedX < 8 && releasedY < 8) { // If in bounds
							Unit unitAtReleasedCoords = unitExistsAtCoords(releasedX, releasedY, checkerboardPane);
							switch (u.getRole()) {
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

							if (unitAtReleasedCoords != null) { // Check if unit exists at coords that unit wants to
																// move to
								// Check the unit's player
								if (unitAtReleasedCoords.getPlayer() == u.getPlayer()) { // If own player's unit,
																							// invalid
																							// move
									System.out.println("Invalid move: Player's own unit exists at destination.");
									validMove = false;
								} else { // Enemy unit, remove the enemy unit from the set
									if (unitAtReleasedCoords.isKing()) {
										System.out.println("Game Over!");
										primaryStage.setScene(welcomeScene);
										primaryStage.show();
										break;
									} else {
										if (validMove == false) {
											break;
										} else {
											System.out.println("Player " + checkerboard.getEnemyPlayer() + " ("
													+ checkerboard.getEnemyPlayerToString() + ")" + " unit down!");
										}
									}
									unitToRemove = unitAtReleasedCoords;
								}
							}
						} else { // Out of bounds
							validMove = false;
						}
						if (validMove) {
							u.setX(releasedX);
							u.setY(releasedY);
							System.out.println("Player " + checkerboard.getCurrPlayer() + " (" + u.getColor() + ")"
									+ " move to " + releasedX + ", " + releasedY + ".");
							checkerboard.changePlayerTurn();
						}
					} else {
						System.out.println("Invalid move: Drag only");
						u.setSelected(false); // Remove if you want clicking
						break;
					}
				} else {
					System.out.println("Invalid: Not your turn. Player " + checkerboard.getCurrPlayer() + " ("
							+ checkerboard.getCurrPlayerToString() + ")" + " turn.");
				}
				u.setSelected(false);
				System.out.println("Unselected Unit.");
				System.out.println("Player " + checkerboard.getCurrPlayer() + " ("
						+ checkerboard.getCurrPlayerToString() + ")" + " turn.");
			}
		}
		checkerboardPane.getUnits().remove(unitToRemove);
		checkerboardPane.drawUnits();
	}

	public Unit unitExistsAtCoords(int x, int y, CheckerboardPane cp) {
		Unit unitAtCoords = null;
		for (Unit u : cp.getUnits()) {
			if ((u.getX() == x) && (u.getY() == y)) {
				unitAtCoords = u;
			}
		}
		return unitAtCoords;
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
			System.out.println("getX: " + currentUnit.getX() + " released: " + releasedX);
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
		Unit unitAtPath;

		if (x_movement != 0 && y_movement == 0) { // Moving right/left
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
		} else if (y_movement != 0 && x_movement == 0) { // Moving forward/back
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

	/* Not used */
	public void eatPiece(int x, int y, char side) {
//        chessPane.getChessPieces().removeIf(e->{
//            if(e.getCol()==x&&e.getRow()==y&&e.getSide()!=side){
//                stack.push(e);
//                return true;
//            }
//            return false;
//        });
	}

	public boolean judgeGame(int x, int y) {
//        for(ChessPiece e:chessPane.getChessPieces()){
//            if(e.getCol()==x&&e.getRow()==y&&(
//                    e.getType()== PieceType.KINGBLACK||e.getType()== PieceType.KINGWHITE))
//                return true;
//        }

		return false;
	}

	public void printTip(char side) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText((side=='B'?"é»‘":"ç™½")+"æ–¹å�–å¾—èƒœåˆ©");
//        alert.setTitle("æ¸¸æˆ�ç»“æ�Ÿ");
//        alert.showAndWait();
	}
}
