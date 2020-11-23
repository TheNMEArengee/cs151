package Controller;

import entity.Piece.ChessPiece;
import entity.PieceType;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import view.ChessBoard;
import view.ChessPane;

import java.util.Stack;

public class ReleaseAction implements EventHandler<MouseEvent> {
    private ChessPane chessPane;
    static Stack<ChessPiece> stack = new Stack<>();

    public ReleaseAction(ChessPane chessPane) {
        this.chessPane = chessPane;
    }

    @Override
    public void handle(MouseEvent e) {
        chessPane.drawBoard();
        ChessBoard chessBoard = chessPane.getChessBoard();
        int x = (int) ((e.getX() - chessBoard.getStartX()) / (chessBoard.getCellLength()));
        int y = (int) ((e.getY() - chessBoard.getStartY()) / (chessBoard.getCellLength()));

        for (ChessPiece o : chessPane.getChessPieces()) {
            if (o.isSelected()) {
                if (chessBoard.getCurrSide()==o.getSide()){
                    if(o.getMoveStrategy().move(x, y,chessPane.getChessPieces())){
                        if(judgeGame(x,y)){
                            printTip(o.getSide());
                        }
                        eatPiece(x,y,o.getSide());
                        stack.push((ChessPiece) o.clone());
                        System.out.println(o.getCol()+" "+o.getRow()+" "+x+" "+y);
                        if(o.getRow()!=y&&o.getCol()!=x){
                            chessBoard.changeSide();
                        }
                        o.setCol(x);
                        o.setRow(y);
                    }

                }
                o.setSelected(false);
                break;
            }

        }

        chessPane.drawPiece();
    }

    public void  eatPiece(int x,int y,char side){
        chessPane.getChessPieces().removeIf(e->{
            if(e.getCol()==x&&e.getRow()==y&&e.getSide()!=side){
                stack.push(e);
                return true;
            }
            return false;
        });
    }

    public boolean judgeGame(int x,int y){
        for(ChessPiece e:chessPane.getChessPieces()){
            if(e.getCol()==x&&e.getRow()==y&&(
                    e.getType()== PieceType.KINGBLACK||e.getType()== PieceType.KINGWHITE))
                return true;
        }

        return false;
    }

    public void printTip(char side){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText((side=='B'?"黑":"白")+"方取得胜利");
        alert.setTitle("游戏结束");
        alert.showAndWait();
    }


}
