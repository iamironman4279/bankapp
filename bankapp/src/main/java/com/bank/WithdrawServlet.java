package com.bank;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final BigDecimal DAILY_WITHDRAWAL_LIMIT = new BigDecimal("20000");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String amountStr = request.getParameter("amount");

        // Validate amount
        if (amountStr == null || amountStr.isEmpty()) {
            showError(response, "Amount cannot be empty");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NumberFormatException("Amount must be positive");
            }
        } catch (NumberFormatException e) {
            showError(response, "Invalid amount");
            return;
        }

        Connection connection = null;
        try {
            connection = Database.getConnection();
            WithdrawDAO withdrawDAO = new WithdrawDAO();

            // Check if daily limit is exceeded
            BigDecimal totalWithdrawnToday = withdrawDAO.getTotalWithdrawnToday(connection, accountNo);
            if (totalWithdrawnToday.add(amount).compareTo(DAILY_WITHDRAWAL_LIMIT) > 0) {
                showError(response, "Withdrawal limit of ₹" + DAILY_WITHDRAWAL_LIMIT + " exceeded for the day.");
                return;
            }

            boolean withdrawSuccess = withdrawDAO.withdraw(connection, accountNo, amount);

            if (withdrawSuccess) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByAccountNo(accountNo);

                if (customer == null) {
                    showError(response, "Customer not found");
                    return;
                }

                double balance = customerDAO.getBalance(accountNo);

                String subject = "Withdrawal Confirmation";
                String messageBody = "<html><body>"
                                   + "Dear " + customer.getFullName() + ",<br><br>"
                                   + "You have successfully withdrawn an amount of <strong>₹" + amount + "</strong> from your account.<br>"
                                   + "Your new balance is: <strong>₹" + balance + "</strong>.<br>"
                                   + "Account Number: <strong>" + customer.getAccountNo() + "</strong><br><br>"
                                   + "Thank you,<br>Bank"
                                   + "</body></html>";

                try {
                    EmailUtility.sendEmail(customer.getEmailId(), subject, messageBody);
                    response.sendRedirect("withdraw.jsp?success=true&accountNo=" + accountNo);
                } catch (MessagingException e) {
                    showError(response, "Failed to send email");
                }
            } else {
                showError(response, "Insufficient balance!");
            }
        } catch (SQLException ex) {
            showError(response, "Database error occurred");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type='text/javascript'>");
        response.getWriter().println("alert('" + errorMessage + "');");
        response.getWriter().println("window.history.back();");
        response.getWriter().println("</script>");
    }
}
