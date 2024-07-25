<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit</title>
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Zilla+Slab">
    <style>
        /* General styles for the entire page */
        body {
            font-family: 'Zilla Slab', serif;
            background-color: #4c6b8a;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
            text-align: center; /* Center align content */
        }

        /* Container styles */
        .container {
            background-color: #e3f0fc;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            max-width: 600px; /* Adjust width as needed */
            margin: 20px auto;
            text-align: left; /* Align text to the left */
        }

        /* Header styles */
        h1, h2 {
            color: #2c3e50;
        }

        /* Form styles */
        form {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            font-size: 1em;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-bottom: 20px;
        }

        button[type="button"] {
            background-color: #92b8de; /* Light blue background */
            color: white;
            border: none;
            padding: 10px 20px;
            margin-top: 10px;
            font-size: 1.1em;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease; /* Smooth hover transition */
        }

        button[type="button"]:hover {
            background-color: #4c6b8a; /* Darker blue on hover */
        }

        /* Link styles */
        .back-to-dashboard {
            display: inline-block;
            background-color: #4c6b8a; /* Dark blue background */
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease; /* Smooth hover transition */
        }

        .back-to-dashboard:hover {
            background-color: #2c3e50; /* Darker blue on hover */
        }
    </style>
    <script>
        function handleDepositResponse(response) {
            var data = JSON.parse(response);
            if (data.status === "success") {
                alert(data.message);
                document.getElementById("amount").value = ""; // Clear amount input
                // Optionally, refresh the page: window.location.reload();
            } else {
                alert(data.message);
            }
        }
        
        function submitDepositForm() {
            var form = document.getElementById("depositForm");
            var xhr = new XMLHttpRequest();
            xhr.open("POST", form.action, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    handleDepositResponse(xhr.responseText);
                }
            };
            xhr.send(new URLSearchParams(new FormData(form)).toString());
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Deposit</h1>
        
        <%-- Retrieve account number from request parameters --%>
        <% String accountNo = request.getParameter("accountNo"); %>
        
        <%-- Display account number --%>
        <h2>Account Number: <%= accountNo %></h2>
        
        <%-- Deposit Form --%>
        <form id="depositForm" action="DepositServlet" method="post">
            <input type="hidden" name="accountNo" value="<%= accountNo %>">
            <label for="amount">Amount:</label>
            <input type="text" id="amount" name="amount">
            <button type="button" onclick="submitDepositForm()">Deposit</button>
        </form>
        
        <a href="CustomerDashboard" class="back-to-dashboard">Back to Dashboard</a>
    </div>
</body>
</html>
