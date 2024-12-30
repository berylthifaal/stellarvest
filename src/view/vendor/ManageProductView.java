package view.vendor;

import controller.vendorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.event;
import view.LoginView;
import view.EventOrganizer.EventDetailView;

public class ManageProductView extends BorderPane{
	Stage stage;
	Scene scene;
	TableView<event> table;
	VBox box;
	HBox btnBox;
	GridPane grid;
	Button backBtn, addProductBtn;
	Label errorLbl;
	vendorController vendor;
	
	private void init() {
    	grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);
        
        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);	
        
        backBtn = new Button("Back");
        addProductBtn = new Button("Add Product");
        
        table = new TableView<>();
        setTableColumns();
        
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(addProductBtn, backBtn);
    }
	
	private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Manage Product Page");
        stage.centerOnScreen();
        stage.show();
	}
	
	private void setTableColumns() {
		TableColumn<event, String> idCol = new TableColumn<>("Product ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		idCol.setMinWidth(150);
		
		TableColumn<event, String> nameCol = new TableColumn<>("Product Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setMinWidth(150);
		
		TableColumn<event, String> descriptionCol = new TableColumn<>("Product Description");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		descriptionCol.setMinWidth(150);
		
		table.getColumns().addAll(idCol, nameCol, descriptionCol);
		refreshTable();
	}
	
	private void refreshTable() {
		//Get Data
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
		addProductBtn.setOnAction(e -> new AddProductView(stage));
		backBtn.setOnAction(e -> new VendorView(stage, vendor));
	}
	
	
	public ManageProductView(Stage stage) {
		this.stage = stage;
		
		init();
		initPosition();
		initStage(stage);
		setEventHandlers();
	}

}
