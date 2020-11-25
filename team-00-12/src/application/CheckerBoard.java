package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


//Constructs the checker board
public class CheckerBoard {
	static CheckerBoard checkerBoard = null;
	private int rows;
	private int cols;
	private int tileSize;
	private int initX;
	private int initY;
	private int currPlayer;
	private Rectangle[][] pieces;
	
	private CheckerBoard() {
		this.rows = 8;
		this.cols = 8;
		this.tileSize = 60;
		this.initX = 0;
		this.initY = 0;
		this.currPlayer = 0;
		this.pieces = new Rectangle[this.rows][this.cols];
	}
	
	public static CheckerBoard getInstance() {
		if(checkerBoard == null) {
			checkerBoard = new CheckerBoard();
		}
		return checkerBoard;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getTileSize() {
		return this.tileSize;
	}
	
	public int getInitX() {
		return this.initX;
	}
	
	public void setInitX(int x) {
		this.initX = x;
	}
	
	public int getInitY() {
		return this.initY;
	}
	
	public void setInitY(int y) {
		this.initY = y;
	}
	
	public int getCurrPlayer() {
		return this.currPlayer;
	}
	
	public void changePlayerTurn() {
		this.currPlayer = (this.currPlayer == 0 ? 1 : 0);
	}
	
	public Rectangle[][] getPieces(){
		return this.pieces;
	}
	
	public void setPiece(int x, int y, Rectangle r) {
		this.pieces[x][y] = r;
	}
	
	
//		Group tilesGroup = new Group();
//		
//		//Chess board container
//		GridPane board = new GridPane();
//
//		Rectangle[][] placementBoard = new Rectangle[Main.WIDTH][Main.HEIGHT];
//		
//		board.setPrefSize((Main.WIDTH * Main.TILE_SIZE) , (Main.HEIGHT * Main.TILE_SIZE) );
//		board.getChildren().addAll(tilesGroup);
//		
//		
//		for(int y=2; y<10; y++) {
//			for(int x=2; x<10; x++) {
//				final int coordX = x-2;
//				final int coordY = y-2;
//				placementBoard[x][y] = new Rectangle();
//				placementBoard[x][y].setWidth(Main.TILE_SIZE);
//				placementBoard[x][y].setHeight(Main.TILE_SIZE);
//				placementBoard[x][y].setStroke(Color.TRANSPARENT);
//				if((x+y)%2 == 0) {
//					placementBoard[x][y].setFill(Color.BLACK);
//				}
//				else {
//					placementBoard[x][y].setFill(Color.WHITE);
//				}
//				placementBoard[x][y].setStrokeType(StrokeType.INSIDE);
//				placementBoard[x][y].setStrokeWidth(1);
//				placementBoard[x][y].relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE);
//				placementBoard[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
//					System.out.println("("+ coordX +", "+ coordY+")");
//				});
//				tilesGroup.getChildren().add(placementBoard[x][y]);
//			}
//		}	
//
//		
//		//Create an 8 x 8 chess board and store it into tilesGroup
//		for (int y = 0; y < Main.HEIGHT; y++) {
//			for (int x = 0; x < Main.WIDTH; x++) {
//				Tile tile = new Tile((x + y) % 2 == 0, x, y);
//				tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
//					System.out.println("Hi: " + e);
//				});
//				board.setAlignment(Pos.CENTER);
//			}
//		}
		
	
	public void test() {
		System.out.println("hi there");
		
	}

}
