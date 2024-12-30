package view.EventOrganizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.EOController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.RegisterView;

public class CreateEventView extends BorderPane{
	 Scene scene;
	 GridPane formContainer;
	 TextField nameField, locationField, descriptionField, organizerField;
	 DatePicker dateField;
	 Label nameLbl, locationLbl, descriptionLbl, dateLbl, errorLbl, createLbl, organizerLbl;
	 Button createBtn;
	 EOController EO;
	 String tempId;
	 
	 public void init() {
		 scene = new Scene(this, 900, 750);

	        // Initialize components
	        nameField = new TextField();
	        locationField = new TextField();
	        descriptionField = new TextField();
	        organizerField = new TextField();
	        dateField = new DatePicker();
	        
	        nameLbl = new Label("Name: ");
	        locationLbl = new Label("Location: ");
	        descriptionLbl = new Label("Description: ");
	        organizerLbl = new Label("Organizer Id: ");
	        dateLbl = new Label("Date: ");
	        createLbl = new Label("Create Event");
	        createBtn = new Button("Create");
	        errorLbl = new Label();  

	        errorLbl.setStyle("-fx-text-fill: red;"); // Style the error message
	    }

	    public void initializeForm() {
	        formContainer = new GridPane();
	        formContainer.setHgap(20); // Horizontal spacing between columns
	        formContainer.setVgap(20); // Vertical spacing between rows
	        formContainer.setAlignment(Pos.CENTER); // Center the form
	        formContainer.setPadding(new Insets(20, 20, 20, 20)); // Padding around the form

	        // Add components to the form
	        double inputWidth = 300;

	        formContainer.add(nameLbl, 0, 0);
	        formContainer.add(nameField, 1, 0);
	        nameField.setPrefWidth(inputWidth);

	        formContainer.add(dateLbl, 0, 1);
	        formContainer.add(dateField, 1, 1);
	        dateField.setPrefWidth(inputWidth);

	        formContainer.add(locationLbl, 0, 3);
	        formContainer.add(locationField, 1, 3);
	        locationField.setPrefWidth(inputWidth);
	        
	        formContainer.add(descriptionLbl, 0, 4);
	        formContainer.add(descriptionField, 1, 4);
	        descriptionField.setPrefWidth(inputWidth);
	        
	        formContainer.add(organizerLbl, 0, 5);
	        formContainer.add(organizerField, 1, 5);
	        organizerField.setPrefWidth(inputWidth);
	        
	        formContainer.add(createBtn, 1, 5);
	        createBtn.setPrefWidth(100); // Smaller width for button
	        createBtn.setAlignment(Pos.CENTER);

	        // Add error label below the password field
	        formContainer.add(errorLbl, 1, 6); // Positioning errorLbl below the password field
	    }

	    public void initializeStage(Stage stage) {
	        // Style top label
	        createLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
	        VBox topContainer = new VBox(createLbl);
	        topContainer.setAlignment(Pos.CENTER);
	        topContainer.setPadding(new Insets(20, 0, 0, 0)); // Reduced vertical padding
	        this.setTop(topContainer);

	        // Add form to the center
	        this.setCenter(formContainer);

	        // Add registration navigation at the bottom
	        VBox bottomContainer = new VBox(10, createBtn);
	        bottomContainer.setAlignment(Pos.CENTER);
	        bottomContainer.setPadding(new Insets(20, 10, 40, 10));
	        this.setBottom(bottomContainer);
	        createBtn.setStyle(
	                "-fx-background-color: #0078d7; " +
	                "-fx-text-fill: white; " +
	                "-fx-font-weight: bold; " +
	                "-fx-font-size: 14px;"
	            );
	        createBtn.setPrefWidth(200); // Wider button

	        stage.setScene(scene);
	        stage.setTitle("Create Event");
	        stage.centerOnScreen();
	        stage.show();
	    }
	    
	    public String dateFormat() {
	    	LocalDate local = dateField.getValue();
	    	if(local != null) {
	    		String formattedDate = local.format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
	    		return formattedDate;
	    	}
	    	return "NULL";
	    }

	    public void setEvent(Stage stage) {	        
	        createBtn.setOnAction(e -> {
	        	String Ename = nameField.getText();
	        	String Edate = dateField.getValue().toString();
	        	String Eloc = locationField.getText();
	        	String Edesc = descriptionField.getText();
	        	String Eorganizer = organizerField.getText();
	        	if(Ename.isEmpty() || Edate.isEmpty() || Eloc.isEmpty() || Edesc.isEmpty() || Eorganizer.isEmpty()) {
	        		errorLbl.setText("Text must be filled and cant be empty");
	        	}else if(Edesc.length() < 10){
	        		errorLbl.setText("Desc must be long and more then 10 char");
	        	}
	        	EO.createEventController("null", Ename, Edate, Eloc, Edesc, Eorganizer);
	        	new EventsView(stage, EO);	        			        		
	        });

	    }
	public CreateEventView(Stage stage, EOController EO) {
		this.EO = EO;
        init();
        initializeForm();
        initializeStage(stage);
        setEvent(stage);	
     }

}
