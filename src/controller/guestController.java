package controller;

import java.util.ArrayList;
import java.util.List;

import model.eventModel;
import model.guestModel;

public class guestController extends userController{
	private guestModel guest;
	private eventModel event;
	private String eid, ename, edate, eloc, edesc, organizerid;

	public guestController(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		guest = new guestModel(id, email, name, password, role);
		event = new eventModel(eid, ename, edate, eloc, edesc, organizerid);
	}
	
	public List<eventModel> seeInvGuestController(){
		List<eventModel> events = new ArrayList<>();
		events = guest.seeInvitationGuest();
		return events;
	}
	
	public void acceptInvController(String eventid) {
		guest.acceptInv(eventid);
	}

	public List<eventModel> seeAcceptedInvController(){
		List<eventModel> events = new ArrayList<>();
		events = guest.seeAcceptedInv();
		return events;
	}
}
