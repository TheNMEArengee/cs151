package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


//Helper class to aid in creating the chess board
public class Tile extends Rectangle{
	
	public Tile(boolean light, int x, int y)
	{
		setWidth(Main.TILE_SIZE);
		setHeight(Main.TILE_SIZE);
		
		
		//Create a tile at the given x and y values and color it black or white depending on position
		relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE);
		setFill(light ? Color.valueOf("#F8F8FF") : Color.valueOf("#000000"));
	}
}