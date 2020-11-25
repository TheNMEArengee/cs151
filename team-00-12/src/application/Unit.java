package application;

public class Unit {
	
	private int x;
	private int y;
	private int player;
	private boolean selected;
	
	public Unit(int x, int y, int player) {
		this.x = x;
		this.y = y;
		this.player = player;
		selected = false;
	}
	
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
		return player;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean b) {
		this.selected = b;
	}
}
