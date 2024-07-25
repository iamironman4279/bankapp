package com.bank;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
   
	private static final long serialVersionUID = -4365537122657836610L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        
        if (accountNo != null && !accountNo.isEmpty()) {
            List<Transaction> transactions = TransactionDAO.getTransactions(accountNo);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("transactions.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
