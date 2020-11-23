package entity.Strategy;

import entity.Piece.ChessPiece;

import java.util.Set;

public class CarStrategy implements MoveStrategy {
    private int curX;
    private int curY;

    public CarStrategy() {
    }

    public CarStrategy(int curX, int curY) {
        this.curX = curX;
        this.curY = curY;
    }

    @Override
    public boolean move(int x, int y, Set<ChessPiece> chessPieces) {
        if(x!=curX&&y!=curY)
            return false;
        if(isOverPiece(Math.min(curX,x),Math.min(curY,y),
                Math.max(curX,x),Math.max(curY,y),chessPieces))
            return false;
        curX = x;
        curY = y;
        return true;
    }

    public  static boolean isOverPiece(int stX,int stY,int edX,int edY,Set<ChessPiece> chessPieces){
        for(ChessPiece e:chessPieces)
            if((e.getRow()>stY&&e.getRow()<edY)&&e.getCol()==stX||
                    (e.getCol()>stX&&e.getCol()<edX&&e.getRow()==stY))
                return true;
        return false;
    }


    public int getCurX() {
        return curX;
    }

    public void setCurX(int curX) {
        this.curX = curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurY(int curY) {
        this.curY = curY;
    }
}
