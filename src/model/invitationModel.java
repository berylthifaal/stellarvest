package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.makeConnection;

public class invitationModel {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	private static makeConnection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public invitationModel(String invitation_id, String event_id, String user_id, String invitation_status,
			String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
		invitationModel.con = makeConnection.getInstance();
	}
	public String getInvitation_id() {
		return invitation_id;
	}
	public void setInvitation_id(String invitation_id) {
		if(invitation_id == null || invitation_id.isEmpty()) {
			this.invitation_id = generateInvId();
		}else {			
			this.invitation_id = invitation_id;
		}
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getInvitation_status() {
		return invitation_status;
	}
	public void setInvitation_status(String invitation_status) {
		this.invitation_status = invitation_status;
	}
	public String getInvitation_role() {
		return invitation_role;
	}
	public void setInvitation_role(String invitation_role) {
		this.invitation_role = invitation_role;
	}
	
	public String generateInvId() {
		String prefix = "iv";
		int nextNumber = 1;
		String query = "SELECT invitation_id FROM invitation ORDER BY invitation_id DESC LIMIT 1";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			if(rs.next()) {				
				String lastId = rs.getString("invitation_id");
				String numberPart = lastId.substring(2);
				nextNumber = Integer.parseInt(numberPart) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix + String.format("%03d", nextNumber);
	}
	
	public void createInvitation(String eventId, String userId, String invRole, String status) {
		String query = "INSERT INTO invitation VALUES (?,?,?,?,?)";
		String invId = generateInvId();
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, invId);
			ps.setString(2, eventId);
			ps.setString(3, userId);
			ps.setString(4, invRole);
			ps.setString(5, status);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error in invitation Model: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
