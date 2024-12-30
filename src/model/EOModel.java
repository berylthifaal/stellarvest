package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.makeConnection;

public class EOModel extends userModel{
	private static makeConnection con;
	private static PreparedStatement ps;
	private static ResultSet rs;
	private static List<eventModel> event;
	private static List<userModel> user;
	private static List<Map<String, Object>> event_detail;
	private static eventModel Eventmodel;
	private static String event_created;
	private String id,ename, edate, eloc, edesc, id2;
	
	public EOModel(String user_id, String email, String name, String password, String role) {
		super(user_id, email, name, password, role);
		EOModel.con = makeConnection.getInstance();
		event = new ArrayList<>();
		user = new ArrayList<>();
		event_detail = new ArrayList<>();
		Eventmodel = new eventModel(id, ename, edate, eloc, edesc, id2);
	}
	
	public static void setEventCreated() {
		event_created = "Created";
		String query = "INSERT INTO eventorganizer VALUES (?)";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, event_created);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<eventModel> getAllEvent(){
		String query = "SELECT * FROM eventmodel";
		event.clear();
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String event_id = rs.getString("event_id");
				String event_name = rs.getString("event_name");
				String event_date = rs.getString("event_date");
				String event_location = rs.getString("event_location");
				String event_description = rs.getString("event_description");
				String organizer_id = rs.getString("organizer_id");
				event.add(new eventModel(event_id, event_name, event_date, event_location, event_description, organizer_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}
	
	public eventModel createEvent(String id, String name, String date, String location, String desc, String organizer_id) {
		Eventmodel = new eventModel("null", name, date, location, desc, organizer_id);
		Eventmodel.createEvent(name, date, location, desc, organizer_id);
		setEventCreated();
		return Eventmodel;
	}
	
	public List<userModel> getVendor(){
		String user_id;
		String user_name;
		String user_role;
		String query = "SELECT user_id, user_name, user_role FROM usermodel WHERE user_role = ?";
		user.clear();
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, "Vendor");
			rs = ps.executeQuery();
			while(rs.next()) {
				user_id = rs.getString("user_id");
				user_name = rs.getString("user_name");
				user_role = rs.getString("user_role");
				user.add(new vendorModel(user_id, "", user_name, "", user_role));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	

	public List<userModel> getGuest(){
		String user_id;
		String user_name;
		String user_role;
		String query = "SELECT user_id, user_name, user_role FROM usermodel WHERE user_role = ?";
		user.clear();
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, "Guest");
			rs = ps.executeQuery();
			while(rs.next()) {
				user_id = rs.getString("user_id");
				user_name = rs.getString("user_name");
				user_role = rs.getString("user_role");
				user.add(new userModel(user_id, "", user_name, "", user_role));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<Map<String, Object>> getEventDetailed(){
		String query = "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description, e.organizer_id, GROUP_CONCAT(DISTINCT u1.user_name) AS guest_name, GROUP_CONCAT(DISTINCT u2.user_name) AS vendor_name "
				+ "FROM invitation i "
				+ "JOIN eventmodel e ON i.event_id = e.event_id "
				+ "LEFT JOIN usermodel u1 ON i.user_id = u1.user_id AND u1.user_role = 'Guest' "
				+ "LEFT JOIN usermodel u2 ON i.user_id = u2.user_id AND u2.user_role = 'Vendor' "
				+ "GROUP BY e.event_id";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String eventId = rs.getString("event_id");
				String eventName = rs.getString("event_name");
				String eventDate = rs.getString("event_date");
				String eventLoc = rs.getString("event_location");
				String eventDesc = rs.getString("event_description");
				String organizerId = rs.getString("organizer_id");
				String guestName = rs.getNString("guest_name");
				String vendorName = rs.getNString("vendor_name");
				
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("event_id", eventId);
                eventMap.put("event_name", eventName);
                eventMap.put("event_date", eventDate);
                eventMap.put("event_location", eventLoc);
                eventMap.put("event_description", eventDesc);
                eventMap.put("organizer_id", organizerId);
                eventMap.put("guest_name", guestName);
                eventMap.put("vendor_name", vendorName);
                
                event_detail.add(eventMap);
			}
		} catch (SQLException e) {
			System.out.println("Error di EOModel getEventDetailed: " + e.getMessage());
			e.printStackTrace();
		}
		return event_detail;
	}
	
	public void updateEvent(String eventId, String eventName, String eventDate, String eventLocation,
			String eventDesc, String organizerId) {
		Eventmodel = new eventModel(eventId, eventName, eventDate, eventLocation, eventDesc, organizerId);
		String query = "UPDATE eventmodel "
				+ "SET event_name = ?, event_date = ?, event_location = ?, event_description = ? "
				+ "WHERE event_id = ?";
		ps = con.prepareStatement(query);
		if(Eventmodel == null) {
			System.out.println("Error query");
		}
			try {
				ps.setString(1, eventName);
				ps.setString(2, eventDate);
				ps.setString(3, eventLocation);
				ps.setString(4, eventDesc);
				ps.setString(5, eventId);
				System.out.println(Eventmodel.getEvent_description());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public List<userModel> getGuestbyInvRole() {
		String query = "SELECT u.user_name "
				+ "FROM invitation i "
				+ "JOIN usermodel u ON i.user_id = u.user_id AND u.user_role = 'Guest' "
				+ "WHERE (i.invitation_status != 'pending' OR i.invitation_status IS NULL)";
		user.clear();
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				String guestName = rs.getString("user_name");
				user.add(new userModel("", guestName, "", "", ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void createInvitation( String eventId, String userId, String invRole, String status) {
		invitationModel invite = new invitationModel("null", eventId, userId, invRole, status);
		invite.createInvitation(eventId, userId, invRole, status);
	}
}
 