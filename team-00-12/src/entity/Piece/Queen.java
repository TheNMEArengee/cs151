package entity.Piece;

import entity.PieceType;
import entity.Strategy.QueenStrategy;

public class Queen extends ChessPiece {
    public Queen(PieceType type, int row, int col) {
        super(type, row, col);
        setMoveStrategy(new QueenStrategy(getCol(),getRow()));
    }
}
