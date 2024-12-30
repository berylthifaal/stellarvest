 package view.EventOrganizer;

import java.util.List;

import controller.EOController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.eventModel;
import view.LoginView;

public class EventsView extends BorderPane{
	Stage stage;
	Scene scene;
	TableView<eventModel> table;
	List<eventModel> event;
	VBox box;
	HBox btnBox;
	GridPane grid;
	Button logoutBtn, viewDetailBtn, addVendorBtn, addGuestBtn, editEventBtn, createEventBtn;
	Label errorLbl;
	EOController EO;
	String tempId;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);
        
        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);	
        
        logoutBtn = new Button("Log Out");
        viewDetailBtn = new Button("View Detail");
        addGuestBtn = new Button("Add Guest");
        addVendorBtn = new Button("Add Vendor");
        editEventBtn = new Button("Edit Event");
        createEventBtn = new Button("Create Event");
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(viewDetailBtn, addGuestBtn, addVendorBtn, editEventBtn, createEventBtn, logoutBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Event Organizer Page");
        stage.centerOnScreen();
        stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void setTableColumns() {
		TableColumn<eventModel, String> idCol = new TableColumn<>("Event ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		idCol.setMinWidth(150);
		
		TableColumn<eventModel, String> nameCol = new TableColumn<>("Event Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		nameCol.setMinWidth(150);
		
		TableColumn<eventModel, String> dateCol = new TableColumn<>("Event Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		dateCol.setMinWidth(150);

		TableColumn<eventModel, String> locationCol = new TableColumn<>("Event Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("event_location"));
		locationCol.setMinWidth(150);
		
		TableColumn<eventModel, String> descCol = new TableColumn<>("Event Description");
		descCol.setCellValueFactory(new PropertyValueFactory<>("event_description"));
		descCol.setMinWidth(150);
		
		TableColumn<eventModel, String> organizerCol = new TableColumn<>("Organizer ID");
		organizerCol.setCellValueFactory(new PropertyValueFactory<>("organizer_id"));
		organizerCol.setMinWidth(150);
		
		table.getColumns().addAll(idCol, nameCol, dateCol, locationCol, descCol, organizerCol);
		refreshTable();
		table.setOnMouseClicked(tableMouseEvent());
	}
	
	private void refreshTable() {
		if(EO != null) {
			event = EO.getAllEvent();
			if(event != null) {
				table.getItems().addAll(event);
			}else {
				errorLbl.setText("No event found");
			}
		}else {
			errorLbl.setText("no admin");
		}
	}
	
	public EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<eventModel> tableSelectModel = table.getSelectionModel();
				tableSelectModel.setSelectionMode(SelectionMode.SINGLE);
				eventModel model = tableSelectModel.getSelectedItem();
				tempId = model.getEvent_id();
				System.out.println(tempId);
			}
		};
	}
	
	private void initPosition() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10); // Horizontal gap between components
        grid.setVgap(10);

        this.setTop(table);

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
		logoutBtn.setOnAction(e -> new LoginView(stage));
	    viewDetailBtn.setOnAction(e -> handleDetail());
	    addGuestBtn.setOnAction(e -> handleAddGuest());
	    addVendorBtn.setOnAction(e -> handleAddVendor());
	    createEventBtn.setOnAction(e -> new CreateEventView(stage, EO));
	    editEventBtn.setOnAction(e -> new editEvent(stage, EO));
	}
	
	private void handleDetail() {
		new EventDetailView(stage, EO);
	}
	
	private void handleAddGuest() {
		new AddGuestView(stage, EO);
	}
	
	private void handleAddVendor() {
		new AddVendorView(stage, EO);
	}
	
	
	public EventsView(Stage stage, EOController EO) {
		this.stage = stage;
		this.EO = EO;
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();
	}

}
