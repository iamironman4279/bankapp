package com.bank;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;


public class CustomerDAO {

	 public static boolean register(Customer customer, String photoFileName) {
	        boolean status = false;

	        // Check for uniqueness of ID Proof, Email, and Phone Number
	        if (!isUniqueIdProof(customer.getIdProof())) {
	            // Show popup message: "ID Proof already exists"
	            return false;
	        }
	        if (!isUniqueEmail(customer.getEmailId())) {
	            // Show popup message: "Email already exists"
	            return false;
	        }
	        if (!isUniqueMobileNo(customer.getMobileNo())) {
	            // Show popup message: "Mobile Number already exists"
	            return false;
	        }

	        // Check if customer is above 18 years old
	        if (!isAbove18(customer.getDateOfBirth())) {
	            // Show popup message: "You must be above 18 years old to register"
	            return false;
	        }

	        try (Connection conn = Database.getConnection()) {
	            String query = "INSERT INTO customer (fullName, address, mobileNo, emailId, accountType, initialBalance, dateOfBirth, idProof, accountNo, tempPassword, photoFileName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, customer.getFullName());
	            stmt.setString(2, customer.getAddress());
	            stmt.setString(3, customer.getMobileNo());
	            stmt.setString(4, customer.getEmailId());
	            stmt.setString(5, customer.getAccountType());
	            stmt.setDouble(6, customer.getInitialBalance());
	            stmt.setString(7, customer.getDateOfBirth());
	            stmt.setString(8, customer.getIdProof());
	            stmt.setString(9, customer.getAccountNo());
	            stmt.setString(10, customer.getTempPassword());
	            stmt.setString(11, photoFileName); // Save photo file name
	            int rows = stmt.executeUpdate();
	            status = rows > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return status;
	    }
	 public static boolean isUniqueIdProof(String idProof) {
		    boolean isUnique = true;
		    try (Connection conn = Database.getConnection();
		         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE idProof = ?")) {
		        stmt.setString(1, idProof);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt(1);
		            System.out.println("ID Proof count: " + count); // Debug output
		            isUnique = count == 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return isUnique;
		}


	 public static boolean isUniqueEmail(String email) {
		    boolean isUnique = true;
		    try (Connection conn = Database.getConnection();
		         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE emailId = ?")) {
		        stmt.setString(1, email);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            isUnique = rs.getInt(1) == 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return isUnique;
		}


	 public static boolean isUniqueMobileNo(String mobileNo) {
		    boolean isUnique = true;
		    try (Connection conn = Database.getConnection();
		         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE mobileNo = ?")) {
		        stmt.setString(1, mobileNo);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt(1);
		            System.out.println("Mobile Number count: " + count); // Debug output
		            isUnique = count == 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return isUnique;
		}


	    private static boolean isAbove18(String dateOfBirth) {
	        try {
	            LocalDate dob = LocalDate.parse(dateOfBirth); // Assuming date format is yyyy-MM-dd
	            LocalDate today = LocalDate.now();
	            Period period = Period.between(dob, today);
	            return period.getYears() >= 18;
	        } catch (DateTimeParseException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


    // Method to authenticate a customer
    public static boolean authenticate(String accountNo, String password) {
        boolean status = false;
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM customer WHERE accountNo = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, accountNo);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Method to update customer password
    public static boolean verifyTempPasswordAndUpdatePassword(String accountNo, String tempPassword, String newPassword) {
        boolean status = false;
        try (Connection conn = Database.getConnection()) {
            // Check if the temporary password matches
            String verifyQuery = "SELECT tempPassword FROM customer WHERE accountNo = ?";
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifyQuery)) {
                verifyStmt.setString(1, accountNo);
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (rs.next()) {
                        String storedTempPassword = rs.getString("tempPassword");
                        // Compare the provided temporary password with the stored one
                        if (storedTempPassword != null && storedTempPassword.equals(tempPassword)) {
                            // Update the actual password and clear the temporary password
                            String updateQuery = "UPDATE customer SET password = ?, tempPassword = NULL WHERE accountNo = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, newPassword);
                                updateStmt.setString(2, accountNo);
                                int rows = updateStmt.executeUpdate();
                                status = rows > 0;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public Customer getCustomerByAccountNo(String accountNo) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE accountNo = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer(
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("mobileNo"),
                        resultSet.getString("emailId"),
                        resultSet.getString("accountType"),
                        resultSet.getDouble("initialBalance"),
                        resultSet.getString("dateOfBirth"),
                        resultSet.getString("idProof"),
                        resultSet.getString("accountNo"),
                        resultSet.getString("tempPassword")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Method to get customer details
    public static Customer getCustomer(String accountNo) {
        Customer customer = null;
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM customer WHERE accountNo = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                    rs.getString("fullName"),
                    rs.getString("address"),
                    rs.getString("mobileNo"),
                    rs.getString("emailId"),
                    rs.getString("accountType"),
                    rs.getDouble("initialBalance"),
                    rs.getString("dateOfBirth"),
                    rs.getString("idProof"),
                    rs.getString("accountNo"),
                    rs.getString("tempPassword")
                );
                customer.setPassword(rs.getString("tempPassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


	private Connection conn;

   
    // Method to deposit an amount to a customer's account
    public boolean deposit(String accountNo, double amount) {
        boolean isSuccess = false;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE customer SET initialBalance = initialBalance + ? WHERE accountNo = ?")) {
            stmt.setDouble(1, amount);
            stmt.setString(2, accountNo);
            int rows = stmt.executeUpdate();
            isSuccess = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // Method to withdraw an amount from a customer's account
    public boolean withdraw(Connection connection, String accountNo, BigDecimal amount) {
        String updateBalanceSql = "UPDATE customer SET initialBalance = initialBalance - ? WHERE accountNo = ?";
        String insertTransactionSql = "INSERT INTO transactions (accountNo, amount, transactionType) VALUES (?, ?, ?)";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateBalanceSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertTransactionSql)) {

            // Start transaction
            connection.setAutoCommit(false);

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

    // Helper method to get customer balance
    double getBalance(String accountNo) {
        double balance = 0.0;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT initialBalance FROM customer WHERE accountNo = ?")) {
            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("initialBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    // Method to close a customer's account
    public boolean closeAccount(String accountNo) {
        boolean isClosed = false;
        String sql = "DELETE FROM customer WHERE accountNo = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNo);
            int rowsAffected = stmt.executeUpdate();
            isClosed = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isClosed;
    }


    public static String getCustomerEmail(String accountNo) {
        String email = null;
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT email FROM customer WHERE accountNo = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, accountNo);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        email = rs.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }   
        
       
      
    }
