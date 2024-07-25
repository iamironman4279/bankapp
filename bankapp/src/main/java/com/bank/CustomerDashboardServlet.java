package com.bank;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerDashboard")
public class CustomerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNo = (String) session.getAttribute("accountNo");

        // Log the account number for debugging
        System.out.println("Account No from session: " + accountNo);

        if (accountNo == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        Customer customer = null;
		try {
			customer = CustomerDAO.getCustomer(accountNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Log whether a customer was found or not
        if (customer == null) {
            System.out.println("Customer not found for account No: " + accountNo);
            response.sendRedirect("error.jsp");
            return;
        } else {
            System.out.println("Customer found: " + customer.getFullName());
        }

        request.setAttribute("customer", customer);
        request.getRequestDispatcher("dashboard.jsp?accountNo="+accountNo).forward(request, response);
    }
}
