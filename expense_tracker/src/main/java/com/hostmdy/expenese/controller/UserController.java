package com.hostmdy.expenese.controller;

import java.io.IOException;

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

@WebServlet("/user")
public class UserController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/living_expense")
	private DataSource dataSource;
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "FORM";
		}
		switch (mode) {
		case "FORM":
			showRegisterForm(req, resp);
			break;
		case "REGISTER":
			register(req, resp);
			break;
		default:
			showRegisterForm(req, resp);
			break;
		}
	}
	
	protected void showRegisterForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		User existedUser = userDAO.getUserByEmail(email);
		
		if(existedUser != null) {
			req.setAttribute("emailAlreadyExists", true);
			showRegisterForm(req, resp);
			return;
		}
		
		
		User user = new User(firstName, lastName, email, password);
		boolean ok = userDAO.createUser(user);
		req.setAttribute("ok", ok);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
