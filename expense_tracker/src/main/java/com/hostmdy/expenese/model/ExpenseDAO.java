package com.hostmdy.expenese.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ExpenseDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private final DataSource dataSource;
	
	public ExpenseDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	private void close() {
		if(connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean createExpense(Expense expense,Long userId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("insert into expense "
					+ "(name,qty,price,sub_total,issued_date,description,image,user_id) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
			pStmt.setString(1, expense.getName());
			pStmt.setInt(2, expense.getQty());
			pStmt.setDouble(3, expense.getPrice());
			pStmt.setDouble(4, expense.getSubTotal());
			pStmt.setTimestamp(5, Timestamp.valueOf(expense.getIssuedDate()));
			pStmt.setString(6, expense.getDescription());
			pStmt.setString(7, expense.getImage());
			pStmt.setLong(8, userId);
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
	
	public boolean deleteExpense(Long expenseId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("delete from expense where id="+expenseId+";");
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
	
	public boolean updateExpense(Expense expense) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement("update expense set "
					+ "name=?,"
					+ "qty=?,"
					+ "price=?,"
					+ "sub_total=?,"
					+ "image=?,"
					+ "description=? where id=?;");
			pStmt.setString(1, expense.getName());
			pStmt.setInt(2, expense.getQty());
			pStmt.setDouble(3, expense.getPrice());
			pStmt.setDouble(4, expense.getSubTotal());
			pStmt.setString(5, expense.getImage());
			pStmt.setString(6, expense.getDescription());
			pStmt.setLong(7, expense.getId());
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
	
	public List<Expense> getAllExpense(Long userId){
		List<Expense> expenses = new ArrayList<Expense>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from expense where user_id='"+userId+"';");
			while(rs.next()) {
				expenses.add(new Expense(
						rs.getLong("id"),
						rs.getString("name"),
						rs.getInt("qty"),
						rs.getDouble("price"),
						rs.getString("description"),
						rs.getString("image")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return expenses;
	}
	
	public Expense getExpenseById(Long expenseId){
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from expense where id='"+expenseId+"';");
			while(rs.next()) {
				return new Expense(
						rs.getLong("id"),
						rs.getString("name"),
						rs.getInt("qty"),
						rs.getDouble("price"),
						rs.getString("description"),
						rs.getString("image"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
	
}
