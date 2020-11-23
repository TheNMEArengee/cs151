package entity.Strategy;

import entity.Piece.ChessPiece;

import java.util.Set;


public class QueenStrategy implements MoveStrategy {
    private int curX;
    private int curY;

    public QueenStrategy(int curX, int curY) {
        this.curX = curX;
        this.curY = curY;
    }

    @Override
    public boolean move (int x, int y, Set<ChessPiece> chessPieces) {
        if(Math.abs(x-curX)==Math.abs(y-curY)||!(x!=curX&&y!=curY)){
            if(isOverPiece(Math.min(curX,x),Math.min(curY,y),
                    Math.max(curX,x),Math.max(curY,y),chessPieces))
                return false;
            curX = x;
            curY = y;
            return true;
        }
        return false;
    }

    public boolean isOverPiece (int stX,int stY,int edX,int edY,Set<ChessPiece> chessPieces) {
        for(ChessPiece e:chessPieces){
            if(e.getRow()!=stY&&e.getCol()!=stX){
                return KnightStrategy.isOverPiece(stX,stY,edX,edY,chessPieces);
            }
            else{
                return CarStrategy.isOverPiece(stX,stY,edX,edY,chessPieces);
            }
        }
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
