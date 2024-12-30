package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.makeConnection;

public class eventModel {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	
	private static makeConnection con;
	private static PreparedStatement ps;
	private static ResultSet rs;
	
	public eventModel(String event_id, String event_name, String event_date, String event_location,
			String event_description, String organizer_id) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_date = event_date;
		this.event_location = event_location;
		this.event_description = event_description;
		this.organizer_id = organizer_id;
		eventModel.con = makeConnection.getInstance();
	}
	
	

	public String getEvent_id() {
		return event_id;
	}


	public void setEvent_id(String event_id) {
		if(event_id == null || event_id.isEmpty()) {
			this.event_id = generateEventId();
		}else {			
			this.event_id = event_id;
		}
	}


	public String getEvent_name() {
		return event_name;
	}


	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}


	public String getEvent_date() {
		return event_date;
	}


	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}


	public String getEvent_location() {
		return event_location;
	}


	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}


	public String getEvent_description() {
		return event_description;
	}


	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}


	public String getOrganizer_id() {
		return organizer_id;
	}


	public void setOrganizer_id(String organizer_id) {
		this.organizer_id = organizer_id;
	}
	
	public String generateEventId() {
		String prefix = "ev";
		int nextNumber = 1;
		String query = "SELECT event_id FROM eventmodel ORDER BY event_id DESC LIMIT 1";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			if(rs.next()) {				
				String lastId = rs.getString("event_id");
				String numberPart = lastId.substring(2);
				nextNumber = Integer.parseInt(numberPart) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix + String.format("%03d", nextNumber);
	}
	
	public void createEvent(String event_name, String event_date, String event_location, String event_desc, String organizer_id) {
		String genId = generateEventId();
		String query = "INSERT INTO eventmodel VALUES(?, ?, ?, ?, ?, ?)";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, genId);
			ps.setString(2, event_name);
			ps.setString(3, event_date);
			ps.setString(4, event_desc);
			ps.setString(5, event_location);
			ps.setString(6, organizer_id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error di event model: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getEventName(String eventid) {
		String query = "SELECT event_name FROM eventmodel WHERE event_id = ?";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				String eventName = rs.getString("event_name");
				return eventName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "null";
	}

}
