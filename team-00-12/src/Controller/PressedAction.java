package Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import view.ChessBoard;
import view.ChessPane;


public class PressedAction implements EventHandler<MouseEvent> {
    private ChessPane chessPane;


    public PressedAction(ChessPane chessPane) {
        this.chessPane = chessPane;
    }

    @Override
    public void handle(MouseEvent e) {
        ChessBoard chessBoard = chessPane.getChessBoard();
        int x=(int)((e.getX()-chessBoard.getStartX())/(chessBoard.getCellLength()));
        int y=(int)((e.getY()-chessBoard.getStartY())/(chessBoard.getCellLength()));


        chessPane.getChessPieces().forEach(o->{
            if(o.getCol()==x&&o.getRow()==y){
                o.setSelected(true);
                chessPane.drawPiece();
            }
        });
    }

}
