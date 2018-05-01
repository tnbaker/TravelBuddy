import java.util.HashMap;
import java.util.Optional;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class travelBuddyGUI extends Application
{
	public Alert pinAlert, favoriteAlert; 
	public BorderPane entertainmentPane; 
	public Button startButton, pinButton, backButton;
	public ButtonType buttonTypeFavorite, buttonTypeSaveForLater, buttonTypeCancel, buttonTypeRemove;
	public CheckBox architectureCheck, barsCheck, museumsCheck, restaurantsCheck;
	public ComboBox<String> selectCityBox, selectBoroughBox;
	public Image manhattanImage, pinImage;
	public ImageView manhattanImageView, pinImageView; 
	public Pane mapPane; 
	public Scene startScene, cityBoroughScene, entertainmentScene; 
	public ScrollPane scrollManhattanMap;
	public Stage window;
	public Text startText, selectCityText, selectBoroughText, currentBoroughText, favoriteText;
	public VBox startBox, cityBoroughBox, entertainmentOptionsBox, favoriteBox; 
	
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
		
		window.setScene(entertainmentScene);
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
		
		// Available entertainment
		entertainment();
		// Favorites
		favorites();
		
		mapOfManhattan();
		
		Hyperlink savedForLater = new Hyperlink("Saved for later");
		savedForLater.setOnAction(e ->
		{
			savedForLaterScreen();
		});
		
		entertainmentPane = new BorderPane();
		entertainmentPane.setTop(currentBoroughText);
		BorderPane.setAlignment(currentBoroughText, Pos.CENTER);
		entertainmentPane.setLeft(favoriteBox);
		entertainmentPane.setRight(entertainmentOptionsBox);
		entertainmentPane.setCenter(scrollManhattanMap);
		entertainmentPane.setBottom(savedForLater);
		
		entertainmentScene = new Scene(entertainmentPane, 750, 700);
	}

	private void savedForLaterScreen() {
		BorderPane savedPane = new BorderPane();
		TabPane weekdayPane = new TabPane();
		backButton = new Button();
		backButton.setPrefHeight(15);
		backButton.setPrefWidth(41);
		backButton.setOnAction(e ->
		{
			window.setScene(entertainmentScene);
			window.centerOnScreen();
		});
		
		Image backImage = new Image("backArrow.png");
		ImageView backImageView = new ImageView();
		backImageView.setImage(backImage);
		backImageView.setFitHeight(20);
		backImageView.setFitWidth(20);
		backImageView.smoothProperty();
		backButton.setGraphic(backImageView);
		
		HashMap<Integer, String> weekdaysMap = new HashMap<Integer, String>();
		weekdaysMap.put(1, "Mon");
		weekdaysMap.put(2, "Tue");
		weekdaysMap.put(3, "Wed");
		weekdaysMap.put(4, "Thu");
		weekdaysMap.put(5, "Fri");
		weekdaysMap.put(6, "Sat");
		weekdaysMap.put(7, "Sun");
		
		for(int i = 1; i < 8; i++)
		{
			Tab weekdayTab = new Tab();
			weekdayTab.setText(weekdaysMap.get(i));
			weekdayTab.setClosable(false);
			weekdayPane.getTabs().add(weekdayTab);
		}
		
		HBox savedForLaterHBox = new HBox(); 
		savedForLaterHBox.getChildren().addAll(backButton, weekdayPane);			
		savedPane.setTop(savedForLaterHBox);
		
		Scene savedScene = new Scene(savedPane, 300, 500);
		window.setScene(savedScene);
		window.centerOnScreen();
	}

	private void entertainment() 
	{
		architectureCheck = new CheckBox("Architecture");
		barsCheck = new CheckBox("Bars");
		museumsCheck = new CheckBox("Museums");
		restaurantsCheck = new CheckBox("Restaurants");
		entertainmentOptionsBox = new VBox();
		entertainmentOptionsBox.getChildren().addAll(architectureCheck, barsCheck, museumsCheck, restaurantsCheck);
		entertainmentOptionsBox.setAlignment(Pos.CENTER_LEFT);
		entertainmentOptionsBox.setPadding(new Insets(0,20,10,20));
		entertainmentOptionsBox.setSpacing(40);
	}

	private void favorites() 
	{
		favoriteText = new Text("Favorites");
		favoriteBox = new VBox(); 
		favoriteBox.getChildren().addAll(favoriteText);
		favoriteBox.setAlignment(Pos.CENTER);
		favoriteBox.setPadding(new Insets(0,20,10,20));
		favoriteBox.setSpacing(40);
	}

	private void mapOfManhattan() 
	{
		// Map of Manhattan
		manhattanImage = new Image("MapOfManhattan.jpg");		
		manhattanImageView = new ImageView();
		manhattanImageView.setImage(manhattanImage);
		
		// Pins
		pinImage = new Image("Pin.png");
		pinImageView = new ImageView();
		pinImageView.setImage(pinImage);
		pinImageView.setFitHeight(20);
		pinImageView.setFitWidth(20);
		
		pinButton = new Button();
		pinButton.setGraphic(pinImageView);
		pinButton.setOnAction(e -> 
		{
			pinAlert = new Alert(AlertType.CONFIRMATION);
			pinAlert.setTitle("Central Park");
			pinAlert.setHeaderText(null);
			pinAlert.setContentText("Venue Information");
			
			buttonTypeFavorite = new ButtonType("Favorite");
			buttonTypeSaveForLater = new ButtonType("Save for Later");
			buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			pinAlert.getButtonTypes().setAll(buttonTypeFavorite, buttonTypeSaveForLater, buttonTypeCancel);

			Optional<ButtonType> result = pinAlert.showAndWait();
			if (result.get() == buttonTypeFavorite)
				addToFavorite();
			if (result.get() == buttonTypeSaveForLater)
				saveForLater();	
		});
		
		pinButton.relocate(190, 140); 
		
		//Map stack pane
		mapPane = new Pane();
		mapPane.getChildren().addAll(manhattanImageView, pinButton);
		
		scrollManhattanMap = new ScrollPane();
		scrollManhattanMap.setContent(mapPane);
	}
	
	private void addToFavorite()
	{
		String added = pinAlert.getTitle();
		Hyperlink addedFav = new Hyperlink(added);
		addedFav.setOnAction(e ->
		{
			favoriteAlert = new Alert(AlertType.CONFIRMATION);
			favoriteAlert.setTitle(added);
			favoriteAlert.setHeaderText(null);
			favoriteAlert.setContentText("Venue Information");
			
			buttonTypeRemove = new ButtonType("Remove From Favorites");
			buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			pinAlert.getButtonTypes().setAll(buttonTypeRemove, buttonTypeCancel);

			Optional<ButtonType> result = pinAlert.showAndWait();
			if (result.get() == buttonTypeRemove)
				removeFavorite();
		});
		favoriteBox.getChildren().addAll(addedFav);
	}
	
	private void saveForLater()
	{
		System.out.println("saved for later");
	}
	
	private void removeFavorite()
	{
		System.out.println("Remove\n");
		favoriteBox.getChildren().remove(1);
	}
}