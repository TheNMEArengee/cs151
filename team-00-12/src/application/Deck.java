package application;

import java.util.ArrayList;
import java.util.Queue;

public class Deck {
	private Queue<Card> deck;


	public Deck(Queue<Card> deck) {
		this.deck = deck;
	}
	
	
	//Returns the deck
	public Queue<Card> getDeck() {
		return deck;
	}


	//Returns the card at the top of the deck
	public Card drawCard() {
		return deck.remove();
	}


	//Add the given card to the bottom of the deck
	public void addCard(Card c) {
		c.setAffiliation(Affiliation.DECK);
		deck.add(c);
	}


	//Add the given cards to the bottom of the deck
	public void addAll(ArrayList<Card> addMe) {
		for(Card c : addMe) {
			c.setAffiliation(Affiliation.DECK);
		}
		deck.addAll(addMe);
	}
}
