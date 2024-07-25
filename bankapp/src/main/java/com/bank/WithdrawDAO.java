package com.bank;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawDAO {

    private static final BigDecimal DAILY_WITHDRAWAL_LIMIT = new BigDecimal("20000"); // Define daily limit

    public boolean withdraw(Connection connection, String accountNo, BigDecimal amount) {
        String updateBalanceSql = "UPDATE customer SET initialBalance = initialBalance - ? WHERE accountNo = ?";
        String insertTransactionSql = "INSERT INTO transactions (accountNo, amount, transactionType, transactionDate) VALUES (?, ?, ?, NOW())";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateBalanceSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertTransactionSql)) {

            // Start transaction
            connection.setAutoCommit(false);

            // Check daily withdrawal limit
            BigDecimal totalWithdrawnToday = getTotalWithdrawnToday(connection, accountNo);
            if (totalWithdrawnToday.add(amount).compareTo(DAILY_WITHDRAWAL_LIMIT) > 0) {
                connection.rollback(); // Rollback if limit is exceeded
                return false;
            }

            // Update initialBalance in customer table
            updateStmt.setBigDecimal(1, amount);
            updateStmt.setString(2, accountNo);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Insert withdrawal record in transactions table
                insertStmt.setString(1, accountNo);
                insertStmt.setBigDecimal(2, amount);
                insertStmt.setString(3, "Withdrawal");
                int rowsInserted = insertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    connection.commit(); // Commit transaction if both operations succeed
                    return true;
                } else {
                    connection.rollback(); // Rollback if transaction insertion fails
                }
            } else {
                connection.rollback(); // Rollback if balance update fails
            }

        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback on exception
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Restore default auto-commit behavior
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public BigDecimal getTotalWithdrawnToday(Connection connection, String accountNo) throws SQLException {
        BigDecimal totalWithdrawnToday = BigDecimal.ZERO;
        String sql = "SELECT SUM(amount) FROM transactions " +
                     "WHERE accountNo = ? AND transactionType = 'Withdrawal' " +
                     "AND DATE(transactionDate) = CURRENT_DATE";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalWithdrawnToday = resultSet.getBigDecimal(1);
            }
        }

        return totalWithdrawnToday == null ? BigDecimal.ZERO : totalWithdrawnToday;
    }
}
