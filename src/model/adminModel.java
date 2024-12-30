package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.makeConnection;

public class adminModel extends userModel{
	private static makeConnection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private List<userModel> admin;
	private List<eventModel> adminEvent;
	
	public adminModel(String user_id, String email, String name, String password, String role) {
		super(user_id, email, name, password, role);
		adminModel.con = makeConnection.getInstance();
		this.admin = new ArrayList<>();
		this.adminEvent = new ArrayList<>();
	}
	
	private String event_id;
	public String getEventId() {
		String query = "SELECT event_id FROM eventmodel";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			int i = 1;
			while(rs.next()) {
				System.out.println(i +" Event id: " + rs.getString("event_id"));
				event_id = rs.getString("event_id");
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return event_id;
	}
	
	public void viewEventDetail(String event_id) {
		event_id = getEventId();
		String query = "SELECT * from eventmodel WHERE event_id = ?";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, event_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("Event name: " + rs.getString("event_name"));				
				System.out.println("Event date: " + rs.getString("event_date"));				
				System.out.println("Event location: " + rs.getString("event_location"));				
				System.out.println("Event description: " + rs.getString("event_description"));
				System.out.println("Organizer id: " + rs.getString("organizer_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEvent(String id){
		String query = "DELETE FROM eventmodel WHERE event_id = ?";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, id);
			ps.execute();
			System.out.println("Event deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<userModel> getAllUser() {
		String query = "SELECT * FROM usermodel";
		admin.clear();
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				String user_id = rs.getString("user_id");
				String email = rs.getString("user_email");
				String name = rs.getString("user_name");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				admin.add(new userModel(user_id,email, name, password, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
		 
	}
	
	public void deleteUser(String userId) {
		String query = "DELETE FROM usermodel WHERE user_id = ?";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, userId);
			ps.execute();
			System.out.println("Data berhasil dihapus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getUserbyEmail() {
		String query = "SELECT user_email FROM usermodel";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("Email: " + rs.getString("user_email"));
			}
		} catch (SQLException e) {
			System.out.println("Error4: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getUserbyUsername() {
		String query = "SELECT user_name FROM usermodel";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			int i = 1;
			while(rs.next()) {
				System.out.println("name " + i + ": " + rs.getString("user_name"));
				i++;
			}
		}catch(SQLException e) {
			System.out.println("Error5: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<eventModel> getAllEvent(){
		String query = "SELECT * FROM eventmodel";
		adminEvent.clear();
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
				adminEvent.add(new eventModel(event_id, event_name, event_date, event_location, event_description, organizer_id));
				System.out.println(event_id);
				System.out.println(event_name);
				System.out.println(event_date);
				System.out.println(event_location);
				System.out.println(event_description);
				System.out.println(organizer_id);System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return adminEvent;
	}
	
	public List<Map<String, Object>> getEventDetailed(){
		List<Map<String, Object>> event_detail = new ArrayList<>();
		String query = "SELECT e.event_id, e.event_name, "
				+ "e.event_date, e.event_location, e.event_description, e.organizer_id, "
				+ "GROUP_CONCAT(DISTINCT u1.user_name) AS guest_name, "
				+ "GROUP_CONCAT(DISTINCT u2.user_name) AS vendor_name,"
				+ "i.invitation_status "
				+ "FROM invitation i "
				+ "JOIN eventmodel e ON i.event_id = e.event_id "
				+ "LEFT JOIN usermodel u1 ON i.user_id = u1.user_id AND u1.user_role = 'Guest' "
				+ "LEFT JOIN usermodel u2 ON i.user_id = u2.user_id AND u2.user_role = 'Vendor' "
				+ "WHERE i.invitation_status = 'accept' "
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
				String invStatus = rs.getString("invitation_status");
				
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("event_id", eventId);
                eventMap.put("event_name", eventName);
                eventMap.put("event_date", eventDate);
                eventMap.put("event_location", eventLoc);
                eventMap.put("event_description", eventDesc);
                eventMap.put("organizer_id", organizerId);
                eventMap.put("guest_name", guestName);
                eventMap.put("vendor_name", vendorName);
                eventMap.put("invitation_status", invStatus);
                
                event_detail.add(eventMap);
			}
		} catch (SQLException e) {
			System.out.println("Error di EOModel getEventDetailed: " + e.getMessage());
			e.printStackTrace();
		}
		return event_detail;
	}
	
}
