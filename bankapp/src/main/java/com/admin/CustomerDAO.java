package com.admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/bankapp";
    private static final String USER = "root";
    private static final String PASSWORD = "4279";

    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (fullName, address, mobileNo, emailId, accountType, initialBalance, dateOfBirth, idProof, accountNo, password, tempPassword, photoFileName, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getMobileNo());
            pstmt.setString(4, customer.getEmailId());
            pstmt.setString(5, customer.getAccountType());
            pstmt.setDouble(6, customer.getInitialBalance());
            pstmt.setDate(7, Date.valueOf(customer.getDateOfBirth()));
            pstmt.setString(8, customer.getIdProof());
            pstmt.setString(9, customer.getAccountNo());
            pstmt.setString(10, customer.getPassword());
            pstmt.setString(11, customer.getTempPassword());
            pstmt.setBytes(12, customer.getPhotoFileName());
            pstmt.setString(13, customer.getGender());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET fullName=?, address=?, mobileNo=?, emailId=?, accountType=?, initialBalance=?, dateOfBirth=?, idProof=?, tempPassword=? WHERE accountNo=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getMobileNo());
            stmt.setString(4, customer.getEmailId());
            stmt.setString(5, customer.getAccountType());
            stmt.setDouble(6, customer.getInitialBalance());
            stmt.setDate(7, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(8, customer.getIdProof());
            stmt.setString(9, customer.getTempPassword());
            stmt.setString(10, customer.getAccountNo());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean deleteCustomerByAccountNo(String accountNo) throws SQLException {
        String sql = "DELETE FROM customer WHERE accountNo = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Customer getCustomer(int id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        }
        return null;
    }

    public Customer getCustomerByAccountNo(String accountNo) throws SQLException {
        String sql = "SELECT * FROM customer WHERE accountNo=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        }
        return customers;
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("id"),
            rs.getString("fullName"),
            rs.getString("address"),
            rs.getString("mobileNo"),
            rs.getString("emailId"),
            rs.getString("accountType"),
            rs.getDouble("initialBalance"),
            rs.getDate("dateOfBirth").toLocalDate(),
            rs.getString("idProof"),
            rs.getString("accountNo"),
            rs.getString("password"),
            rs.getString("tempPassword"),
            rs.getBytes("photoFileName"),
            rs.getString("gender")
        );
    }
}