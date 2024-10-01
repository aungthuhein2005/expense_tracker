package com.hostmdy.expenese.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.expenese.model.User;
import com.hostmdy.expenese.model.UserDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/living_expense")
	private DataSource dataSource;
	
	private UserDAO userDAO;
	
	public AdminController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		
		if(mode == null) {
			mode = "PORTAL";
		}
		
		switch (mode) {
		case "PORTAL":
			showPortal(req, resp);
			break;
		case "ENABLE":
			enable(req, resp);
			break;
		case "DISABLE":
			disable(req, resp);
			break;
		default:
			showPortal(req, resp);
			break;
		}
	}
	
	protected void enable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.enableUser(userId);
		if(ok) {
			showPortal(req, resp);
		}
		else {
			System.out.println("user enabled action is failed");
		}
	}
	
	protected void disable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.disableUser(userId);
		if(ok) {
			showPortal(req, resp);
		}
		else {
			System.out.println("user disabled action is failed");
		}
	}
	
	protected void showPortal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = userDAO.getAllUsers();
		req.setAttribute("users", users);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/admin-portal.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
