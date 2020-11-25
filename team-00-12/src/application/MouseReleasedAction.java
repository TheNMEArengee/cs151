package application;

import entity.Piece.ChessPiece;
import entity.PieceType;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import view.ChessBoard;
import view.ChessPane;

public class MouseReleasedAction implements EventHandler<MouseEvent> {
    private CheckerBoardPane cp;

    public MouseReleasedAction(CheckerBoardPane cp) {
        this.cp = cp;
    }

    @Override
    public void handle(MouseEvent e) {
    	cp.drawBoard();
        CheckerBoard cb = cp.getCheckerBoard();
        int x = (int) ((e.getX() - cb.getInitX()) / (cb.getTileSize()));
        int y = (int) ((e.getY() - cb.getInitY()) / (cb.getTileSize()));

        for (Unit u : cp.getUnits()) {
            if (u.isSelected()) {
                if (cb.getCurrPlayer()==u.getPlayer()){
                	
//                    if(o.getMoveStrategy().move(x, y,chessPane.getChessPieces())){
//                        if(judgeGame(x,y)){
//                            printTip(o.getSide());
//                        }
//                        eatPiece(x,y,o.getSide());
//                        stack.push((ChessPiece) o.clone());
//                        System.out.println(o.getCol()+" "+o.getRow()+" "+x+" "+y);
//                        if(o.getRow()!=y&&o.getCol()!=x){
//                            chessBoard.changeSide();
//                        }
//                        u.setX(x);
//                        u.setY(y);
//                    }
                	u.setX(x);
                	u.setY(y);
                	System.out.println("Move to: " + x + ", "+ y);
                }
                u.setSelected(false);
                break;
            }

        }
        cp.drawUnits();
    }

    public void  eatPiece(int x,int y,char side){
//        chessPane.getChessPieces().removeIf(e->{
//            if(e.getCol()==x&&e.getRow()==y&&e.getSide()!=side){
//                stack.push(e);
//                return true;
//            }
//            return false;
//        });
    }

    public boolean judgeGame(int x,int y){
//        for(ChessPiece e:chessPane.getChessPieces()){
//            if(e.getCol()==x&&e.getRow()==y&&(
//                    e.getType()== PieceType.KINGBLACK||e.getType()== PieceType.KINGWHITE))
//                return true;
//        }

        return false;
    }

    public void printTip(char side){
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText((side=='B'?"é»‘":"ç™½")+"æ–¹å�–å¾—èƒœåˆ©");
//        alert.setTitle("æ¸¸æˆ�ç»“æ�Ÿ");
//        alert.showAndWait();
    }


}
