package com.hostmdy.expenese.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.expenese.model.Expense;
import com.hostmdy.expenese.model.ExpenseDAO;
import com.hostmdy.expenese.model.User;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/expense")
public class ExpenseController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpenseController() {
	};

	@Resource(name = "jdbc/living_expense")
	private DataSource dataSource;

	private ExpenseDAO expenseDAO;

	@Override
	public void init() throws ServletException {
		expenseDAO = new ExpenseDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "LIST";
		}
		switch (mode) {
		case "LIST":
			showList(req, resp, user.getId());
			break;
		case "FORM":
			showForm(req, resp);	
			break;
		case "CREATE":
			createExpense(req, resp,user.getId());
			break;
		case "DETAILS":
			showDetails(req, resp);
			break;
		case "LOAD":
			loadExpense(req, resp);
			break;
		case "UPDATE":
			updateExpense(req, resp);
			break;
		case "DELETE":
			deleteExpense(req, resp);
			break;
		case "SEARCH":
			searchExpenseByTitle(req, resp, user.getId());
			break;
	}
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp, Long userId)
			throws ServletException, IOException {
		List<Expense> expenses = expenseDAO.getAllExpense(userId);
		req.setAttribute("expenses", expenses);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void showDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long expenseId = Long.parseLong(req.getParameter("expenseId"));
		Expense expense = expenseDAO.getExpenseById(expenseId);
		if(expense == null) {
			System.out.print("expense with id "+expenseId+" is not found");
			return;
		}
		req.setAttribute("expense", expense);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/expense/expense-detail.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void loadExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long expenseId = Long.parseLong(req.getParameter("expenseId"));
		Expense expense = expenseDAO.getExpenseById(expenseId);
		if(expense == null) {
			System.out.print("expense with id "+expenseId+" is not found");
			return;
		}
		req.setAttribute("expense", expense);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/expense/expense-update.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void showForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/expense/expense-form.jsp");
		dispatcher.forward(req, resp);
	}
	

	private void searchExpenseByTitle(HttpServletRequest req, HttpServletResponse resp,Long userId)
			throws ServletException, IOException {
		String query = req.getParameter("query");
		List<Expense> expenses = expenseDAO.getAllExpense(userId);
		List<Expense> filteredExpenses = expenses.stream()
				.filter(e->e.getName().toLowerCase().contains(query))
				.toList();
		req.setAttribute("expenses", filteredExpenses);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	private void createExpense(HttpServletRequest req, HttpServletResponse resp,Long userId)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		Integer qty = Integer.parseInt(req.getParameter("qty"));
		String image = req.getParameter("image");
		Double price = Double.parseDouble(req.getParameter("price"));
		String description = req.getParameter("description");
		
		Expense expense = new Expense(name, qty, price, description, image);
		boolean ok = expenseDAO.createExpense(expense,userId);
		if(ok) {
			System.out.println("expense created");
			showList(req, resp,userId);
		}else {
			req.setAttribute("ok", ok);
			showForm(req, resp);
		}
	}
	
	private void updateExpense(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long expenseId = Long.parseLong(req.getParameter("expenseId"));
		String name = req.getParameter("name");
		Integer qty = Integer.parseInt(req.getParameter("qty"));
		String image = req.getParameter("image");
		Double price = Double.parseDouble(req.getParameter("price"));
		String description = req.getParameter("description");
		
		Expense expense = new Expense(expenseId,name, qty, price, description, image);
		boolean ok = expenseDAO.updateExpense(expense);
		if(ok) {
			resp.sendRedirect("expense?mode=DETAILS&expenseId="+expenseId);
		}else {
			resp.sendRedirect("expense?mode=LOAD&expenseId="+expenseId);
		}
	}
	
	protected void deleteExpense(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long expenseId = Long.parseLong(req.getParameter("expenseId"));
		boolean ok = expenseDAO.deleteExpense(expenseId);
		if(ok) {
			resp.sendRedirect("expense");
		}else {
			System.out.println("Delete failed...");
		}
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
