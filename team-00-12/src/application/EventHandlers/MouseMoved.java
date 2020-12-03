package application.EventHandlers;

import javafx.scene.input.MouseEvent;
import application.Card;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;

public class MouseMoved implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;


	public MouseMoved(CheckerboardPane checkerboardPane) {
		this.checkerboardPane = checkerboardPane;
	}



	@Override
	public void handle(MouseEvent e) {
		// Location of the mouse
		int mouseX = (int) e.getX();
		int mouseY = (int) e.getY();
		
		
		//Get the card at the mouse's position
		Card c = checkerboardPane.getCardAt(mouseX, mouseY);
		
		//If the card exists, draw the enlarged version
		if(c != null) {
			checkerboardPane.drawEnlargedCard(c);
		}
	}
}
