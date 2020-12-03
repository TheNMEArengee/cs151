package application.CardContainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import application.Affiliation;
import application.Card;


public class Deck{
	private Queue<Card> deck;


	public Deck() {
		
		this.deck = new LinkedList<Card>();
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
	
	
	//Shuffle the deck
	public void shuffle() {
		Collections.shuffle((LinkedList<Card>) deck); 
	}


	@Override
	public String toString() {
		String s = "Deck [deck= \n";
		for(Card c : deck) {
			s += c.toString() + "\n";
		}
		s += "]";
		return s;
	}	
	
	
}
