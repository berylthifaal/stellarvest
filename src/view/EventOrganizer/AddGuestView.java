package view.EventOrganizer;

import java.util.List;import java.util.Map;

import controller.EOController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.eventModel;
import model.userModel;

public class AddGuestView extends BorderPane {
    private Stage stage;
    private Scene scene;
    private TableView<eventModel> eventTable;
    private TableView<userModel> guestTable;
    private TextField selectedEventField, selectedGuestField;
    private Button addInvitationBtn, backBtn;
    private Label errorLbl;
    private EOController EO;
    private String userId, eventId;

    public AddGuestView(Stage stage, EOController EO) {
        this.stage = stage;
        this.EO = EO;

        init();
        initPosition();
        initStage();
        setEventHandlers();
    }

    private void init() {
        // Initialize components
        eventTable = new TableView<>();
        guestTable = new TableView<>();
        setEventTableColumns();
        setGuestTableColumns();

        selectedEventField = new TextField();
        selectedEventField.setEditable(false);
        selectedGuestField = new TextField();
        selectedGuestField.setEditable(false);

        addInvitationBtn = new Button("Add Invitation");
        backBtn = new Button("Back");

        errorLbl = new Label();
        errorLbl.setTextFill(Color.RED);

        scene = new Scene(this, 900, 750);
    }

    private void initPosition() {
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));
        centerBox.setAlignment(Pos.CENTER);

        // Event Table
        Label eventTableLabel = new Label("Event Details");
        VBox eventBox = new VBox(5, eventTableLabel, eventTable);
        eventBox.setAlignment(Pos.CENTER);

        // Guest Table
        Label guestTableLabel = new Label("Guest List");
        VBox guestBox = new VBox(5, guestTableLabel, guestTable);
        guestBox.setAlignment(Pos.CENTER);

        // Fields for Selected Event and Guest
        GridPane fieldGrid = new GridPane();
        fieldGrid.setAlignment(Pos.CENTER);
        fieldGrid.setHgap(10);
        fieldGrid.setVgap(10);
        fieldGrid.add(new Label("Selected Event:"), 0, 0);
        fieldGrid.add(selectedEventField, 1, 0);
        fieldGrid.add(new Label("Selected Guest:"), 0, 1);
        fieldGrid.add(selectedGuestField, 1, 1);

        centerBox.getChildren().addAll(eventBox, guestBox, fieldGrid);
        this.setCenter(centerBox);

        // Bottom Buttons
        HBox btnBox = new HBox(10, addInvitationBtn, backBtn);
        btnBox.setAlignment(Pos.CENTER);
        VBox bottomBox = new VBox(10, btnBox, errorLbl);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        this.setBottom(bottomBox);
    }

    private void initStage() {
        stage.setScene(scene);
        stage.setTitle("Invitation Page");
        stage.centerOnScreen();
        stage.show();
    }

    @SuppressWarnings("unchecked")
	private void setEventTableColumns() {
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
        descCol.setMinWidth(200);

        eventTable.getColumns().addAll(nameCol, dateCol, locationCol, descCol);
        refreshEventTable();
    }

    private void setGuestTableColumns() {
        TableColumn<userModel, String> nameCol = new TableColumn<>("Guest Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        nameCol.setMinWidth(200);

        guestTable.getColumns().add(nameCol);
        refreshGuestTable();
    }

    private void refreshEventTable() {
        if (EO != null) {
            List<eventModel> events = EO.getAllEvent();
            if (events != null) {
                eventTable.getItems().addAll(events);
            } else {
                errorLbl.setText("No events found.");
            }
        } else {
            errorLbl.setText("No admin found.");
        }
    }

    private void refreshGuestTable() {
        if (EO != null) {
            List<userModel> guests = EO.getGuestController();
            if (guests != null) {
                guestTable.getItems().addAll(guests);
                System.out.println();
            } else {
                errorLbl.setText("No guests found.");
            }
        } else {
            errorLbl.setText("No admin found.");
        }
    }

    private void setEventHandlers() {
        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	eventId = newSelection.getEvent_id();
            	System.out.println(eventId);
                selectedEventField.setText(newSelection.getEvent_name());
            }
        });

        guestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	userId = newSelection.getUser_id();
            	System.out.println(userId);
                selectedGuestField.setText(newSelection.getUser_name());
            }
        });

        addInvitationBtn.setOnAction(e -> handleAddInvitation());
        backBtn.setOnAction(e -> new EventsView(stage, EO));
    }

    private void handleAddInvitation() {
        String selectedEvent = selectedEventField.getText();
        String selectedGuest = selectedGuestField.getText();

        if (selectedEvent.isEmpty() || selectedGuest.isEmpty()) {
            errorLbl.setText("Please select both an event and a guest.");
        } else {
        	EO.createInvitationController(eventId, userId, "pending", "Guest");
            errorLbl.setTextFill(Color.GREEN);
            errorLbl.setText("Invitation successfully created for " + selectedGuest + " to " + selectedEvent);
        }
    }
}
