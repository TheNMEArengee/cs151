package application;

import javafx.scene.paint.Color;

import java.io.IOException;

import Controller.PressedAction;
import Controller.ReleaseAction;
import Controller.ResetAction;
import application.EventHandlers.MousePressedAction;
import application.EventHandlers.MouseReleasedAction;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ChessBoard;
import view.ChessPane;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
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
	// Used for MasterCard #ad circle movements
	private double initX;
	private double initY;

	// Method to open application
	@Override
	public void start(Stage primaryStage) {
		try {
//			System.out.println("Chess Fight by Jason Huynh, Eric Nguyen, and Justin Zhu");
			System.out.println("Player 0: White \nPlayer 1: Black");
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

			// MasterCard Circles #ad
			Circle redCircle = createCircle(Color.rgb(235, 0, 27), 20, 30, 30);
			Circle goldCircle = createCircle(Color.rgb(247, 158, 27), 20, 50, 30);
			root.getChildren().addAll(redCircle, goldCircle);

			// Title screen creation
			Scene welcomeScene = new Scene(root, 400, 400);
			primaryStage.setScene(welcomeScene);
			primaryStage.show();

			/*
			 * BUTTON EVENTS
			 */

			// Event for start button. Sends the user to the game
			startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				/* Old code */
//				VBox battleField = constructVBox();
//				battleField.getChildren().addAll(CreateBoard.createBoard(), backBtn);
//				BorderPane gameRoot = new BorderPane();

//				gameRoot.setCenter(battleField);

//				Scene battleInit = new Scene(gameRoot, 600, 650);
//				primaryStage.setScene(battleInit);

				/* Current Code */
				primaryStage.close();
				VBox battleField = constructVBox();
				Checkerboard checkerboard = Checkerboard.getInstance();
				checkerboard.setCurrentPlayer(0); // Ensure Player 0 starts every time 'Start' is pressed.
				CheckerboardPane checkerboardPane = new CheckerboardPane(checkerboard);
				battleField.getChildren().addAll(checkerboardPane, backBtn);
				checkerboardPane.setOnMousePressed(new MousePressedAction(checkerboardPane));
				checkerboardPane.setOnMouseReleased(new MouseReleasedAction(checkerboardPane));
				BorderPane bp = new BorderPane();
				bp.setCenter(battleField);
				Scene battleFieldInit = new Scene(bp, 600, 650);
				primaryStage.setScene(battleFieldInit);
				primaryStage.show();
//				System.out.println("-----------\nStart!\nPlayer " +cb.getCurrPlayer() + " turn");

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
			quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> primaryStage.close());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * METHODS
	 */

	// Create Circle Functions for MasterCard Circles #ad
	private Circle createCircle(Color color, int radius, int x, int y) {
		Circle c = new Circle();
		c.setCenterX(x);
		c.setCenterY(y);
		c.setRadius(radius);
		c.setFill(color);
		c.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			initX = c.getTranslateX();
			initY = c.getTranslateY();
			c.toFront();
		});
		c.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
			double dragX = e.getSceneX();
			double dragY = e.getSceneY();
			double fi = initX + dragX;
			double fo = initY + dragY;
			c.setCenterX(fi);
			c.setCenterY(fo);
			c.toFront();
		});
		return c;
	}

	// Constructs an HBox with the dimensions specified below (Not used yet)
	private HBox constructHBox() {
		HBox hbox = new HBox();
		// WIP

		return hbox;
	}

	// Constructs a VBox with the dimensions specified below
	private VBox constructVBox() {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	// NOT USED
	public void goToGame(ActionEvent event) throws IOException {

		ChessBoard chessBoard = ChessBoard.getInstance(68.75, 25, 25);
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
		Scene scene = new Scene(borderPane, 600, 600);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.setTitle("Chess");
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
