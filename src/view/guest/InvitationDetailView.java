package view.guest;

import controller.guestController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.event;
import view.EventOrganizer.EventsView;

public class InvitationDetailView extends BorderPane{
	Stage stage;
	Scene scene;
	VBox box;
	HBox btnBox;
	GridPane grid;	 
	TextField nameField, locationField, descriptionField;
	DatePicker dateField;
	Label nameLbl, locationLbl, descriptionLbl, dateLbl, errorLbl, detailLbl;
	Button backBtn;
	guestController guest;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);
        
        nameField = new TextField();
        locationField = new TextField();
        descriptionField = new TextField();
        dateField = new DatePicker();
        
        nameLbl = new Label("Name: ");
        locationLbl = new Label("Location: ");
        descriptionLbl = new Label("Description: ");
        dateLbl = new Label("Date: ");
        detailLbl = new Label("Event Detail");
        errorLbl = new Label();  

        
        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);	
        
        backBtn = new Button("Back");

        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(backBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Event Details Page");
        stage.centerOnScreen();
        stage.show();
	}
	
	
	private void initPosition() {
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10); // Horizontal gap between components
	    grid.setVgap(10);

	    // Add components to the grid
	    grid.add(nameLbl, 0, 0);
	    grid.add(nameField, 1, 0);

	    grid.add(locationLbl, 0, 1);
	    grid.add(locationField, 1, 1);

	    grid.add(descriptionLbl, 0, 2);
	    grid.add(descriptionField, 1, 2);

	    grid.add(dateLbl, 0, 3);
	    grid.add(dateField, 1, 3);

	    // Add the grid to the center of the BorderPane
	    this.setCenter(grid);

	    // Center buttons at the bottom
	    btnBox.setAlignment(Pos.CENTER);
	    btnBox.setSpacing(10);
	    btnBox.setPadding(new Insets(10, 10, 10, 10)); // Padding around the buttons

	    // Add buttons and error label to a VBox
	    VBox bottomBox = new VBox(10); // Vertical spacing of 10
	    bottomBox.setAlignment(Pos.CENTER);
	    bottomBox.setPadding(new Insets(10, 10, 30, 10)); // Additional padding
	    bottomBox.getChildren().addAll(btnBox, errorLbl); // Add btnBox and errorLbl

	    this.setBottom(bottomBox); // Set VBox as the bottom component
	    BorderPane.setAlignment(bottomBox, Pos.CENTER);
	}

	
	private void setEventHandlers() {
	    backBtn.setOnAction(e -> new AcceptedInvitationView(stage, guest));
	}
	
	private void getData() {
		// Get Event Data from Database
		
	}

	public InvitationDetailView(Stage stage) {
		this.stage = stage;
		
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();	
		getData();
	}
}
