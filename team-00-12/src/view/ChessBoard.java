package view;

public  class ChessBoard {
    static ChessBoard chessBoard = null;
    private int row;
    private int col;
    private double cellLength;
    private double startX;
    private double startY;
    private char currSide;

    private ChessBoard(double cellLength, double startX, double startY) {
        this.row = 8;
        this.col = 8;
        this.cellLength = cellLength;
        this.startX = startX;
        this.startY = startY;
        this.currSide = 'B';
    }

    public static ChessBoard getInstance(double cellLength, double startX, double startY){
        if(chessBoard == null)
            return new ChessBoard(cellLength,startX,startY);
        return chessBoard;
    }

    public ChessBoard getInstance(){
        return chessBoard;
    }

    public int getCol() {
        return col;
    }



    public int getRow() {
        return row;
    }

    public double getCellLength() {
        return cellLength;
    }

    public void changeSide(){
        currSide=(currSide=='B'?'W':'B');
    }

    public void setCellLength(double cellLength) {
        this.cellLength = cellLength;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public char getCurrSide() {
        return currSide;
    }
}
