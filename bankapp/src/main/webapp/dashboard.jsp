<%@ page import="com.bank.Customer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <style>
        @font-face {
            font-family: 'Roboto9000Italic';
            src: url('fonts/EvilTypewriter-8MVBz.ttf') format('truetype');
        }

        body {
            font-family: 'Roboto9000Italic', serif;
            background-color: #4c6b8a;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
        }

        .container {
            background-color: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            width: 100%;
            max-width: 1200px;
            margin: 20px auto;
            text-align: center;
            font-size: 1.2em;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .header h1 {
            color: #2c3e50;
            margin: 0;
            flex: 1 1 auto;
        }

        .customer-photo {
            border-radius: 50%;
            width: 160px;
            height: 160px;
            object-fit: cover;
            border: 1px solid #333;
        }

        .details {
            display: grid;
            grid-template-columns: repeat(2, 1fr); /* 2 columns layout */
            gap: 15px;
            margin-top: 20px;
        }

        .detail-item {
            background-color: #cadffa;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            min-width: 300px; /* Adjust minimum width as needed */
        }

        .detail-item p {
            margin: 5px 0;
        }

        .actions {
            margin-top: 20px;
            text-align: center;
            background-color: #4c6b8a;
            padding: 10px;
            border-radius: 5px;
        }

        .actions h2 {
            color: white;
            margin-bottom: 10px;
        }

        .actions button {
            font-family: 'Roboto9000Italic', serif;
            background-color: #e3f0fc;
            color: black;
            border: none;
            padding: 10px 20px;
            margin: 10px;
            font-size: 1.1em;
            cursor: pointer;
            border-radius: 5px;
        }

        .actions button:hover {
            background-color: #92b8de;
        }

        .actions form {
            display: inline-block;
            margin: 10px;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: center;
            }

            .details, .header h1 {
                text-align: center;
            }

            .customer-photo {
                margin-bottom: 20px;
            }

            .details {
                grid-template-columns: 1fr; /* 1 column for small screens */
            }
        }

        @media (min-width: 769px) and (max-width: 1024px) {
            .container {
                padding: 20px 30px;
            }

            .detail-item p {
                font-size: 1.1em;
            }
        }

        @media (min-width: 1025px) {
            .container {
                padding: 30px 40px;
            }

            .detail-item p {
                font-size: 1.2em;
            }
        }
    </style>

    <script>
        function confirmCloseAccount() {
            if (confirm("Do you really want to close your account?")) {
                document.getElementById("closeAccountForm").submit();
            }
        }
        
    </script>
</head>
<body>
    <div class="container">
        <div class="header">
            <% 
                Customer customer = (Customer) request.getAttribute("customer");
                if (customer != null) {
            %>
                <h1>Welcome, <%= customer.getFullName() %></h1>
                <img src="images/<%= customer.getAccountNo() %>.jpg" alt="Customer Photo" class="customer-photo">
            <% 
                } else {
            %>
                <h1>Error: Customer details not found. Please try again.</h1>
            <% 
                    return;
                }
            %>
        </div>
        
        <div class="details">
            <div class="detail-item">
                <p><strong>Account No:</strong> <%= customer.getAccountNo() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Name:</strong> <%= customer.getFullName() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Email Id:</strong> <%= customer.getEmailId() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Address:</strong> <%= customer.getAddress() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Mobile No:</strong> <%= customer.getMobileNo() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Account Type:</strong> <%= customer.getAccountType() %></p>
            </div>
            <div class="detail-item">
                <p><strong>Balance:</strong> <%= customer.getInitialBalance() %></p>
            </div>
        </div>
        
        <div class="actions">
            <h2>Actions</h2>
            
            <form action="deposit.jsp" method="get">
                <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>">
                <button type="submit">Deposit</button>
            </form>

            <form action="withdraw.jsp" method="get">
                <input type="hidden" name="accountNo" required value="<%= customer.getAccountNo() %>">
                <button type="submit">Withdraw</button>
            </form>
            
            <form action="transactions.jsp" method="get">
                <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>">
                <button type="submit">Transaction History</button>
            </form>
            
            <form action="DeleteAccountServlet" method="post" id="closeAccountForm">
                <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>">
                <button type="button" onclick="confirmCloseAccount()">Delete Account</button>
            </form>
<script>
window.onload = function() {
    <% if (request.getAttribute("successMessage") != null) { %>
        alert("<%= request.getAttribute("successMessage") %>");
        window.location.href = "mainPage.jsp";
    <% } else if (request.getAttribute("errorMessage") != null) { %>
        alert("<%= request.getAttribute("errorMessage") %>");
        // Redirect or handle as needed
    <% } %>
}
</script>
            <form action="logout" method="post">
                <button type="submit">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>
