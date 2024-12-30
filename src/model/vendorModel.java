package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import utils.makeConnection;

public class vendorModel extends userModel{
	private makeConnection con;
	
	
	public vendorModel(String user_id, String email, String name, String password, String role) {
		super(user_id, email, name, password, role);
		this.con = makeConnection.getInstance();
		
	}

	public List<eventModel> seeInvitationVendor() {
		String query = "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description "
				+ "FROM invitation i "
				+ "JOIN eventmodel e ON i.event_id = e.event_id "
				+ "WHERE i.invitation_status = 'pending' and i.invitation_role = 'Vendor'";
		List<eventModel> events = new ArrayList<>();
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String eventid = rs.getString("event_id");
				String name = rs.getString("event_name");
				String date = rs.getString("event_date");
				String location = rs.getString("event_location");
				String desc = rs.getString("event_description");
				events.add(new eventModel(eventid, name, date, location, desc, ""));
			}
		} catch (SQLException e) {
			System.out.println("Error msg vendorModel: " + e.getMessage());
			e.printStackTrace();
		}
		return events;
	}
	
	public void acceptInv(String eventid) {
		String query = "UPDATE invitation "
				+ "SET invitation_status = 'accept' "
				+ "WHERE event_id = ? AND invitation_role = 'Vendor'";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, eventid);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<eventModel> seeAcceptedInv() {
		String query = "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description "
				+ "FROM invitation i "
				+ "JOIN eventmodel e ON i.event_id = e.event_id "
				+ "WHERE i.invitation_status = 'accept' and i.invitation_role = 'Vendor'";
		List<eventModel> events = new ArrayList<>();
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String eventid = rs.getString("event_id");
				String name = rs.getString("event_name");
				String date = rs.getString("event_date");
				String location = rs.getString("event_location");
				String desc = rs.getString("event_description");
				events.add(new eventModel(eventid, name, date, location, desc, ""));
			}
		} catch (SQLException e) {
			System.out.println("Error msg vendorModel: " + e.getMessage());
			e.printStackTrace();
		}
		return events;
	}
}
