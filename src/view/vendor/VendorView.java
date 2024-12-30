package view.vendor;

import java.util.List;

import controller.vendorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.eventModel;
import view.LoginView;

public class VendorView extends BorderPane {
    Stage stage;
    Scene scene;
    TableView<eventModel> table;
    List<eventModel> event;
    VBox box;
    HBox btnBox;
    GridPane grid;
    Button logoutBtn, viewAcceptedBtn, acceptBtn, manageBtn;
    Label errorLbl, successLbl;
    vendorController vendor;
    String eventid;

    private void init() {
        grid = new GridPane();
        box = new VBox();
        btnBox = new HBox();
        scene = new Scene(this, 900, 750);

        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);

        successLbl = new Label();
        successLbl.setTextFill(Color.GREEN);

        logoutBtn = new Button("Log Out");
        viewAcceptedBtn = new Button("View Accepted Invitation");
        acceptBtn = new Button("Accept Invitation");
        manageBtn = new Button("Manage Product");

        table = new TableView<>();
        setTableColumns();

        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(10);
        btnBox.getChildren().addAll(acceptBtn, viewAcceptedBtn, manageBtn, logoutBtn);

        addTableClickHandler();
    }

    private void initStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Vendor Page");
        stage.centerOnScreen();
        stage.show();
    }

    private void setTableColumns() {
        TableColumn<eventModel, String> nameCol = new TableColumn<>("Event Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("event_name"));
        nameCol.setMinWidth(150);

        TableColumn<eventModel, String> dateCol = new TableColumn<>("Event Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("event_date"));
        dateCol.setMinWidth(150);

        TableColumn<eventModel, String> locationCol = new TableColumn<>("Event Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("event_description"));
        locationCol.setMinWidth(150);

        TableColumn<eventModel, String> descriptionCol = new TableColumn<>("Event Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("event_location"));
        descriptionCol.setMinWidth(150);

        table.getColumns().addAll(nameCol, dateCol, locationCol, descriptionCol);
        refreshTable();
    }

    private void refreshTable() {
        if (vendor != null) {
            event = vendor.seeInvVendorController();
            if (event != null) {
                table.getItems().setAll(event); // Menggunakan setAll untuk mengganti seluruh item di tabel
            } else {
                errorLbl.setText("No event found");
            }
        } else {
            errorLbl.setText("No admin");
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

        // Add buttons and labels to a VBox
        VBox bottomBox = new VBox(10); // Vertical spacing of 10
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10, 10, 30, 10)); // Additional padding
        bottomBox.getChildren().addAll(btnBox, errorLbl, successLbl); // Add btnBox, errorLbl, and successLbl

        this.setBottom(bottomBox); // Set VBox as the bottom component
        BorderPane.setAlignment(bottomBox, Pos.CENTER);
    }

    private void setEventHandlers() {
        logoutBtn.setOnAction(e -> new LoginView(stage));
        viewAcceptedBtn.setOnAction(e -> new AcceptedInvitationView(stage, vendor));
        acceptBtn.setOnAction(e -> handleAccept());
        manageBtn.setOnAction(e -> new ManageProductView(stage));
    }

    private void addTableClickHandler() {
        table.setRowFactory(tv -> {
            TableRow<eventModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    eventModel clickedEvent = row.getItem();
                    eventid = clickedEvent.getEvent_id();
                    successLbl.setText("Selected Event: " + clickedEvent.getEvent_name());
                    
                }
            });
            return row;
        });
    }

    private void handleAccept() {
    	if(eventid != null) {
			vendor.acceptInvController(eventid);
			successLbl.setText("Invitation accepted successfully!");			
		}else {
			errorLbl.setText("No event selected");
		}
        
    }

    public VendorView(Stage stage, vendorController vendor) {
        this.stage = stage;
        this.vendor = vendor;

        init();
        initPosition();
        initStage(stage);
        setEventHandlers();
    }
}
