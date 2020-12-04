package application.CardContainers;

import java.util.ArrayList;

public class Effect {

	public static ArrayList<String> getEffect(int ID) {
		ArrayList<String> effects = new ArrayList<String>();

		if (ID == 1) {
			effects.add("King");
			effects.add("Gain King movement for 1 turn");
			effects.add("img/KingBlack.jpg");
		} else if (ID == 2) {
			effects.add("Rook");
			effects.add("Gain Rook movement for 1 turn");
			effects.add("img/CarBlack.jpg");
		} else if (ID == 3) {
			effects.add("Bishop");
			effects.add("Gain Bishop movement for 1 turn");
			effects.add("img/KnightBlack.jpg");
		} else if (ID == 4) {
			effects.add("Knight");
			effects.add("Gain Knight movement for 1 turn");
			effects.add("img/HorseBlack.jpg");
		} else if (ID == 5) {
			effects.add("Queen");
			effects.add("Gain Queen movement for 1 turn");
			effects.add("img/QueenBlack.jpg");
		} else {
			effects.add("Broke");
			effects.add("Broke");
			effects.add("img/pileofshitpawn.png");
		}

		return effects;
	}

}
