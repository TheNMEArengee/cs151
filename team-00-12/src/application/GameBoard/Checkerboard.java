package application.GameBoard;

//Constructs the Checkerboard battlefield
public class Checkerboard {
	static Checkerboard checkerboard = null;
	private int rows;
	private int cols;
	private int tileSize; // Size of each tile on checker board
	private int currPlayer; // Player 0 or 1

	// initX and initY supposed to be used to move the drawn board
	// But does not work for now, so set both to 0.
	private int initX;
	private int initY;

	// Private Constructor, only one instance allowed
	private Checkerboard() {
		this.rows = 8;
		this.cols = 8;
		this.tileSize = 60;
		this.currPlayer = 0;
		this.initX = 0;
		this.initY = 0;
	}

	// Get that one instance of Checkerboard
	public static Checkerboard getInstance() {
		if (checkerboard == null) {
			checkerboard = new Checkerboard();
		}
		return checkerboard;
	}

	/* When a player moves, switch turn to other player */
	public void changePlayerTurn() {
		this.currPlayer = (this.currPlayer == 0 ? 1 : 0);
	}

	/* Get and Set methods */
	public int getCols() {
		return this.cols;
	}

	public int getRows() {
		return this.rows;
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public int getCurrPlayer() {
		return this.currPlayer;
	}

	public void setCurrentPlayer(int playerNum) {
		if (playerNum == 0 || playerNum == 1)
			this.currPlayer = playerNum;
	}

	public int getEnemyPlayer() {
		return this.currPlayer == 0 ? 1 : 0;
	}

	public String getCurrPlayerToString() {
		return this.currPlayer == 0 ? "White" : "Black";
	}

	public String getEnemyPlayerToString() {
		return this.currPlayer == 0 ? "Black" : "White";
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

	/* Old Code */
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

}
