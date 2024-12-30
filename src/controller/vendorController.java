package controller;

import java.util.ArrayList;
import java.util.List;

import model.eventModel;
import model.vendorModel;

public class vendorController extends userController{
	private vendorModel vendor;
	private eventModel event;
	private String eid, ename, edate, eloc, edesc, organizerid;

	public vendorController(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		vendor = new vendorModel(id, email, name, password, role);
		event = new eventModel(eid, ename, edate, eloc, edesc, organizerid);
	}
		

	public List<eventModel> seeInvVendorController() {
		List<eventModel> events = new ArrayList<>();
		events = vendor.seeInvitationVendor();
		return events;
	}
	
	public void acceptInvController(String eventid) {
		vendor.acceptInv(eventid);
	}
	
	public List<eventModel> seeAcceptedInvController(){
		List<eventModel> events = new ArrayList<>();
		events = vendor.seeAcceptedInv();
		return events;
	}
}
