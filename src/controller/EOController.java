package controller;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.EOModel;
import model.eventModel;
import model.userModel;
import model.vendorModel;

public class EOController extends userController{
	
	private static EOModel EO;
	private List <userModel> user;
	private List<Map<String, Object>> event_detail;
	private eventModel event;
	public EOController(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role); 
		EO = new EOModel(id, email, name, password, role);
	}
	
	public List<eventModel> getAllEvent() {
		return EO.getAllEvent();
	}
	
	public EOModel createEventController(String id, String name, String date, String location, String desc, String organizer_id) {
		EO.createEvent("null", name, date, location, desc, organizer_id);
		System.out.println("Berhasil buat event baru");
		return EO;
	}
	
	public List<userModel> getVendorController(){
		user = EO.getVendor();
		return user;
	}
	
	public List<userModel> getGuestController(){
		user = EO.getGuest();
		return user;
	}
	
	public List<Map<String, Object>> getEventDetailedController(){
		event_detail = EO.getEventDetailed();
		return event_detail;
	}
	
	public void updateEventController(String eventId, String eventName, String eventDate, String eventLocation,
			String eventDesc, String organizerId) {
		EO.updateEvent(eventId, eventName, eventDate, eventLocation, eventDesc, organizerId);
	}
	
	public List<userModel> getGuestbyInvController(){
		user = EO.getGuestbyInvRole();
		return user;
	}
	
	public void createInvitationController(String eventId, String userId, String invRole, String status) {
		EO.createInvitation( eventId, userId, invRole, status);
	}
}
