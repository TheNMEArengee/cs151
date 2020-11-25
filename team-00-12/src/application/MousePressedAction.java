package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import view.ChessBoard;
import view.ChessPane;


public class MousePressedAction implements EventHandler<MouseEvent> {
    private CheckerBoardPane cp;


    public MousePressedAction(CheckerBoardPane cp) {
        this.cp = cp;
    }

    @Override
    public void handle(MouseEvent e) {
        CheckerBoard cb = cp.getCheckerBoard();
        int pressedX = (int) e.getX() / cb.getTileSize();
        int pressedY = (int) e.getY() / cb.getTileSize();
        
        
        cp.getUnits().forEach(unit ->{
        	
        	if(unit.getX() == pressedX && unit.getY() == pressedY) {
        		System.out.println("Curr Unit: "+ unit.getX() +", " + unit.getY());
            	System.out.println("Pressed: " + pressedX + ", " + pressedY);
        		unit.setSelected(true);
        		cp.drawUnits();
        	}
        });
    }

}
