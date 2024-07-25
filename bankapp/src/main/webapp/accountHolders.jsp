<%@ page import="com.admin.Customer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Customers</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&family=Lobster&display=swap">
    <style>
        /* Reset default margin and padding */
        body, h1, h2, h3, p, input, label, form, html {
            margin: 0;
            padding: 0;
        }

        /* Set up background and font */
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to top, #2b5876 0%, #4e4376 100%);
            height: 100vh;
            margin: 0;
            overflow: hidden; /* Prevent scrolling on body */
        }

        /* Wrapper for entire page content */
        .wrapper {
            display: flex;
            height: 100%;
            margin: 20px;
            border-radius: 10px;
            overflow: hidden;
            background: rgba(255, 255, 255, 0.9);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            flex-direction: row; /* Keep content side-by-side */
        }

        /* Navigation styling */
        nav {
            background-color: #2c3e50;
            padding: 20px 0;
            text-align: center;
            width: 220px; /* Fixed width for desktop view */
            height: 100%;
            position: relative;
            overflow-x: hidden;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            margin-top: 15px;
            margin-bottom: 15px;
            font-family: 'Open Sans', sans-serif;
        }

        /* Navigation links */
        nav a {
            color: #ecf0f1;
            text-decoration: none;
            display: block;
            padding: 12px 16px;
            margin: 10px 0;
            transition: background-color 0.3s, padding-left 0.3s, color 0.3s;
            font-family: 'Open Sans', sans-serif;
            text-align: left;
        }

        nav a i {
            margin-right: 10px;
        }

        nav a:hover {
            background-color: #1abc9c;
            padding-left: 24px;
            color: #fff;
        }

        /* Main content styling */
        main {
            flex-grow: 1;
            padding: 20px;
            overflow-y: auto; /* Enable vertical scrolling */
            margin-top: 0; /* Remove margin top to align with header */
        }

        /* Header and Footer styling */
        header, footer {
            background-color: #34495e;
            color: #fff;
            padding: 15px 30px;
            text-align: center;
            width: calc(100% - 62px); /* Full width minus nav width */
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            font-family: LEMON MILK;
            position: relative;
            z-index: 1; /* Ensure it is above other content */
            transition: background-color 0.3s, transform 0.3s;
        }


        header:hover, footer:hover {
            background-color: #2c3e50;
            transform: translateY(-5px);
        }

        .button-container {
            display: flex;
            justify-content: center; /* Center buttons horizontally */
            align-items: center;
            gap: 20px; /* Space between buttons */
            margin-bottom: 20px; /* Space below the button container */
        }

        .back-btn {
            background-color: #1abc9c;
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9em;
            display: inline-flex;
            align-items: center;
            transition: background-color 0.3s;
        }

        .back-btn:hover {
            background-color: #148f77; /* Darker shade on hover */
        }

        .back-btn i {
            margin-right: 8px; /* Space between icon and text */
        }

        .action-btn {
            background-color: #1abc9c;
            color: #fff;
            border: none;
            cursor: pointer;
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 0.9em;
            display: inline-flex;
            align-items: center;
            transition: background-color 0.3s;
        }

        .action-btn i {
            margin-right: 8px; /* Space between icon and text */
        }

        .action-btn:hover {
            background-color: #148f77; /* Darker shade on hover */
        }

        /* Section styling */
        section {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            font-family: 'Open Sans', sans-serif;
            animation: fadeInUp 1s ease-in-out;
            margin-left: 10px; /* Margin to align content beside the nav */
        }

        section h2 {
            font-family: 'Lobster', cursive;
        }

        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
            font-family: 'Open Sans', sans-serif;
            font-size: 0.8em; /* Adjust font size for the table */
        }

        thead th {
            background-color: #f4f4f4;
            font-weight: bold;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 6px; /* Adjust padding inside table cells */
            text-align: left;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        /* Footer styling */
        footer {
            margin-top: 15px;
            animation: fadeInUp 1s ease-in-out;
            font-family: 'Roboto', sans-serif;
        }

        /* Keyframe animations */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Responsive design */
        @media (max-width: 1024px) {
            nav {
                width: 180px; /* Adjust navigation width for tablets */
            }

            main {
                padding: 15px;
            }

            .action-btn {
                padding: 5px 10px; /* Adjust button padding for tablets */
                font-size: 0.8em;
            }

            th, td {
                padding: 5px; /* Adjust table cell padding for tablets */
            }
        }

        @media (max-width: 768px) {
            .wrapper {
                flex-direction: column; /* Stack navigation and main content vertically */
                margin: 10px;
            }

            nav {
                width: 100%;
                height: auto; /* Adjust navigation height for mobile */
                margin: 0;
                box-shadow: none;
                padding: 10px 0;
            }

            nav a {
                padding: 10px 12px;
            }

            main {
                padding: 10px;
                height: auto;
            }

            .action-btn {
                padding: 5px 10px; /* Adjust button padding for mobile */
                font-size: 0.8em;
            }

            th, td {
                padding: 5px; /* Adjust table cell padding for mobile */
            }

            table {
                font-size: 0.7em; /* Adjust table font size for mobile */
            }
        }

        @media (max-width: 480px) {
            header {
                padding: 10px 20px;
                font-size: 2em; /* Adjust header font size for small screens */
            }


            nav {
                padding: 5px 0;
            }

            nav a {
                padding: 8px 10px;
                font-size: 0.9em; /* Adjust navigation link font size for small screens */
            }

            main {
                padding: 8px;
            }

            .action-btn {
                padding: 4px 8px; /* Adjust button padding for small screens */
                font-size: 0.75em;
            }

            th, td {
                padding: 4px; /* Adjust table cell padding for small screens */
            }

            table {
                font-size: 0.6em; /* Adjust table font size for small screens */
            }
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <nav>
            <a href="adminList.jsp"><i class="fas fa-home"></i> Home</a>
            <a href="registerCustomer.html"><i class="fas fa-user-plus"></i> Register Customer</a>
            <a href="customerAccounts.jsp"><i class="fas fa-search"></i> Search Customer</a>
            <a href="accountHolders.jsp"><i class="fas fa-users"></i> All Customers</a>
            <a href="adminLogin.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </nav>

        <main>
            <header>
                <h1>All Customers</h1>
            </header>
            <section>
                <div class="button-container">
                    <form action="AccountHoldersServlet" method="post">
                        <input type="hidden" name="action" value="viewAll" />
                        <button type="submit" class="action-btn">
                            <i class="fas fa-list"></i> View All Customers
                        </button>
                    </form>
                    <button class="back-btn" onclick="history.back()"><i class="fas fa-arrow-left"></i> Back</button>
                </div>
                <hr>
                <% List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                   if (customers != null && !customers.isEmpty()) { %>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Full Name</th>
                                <th>Address</th>
                                <th>Mobile No</th>
                                <th>Email ID</th>
                                <th>Account Type</th>
                                <th>Date of Birth</th>
                                <th>ID Proof</th>
                                <th>Account No</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Customer customer : customers) { %>
                                <tr>
                                    <td><%= customer.getId() %></td>
                                    <td><%= customer.getFullName() %></td>
                                    <td><%= customer.getAddress() %></td>
                                    <td><%= customer.getMobileNo() %></td>
                                    <td><%= customer.getEmailId() %></td>
                                    <td><%= customer.getAccountType() %></td>
                                    <td><%= customer.getDateOfBirth() %></td>
                                    <td><%= customer.getIdProof() %></td>
                                    <td><%= customer.getAccountNo() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    
                <% } %>
            </section>
            
            <footer>
                &copy; 2024 Admin Dashboard. All rights reserved.
            </footer>
        </main>
    </div>
</body>
</html>