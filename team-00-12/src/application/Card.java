package application;

import java.util.ArrayList;

import application.CardContainers.Effect;
import javafx.scene.image.Image;

public class Card {
	private Affiliation affiliation;
	private boolean selected;
	private int movementTypeID;
	private String title;
	private String description;
	private Image image;
	
	
		// Constructor
		public Card(Affiliation affiliation, int movementTypeID) {
			this.affiliation = affiliation;
			selected = false;
			this.movementTypeID = movementTypeID;
			ArrayList<String> effects = Effect.getEffect(movementTypeID);
			this.title = effects.get(0);
			this.description = effects.get(1);
			this.image = new Image(effects.get(2), 50, 50, true, true);
		}
	
		
		/* Get and Set methods */
		public Affiliation getAffiliation() {
			return affiliation;
		}
		
		public void setAffiliation(Affiliation affiliation) {
			this.affiliation = affiliation;
		}
		
		public int getMovementTypeID() {
			return this.movementTypeID;
		}

		public void setSelected(boolean b) {
			this.selected = b;
		}

		public boolean isSelected() {
			return this.selected;
		}
		
		public String getTitle() {
			return this.title;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		public Image getImage() {
			return this.image;
		}
}
