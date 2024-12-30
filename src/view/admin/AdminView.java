package view.admin;

import java.util.List;
import java.util.Vector;

import controller.adminController;
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
import view.vendor.AcceptedInvitationView;
import view.vendor.ManageProductView;

public class AdminView extends BorderPane {

	Stage stage;
	Scene scene;
	TableView<eventModel> table;
	List<eventModel> event;
	VBox box;
	HBox btnBox;
	GridPane grid;
	Button logoutBtn, viewDetailBtn, deleteBtn, viewUserBtn;
	Label errorLbl;
	adminController admin;
	String tempId = null;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);
        
        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);	
        
        logoutBtn = new Button("Log Out");
        viewDetailBtn = new Button("View Event Detail");
        deleteBtn = new Button("Delete Event");
        viewUserBtn = new Button("View All User");
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(viewDetailBtn, deleteBtn, viewUserBtn, logoutBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.centerOnScreen();
        stage.show();
	}
	
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
		
		TableColumn<eventModel, String> descriptionCol = new TableColumn<>("Event Description");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("event_description"));
		descriptionCol.setMinWidth(150);
		
		TableColumn<eventModel, String> organizerCol = new TableColumn<>("Organizer Id");
		organizerCol.setCellValueFactory(new PropertyValueFactory<>("organizer_id"));
		organizerCol.setMinWidth(150);
		
		table.getColumns().addAll(idCol, nameCol, dateCol, locationCol, descriptionCol, organizerCol);
		table.setOnMouseClicked(tableMouseEvent());
		refreshTable();
	}
	
 	private void refreshTable() {
 		if(admin != null) {
			event = admin.getAllEventController();;
			table.getItems().clear();
			if(event != null) {
				table.getItems().addAll(event);
			}else {
				errorLbl.setText("No event found");
			}
		}else {
			errorLbl.setText("no admin");
		}
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
        viewDetailBtn.setOnAction(e -> handleViewDetail());
        deleteBtn.setOnAction(e -> handleDelete());
        viewUserBtn.setOnAction(e -> new UserListView(stage, admin));
	}
	
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<eventModel> tableSelectModel = table.getSelectionModel();
				tableSelectModel.setSelectionMode(SelectionMode.SINGLE);
				eventModel model = tableSelectModel.getSelectedItem();
				tempId = model.getEvent_id();
			}
		};
	}
	
	private void handleViewDetail() {
		new EventDetailsView(stage, admin);
	}
	
	private void handleDelete() {
		if(tempId != null) {
			admin.deleteEventController(tempId);
			System.out.println("sukses hapus event");			
		}else {
			errorLbl.setText("Tidak ada event yang dipilih");
		}
	}
	
	
	public AdminView(Stage stage, adminController admin) {
		this.stage = stage;
		this.admin = admin;
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();
	}
}
