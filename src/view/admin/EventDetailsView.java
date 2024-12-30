package view.admin;


import java.util.List;
import java.util.Map;

import controller.adminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.adminModel;
import model.event;

public class EventDetailsView extends BorderPane{
	Stage stage;
	Scene scene;
	TableView<Map<String, Object>> table = new TableView<>();
	List<Map<String, Object>> event;
	VBox box;
	HBox btnBox;
	GridPane grid;	 
	TextField nameField, locationField, descriptionField;
	DatePicker dateField;
	Label nameLbl, locationLbl, descriptionLbl, dateLbl, errorLbl, detailLbl;
	Button backBtn;
	adminController admin;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 1050, 750);
        
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
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(backBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Admin Event Details Page");
        stage.centerOnScreen();
        stage.show();
	}
	
	private void setTableColumns() {
		 TableColumn<Map<String, Object>, String> nameCol = new TableColumn<>("Event Name");
		    nameCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("event_name")));
		    nameCol.setMinWidth(150);

		    TableColumn<Map<String, Object>, String> dateCol = new TableColumn<>("Event Date");
		    dateCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("event_date")));
		    dateCol.setMinWidth(150);
		    
		    TableColumn<Map<String, Object>, String> locCol = new TableColumn<>("Event Location");
		    locCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("event_location")));
		    locCol.setMinWidth(150);
		    
		    TableColumn<Map<String, Object>, String> descCol = new TableColumn<>("Event Description");
		    descCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("event_description")));
		    descCol.setMinWidth(150);
		    
		    TableColumn<Map<String, Object>, String> guestCol = new TableColumn<>("Guest");
		    guestCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("guest_name")));
		    guestCol.setMinWidth(150);
		    
		    
		    TableColumn<Map<String, Object>, String> vendorCol = new TableColumn<>("Vendor");
		    vendorCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty((String) cellData.getValue().get("vendor_name")));
		    vendorCol.setMinWidth(150);
		    
		    TableColumn<Map<String, Object>, String> invCol = new TableColumn<>("Invitation Status");
		    invCol.setCellValueFactory(cellData -> 
		    new SimpleStringProperty((String) cellData.getValue().get("invitation_status")));
		    invCol.setMinWidth(150);


		    table.getColumns().addAll(nameCol, dateCol, locCol, descCol, guestCol, vendorCol, invCol);
		    getData();
	}

	
	
	
	private void initPosition() {
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10); // Horizontal gap between components
	    grid.setVgap(10);

	    this.setTop(table); // Add the table at the top

	    

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
	    backBtn.setOnAction(e -> new AdminView(stage, admin));
	}
	
	private void getData() {
		if(admin != null) {
			event = admin.getEventDetailedController();
			if(event != null) {
				table.getItems().addAll(event);
			}else {
				errorLbl.setText("No event found");
			}
		}else {
			errorLbl.setText("no Data");
		}
		
	}

	public EventDetailsView(Stage stage, adminController admin) {
		this.stage = stage;
		this.admin = admin;
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();	
		getData();
	}

}