package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


//Constructs the checker board
public class Board extends Pane{
	private static Rectangle[][] placementBoard = new Rectangle[Main.WIDTH][Main.HEIGHT];
	private static Image[][] images;
	private static ImageView[][] imageviews;


	public Board() {
		Group tilesGroup = new Group();


		//Chess board container
		GridPane board = new GridPane();

		

		board.setPrefSize((Main.WIDTH * Main.TILE_SIZE) , (Main.HEIGHT * Main.TILE_SIZE) );
		board.getChildren().addAll(tilesGroup);


		for(int y=2; y<10; y++) {
			for(int x=2; x<10; x++) {
				final int coordX = x-2;
				final int coordY = y-2;
				placementBoard[x][y] = new Rectangle();
				placementBoard[x][y].setWidth(Main.TILE_SIZE);
				placementBoard[x][y].setHeight(Main.TILE_SIZE);
				placementBoard[x][y].setStroke(Color.TRANSPARENT);
				if((x+y)%2 == 0) {
					placementBoard[x][y].setFill(Color.BLACK);
				}
				else {
					placementBoard[x][y].setFill(Color.WHITE);
				}
				placementBoard[x][y].setStrokeType(StrokeType.INSIDE);
				placementBoard[x][y].setStrokeWidth(1);
				placementBoard[x][y].relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE);
				placementBoard[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
					System.out.println("("+ coordX +", "+ coordY+")");
				});
				tilesGroup.getChildren().add(placementBoard[x][y]);
			}
		}	


		//Create an 8 x 8 chess board and store it into tilesGroup
		for (int y = 0; y < Main.HEIGHT; y++) {
			for (int x = 0; x < Main.WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
					System.out.println("Hi: " + e);
				});
				board.setAlignment(Pos.CENTER);
			}
		}


		// New image array
		images = new Image[8][8];

		// first row of renders (black)
		for(int row = 0; row < 8; row++)
		{
			for(int column = 0; column < 2; column++)
			{
				images[row][column] = new Image("img/pileofshitpawn.png"); 
			}
		}
		
		
//		images[7][0] = new Image("img/pileofshitpawn.png"); 
//		images[6][0] = new Image("img/pileofshit.png");
//		images[5][0] = new Image("img/pileofshit.png"); 
//		images[4][0] = new Image("img/pileofshit.png");
//		images[3][0] = new Image("img/pileofshit.png"); 
//		images[2][0] = new Image("img/pileofshit.png");
//		images[1][0] = new Image("img/pileofshit.png"); 
//		images[0][0] = new Image("img/pileofshit.png");
//		// second row (black)
//		images[7][1] = new Image("img/pileofshit.png"); 
//		images[6][1] = new Image("img/pileofshit.png");
//		images[5][1] = new Image("img/pileofshit.png"); 
//		images[4][1] = new Image("img/pileofshit.png");
//		images[3][1] = new Image("img/pileofshit.png"); 
//		images[2][1] = new Image("img/pileofshit.png");
//		images[1][1] = new Image("img/pileofshit.png"); 
//		images[0][1] = new Image("img/pileofshit.png");

		// Viewers for each image
		imageviews = new ImageView[8][8];

		// Initializes imageviewers and windows
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				imageviews[x][y] = new ImageView();
			}
		}

		// Puts images into imageviewers
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				imageviews[x][y].setImage(images[x][y]);
				imageviews[x][y].setFitWidth(5);
				imageviews[x][y].setFitHeight(8);
				imageviews[x][y].setPreserveRatio(true);
				imageviews[x][y].setSmooth(true);
				imageviews[x][y].setCache(true);	
				imageviews[x][y].setTranslateX(placementBoard[x + 2][y + 2].getWidth() / 8);	
			}
		}
//		//initialize the board: background, data structures, inital layout of pieces
//		Piece[][] pieces = new Piece[boardWidth][boardHeight];
		// Places background squares
	}
	
	
	public void placeboard(final int i, final int j){
		getChildren().add(placementBoard[i][j]);
	}
	
	
	public void placeimages(final int i, final int j){
		getChildren().addAll(imageviews[i][j]);
	}

}
