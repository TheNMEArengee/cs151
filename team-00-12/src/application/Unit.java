package application;

// Game pieces, aka Units
public class Unit {
	private int x;
	private int y;
	private int player;
	private int role;
	private String color;
	private boolean selected;

	// Constructor
	public Unit(int x, int y, int player, int role) {
		this.x = x;
		this.y = y;
		this.player = player;
		this.color = (this.player == 0) ? "White" : "Black"; // 0 = White, 1 = Black
		this.role = role; //0 = Pawn, 1 = King
		selected = false;
	}

	/* Get and Set methods */
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayer() {
		return this.player;
	}

	public String getColor() {
		return this.color;
	}

	public String getEnemyColor() {
		if (this.player == 0) {
			return "Black";
		} else if (this.player == 1) {
			return "White";
		}
		return "Error";
	}
	
	public boolean isKing() {
		return this.role == 1 ? true : false;
	}
	
	public int getRole() {
		return this.role;
	}

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public boolean isSelected() {
		return this.selected;
	}
}
