package view.admin;

import java.util.List;

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
import model.userModel;
import view.LoginView;

public class UserListView extends BorderPane{

	Stage stage;
	Scene scene;
	TableView<userModel> table;
	List<userModel> user;
	VBox box;
	HBox btnBox; 
	GridPane grid;
	Button backBtn, deleteBtn;
	Label errorLbl;
	adminController admin;
	String email, name, password, role, tempId;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);
        
        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);	
        
        backBtn = new Button("Back");
        deleteBtn = new Button("Delete User");
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(deleteBtn, backBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("User List Page");
        stage.centerOnScreen();
        stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void setTableColumns() {
	    TableColumn<userModel, String> idCol = new TableColumn<>("ID");
	    idCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
	    idCol.setMinWidth(150);

	    TableColumn<userModel, String> emailCol = new TableColumn<>("Email");
	    emailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));
	    emailCol.setMinWidth(150);
	    
	    TableColumn<userModel, String> nameCol = new TableColumn<>("Name");
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
	    nameCol.setMinWidth(150);
	    
	    TableColumn<userModel, String> passCol = new TableColumn<>("Password");
	    passCol.setCellValueFactory(new PropertyValueFactory<>("user_password"));
	    passCol.setMinWidth(150);
	    
	    TableColumn<userModel, String> roleCol = new TableColumn<>("Role");
	    roleCol.setCellValueFactory(new PropertyValueFactory<>("user_role"));
	    roleCol.setMinWidth(150);


	   table.getColumns().addAll(idCol, emailCol, nameCol, passCol, roleCol);
	   table.setOnMouseClicked(tableMouseEvent());
	   refreshTable();
	}
	
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<userModel> tableSelectModel = table.getSelectionModel();
				tableSelectModel.setSelectionMode(SelectionMode.SINGLE);
				userModel model = tableSelectModel.getSelectedItem();
				tempId = model.getUser_id();
				System.out.println(tempId);
			}
		};
	}
	
	private void refreshTable() {
		if(admin != null) {
			user = admin.getAllUserController();
			if(user != null) {
				table.getItems().addAll(user);
			}else {
				errorLbl.setText("No user found");
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
        deleteBtn.setOnAction(e -> handleDelete());
        backBtn.setOnAction(e -> new AdminView(stage, admin));
	}
	
	private void handleDelete() {
		admin.deleteUserController(tempId);
		refreshTable();
	}
	
	
	public UserListView(Stage stage, adminController admin) {
		this.stage = stage;
		this.admin = admin;
		
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();
	}
}
