package application.GameBoard;

import java.util.HashSet;
import java.util.Set;

import application.Unit;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

//Checkerboard field's controller basically
public class CheckerboardPane extends Pane {
	private Set<Unit> units; // Player's pawns, or units
	private Checkerboard checkerboard; // Checkerboard instance
	private GridPane checkerboardGridPane; // Grid to place the checkerboard
	private Group tileGroup; // For checkerboard tiles

	// Constructor for CheckerboardPane
	public CheckerboardPane(Checkerboard checkerboard) {
		this.checkerboard = checkerboard;
		this.checkerboardGridPane = new GridPane();
		tileGroup = new Group();
		checkerboardGridPane.getChildren().add(tileGroup);
		this.units = new HashSet<>();
		setUnits();
		draw();
	}

	// Place the player units onto board (init/reset)
	public void setUnits() {
		// Units for player 0
		int player = 0;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 8; x++) {
				if(x == 4 && y == 0) { // Add king
					units.add(new Unit(x, y, player, 2));
				}
				else { //Add pawn
					units.add(new Unit(x, y, player, 0));
				}
			}
		}

		// Units for Player 1
		player = 1;
		for (int y = 6; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if(x == 4 && y == 7) { //Add king
					units.add(new Unit(x, y, player, 2));
				}
				else { //Add pawn
					units.add(new Unit(x, y, player, 0));
				}
			}
		}
	}

	// Used in ctor to draw and show everything
	public void draw() {
		drawBoard();
		drawUnits();
		getChildren().add(checkerboardGridPane);
	}

	// Draw the checkerboard
	public void drawBoard() {
		int tileSize = checkerboard.getTileSize();
		// Nested for loop to draw 8x8 checkerboard
		// Using i, j since x, y used as coordinate variables
		for (int j = 0; j < checkerboard.getCols(); j++) { // 0-7
			for (int i = 0; i < checkerboard.getRows(); i++) { // 0-7
				// Rectangle(x coord, y coord, width, height)
				int x = checkerboard.getInitX() + (i * tileSize);
				int y = checkerboard.getInitY() + (j * tileSize);
				Rectangle r = new Rectangle(x, y, tileSize, tileSize);
				// Alternate Colors
				if ((i + j) % 2 == 0) {
					r.setFill(Color.rgb(232, 235, 239));
				} else {
					r.setFill(Color.rgb(125, 135, 150));
				}

				tileGroup.getChildren().add(r);
			}
		}
	}

	// Draw the units using the "units" set that was initialized in setUnits()
	public void drawUnits() {
		int tileSize = checkerboard.getTileSize();
		// Traverse entire "units" set
		units.forEach(u -> {
			// If we ever want to draw something to symbolize "Selected" visually
//			if (u.isSelected()) {
//				System.out.println("Selected: " + u.getX() + ", " + u.getY());
//			}

			// Rectangle(x coord, y coord, width, height)
			int x = 10 + (u.getX() * tileSize);
			int y = 10 + (u.getY() * tileSize);
			Rectangle r = new Rectangle(x, y, 40, 40);

			// Rounded edges for units
			r.setArcWidth(20);
			r.setArcHeight(20);

			// Determine color of pieces, check which player the unit belongs to
			if (u.getPlayer() == 0) {
				if(u.getRole() == 2) { // King
					r.setFill(Color.rgb(235, 0, 27));
				}
				else { // Pawn
					r.setFill(Color.WHITE);
				}
				r.setStroke(Color.BLACK);
			} else {
				if(u.getRole() == 2) { // King
					r.setFill(Color.rgb(247, 158, 27));
				}
				else { // Pawn
					r.setFill(Color.GREY);
				}
				r.setStroke(Color.BLACK);
			}

			tileGroup.getChildren().add(r);
		});
	}

	/* Get methods */
	public Checkerboard getCheckerBoard() {
		return this.checkerboard;
	}

	public Set<Unit> getUnits() {
		return units;
	}
}
