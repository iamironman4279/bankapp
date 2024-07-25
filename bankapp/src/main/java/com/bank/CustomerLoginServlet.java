package com.bank;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String password = request.getParameter("password");

        boolean authenticated = CustomerDAO.authenticate(accountNo, password);

        if (authenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("accountNo", accountNo);
            response.sendRedirect("CustomerDashboard");
        } else {
            request.setAttribute("errorMessage", "Invalid account number or password");
            request.getRequestDispatcher("customerLogin.jsp").forward(request, response);
        }
    }
}