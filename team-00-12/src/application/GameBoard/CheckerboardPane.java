package application.GameBoard;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import application.Unit;
import application.CardContainers.Deck;
import application.CardContainers.Effect;
import application.CardContainers.Hand;
import javafx.scene.control.Label;
import application.Affiliation;
import application.Card;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Checkerboard field's controller basically
public class CheckerboardPane extends Pane {
	private Set<Unit> units; // Player's pawns, or units
	private Hand player0Hand; // Player 0's hand
	private Hand player1Hand; // Player 1's hand
	private Deck deck; // Shared player deck
	private Checkerboard checkerboard; // Checkerboard instance
	private GridPane checkerboardGridPane; // Grid to place the checkerboard
	private Group tileGroup; // For checkerboard tiles
	private Canvas canvas;
	private GraphicsContext gc;

	// Constructor for CheckerboardPane
	public CheckerboardPane(Checkerboard checkerboard) {
		this.checkerboard = checkerboard;
		this.checkerboardGridPane = new GridPane();
		tileGroup = new Group();
		checkerboardGridPane.getChildren().add(tileGroup);
		// checkerboardGridPane.getChildren().add(cardGroup);
		this.units = new HashSet<>();
		this.player0Hand = new Hand(Affiliation.WHITE);
		this.player1Hand = new Hand(Affiliation.BLACK);
		this.deck = new Deck();
		setUnits();
		setCards();
		canvas = new Canvas(800,480);
		gc = canvas.getGraphicsContext2D();
		draw();
	}

