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
	/* get the y coordinate */
	public int getY() {
		return this.y;
	}
	/*Sets the current X coordinate to the new X coordinate*/
	public void setX(int x) {
		this.x = x;
	}
	/*Sets the current Y coordinate to the new Y coordinate*/
	public void setY(int y) {
		this.y = y;
	}
	/*Get the player; returns either a 0 or 1*/
	public int getPlayer() {
		return this.player;
	}
	/*Get the color of the player; returns a string*/
	public String getColor() {
		return this.color;
	}
	/*returns the color of the opponent; Uses 1 or 0 to determine the opposing player */
	public String getEnemyColor() {
		if (this.player == 0) {
			return "Black";
		} else if (this.player == 1) {
			return "White";
		}
		return "Error";
	}
	
	/*A boolean that returns a true or false when determining a king; 
	 *It is based off the role of the current unit */
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
