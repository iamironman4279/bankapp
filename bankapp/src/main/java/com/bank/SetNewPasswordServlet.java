package com.bank;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SetNewPasswordServlet")
public class SetNewPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String tempPassword = request.getParameter("tempPassword");
        String newPassword = request.getParameter("newPassword");

        // Validate inputs (basic example, add more validation as needed)
        if (accountNo == null || tempPassword == null || newPassword == null || newPassword.isEmpty()) {
            response.sendRedirect("setNewPassword.html");
            return;
        }

        // Attempt to verify the temporary password and update the actual password
        boolean isUpdated = CustomerDAO.verifyTempPasswordAndUpdatePassword(accountNo, tempPassword, newPassword);

        if (isUpdated) {
            response.sendRedirect("customerLogin.jsp");
        } else {
            // Optionally, add an error message to the redirect (requires modification in HTML/JS)
            response.sendRedirect("setNewPassword.jsp?error=true");
        }
    }
}
