package com.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccountHoldersServlet")
public class AccountHoldersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("viewAll".equals(action)) {
            try {
                List<Customer> customers = customerDAO.getAllCustomers();
                request.setAttribute("customers", customers);
                request.getRequestDispatcher("/accountHolders.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Unable to retrieve customer data.");
                request.getRequestDispatcher("/accountHolders.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Invalid action.");
            request.getRequestDispatcher("/accountHolders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/accountHolders.jsp");
    }
}