package application.CardContainers;

import java.util.ArrayList;

import application.Elements.Affiliation;
import application.Elements.Card;

public class Hand {
	private ArrayList<Card> hand;
	private int handSize;
	private Affiliation affiliation;

	public Hand(Affiliation affiliation) {
		this.hand = new ArrayList<Card>();
		this.handSize = 5;
		this.affiliation = affiliation;
	}

	// Returns the hand
	public ArrayList<Card> getHand() {
		return hand;
	}

	// Add a card to the hand
	public void addCard(Card c) {
		if (hand.size() < handSize) {
			c.setAffiliation(affiliation);
			hand.add(c);
		}
	}

	// Add the set of cards to the hand
	public void addAll(ArrayList<Card> addMe) {
		if (hand.size() + addMe.size() <= handSize) {
			for (Card c : addMe) {
				c.setAffiliation(affiliation);
			}
			hand.addAll(addMe);
		}
	}

	// Removes the card from the hand
	public Card removeCard(Card c) {
		int cardIndex = hand.indexOf(c);
		Card cardInHand = hand.get(hand.indexOf(c));
		cardInHand.setAffiliation(Affiliation.DECK);
		hand.remove(cardIndex);
		return cardInHand;
	}

	// Return hand affiliation
	public Affiliation getAffiliation() {
		return affiliation;
	}

	// Sets the selected state of all the cards in the hand to null
	public void resetSelectedCards() {
		this.hand.forEach((c) -> {
			c.setSelected(false);
		});
	}
}
