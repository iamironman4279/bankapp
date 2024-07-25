package com.bank;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final BigDecimal MAX_DEPOSIT_AMOUNT = new BigDecimal("10000");
    private static final BigDecimal DAILY_DEPOSIT_LIMIT = new BigDecimal("200000");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String amountStr = request.getParameter("amount");

        if (amountStr == null || amountStr.isEmpty()) {
            sendResponse(response, "error", "Amount cannot be empty.");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NumberFormatException("Amount must be positive");
            }
        } catch (NumberFormatException e) {
            sendResponse(response, "error", "Invalid amount.");
            return;
        }

        if (amount.compareTo(MAX_DEPOSIT_AMOUNT) > 0) {
            sendResponse(response, "error", "Deposit amount exceeds the maximum limit of ₹" + MAX_DEPOSIT_AMOUNT);
            return;
        }

        Connection connection = null;
        try {
            connection = Database.getConnection();
            DepositDAO depositDAO = new DepositDAO();

            BigDecimal todayDeposits = depositDAO.getTodayDeposits(connection, accountNo);
            if (todayDeposits.add(amount).compareTo(DAILY_DEPOSIT_LIMIT) > 0) {
                sendResponse(response, "error", "Daily deposit limit of ₹" + DAILY_DEPOSIT_LIMIT + " exceeded.");
                return;
            }

            boolean depositSuccess = depositDAO.deposit(connection, accountNo, amount);

            if (depositSuccess) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
                if (customer == null) {
                    sendResponse(response, "error", "Customer not found.");
                    return;
                }

                double balance = customerDAO.getBalance(accountNo);

                String subject = "Deposit Confirmation";
                String messageBody = "<html><body>"
                                   + "Dear " + customer.getFullName() + ",<br><br>"
                                   + "You have successfully deposited an amount of <strong>₹" + amount + "</strong> into your account.<br>"
                                   + "Your new balance is: <strong>₹" + balance + "</strong>.<br>"
                                   + "Account Number: <strong>" + customer.getAccountNo() + "</strong><br><br>"
                                   + "Thank you,<br>Bank"
                                   + "</body></html>";

                try {
                    EmailUtility.sendEmail(customer.getEmailId(), subject, messageBody);
                    sendResponse(response, "success", "Deposit successful! An email confirmation has been sent.");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    sendResponse(response, "error", "Deposit successful, but failed to send email.");
                }
            } else {
                sendResponse(response, "error", "Deposit failed. Please try again.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            sendResponse(response, "error", "Database error occurred. " + ex.getMessage());
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

    private void sendResponse(HttpServletResponse response, String status, String message) throws IOException {
        String jsonResponse = "{ \"status\": \"" + status + "\", \"message\": \"" + message + "\" }";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}