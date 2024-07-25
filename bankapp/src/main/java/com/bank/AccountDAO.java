package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.MessagingException;

public class AccountDAO {
    private static final String CHECK_BALANCE_SQL = "SELECT initialBalance, fullName, emailId FROM customer WHERE accountNo = ?";
    private static final String DELETE_ACCOUNT_SQL = "DELETE FROM customer WHERE accountNo = ?";
    private static final String DELETE_TRANSACTIONS_SQL = "DELETE FROM transactions WHERE accountNo = ?";

    public AccountDAO() {
        // Constructor if needed
    }

    public boolean deleteAccountIfZeroBalance(String accountNo) throws ClassNotFoundException, SQLException {
        boolean isDeleted = false;

        try (Connection connection = Database.getConnection();
             PreparedStatement checkBalanceStmt = connection.prepareStatement(CHECK_BALANCE_SQL);
             PreparedStatement deleteAccountStmt = connection.prepareStatement(DELETE_ACCOUNT_SQL);
             PreparedStatement deleteTransactionsStmt = connection.prepareStatement(DELETE_TRANSACTIONS_SQL)) {

            // Check balance and retrieve customer details
            checkBalanceStmt.setString(1, accountNo);
            try (ResultSet rs = checkBalanceStmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("initialBalance");
                    String fullName = rs.getString("fullName");
                    String emailId = rs.getString("emailId");

                    System.out.println("Balance for account " + accountNo + " is: " + balance);
                    if (balance == 0.00) {
                        // Delete transactions associated with the account
                        deleteTransactionsStmt.setString(1, accountNo);
                        deleteTransactionsStmt.executeUpdate();

                        // Then delete the account itself
                        deleteAccountStmt.setString(1, accountNo);
                        isDeleted = deleteAccountStmt.executeUpdate() > 0;
                        System.out.println("Account " + accountNo + " deleted: " + isDeleted);

                        if (isDeleted) {
                            sendAccountClosureEmail(fullName, emailId, accountNo);
                        }
                    } else {
                        System.out.println("Account " + accountNo + " has a non-zero balance.");
                    }
                } else {
                    System.out.println("Account " + accountNo + " not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving balance for account " + accountNo + ": " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    private void sendAccountClosureEmail(String fullName, String emailId, String accountNo) {
        String subject = "Account Closure Confirmation";
        String messageBody = "<html><body>"
                           + "Dear " + fullName + ",<br><br>"
                           + "Your account with account number <strong>" + accountNo + "</strong> has been successfully closed.<br><br>"
                           + "Thank you,<br>Bank"
                           + "</body></html>";

        try {
            EmailUtility.sendEmail(emailId, subject, messageBody);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email to " + emailId);
        }
    }
}
