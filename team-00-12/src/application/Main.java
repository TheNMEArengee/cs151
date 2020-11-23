package application;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Main extends Application {

	public static final int TILE_SIZE = 60;
	public static final int HEIGHT = 8;
	public static final int WIDTH = 8;
	private double initX;
	private double initY;
	
	private Rectangle[][] placementBoard;



	@Override
	public void start(Stage primaryStage) {

		try {
			primaryStage.setTitle("Chess Fight");
			Label titleLbl = new Label("Chess Fight");
			titleLbl.setStyle("-fx-font-size: 20;");

			// Buttons
			Button startBtn = new Button("Start");
			startBtn.setPrefSize(100, 60);
			Button tutorialBtn = new Button("Tutorial");
			tutorialBtn.setPrefSize(100, 60);
			Button quitBtn = new Button("Quit");
			quitBtn.setPrefSize(100, 60);
			Button backBtn = new Button("Back");
			backBtn.setPrefSize(100, 60);
			
			// Container for title screen
			VBox vbox = constructVBox();
			vbox.setPadding(new Insets(10, 10, 10, 10));
			vbox.getChildren().addAll(titleLbl, startBtn, tutorialBtn, quitBtn);

			BorderPane root = new BorderPane();
			root.setCenter(vbox);
			
			Circle c = createCircle(Color.RED, 20, 100, 100);
			Circle b = createCircle(Color.CORAL, 20, 200, 200);
			root.getChildren().addAll(c, b);
			
			// Title screen creation
			Scene welcomeScene = new Scene(root, 400, 400);
			primaryStage.setScene(welcomeScene);
			primaryStage.show();

			/*
			 * BUTTON EVENTS
			 */

			
			// Event for start button. Sends the user to the game (WIP, just the board for
			// now)
			startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				VBox battleField = constructVBox();
				battleField.getChildren().addAll(createBoard(), backBtn);
				BorderPane gameRoot = new BorderPane();

				gameRoot.setCenter(battleField);

				Scene battleInit = new Scene(gameRoot, 600, 650);
				primaryStage.setScene(battleInit);
			});

			// Event for tutorial button. Brings up the tutorial section for the game.
			tutorialBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				// Creating the container for the tutorial scene
				VBox tutorialVBox = constructVBox();
				tutorialVBox.setPadding(new Insets(15, 11, 15, 11));
				tutorialVBox.setStyle("-fx-background-color: lightgray;");
				Label tutorialLbl = new Label("Tutorial");
				tutorialLbl.setStyle("-fx-font-size: 20;");

				// Tutorial text
				Label instructions = new Label("Chess Fight is a game of chess where movement"
						+ " is based on cards that players draw in a deck. User interaction"
						+ " is through simple mouse clicks on cards and pieces.");
				instructions.setWrapText(true);

				// Inserting elements into VBox and change to tutorial scene
				BorderPane tutorialRoot = new BorderPane();
				tutorialVBox.getChildren().addAll(tutorialLbl, instructions, backBtn);
				tutorialRoot.setCenter(tutorialVBox);
				BorderPane.setMargin(tutorialVBox, new Insets(0, 60, 0, 60));
				Scene tutorialScene = new Scene(tutorialRoot, 400, 400);
				primaryStage.setScene(tutorialScene);
			});

			
			// Event handler for tutorial back button. Sends the user back to the title
			// screen
			backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				primaryStage.setScene(welcomeScene);
			});

						
			// Event handler for back button on title screen. Exits the application
			quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> primaryStage.hide());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * METHODS
	 */
	
	private Circle createCircle(Color color, int radius, int x, int y) {
		Circle c = new Circle();
		c.setCenterX(x);
		c.setCenterY(y);
		c.setRadius(radius);
		c.setFill(color);
		c.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
			initX = c.getTranslateX();
			initY = c.getTranslateY();
			System.out.println("("+ initX +", "+initY+")");
			c.toFront();
		});
		c.addEventFilter(MouseEvent.MOUSE_DRAGGED, e->{
			double dragX = e.getSceneX();
			double dragY = e.getSceneY();
			double fi = initX + dragX;
			double fo = initY + dragY;
//			System.out.println("fi: "+ fi);
			c.setCenterX(fi);
			c.setCenterY(fo);
			c.toFront();
		});
		return c;
	}
	
	// Constructs an HBox with the dimensions specified below (Not used yet)
	private HBox constructHBox() {
		HBox hbox = new HBox();
		//WIP

		return hbox;
	}

	
	// Constructs a VBox with the dimensions specified below
	private VBox constructVBox() {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}
	
	
	// Constructs the checker board (WIP)
	private Parent createBoard() {
		Group tilesGroup = new Group();
		
		
		//Chess board container
		GridPane board = new GridPane();

		placementBoard = new Rectangle[WIDTH][HEIGHT];
		//board.setPrefSize((WIDTH * TILE_SIZE) , (HEIGHT * TILE_SIZE) );

		
	
	board.getChildren().addAll(tilesGroup);

		for(int x=0; x < 8; x++){
			for(int j=0; j < 8; j++){
				placementBoard[x][j] = new Rectangle();
				placementBoard[x][j].setWidth(TILE_SIZE);
				placementBoard[x][j].setHeight(TILE_SIZE);
				placementBoard[x][j].setStroke(Color.TRANSPARENT);
				placementBoard[x][j].setStrokeType(StrokeType.INSIDE);
				placementBoard[x][j].setStrokeWidth(1);				
			}
		}
		//Create an 8 x 8 chess board and store it into tilesGroup
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				tilesGroup.getChildren().add(tile);
				board.setAlignment(Pos.CENTER);
			}
		}
		
		return board;
	}
	
	public void movePiece(MouseEvent e) {
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
