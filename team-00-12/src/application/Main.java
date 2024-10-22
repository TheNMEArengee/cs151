package application;

import javafx.scene.paint.Color;
import application.EventHandlers.MouseMoved;
import application.EventHandlers.MousePressedAction;
import application.EventHandlers.MouseReleasedAction;
import application.GameBoard.Checkerboard;
import application.GameBoard.CheckerboardPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;


public class Main extends Application {
	// Used for MasterCard #ad circle movements
	private double initX;
	private double initY;
	

	// Method to open application
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("Chess Fight by Jason Huynh, Eric Nguyen, and Justin Zhu");

			// Creating home screen containers
			VBox vbox = constructVBox();
			vbox.setPadding(new Insets(10, 10, 10, 10));
			BorderPane root = new BorderPane();
			root.setCenter(vbox);
			Scene welcomeScene = new Scene(root, 400, 400);
			primaryStage.setScene(welcomeScene);
			
			
			//Creating game over screen containers
			VBox gameOverBox = constructVBox();
			BorderPane gameOverScreen = new BorderPane();
			gameOverScreen.setCenter(gameOverBox);
			Label overMessage = new Label("Game over! "+ "You won!");
			Scene gameFinished = new Scene(gameOverScreen, 400, 400);	
			
			
			// Creating buttons and title screen label
			Button forfeitButton = createForfeitButton(primaryStage, gameFinished);
			Button backButton = createBackButton(primaryStage, welcomeScene);
			Button gameoverbackButton = createBackButton(primaryStage, welcomeScene);
			Button startButton = createStartButton(primaryStage, welcomeScene, backButton, forfeitButton, gameFinished);
			Button tutorialButton = createTutorialButton(primaryStage, backButton);
			Button quitButton = createQuitButton(primaryStage);
			Label titleLabel = new Label("Chess Fight");
			titleLabel.setStyle("-fx-font-size: 20;");
			
			
			//Add everything to the vboxes
			vbox.getChildren().addAll(titleLabel, startButton, tutorialButton, quitButton);
			gameOverBox.getChildren().addAll(overMessage,gameoverbackButton);

			
			// MasterCard Circles #ad
			Circle redCircle = createCircle(Color.rgb(235, 0, 27), 20, 30, 30);
			Circle goldCircle = createCircle(Color.rgb(247, 158, 27), 20, 50, 30);
			root.getChildren().addAll(redCircle, goldCircle);
			
		
			//Show stage
			primaryStage.setTitle("Chess Fight");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


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

	// Creates the start button
	private Button createStartButton(Stage primaryStage, Scene welcomeScene, Button backBtn, Button forfeitBtn, Scene gameFinished) {
		Button startBtn = new Button("Start");
		startBtn.setPrefSize(100, 60);

		// Event for start button. Sends the user to the game
		startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			System.out.println("Player 0: White \nPlayer 1: Black");
			primaryStage.close();
			VBox battleField = constructVBox();
			Checkerboard checkerboard = Checkerboard.getInstance();
			checkerboard.setCurrentPlayer(0); // Ensure Player 0 starts every time 'Start' is pressed.
			CheckerboardPane checkerboardPane = new CheckerboardPane(checkerboard);
			battleField.getChildren().addAll(checkerboardPane, backBtn, forfeitBtn);
			checkerboardPane.setOnMousePressed(new MousePressedAction(checkerboardPane));
			checkerboardPane.setOnMouseReleased(new MouseReleasedAction(checkerboardPane, primaryStage, welcomeScene, gameFinished));
			checkerboardPane.setOnMouseMoved(new MouseMoved(checkerboardPane));
			BorderPane bp = new BorderPane();
			bp.setCenter(battleField);
			Scene battleFieldInit = new Scene(bp, 800, 650);
		
			
			primaryStage.setScene(battleFieldInit);
			primaryStage.show();
		});

		return startBtn;
	}

	// Creates the tutorial button
	private Button createTutorialButton(Stage primaryStage, Button backBtn) {
		Button tutorialBtn = new Button("Tutorial");
		tutorialBtn.setPrefSize(100, 60);

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

		return tutorialBtn;
	}

	// Creates the quit button
	private Button createQuitButton(Stage primaryStage) {
		Button quitBtn = new Button("Quit");
		quitBtn.setPrefSize(100, 60);

		// Event handler for back button on title screen. Exits the application
		quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> primaryStage.close());

		return quitBtn;
	}

	// Creates the back button
	private Button createBackButton(Stage primaryStage, Scene welcomeScene) {
		Button backBtn = new Button("Back");
		backBtn.setPrefSize(100, 60);

		// Event handler for tutorial back button. Sends the user back to the title
		// screen
		backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			primaryStage.close();
			primaryStage.setScene(welcomeScene);
			primaryStage.show();
		});

		return backBtn;
	}
	
	private Button createForfeitButton(Stage primaryStage, Scene gameFinished) {
		Button backBtn = new Button("Forfeit");
		backBtn.setPrefSize(100, 60);

		// Event handler for tutorial back button. Sends the user back to the title
		// screen
		backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			primaryStage.close();
			primaryStage.setScene(gameFinished);
			primaryStage.show();
		});

		return backBtn;
	}

	// Constructs a VBox with the dimensions specified below
	private VBox constructVBox() {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	// Runs the program
	public static void main(String[] args) {
		launch(args);
	}
}
