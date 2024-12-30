package view;

import controller.userController;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.EOModel;
import model.adminModel;
import model.guestModel;
import model.vendorModel;
import view.EventOrganizer.EventsView;
import view.guest.GuestView;
import view.vendor.VendorView;

public class RegisterView extends BorderPane{
	Stage stage;
	Scene scene;
	GridPane formContainer;
	TextField emailField, usernameField;
	PasswordField passwordField;
	ComboBox<String> roleBox;
	Label emailLbl, usernameLbl, passwordLbl, roleLbl, registerLbl, errorLbl, intentloginLbl;
	Button registerBtn;
	userController UserController;
	adminModel admin;
	EOModel eo;
	vendorModel vendor;
	guestModel guest;
	String user_id;
	
	public void init() {
		emailField = new TextField();
		usernameField = new TextField();
		passwordField = new PasswordField();
		roleBox = new ComboBox<>();
		
		emailLbl = new Label("Email: ");
		usernameLbl = new Label("Username: ");
		passwordLbl = new Label("Password: ");
	    errorLbl = new Label();
        errorLbl.setStyle("-fx-text-fill: red;");
        roleLbl = new Label("Role: ");
        intentloginLbl = new Label("Already have an account? Login here.");
        
        registerBtn = new Button("Register");
		
        formContainer = new GridPane();
	}
	
	public void initForm() {
		roleBox.getItems().addAll("","Event Organizer", "Vendor", "Guest", "Admin");
		roleBox.getSelectionModel().selectFirst();
		
        formContainer.setHgap(20); 
        formContainer.setVgap(20);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20, 20, 20, 20)); 

        double inputWidth = 350;
        formContainer.add(emailLbl, 0, 0);
        formContainer.add(emailField, 1, 0);
        usernameField.setPrefWidth(inputWidth);
        
        formContainer.add(usernameLbl, 0, 1);
        formContainer.add(usernameField, 1, 1);
        usernameField.setPrefWidth(inputWidth);
        
        formContainer.add(passwordLbl, 0, 2);
        formContainer.add(passwordField, 1, 2);
        usernameField.setPrefWidth(inputWidth);
        
        formContainer.add(roleLbl, 0, 3);
        formContainer.add(roleBox, 1, 3);
        usernameField.setPrefWidth(inputWidth);
        
        formContainer.add(errorLbl, 0, 4, 2, 1);
        errorLbl.setStyle("-fx-text-fill: red;");
	}
	
	public void initStage() {
	      this.setCenter(formContainer);

	        registerLbl = new Label("Register");
	        registerLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
	        VBox topContainer = new VBox(registerLbl);
	        topContainer.setAlignment(Pos.CENTER);
	        topContainer.setPadding(new Insets(20, 0, 0, 0)); // Reduced vertical padding
	        this.setTop(topContainer);

	        intentloginLbl.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");

	        VBox bottomContainer = new VBox(10, registerBtn, intentloginLbl);
	        bottomContainer.setAlignment(Pos.CENTER);
	        bottomContainer.setPadding(new Insets(20, 10, 40, 10));
	        this.setBottom(bottomContainer);

	        registerBtn.setStyle(
	            "-fx-background-color: #0078d7; " +
	            "-fx-text-fill: white; " +
	            "-fx-font-weight: bold; " +
	            "-fx-font-size: 14px;"
	        );
	        registerBtn.setPrefWidth(200);		
	        
	        scene = new Scene(this, 900, 750); 
	        stage.setScene(scene);
	        stage.setTitle("User Registration");
	        stage.centerOnScreen();
	        stage.show();
	}
	
	public void setEvent() {
		registerBtn.setOnMouseClicked(e -> {
			String email = emailField.getText();
			String username = usernameField.getText();
			String password = passwordField.getText();
			String role = roleBox.getSelectionModel().getSelectedItem();
			if(email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
				errorLbl.setText("Field cant be empty");
			}
			if(role.equals("Admin")) {
				admin = new adminModel(user_id, email, username, password, role);
				UserController = new userController(user_id, email, username, password, role);
				UserController.registerController(admin);
				new LoginView(stage);
			}
			if(role.equals("Event Organizer")) {
				eo = new EOModel(user_id, email, username, password, role);
				UserController = new userController(user_id, email, username, password, role);
				UserController.registerController(eo);
				new LoginView(stage);
			}
			if(role.equals("Vendor")) {
				vendor = new vendorModel(user_id, email, username, password, role);
				UserController = new userController(user_id, email, username, password, role);
				UserController.registerController(vendor);
				new LoginView(stage);
			}
			if(role.equals("Guest")) {
				guest = new guestModel(user_id, email, username, password, role);
				UserController = new userController(user_id, email, username, password, role);
				UserController.registerController(guest);
				new LoginView(stage);
			}
			
		});
        intentloginLbl.setOnMouseClicked(e -> new LoginView(stage));


	}
	
	public RegisterView(Stage stage) {
	       this.stage = stage;
	        init();
	        initForm();
	        initStage();
	        setEvent();
	}
}
