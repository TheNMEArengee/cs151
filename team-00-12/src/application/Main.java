package application;
	
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
  
	@Override
	public void start(Stage primaryStage) {
	  
		try {
		  
		  Circle shape = constructCircle();
		  shape.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> shape.setFill(Color.BLUE));
		  shape.addEventHandler(MouseEvent.MOUSE_EXITED, e -> shape.setFill(Color.RED));
		  
		  Group root = new Group(shape);
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private Circle constructCircle() {
    Circle shape = new Circle();
    shape.setCenterX(200);
    shape.setCenterY(150);
    shape.setRadius(100);
    shape.setFill(Color.RED);
    shape.setCursor(Cursor.HAND);
    return shape;
  }
	
	
  public static void main(String[] args) {
		launch(args);
	}
}
