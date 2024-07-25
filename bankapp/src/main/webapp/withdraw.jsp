<%@ page import="com.bank.Customer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Withdraw</title>
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Zilla+Slab">
    <style>
        body {
            font-family: 'Zilla Slab', serif;
            background-color: #4c6b8a;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
            text-align: center;
        }
        .container {
            background-color: #e3f0fc;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            max-width: 600px;
            margin: 20px auto;
            text-align: left;
        }
        h1, h2 {
            color: #2c3e50;
        }
        form {
            margin-top: 20px;
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            font-size: 1em;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-bottom: 20px;
        }
        button[type="submit"] {
            background-color: #92b8de;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-top: 10px;
            font-size: 1.1em;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        button[type="submit"]:hover {
            background-color: #4c6b8a;
        }
        .back-to-dashboard {
            display: inline-block;
            background-color: #4c6b8a;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .back-to-dashboard:hover {
            background-color: #2c3e50;
        }
        .alert {
            color: #d9534f;
            margin-top: 20px;
        }
    </style>
    <script>
        function showSuccessMessage() {
            alert('Withdrawal successful!');
        }

        function showErrorMessage(message) {
            alert(message);
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Withdraw Funds</h1>
        
        <%
            String accountNo = request.getParameter("accountNo");
            String error = request.getParameter("error");
            String success = request.getParameter("success");
        %>
        
        <h2>Account Number: <%= accountNo != null ? accountNo : "Not provided" %></h2>
        
        <form action="WithdrawServlet" method="post">
            <input type="hidden" name="accountNo" value="<%= accountNo %>">
            <label for="amount">Amount:</label>
            <input type="text" id="amount" name="amount" required pattern="^\d+(\.\d{1,2})?$" title="Enter a valid amount">
            <button type="submit">Withdraw</button>
        </form>
        <a href="CustomerDashboard" class="back-to-dashboard">Back to Dashboard</a>

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert"><%= error %></div>
            <script>showErrorMessage("<%= error %>");</script>
        <% } %>

        <% if ("true".equals(success)) { %>
            <script>showSuccessMessage();</script>
        <% } %>
    </div>
</body>
</html>
