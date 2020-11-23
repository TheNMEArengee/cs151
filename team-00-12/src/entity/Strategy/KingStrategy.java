package entity.Strategy;

import entity.Piece.ChessPiece;

import java.util.Set;

public class KingStrategy implements MoveStrategy {
    private int curX;
    private int curY;

    public KingStrategy(int curX, int cuY) {
        this.curX = curX;
        this.curY = cuY;
    }

    @Override
    public boolean move(int x, int y, Set<ChessPiece> chessPieces) {
        if(Math.abs(curX-x)<=1&&Math.abs(curY-y)<=1){
            curX = x;
            curY = y;
            return true;
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
