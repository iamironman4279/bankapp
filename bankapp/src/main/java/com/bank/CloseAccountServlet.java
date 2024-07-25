package com.bank;

import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/DeleteAccountServlet")
public class CloseAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        AccountDAO accountDAO = new AccountDAO();
        boolean isDeleted = false;

        try {
            isDeleted = accountDAO.deleteAccountIfZeroBalance(accountNo);

            if (isDeleted) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByAccountNo(accountNo);

                if (customer != null) {
                    String subject = "Account Closure Confirmation";
                    String messageBody = "<html><body>"
                                       + "Dear " + customer.getFullName() + ",<br><br>"
                                       + "Your account with account number <strong>" + accountNo + "</strong> has been successfully closed.<br><br>"
                                       + "Thank you,<br>Bank"
                                       + "</body></html>";

                    try {
                        EmailUtility.sendEmail(customer.getEmailId(), subject, messageBody);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        request.setAttribute("errorMessage", "Account closed but failed to send email notification.");
                    }
                }

                // Set attribute to indicate success
                request.setAttribute("successMessage", "Account closed successfully.");
            } else {
                request.setAttribute("errorMessage", "Account not found, has non-zero balance, or error occurred.");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("AccountDeleteSuccessfully.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("errorPage.jsp");
            dispatcher.forward(request, response);
        }
    }
}
