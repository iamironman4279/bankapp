<%@ page import="com.bank.Customer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bank Transfer</title>
    <script>
        function showTransferForm() {
            document.getElementById('transferForm').style.display = 'block';
        }
    </script>
</head>
<body>
    <h1>Bank Transfer</h1>

    <form action="VerifyAccountServlet" method="post">
        <label for="targetAccountNo">Target Account Number:</label>
        <input type="text" id="targetAccountNo" name="targetAccountNo">
        <button type="submit">Verify Account</button>
    </form>

    <%
        String targetAccountNo = request.getParameter("targetAccountNo");
        String targetFullName = request.getParameter("targetFullName");
        if (targetFullName != null) {
    %>
        <h2>Target Account Holder: <%= targetFullName %></h2>
        <script>
            showTransferForm();
        </script>
    <%
        }
    %>

    <form id="transferForm" action="TransferServlet" method="post" style="display:none;">
        <input type="hidden" name="sourceAccountNo" value="<%= request.getParameter("accountNo") %>">
        <input type="hidden" name="targetAccountNo" value="<%= targetAccountNo %>">
        <label for="amount">Amount:</label>
        <input type="text" id="amount" name="amount">
        <button type="submit">Transfer</button>
    </form>
    <a href="CustomerDashboard">Back to Dashboard</a>
</body>
</html>
