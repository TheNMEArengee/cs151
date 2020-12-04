package application.EventHandlers;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import application.Affiliation;
import application.Card;
import application.CardContainers.Effect;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.event.EventHandler;

public class MouseMoved implements EventHandler<MouseEvent> {
	private CheckerboardPane checkerboardPane;
	private Checkerboard checkerboard;


	//Constructor
	public MouseMoved(CheckerboardPane checkerboardPane) {
		this.checkerboardPane = checkerboardPane;
		this.checkerboard = checkerboardPane.getCheckerBoard();
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
			drawEnlargedCard(c);
		}
	}


	// Draws the card that is hovered over by the mouse
	// For MouseMoved class
	private void drawEnlargedCard(Card c) {
		int ID = c.getMovementTypeID();
		Affiliation affiliation = c.getAffiliation();
		ArrayList<String> cardDetails = Effect.getEffect(ID);
		Group tileGroup = checkerboardPane.getTileGroup();
		GraphicsContext gc = checkerboardPane.getGraphicsContext();

		// Rectangle
		Rectangle r = new Rectangle(10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()), 135,
				checkerboard.getCardSizeX() * 2, checkerboard.getCardSizeY() * 2 - 10);
		if (affiliation == Affiliation.WHITE) {
			r.setFill(Color.WHITE);
		} else {
			r.setFill(Color.GREY);
		}
		r.setStroke(Color.BLACK);

		// Rounded edges for units
		r.setArcWidth(20);
		r.setArcHeight(20);
		tileGroup.getChildren().add(r);

		// Title text
		Text title = new Text(10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 20, 175,
				cardDetails.get(0));
		title.setUnderline(true);
		title.setFont(new Font("Happy Monkey", 30));
		tileGroup.getChildren().add(title);

		// Image
		Image image = new Image(cardDetails.get(2), 100, 100, true, true);
		gc.drawImage(image, 10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 10, 182);

		// Description text
		Text description = new Text((checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 20, 300,
				cardDetails.get(1));
		description.setWrappingWidth(100);

		tileGroup.getChildren().add(description);
	}
}
