package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.db2.cmx.runtime.internal.repository.api.DataManager;

import bean.UserBean;

public class UserDAO {
	private Connection con;
	private String url = "jdbc:db2://dashdb-txn-sbox-yp-dal09-08.services.dal.bluemix.net:50000/BLUDB";
	private String user = "sgr65162";
	private String password = "7-z7882xkzvpmxth";

	public UserDAO() throws ClassNotFoundException {		
		try {
			// Load the IBM Data Server Driver for JDBC and SQLJ with DriverManager
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Create Connection
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public List<UserBean> retrieveAllUsers() throws SQLException {
		String query = "SELECT * FROM USERS";
		List<UserBean> users = new ArrayList<UserBean>();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String username = r.getString("USERNAME");
			String password = r.getString("PASSWORD");
	
			UserBean user = new UserBean(username, password);
			users.add(user);
		}
		r.close();
		p.close();
		return users;
	}
	
	public int insertUser(String username, String password) throws SQLException {
		String query = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES  (?,?)";
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, username);
		p.setString(2, password);		
		int i = p.executeUpdate();
		return i;
	}
}
