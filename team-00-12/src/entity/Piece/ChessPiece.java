package entity.Piece;

import entity.PieceType;
import entity.Strategy.MoveStrategy;

import java.util.Objects;

public class ChessPiece implements Cloneable{
    private int id;
    private PieceType type;
    private MoveStrategy moveStrategy;
    private boolean selected;
    private int row;
    private int col;
    private char side;
    private static int auto_id = 0;


    public ChessPiece(PieceType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
        selected = false;
        side = type.getDesc().endsWith("Black")?'B':'W';
        this.id = ++auto_id;
    }

    public Object clone(){
        Object obj = null;
        try{
            obj = super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }

        return obj;
    }
    public PieceType getType() {
        return type;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy){
        this.moveStrategy = moveStrategy;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "id=" + id +
                ", type=" + type +
                ", row=" + row +
                ", col=" + col +
                ", side=" + side +
                '}';
    }
}
