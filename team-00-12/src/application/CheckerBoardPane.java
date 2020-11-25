package application;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CheckerBoardPane extends Pane{
	private Set<Unit> units;
	private CheckerBoard cb;
	private GridPane gp;
	private Group tg;
	
	public CheckerBoardPane(CheckerBoard cb){
		this.cb = cb;
		this.gp = new GridPane();
		this.units = new HashSet<>();
		gp.setPrefSize(100, 100);
		setUnits();
		draw();
	}
	
	public void setUnits() {
		//add pieces here
		System.out.println("Set Units");
		int playerNumber = 0;
		for(int y = 0; y<2; y++) {
			for(int x = 0; x<8; x++) {
				units.add(new Unit(x, y, playerNumber));
//				System.out.println("new unit: " + x + ", " + y);
			}
		}
		
		playerNumber = 1;
		for(int y = 6; y<8; y++) {
			for(int x = 0; x<8; x++) {
				units.add(new Unit(x, y, playerNumber));
//				System.out.println("new unit: " + x + ", " + y);
			}
		}
		
		for(Unit u: units) {
			System.out.println("Unit:" + u);
		}
	}
	
	public void draw() {
		drawBoard();
		drawUnits();
		getChildren().add(gp);
	}
	
	public void drawBoard() {
//		System.out.println("Draw Board");
		tg = new Group();
		gp.getChildren().add(tg);
		
		int ts = cb.getTileSize();
		for(int y=0; y<cb.getCols(); y++) { //0-7
			for(int x=0; x<cb.getRows(); x++) { //0-7
				final int coordX = x;
				final int coordY = y;
				
				Rectangle r = new Rectangle(ts*x, ts*y, ts, ts);
				if((x+y)%2 == 0) {
					r.setFill(Color.rgb(232, 235, 239));
				}
				else {
					r.setFill(Color.rgb(125, 135, 150));
				}
				r.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
					System.out.println(coordX + ", " + coordY);
				});	
				tg.getChildren().add(r);
			}
		}	
	}
	
	public void drawUnits() {
//		System.out.println("Draw Units");
		int ts = cb.getTileSize();	
		units.forEach(u->{
			if(u.isSelected()) {
				System.out.println("Selected: " + u.getX() + ", " + u.getY());
			}
			int x = 10 + (u.getX() * ts);
			int y = 10 + (u.getY() * ts);
//			System.out.println("new unit: " + x + ", " + y);
			Rectangle r = new Rectangle(10 + (u.getX() * ts), 10 + (u.getY() * ts), 40, 40);
			r.setArcWidth(20);
			r.setArcHeight(20);
			if(u.getPlayer() == 0) {
				r.setFill(Color.WHITE);
				r.setStroke(Color.BLACK);
			}
			else{
				r.setFill(Color.GREY);
				r.setStroke(Color.BLACK);
			}
			r.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
				System.out.println("Clicked Unit");
			});
			//Don't really need this, the double array
//			cb.setPiece(u.getX(), u.getY(), r);
			tg.getChildren().add(r);
		});
	}
	
	public CheckerBoard getCheckerBoard() {
		return cb;
	}
	
	public Set<Unit> getUnits() {
		return units;
	}
}
