import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class travelBuddyGUI extends Application
{

	public BorderPane entertainmentPane; 
	public Button startButton;
	public CheckBox architectureCheck, barsCheck, museumsCheck, restaurantsCheck;
	public ComboBox<String> selectCityBox, selectBoroughBox;
	public Image manhattanImage, pinImage;
	public ImageView manhattanImageView, pinImageView; 
	public Scene startScene, cityBoroughScene, entertainmentScene; 
	public ScrollPane scrollManhattanMap;
	public Stage window;
	public Text startText, selectCityText, selectBoroughText, currentBoroughText;
	public VBox startBox, cityBoroughBox, entertainmentOptionsBox; 
	
	public static void main(String[] args)
	{
		launch(args); 
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		window = primaryStage;
		
		startScreen();
		cityBoroughScreen();
		entertainmentScreen();
		
		window.setScene(startScene);
		window.setTitle("Travel Buddy");
		window.show();			
	}

	private void startScreen() 
	{
		startText = new Text("Welcome to Travel Buddy!");
		startButton = new Button("Start");
		startButton.setOnAction(e ->
		{	
			window.setScene(cityBoroughScene);
		});
		
		startBox = new VBox();
		startBox.getChildren().addAll(startText, startButton);
		startBox.setAlignment(Pos.CENTER);
		startBox.setPadding(new Insets(0,20,10,20));
		startBox.setSpacing(40);
		
		startScene = new Scene(startBox, 300, 500);
	}
	
	private void cityBoroughScreen()
	{
		selectCityText = new Text("Please select a city");
		selectCityBox = new ComboBox<String>();
		
		selectBoroughText = new Text("Please select a borough");
		selectBoroughBox = new ComboBox<String>();
		selectBoroughText.setVisible(false);
		selectBoroughBox.setVisible(false);
		
		// Fix city combo box 
		selectCityBox.getItems().addAll(
				"New York City",
				"Paris"
				);
		selectCityBox.setOnAction(e ->
		{
			selectBoroughText.setVisible(true);
			selectBoroughBox.setVisible(true);
		});
		
		// Fix borough combo box
		selectBoroughBox.getItems().addAll(
				"Brooklyn",
				"Manhattan",
				"Queens",
				"Staten Island",
				"The Bronx"
				);
		selectBoroughBox.setOnAction(e ->
		{
			window.setScene(entertainmentScene);
			window.centerOnScreen();
		});
		
		
		cityBoroughBox = new VBox();
		cityBoroughBox.getChildren().addAll(selectCityText, selectCityBox, selectBoroughText, selectBoroughBox);
		cityBoroughBox.setAlignment(Pos.CENTER);
		cityBoroughBox.setPadding(new Insets(0,20,10,20));
		cityBoroughBox.setSpacing(40);
		
		cityBoroughScene = new Scene(cityBoroughBox, 300, 500);
	}
	
	private void entertainmentScreen()
	{
		// Fix text to display whatever borough is selected
		currentBoroughText = new Text("Current Borough");
		
		// check boxes for entertainment
		architectureCheck = new CheckBox("Architecture");
		barsCheck = new CheckBox("Bars");
		museumsCheck = new CheckBox("Museums");
		restaurantsCheck = new CheckBox("Restaurants");
		
		mapOfManhattan();
		
		entertainmentOptionsBox = new VBox();
		entertainmentOptionsBox.getChildren().addAll(architectureCheck, barsCheck, museumsCheck, restaurantsCheck);
		entertainmentOptionsBox.setAlignment(Pos.CENTER_LEFT);
		entertainmentOptionsBox.setPadding(new Insets(0,20,10,20));
		entertainmentOptionsBox.setSpacing(40);
		
		entertainmentPane = new BorderPane();
		entertainmentPane.setTop(currentBoroughText);
		BorderPane.setAlignment(currentBoroughText, Pos.CENTER);
		entertainmentPane.setRight(entertainmentOptionsBox);
		entertainmentPane.setCenter(scrollManhattanMap);
		
		entertainmentScene = new Scene(entertainmentPane, 800, 800);
	}

	private void mapOfManhattan() 
	{
		// Map of Manhattan
		manhattanImage = new Image("MapOfManhattan.jpg");		
		manhattanImageView = new ImageView();
		manhattanImageView.setImage(manhattanImage);
		
		pinImage = new Image("Pin.png");
		pinImageView = new ImageView();
		pinImageView.setImage(pinImage);
		pinImageView.setFitHeight(20);
		pinImageView.setFitWidth(20);
		
		Button pinButton = new Button();
		pinButton.setGraphic(pinImageView);
		pinButton.setOnAction(e -> 
		{
			Alert pinAlert = new Alert(AlertType.INFORMATION);
			pinAlert.setTitle("Information");
			pinAlert.setHeaderText("Name of Venue");
			pinAlert.setContentText("Venue Information");
			pinAlert.showAndWait();			
		});
		
		// CHANGE LOCATION OF PIN USE imageview.relocate(x,y)
		pinButton.relocate(200, 400); // 400 is the farthest to the right the pins should go
		//pinImageView2.relocate(400, 1200); 1200 is the lowest the pins should go
		
		//Map stack pane
		Pane mapPane = new Pane();
		mapPane.getChildren().addAll(manhattanImageView, pinButton);//, pinImageView2);
		
		scrollManhattanMap = new ScrollPane();
		scrollManhattanMap.setContent(mapPane);
	}
	
	
}