package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class makeConnection {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String HOST = "localhost:3306";
	private final String DATABASE  = "stellarvest";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	
	private Connection connect;
	private Statement st;
	
	public makeConnection() {
		try {
			connect = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = connect.createStatement();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static makeConnection con = null;
	public static makeConnection getInstance() {
		if(con == null) {
			return con = new makeConnection();
		}
		return con;
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = connect.prepareStatement(query);
		}catch(SQLException e) {
			System.out.println("erorr prepared statement: " + e.getMessage());
			e.printStackTrace();
		}
		return ps;
	}
	
	
}
