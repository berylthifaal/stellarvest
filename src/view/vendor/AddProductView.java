package view.vendor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.EventOrganizer.EventsView;

public class AddProductView extends BorderPane{
	 Scene scene;
	 GridPane formContainer;
	 TextField nameField, descriptionField;
	 Label nameLbl, descriptionLbl, errorLbl, addLbl;
	 Button addBtn;
	 
	 public void init() {
		 scene = new Scene(this, 900, 750);

	        // Initialize components
	        nameField = new TextField();
	        descriptionField = new TextField();
	        
	        nameLbl = new Label("Name: ");
	        descriptionLbl = new Label("Description: ");
	        addLbl = new Label("Add Product");
	        addBtn = new Button("Add");
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
	        
	        formContainer.add(descriptionLbl, 0, 4);
	        formContainer.add(descriptionField, 1, 4);
	        descriptionField.setPrefWidth(inputWidth);
	        
	        formContainer.add(addBtn, 1, 5);
	        addBtn.setPrefWidth(100); // Smaller width for button
	        addBtn.setAlignment(Pos.CENTER);

	        // Add error label below the password field
	        formContainer.add(errorLbl, 1, 6); // Positioning errorLbl below the password field
	    }

	    public void initializeStage(Stage stage) {
	        // Style top label
	        addBtn.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
	        VBox topContainer = new VBox(addBtn);
	        topContainer.setAlignment(Pos.CENTER);
	        topContainer.setPadding(new Insets(20, 0, 0, 0)); // Reduced vertical padding
	        this.setTop(topContainer);

	        // Add form to the center
	        this.setCenter(formContainer);

	        // Add registration navigation at the bottom
	        VBox bottomContainer = new VBox(10, addBtn);
	        bottomContainer.setAlignment(Pos.CENTER);
	        bottomContainer.setPadding(new Insets(20, 10, 40, 10));
	        this.setBottom(bottomContainer);
	        addBtn.setStyle(
	                "-fx-background-color: #0078d7; " +
	                "-fx-text-fill: white; " +
	                "-fx-font-weight: bold; " +
	                "-fx-font-size: 14px;"
	            );
	        addBtn.setPrefWidth(200); // Wider button

	        stage.setScene(scene);
	        stage.setTitle("Add Product");
	        stage.centerOnScreen();
	        stage.show();
	    }

	    public void setEvent(Stage stage) {	        
	        addBtn.setOnAction(e -> {
	        	new ManageProductView(stage);
	        });

	    }
	public AddProductView(Stage stage) {
       init();
       initializeForm();
       initializeStage(stage);
       setEvent(stage);	
    }
}
