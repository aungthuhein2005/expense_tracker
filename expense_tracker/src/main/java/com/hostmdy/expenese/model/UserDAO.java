package com.hostmdy.expenese.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.expenese.crypto.PasswordEncoder;
import com.hostmdy.expenese.crypto.PasswordValidator;

public class UserDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	private DataSource dataSource;
	
	public UserDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	private void close() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean disableUser(Long userId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("update user set enable=? where id=?;");
			pStmt.setBoolean(1, false);
			pStmt.setLong(2, userId);
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) {
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return ok;
	}
	
	public boolean enableUser(Long userId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("update user set enable=? where id=?;");
			pStmt.setBoolean(1, true);
			pStmt.setLong(2, userId);
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) {
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return ok;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<User>();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from user where role='user';");
			while(rs.next()) {
				users.add(new User(
						rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("email"),
						rs.getBoolean("enable")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public User getUserByEmail(String email) {
		User user = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from user where email='"+email+"';");
			while(rs.next()) {
				user = new User(
						rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role"),
						rs.getBoolean("enable")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return user;
	}
	
	public boolean isAuthenticated(String email,String password) {
		boolean ok = false;
		User user = getUserByEmail(email);
		try {
			if(user != null && user.isEnable() && PasswordValidator.validatePassword(password, user.getPassword())) {
				ok = true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean createUser(User user) {
		
		
		boolean ok = false;
		
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("insert into user "
					+ "(firstname,lastname,email,password,enable,role) "
					+ "values (?,?,?,?,?,?);");
			pStmt.setString(1, user.getFirstName());
			pStmt.setString(2, user.getLastName());
			pStmt.setString(3, user.getEmail());
			try {
				pStmt.setString(4, PasswordEncoder.encode(user.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pStmt.setBoolean(5, user.isEnable());
			pStmt.setString(6, "user");
		int rowEffected = pStmt.executeUpdate();
		if(rowEffected > 0) {
			ok = true;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return ok; 
	}
}
