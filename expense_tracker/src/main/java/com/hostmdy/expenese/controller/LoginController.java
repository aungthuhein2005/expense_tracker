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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/living_expense")
	private DataSource dataSource;
	
	public LoginController() {
		super();
	}
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		userDAO = new UserDAO(dataSource);
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "FORM";
		}
		switch (mode) {
		case "FORM":
			showLoginFORM(req, resp);
			break;
		case "LOGIN":
			login(req, resp);
			break;
		case "LOGOUT":
			logout(req, resp);
			break;
		default:
			showLoginFORM(req, resp);
			break;
		}
	}
	
	protected void showLoginFORM(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/login.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean loginOk = userDAO.isAuthenticated(email, password);
		if(loginOk) {
			User user = userDAO.getUserByEmail(email);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			resp.sendRedirect("expense");
		}else {
			req.setAttribute("ok", loginOk);
			showLoginFORM(req, resp);
		}
	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();
			session.removeAttribute("user");
//			session.invalidate();
			resp.sendRedirect("login");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
