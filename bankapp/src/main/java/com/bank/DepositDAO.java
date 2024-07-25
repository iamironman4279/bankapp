package com.bank;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepositDAO {

    // Method to get today's total deposit for a specific account
    public BigDecimal getTodayDeposits(Connection connection, String accountNo) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions " +
                     "WHERE accountNo = ? AND transactionType = 'Deposit' AND DATE(transactionDate) = CURRENT_DATE";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, accountNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal todayDeposits = rs.getBigDecimal(1);
                    return todayDeposits != null ? todayDeposits : BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Method to perform deposit operation
    public boolean deposit(Connection connection, String accountNo, BigDecimal amount) throws SQLException {
        // Update the customer's balance
        String updateBalanceSql = "UPDATE customer SET initialBalance = initialBalance + ? WHERE accountNo = ?";
        // Insert the deposit transaction
        String insertTransactionSql = "INSERT INTO transactions (accountNo, amount, transactionType, transactionDate) VALUES (?, ?, 'Deposit', NOW())";

        try (PreparedStatement updateBalancePstmt = connection.prepareStatement(updateBalanceSql);
             PreparedStatement insertTransactionPstmt = connection.prepareStatement(insertTransactionSql)) {

            // Start transaction
            connection.setAutoCommit(false);

            try {
                // Update balance
                updateBalancePstmt.setBigDecimal(1, amount);
                updateBalancePstmt.setString(2, accountNo);
                int rowsAffected = updateBalancePstmt.executeUpdate();

                // Insert transaction
                insertTransactionPstmt.setString(1, accountNo);
                insertTransactionPstmt.setBigDecimal(2, amount);
                int transactionRowsAffected = insertTransactionPstmt.executeUpdate();

                // Commit transaction
                if (rowsAffected > 0 && transactionRowsAffected > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e; // Re-throw exception to handle it in the servlet
            } finally {
                connection.setAutoCommit(true); // Reset auto-commit
            }
        }
    }
}