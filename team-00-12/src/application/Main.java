package application;

import javafx.scene.paint.Color;

import java.io.IOException;

import Controller.PressedAction;
import Controller.ReleaseAction;
import Controller.ResetAction;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ChessBoard;
import view.ChessPane;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import javafx.scene.Node;

public class Main extends Application {

	public static final int TILE_SIZE = 60;
	public static final int HEIGHT = 10;
	public static final int WIDTH = 10;
	private double initX;
	private double initY;
	

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
				battleField.getChildren().addAll(CreateBoard.createBoard(), backBtn);
				BorderPane gameRoot = new BorderPane();

				gameRoot.setCenter(battleField);

				Scene battleInit = new Scene(gameRoot, 600, 650);
				primaryStage.setScene(battleInit);
				
				
//				ChessBoard chessBoard = ChessBoard.getInstance(68.75,25,25);
//				ChessPane pane = new ChessPane(chessBoard);
//				
//				pane.setOnMousePressed(new PressedAction(pane));
//				pane.setOnMouseReleased(new ReleaseAction(pane));
//
//				BorderPane borderPane = new BorderPane();
//				borderPane.setCenter(pane);
//				HBox hBox = new HBox();
//				hBox.setAlignment(Pos.TOP_CENTER);
//				Button button = new Button("Return");
//				button.setOnAction(new ResetAction(pane));
//				hBox.getChildren().add(button);
//				hBox.getChildren().add(backBtn);
//				borderPane.setBottom(hBox);
//				Scene scene = new Scene(borderPane,600,600);
//				primaryStage.setScene(scene);
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
	
	
	//imported from 133 project
	public void goToGame(ActionEvent event) throws IOException {


		ChessBoard chessBoard = ChessBoard.getInstance(68.75,25,25);
		ChessPane pane = new ChessPane(chessBoard);
		pane.setOnMousePressed(new PressedAction(pane));

		pane.setOnMouseReleased(new ReleaseAction(pane));

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.TOP_CENTER);
		Button button = new Button("Regret");
		button.setOnAction(new ResetAction(pane));
		Button button1 = new Button("back");
		
		hBox.getChildren().add(button);
		hBox.getChildren().add(button1);
		borderPane.setBottom(hBox);
		Scene scene = new Scene(borderPane,600,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.setTitle("Chess");
		window.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
