package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.makeConnection;

public class userModel {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	private static makeConnection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public userModel(String user_id, String email, String name, String password, String role ) {
		this.user_id = user_id;
		this.user_email = email;
		this.user_name = name;
		this.user_password = password;
		this.user_role = role;
		userModel.con = makeConnection.getInstance();
	}

	
	public String getUser_id() {
		return user_id;
	}
	

	public void setUser_id(String user_id) {
		if(user_id == null || user_id.isEmpty()) {
			this.user_id = generateId();
		}else {			
			this.user_id = user_id;
		}
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getUser_password() {
		return user_password;
	}


	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}


	public String getUser_role() {
		return user_role;
	}


	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}


	public void register(userModel user) {
		if(user.getUser_id() == null || user.getUser_id().isEmpty()) {
			user.setUser_id(null);
		}
		String query = "INSERT INTO usermodel VALUES (?,?,?,?,?)";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, user.getUser_id());
			ps.setString(2, user.getUser_email());
			ps.setString(3, user.getUser_name());
			ps.setString(4, user.getUser_password());
			ps.setString(5, user.getUser_role());
			ps.execute();
		} catch (SQLException e) {
			System.out.println("error: " + e.getMessage());	
		}
	}
	
	public String login(String username, String pass) {
		String query = "SELECT * FROM usermodel WHERE user_name = ? AND user_password = ?";
		ps = con.prepareStatement(query);
		try {
			ps.setString(1, username);
			ps.setString(2, pass);
			
			try {
	 			rs = ps.executeQuery();
				if(rs.next()) {
					System.out.println("Welcome " + rs.getString("user_name"));
					user_email = rs.getString("user_email");
					user_name = rs.getString("user_name");
					user_password = rs.getString("user_password");
					user_role = rs.getString("user_role");
				} else {
					System.out.println("Wrong credential");
					return "NULL";
				}
			}catch(Exception e) {
				System.out.println("Error1: " + e.getMessage());
			}
		}catch (SQLException e) {
			System.out.println("Error2: " + e.getMessage());
		}
		return user_role;
	}
	
	public boolean changeProfile(String currentId, String newEmail, String newName, String newPass) {
		if(currentId == null) {
			System.out.println("no id");
			return false;
		}
		StringBuilder query = new StringBuilder("UPDATE usermodel SET ");
		boolean needComma = false;
		if (newEmail != null && !newEmail.isEmpty()) {
			if(needComma) query.append(", ");
			query.append("user_email = ?");
			needComma = true;
		}
		
		if (newName != null && !newName.isEmpty()) {
			if(needComma) query.append(", ");
			query.append("user_name = ?");
			needComma = true;
		}
		
		if (newPass != null && !newPass.isEmpty()) {
			if(needComma) query.append(", ");
			query.append("user_password = ?");
			needComma = true;
		}
		query.append("WHERE user_id = ?");
		
		if(!query.toString().contains("SET")) {
			System.out.println("No updates");
			return false;
		}
		
		try {
			ps = con.prepareStatement(query.toString());
			int paramIndex = 1;
			
			if(newEmail != null && !newEmail.isEmpty()) {
				ps.setString(paramIndex++, newEmail);
			}
			
			if(newName != null && !newName.isEmpty()) {
				ps.setString(paramIndex++, newName);
			}
			
			if(newPass != null && !newPass.isEmpty()) {
				ps.setString(paramIndex++, newPass);
			}
			ps.setString(paramIndex, currentId);
			
			int rowUpdated = ps.executeUpdate();
			if(rowUpdated > 0) {
				System.out.println("Updated new profile");
				return true;
			}else {
				System.out.println("No updated");
				return false;
			}
		}catch(Exception e) {
			System.out.println("Error3: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public String generateId() {
		String prefix = "us";
		int nextNumber = 1;
		String query = "SELECT user_id FROM usermodel ORDER BY user_id DESC LIMIT 1";
		ps = con.prepareStatement(query);
		try {
			rs = ps.executeQuery();
			if(rs.next()) {				
				String lastId = rs.getString("user_id");
				String numberPart = lastId.substring(2);
				nextNumber = Integer.parseInt(numberPart) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix + String.format("%03d", nextNumber);
	}
}