	// Place the player units onto board (init/reset)
	public void setUnits() {
		// Units for player 0
		int player = 0;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 8; x++) {
				if (x == 4 && y == 0) { // Add king
					units.add(new Unit(x, y, player, 5));
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
				if (x == 4 && y == 7) { // Add king
					units.add(new Unit(x, y, player, 5));
				}
				else { //Add pawn
					units.add(new Unit(x, y, player, 0));
				}
			}
		}
	}

	// Place the player units onto board (init/reset)
	public void setCards() {
		ArrayList<Card> hand0 = new ArrayList<Card>();
		ArrayList<Card> hand1 = new ArrayList<Card>();
		ArrayList<Card> deck = new ArrayList<Card>();
		Random rand = new Random();

		// Cards in player 0 hand
		for (int numOfCards = 0; numOfCards < 5; numOfCards++) {
			hand0.add(new Card(Affiliation.WHITE, rand.nextInt(5)));
		}

		// Cards in player 1 hand
		for (int numOfCards = 0; numOfCards < 5; numOfCards++) {
			hand1.add(new Card(Affiliation.BLACK, rand.nextInt(5)));
		}

		// Cards in deck
		for (int numOfCards = 0; numOfCards < 21; numOfCards++) {
			deck.add(new Card(Affiliation.DECK, rand.nextInt(5)));
		}

		player0Hand.addAll(hand0);
		player1Hand.addAll(hand1);
		this.deck.addAll(deck);
	}

	// Used in ctor to draw and show everything
	public void draw() {
		drawBoard();
		drawUnits();
		drawCards();
		getChildren().add(checkerboardGridPane);
		getChildren().add(canvas);
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
			// if (u.isSelected()) {
			// System.out.println("Selected: " + u.getX() + ", " + u.getY());
			// }

			// Rectangle(x coord, y coord, width, height)
			int x = 10 + (u.getX() * tileSize);
			int y = 10 + (u.getY() * tileSize);
			Rectangle r = new Rectangle(x, y, 40, 40);

			// Rounded edges for units
			r.setArcWidth(20);
			r.setArcHeight(20);

			// Determine color of pieces, check which player the unit belongs to
			if (u.getPlayer() == 0) {
				if (u.getRole() == 5) { // King
					r.setFill(Color.rgb(235, 0, 27));
				} else { // Pawn
					r.setFill(Color.WHITE);
				}
				r.setStroke(Color.BLACK);
			} else {
				if (u.getRole() == 5) { // King
					r.setFill(Color.rgb(247, 158, 27));
				} else { // Pawn
					r.setFill(Color.GREY);
				}
				r.setStroke(Color.BLACK);
			}

			tileGroup.getChildren().add(r);
		});
	}

	// Draw the cards using the "cards" set that was initialized in setCards()
	public void drawCards() {
		drawPlayer0Hand();
		drawPlayer1Hand();
		drawDeck();
	}

	//Draws player 0 hand
	public void drawPlayer0Hand() {
		// Draw player 0 hand
		int tileSize = checkerboard.getTileSize();
		int cardSizeX = checkerboard.getCardSizeX();
		int cardSizeY = checkerboard.getCardSizeY();


		for (Card c : player0Hand.getHand()) {
			int handPosition = player0Hand.getHand().indexOf(c);
			int x = 10 + (tileSize * 8) + (handPosition * cardSizeX);
			int y = 10;

			Rectangle r = new Rectangle(x, y, cardSizeX, cardSizeY);

			// Rounded edges for units
			r.setArcWidth(20);
			r.setArcHeight(20);

			// Determine color of pieces, check which player the unit belongs to
			r.setFill(Color.WHITE);
			r.setStroke(Color.BLACK);
			tileGroup.getChildren().add(r);

			//Generating card text for player 0 cards
			Text t = new Text(x + 10, y + 20, c.getTitle() + "");
			tileGroup.getChildren().add(t);

			//Generating images for player 1 cards
			Image i = c.getImage();
			gc.drawImage(i, x + 5, y + 30);
		}
	}

	//Draws player 1 hand
	public void drawPlayer1Hand() {
		int tileSize = checkerboard.getTileSize();
		int cardSizeX = checkerboard.getCardSizeX();
		int cardSizeY = checkerboard.getCardSizeY();


		for (Card c : player1Hand.getHand()) {
			int handPosition = player1Hand.getHand().indexOf(c);
			int x = 10 + (tileSize * 8) + (handPosition * cardSizeX);
			int y = 10 + (tileSize * 6);

			Rectangle r = new Rectangle(x, y, cardSizeX, cardSizeY);

			// Rounded edges for units
			r.setArcWidth(20);
			r.setArcHeight(20);

			// Determine color of pieces, check which player the unit belongs to
			r.setFill(Color.GREY);
			r.setStroke(Color.BLACK);
			tileGroup.getChildren().add(r);

			Text t = new Text(x + 10, y + 20, c.getTitle() + "");
			tileGroup.getChildren().add(t);

			Image i = c.getImage();
			gc.drawImage(i, x + 5, y + 30);
		}
	}

	//Draws the deck
	public void drawDeck() {
		int tileSize = checkerboard.getTileSize();
		int cardSizeX = checkerboard.getCardSizeX();
		int cardSizeY = checkerboard.getCardSizeY();


		//Deck rectangle
		Rectangle deck = new Rectangle(10 + (tileSize * 8), 10 + (tileSize * 3), cardSizeX, cardSizeY);
		deck.setFill(Color.AQUA);
		deck.setStroke(Color.BLACK);
		deck.setArcWidth(20);
		deck.setArcHeight(20);
		tileGroup.getChildren().add(deck);

		//Deck text
		Text deckText = new Text(10 + (tileSize * 8) + 10, 10 + (tileSize * 3) + 20, "Deck");
		tileGroup.getChildren().add(deckText);

		//Deck Image
		Image i = new Image("img/pileofshitpawn.png", 50, 50, true, true);
		gc.drawImage(i, 10 + (tileSize * 8) + 5, 10 + (tileSize * 3) + 30);
	}

	//Draws the card that is hovered over by the mouse
	public void drawEnlargedCard(Card c) {
		int ID = c.getMovementTypeID();
		Affiliation affiliation = c.getAffiliation();
		ArrayList<String> cardDetails = Effect.getEffect(ID);


		//Rectangle
		Rectangle r = new Rectangle(10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()), 135, checkerboard.getCardSizeX() * 2, checkerboard.getCardSizeY() * 2 - 10);
		if(affiliation == Affiliation.WHITE) {
			r.setFill(Color.WHITE);
		}
		else {
			r.setFill(Color.GREY);
		}
		r.setStroke(Color.BLACK);


		// Rounded edges for units
		r.setArcWidth(20);
		r.setArcHeight(20);
		tileGroup.getChildren().add(r);


		//Title text
		Text title = new Text(10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 20, 175, cardDetails.get(0));
		title.setUnderline(true);
		title.setFont(new Font("Happy Monkey", 30));
		tileGroup.getChildren().add(title);


		//Image
		Image image = new Image(cardDetails.get(2), 100, 100, true, true);
		gc.drawImage(image, 10 + (checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 10, 182);


		//Description text
		Text description = new Text((checkerboard.getTileSize() * 8) + (3 * checkerboard.getCardSizeX()) + 20, 300, cardDetails.get(1));
		description.setWrappingWidth(100);
		tileGroup.getChildren().add(description);
	}

	//Gets the card at the given x, y position
	public Card getCardAt(int x, int y) {
		Card c = null;
		int tileSize = checkerboard.getTileSize();
		int leftMostCardRegion = (tileSize * 8) + 10;
		int rightMostCardRegion = leftMostCardRegion + (checkerboard.getCardSizeX() * 5);
		int whiteCardRegionTop = 10;
		int whiteCardRegionBottom = whiteCardRegionTop + checkerboard.getCardSizeY();
		int blackCardRegionTop = 10 + (tileSize * 6);;
		int blackCardRegionBottom = blackCardRegionTop + checkerboard.getCardSizeY();


		//If the x area is within the card area
		if(x >= leftMostCardRegion && x < rightMostCardRegion) {
			//If the y coordinate is in the white card region, retrieve and return the card at that position
			if(y >= whiteCardRegionTop && y <= whiteCardRegionBottom) {
				int cardIndex = (x - leftMostCardRegion) / checkerboard.getCardSizeX();
				c = player0Hand.getHand().get(cardIndex);
			}
			//Else, if the y coordinate is in the black card region, retrieve and return the card at that position
			else if(y >= blackCardRegionTop && y <= blackCardRegionBottom) {
				int cardIndex = (x - leftMostCardRegion) / checkerboard.getCardSizeX();
				c = player1Hand.getHand().get(cardIndex);
			}
		}


		//Return null if no card is found
		return c;
	}

	/* Get and set methods */
	public Checkerboard getCheckerBoard() {
		return this.checkerboard;
	}

	//Returns the set of units
	public Set<Unit> getUnits() {
		return units;
	}

	//Returns the graphics context
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	//
	public void updateTurnPlayer(Label label) {
		
	}
	
}
