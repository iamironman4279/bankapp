<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to top, #c4c5c7 0%, #dcdddf 52%, #ebebeb 100%);
        }
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            text-align: center;
        }
        nav {
            background-color: #555;
            padding: 10px;
            text-align: center;
        }
        nav a {
            color: #fff;
            text-decoration: none;
            padding: 8px 16px;
            margin: 0 5px;
        }
        nav a:hover {
            background-color: #777;
        }
        section {
            padding: 20px;
            margin: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        footer {
            text-align: center;
            padding: 10px;
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #333;
            color: #fff;
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to Admin Home Page</h1>
    </header>

    <nav>
        <a href="adminDashboard.jsp">Home</a>
        <a href="registerCustomer.html">New Openings</a>
        <a href="customerAccounts.jsp">Get Customer Details</a>
        <a href="accountHolders.jsp">Account Holders</a>

        <a href="adminLogin.html">Logout</a>
    </nav>

    <section>
        <h2>Admin Dashboard</h2>
        <p>Welcome, Admin! Here you can manage customers, generate reports, and configure system settings.</p>
        <p>Use the navigation links above to access different sections.</p>
    </section>
<% 
    if (session.getAttribute("userRole") == null || !session.getAttribute("userRole").equals("admin")) {
        response.sendRedirect("adminLogin.jsp");
        return; // Stop further processing of the page
    }
%>

</body>
</html>