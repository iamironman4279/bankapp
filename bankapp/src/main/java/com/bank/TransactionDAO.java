package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Method to save a deposit transaction and update initialBalance
    public static void saveDeposit(String accountNo, double amount) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (accountNo, amount, transactionType) VALUES (?, ?, ?)")) {
            stmt.setString(1, accountNo);
            stmt.setDouble(2, amount);
            stmt.setString(3, "Deposit");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all transactions for a given account number
    public static List<Transaction> getTransactions(String accountNo) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions WHERE accountNo = ? ORDER BY transactionDate DESC")) {
            stmt.setString(1, accountNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                            rs.getString("accountNo"),
                            rs.getDouble("amount"),
                            rs.getString("transactionType"),
                            rs.getTimestamp("transactionDate")
                    );
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve transactions for account: " + accountNo);
        }
        return transactions;
    }
}
