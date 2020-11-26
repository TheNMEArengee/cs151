package application.EventHandlers;

import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import view.ChessBoard;
import view.ChessPane;

// Used for unit movement (selecting unit to move)
public class MousePressedAction implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;

	// Constructor
	public MousePressedAction(CheckerboardPane checkerboardPane) {
		this.checkerboardPane = checkerboardPane;
	}

	// Main handler
	@Override
	public void handle(MouseEvent e) {
		Checkerboard checkerboard = checkerboardPane.getCheckerBoard();
		int tileSize = checkerboard.getTileSize();

		// Location of mouse press, translated to grid
		int pressedX = (int) e.getX() / tileSize;
		int pressedY = (int) e.getY() / tileSize;

		// Traverse 'units' set in checkerboardPane to redraw/update units
		checkerboardPane.getUnits().forEach(unit -> {
			// If unit's coordinates match calculated mouse press coordinates
			if (unit.getX() == pressedX && unit.getY() == pressedY) {
				System.out.println("-----------------------------------");
				System.out.println("Selected Unit: " + unit.getX() + ", " + unit.getY() + ".");
//            	System.out.println("Pressed: " + pressedX + ", " + pressedY);
				unit.setSelected(true);
				checkerboardPane.drawUnits();
			}
		});
	}
}
