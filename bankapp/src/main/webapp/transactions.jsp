<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bank.Transaction" %>
<%@ page import="com.bank.TransactionDAO" %>
<%@ page import="com.bank.CustomerDAO" %>
<%@ page import="com.bank.Customer" %>
<%@ page import="com.itextpdf.text.Document" %>
<%@ page import="com.itextpdf.text.DocumentException" %>
<%@ page import="com.itextpdf.text.Paragraph" %>
<%@ page import="com.itextpdf.text.pdf.PdfWriter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    String accountNo = (String) session.getAttribute("accountNo");

    // Retrieve customer details
    Customer customer = CustomerDAO.getCustomer(accountNo);

    // Retrieve transactions
    List<Transaction> transactions = TransactionDAO.getTransactions(accountNo);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #baddff;
            padding: 20px;
        }
        h1 {
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }
        h2 {
            font-size: 20px;
            font-weight: bold;
            color: #555;
        }
        h3 {
            font-size: 18px;
            font-weight: bold;
            color: #777;
            margin-top: 20px;
        }
        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: black;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            background-color: #b5deff;
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #aebcd6;
            font-weight: bold;
        }
        .download-btn {
            background-color: #004991;
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px auto;
            cursor: pointer;
            border-radius: 4px;
            display: block;
        }
        .download-btn:hover {
            background-color: #2671bd;
        }
    </style>
</head>
<body>
    <h1>Welcome, <%= customer.getFullName() %></h1>
    <h2>Account Number: <%= customer.getAccountNo() %></h2>
    <h3>Your Transactions:</h3>
    <table border="1">
        <thead>
            <tr>
                <th>Serial Number</th>
                <th>Date</th>
                <th>Type</th>
                <th>Amount</th>
            </tr>
        </thead>
        <tbody>
            <% 
            int serialNumber = 1; 
            for (Transaction transaction : transactions) { 
            %>
                <tr>
                    <td><%= serialNumber %></td>
                    <td><%= transaction.getTransactionDate() %></td>
                    <td><%= transaction.getTransactionType() %></td>
                    <td>₹ <%= transaction.getAmount() %></td>
                </tr>
            <% 
            serialNumber++; 
            } 
            %>
        </tbody>
    </table>
    
    <!-- Download button -->
    <form action="downloadTransaction" method="get">
        <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>">
        <button class="download-btn" type="submit">Download Transactions as PDF</button>
    </form>
</body>
</html>
