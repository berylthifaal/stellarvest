package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.adminModel;
import model.eventModel;
import model.userModel;

public class adminController extends userController{
	private adminModel admin;
	
	public adminController(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		admin = new adminModel(id, email, name, password, role);
	}
	
	public List<userModel> getAllUserController() {
		return admin.getAllUser();
	}
	
	public List<eventModel> getAllEventController() {
		return admin.getAllEvent();
	}
	
	public void deleteEventController(String id) {
		admin.deleteEvent(id);
	}

	public void deleteUserController(String id) {
		admin.deleteUser(id);
	}
	
	public List<Map<String, Object>> getEventDetailedController(){
		List<Map<String, Object>> event = new ArrayList<>();
		event = admin.getEventDetailed();
		return event;
	}
}
