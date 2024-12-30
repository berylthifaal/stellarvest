package view.EventOrganizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import controller.EOController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.event;
import model.eventModel;
import model.userModel;

public class EventDetailView extends BorderPane{
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
	Button backBtn, updateBtn;
	EOController EO;
	private String tempId, organizerId, eventName, eventDate, eventLoc, eventDesc;
	eventModel Eventmodel;
	
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
        updateBtn = new Button("Update");
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(backBtn, updateBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Event Details Page");
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


	    table.getColumns().addAll(nameCol, dateCol, locCol, descCol, guestCol, vendorCol);
	    getData();
		table.setOnMouseClicked(tableMouseEvent());
	}

	public EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Map<String, Object>> tableSelectModel = table.getSelectionModel();
				tableSelectModel.setSelectionMode(SelectionMode.SINGLE);
				Map<String, Object> model = tableSelectModel.getSelectedItem();
				if(model != null) {
					String eventId = (String) model.get("event_id");
					tempId = eventId;
					System.out.println("Event ID: " + eventId);
					nameField.setText((String) model.get("event_name"));
					locationField.setText((String) model.get("event_location"));
					descriptionField.setText((String) model.get("event_description"));
					String date = (String) model.get("event_date");
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate local = LocalDate.parse(date, format);
					dateField.setValue(local);
					organizerId = (String) model.get("organizer_id");
					
				}else {
					errorLbl.setText("Error, no events");
				}
			}
		};
	}
	
	
	private void initPosition() {
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10); // Horizontal gap between components
	    grid.setVgap(10);

	    // Add components to the grid
	    grid.add(nameLbl, 0, 0);
	    grid.add(nameField, 1, 0);

	    grid.add(dateLbl, 0, 1);
	    grid.add(dateField, 1, 1);
	    
	    grid.add(locationLbl, 0, 3);
	    grid.add(locationField, 1, 3);

	    grid.add(descriptionLbl, 0, 2);
	    grid.add(descriptionField, 1, 2);


	    this.setTop(table); // Add the table at the top

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
	    backBtn.setOnAction(e -> new EventsView(stage, EO));
	    updateBtn.setOnAction(e -> updateData());
	}
	
	private void updateData() {
		table.setOnMouseClicked(tableMouseEvent());
		eventName = nameField.getText();
		eventDate = dateField.getValue().toString();
		eventLoc = locationField.getText();
		eventDesc = descriptionField.getText();
		if(eventName.isEmpty() || eventDate.isEmpty() || eventLoc.isEmpty() || eventDesc.isEmpty()) {
			errorLbl.setText("Field must be filled");
		}else {
			Eventmodel = new eventModel(tempId, eventName, eventDate, eventLoc, eventDesc, organizerId);
			EO.updateEventController(tempId,  eventName, eventDate, eventLoc, eventDesc, organizerId);
			System.out.println("Berhasil update");
		}
//		new EventsView(stage, EO);			
		
	};
	
	private void getData() {
		if(EO != null) {
			event = EO.getEventDetailedController();
			if(event != null) {
				table.getItems().addAll(event);
			}else {
				errorLbl.setText("No event found");
			}
		}else {
			errorLbl.setText("no Data");
		}

	}

	public EventDetailView(Stage stage, EOController EO) {
		this.stage = stage;
		this.EO = EO;
		
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();	
	}

}
