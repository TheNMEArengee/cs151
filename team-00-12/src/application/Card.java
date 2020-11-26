package application;

public class Card {
	private Affiliation affiliation;
	private boolean selected;
	private int movementTypeID;
	
	
		// Constructor
		public Card(Affiliation affiliation, int movementTypeID) {
			this.affiliation = affiliation;
			selected = false;
			this.movementTypeID = movementTypeID;
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
}
