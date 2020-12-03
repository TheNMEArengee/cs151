package application.GameBoard;

//Constructs the Checkerboard battlefield
public class Checkerboard {
	static Checkerboard checkerboard = null;
	private int rows;
	private int cols;
	private int tileSize; // Size of each tile on checker board
	private int cardSizeX;
	private int cardSizeY;
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
		this.cardSizeX = 60;
		this.cardSizeY = 120;
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
	
	public int getCardSizeX() {
		return this.cardSizeX;
	}
	
	public int getCardSizeY() {
		return this.cardSizeY;
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
}
