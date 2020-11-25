package application.EventHandlers;

import application.Unit;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.ChessBoard;
import view.ChessPane;
import entity.Piece.ChessPiece;
import javafx.scene.control.Alert;
import entity.PieceType;

// Used for unit movement (moving the selected unit to a tile)
public class MouseReleasedAction implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;

	// Constructor
	public MouseReleasedAction(CheckerboardPane checkerboardPane) {
		this.checkerboardPane = checkerboardPane;
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

		// Check all units to see which one is selected, then determine validity of
		// movement with game logic
		for (Unit u : checkerboardPane.getUnits()) {
			if (u.isSelected()) { // unit is selected
				if (checkerboard.getCurrPlayer() == u.getPlayer()) { // unit moved belongs to current player
					// If the player did not just click and release on the same tile
					if (!((u.getX() == releasedX) && (u.getY() == releasedY))) {
						// Means they moved, set the unit's location to new coordinates
						u.setX(releasedX);
						u.setY(releasedY);
						System.out.println(
								"Player " + checkerboard.getCurrPlayer() + " ("+u.getColor()+")"+ " move to " + releasedX + ", " + releasedY + ".");
						checkerboard.changePlayerTurn();
					} else {
						System.out.println("Invalid move u cuck");
						break;
					}
					System.out.println("Player " + checkerboard.getCurrPlayer() + " ("+u.getEnemyColor()+")"+" turn.");

					/* For reference */
//                  if(o.getMoveStrategy().move(x, y,chessPane.getChessPieces())){
//                  if(judgeGame(x,y)){
//                      printTip(o.getSide());
//                  }
//                  eatPiece(x,y,o.getSide());
//                  stack.push((ChessPiece) o.clone());
//                  System.out.println(o.getCol()+" "+o.getRow()+" "+x+" "+y);
//                  if(o.getRow()!=y&&o.getCol()!=x){
//                      chessBoard.changeSide();
//                  }
//                  u.setX(x);
//                  u.setY(y);
//              }

				} else {
					System.out.println("Invalid: Not your turn. Player " + checkerboard.getCurrPlayer() + " ("+u.getEnemyColor()+")"+" turn.");
				}
				u.setSelected(false);
			}
		}
		checkerboardPane.drawUnits();
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
