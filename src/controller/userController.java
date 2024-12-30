package controller;

import model.userModel;

public class userController{
	private userModel user;
	public userController(String id, String email, String name, String password, String role ) {
		user = new userModel(id, email, name, password, role);
	}

	
	public boolean registerController(userModel users) {
		
		if(users.getUser_email().equalsIgnoreCase("break")) return false;
		if(users.getUser_email().isEmpty()) {
			System.out.println("It cant be empty");
			return false;
		}
		if(users.getUser_name().isEmpty()) {
			System.out.println("username cant be empty");
			return false;
		}
		if(users.getUser_password().isEmpty()) {
			System.out.println("Password cant be empty ");
			return false;
		}
		if(users.getUser_role().isEmpty()) {
			System.out.println("Role cant be empty");
			return false;
		}
		users.setUser_id(users.getUser_id());
		users.setUser_email(users.getUser_email());
		users.setUser_name(users.getUser_name());
		users.setUser_password(users.getUser_password());
		users.setUser_role(users.getUser_role());
		users.register(users);
		System.out.println("berhasil");
		return true;
	}
	
	public String loginController(String username, String password) {
		if(username.isEmpty() || password.isEmpty()) {
			System.out.println("Error occured");
		}
		return user.login(username, password);
		
	}
		
}
