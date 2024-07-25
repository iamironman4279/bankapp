<%@ page import="com.admin.Customer" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&family=Lobster&family=LEMON+MILK&display=swap">
    <style>
        body, h1, h2, h3, p, input, label, form, html {
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to top, #2b5876 0%, #4e4376 100%);
            height: 100vh;
            overflow: hidden;
        }.popup-content {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            position: relative;
        }

        .popup-close {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
            color: #aaa;
            font-size: 24px;
        }

        .popup-close:hover,
        .popup-close:focus {
            color: black;
        }

        
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

        main {
            flex-grow: 1;
            padding: 20px;
            overflow-y: auto;
            text-align: center;
            
        }

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

        .back-btn {
            background-color: #1abc9c;
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9em;
            transition: background-color 0.3s;
            display: inline-flex;
            align-items: center;
            margin-left: 10px;
        }

        .back-btn:hover {
            background-color: #148f77;
        }

        .back-btn i {
            margin-right: 8px;
        }

        section {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            font-family: 'Open Sans', sans-serif;
            animation: fadeInUp 1s ease-in-out;
            margin: 0 auto;
            max-width: 800px;
        }

        section h2 {
            font-family: 'Lobster', cursive;
            text-align: center;
        }

        form {
            margin-top: 10px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
        }

        .form-group {
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .form-group label {
            width: 30%;
            text-align: right;
            margin-right: 10px;
        }

        .form-group input,
        .form-group select {
            width: 65%; /* Reduced width */
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 0.9em;
        }

        .button-group {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        form input[type=submit], 
        form .btn {
            background-color: #1abc9c;
            color: white;
            padding: 8px 14px;
            border: none;
            cursor: pointer;
            margin: 0 10px;
            opacity: 0.8;
            border-radius: 5px;
            font-size: 0.9em;
            display: inline-flex;
            align-items: center;
        }

        form input[type=submit] i,
        form .btn i {
            margin-right: 8px;
        }

        form .btn.delete-btn {
            background-color: #f44336;
        }

        form input[type=submit]:hover, 
        form .btn:hover {
            opacity: 1;
        }

        section.update-details {
            background-color: transparent;
            border: none;
            box-shadow: none;
        }

        section.update-details form {
            background-color: transparent;
            box-shadow: none;
        }

        section.update-details h2 {
            margin-top: 0;
        }

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

        @media (max-width: 1024px) {
            nav {
                width: 180px;
            }

            main {
                padding: 15px;
            }
        }

        @media (max-width: 768px) {
            .wrapper {
                flex-direction: column;
                margin: 10px;
            }

            nav {
                width: 100%;
                height: auto;
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

            form input[type=submit], 
            form .btn {
                padding: 5px 10px;
                font-size: 0.8em;
            }
        }

        @media (max-width: 480px) {
            header {
                padding: 10px 20px;
                font-size: 1.5em;
            }

            nav {
                padding: 5px 0;
            }

            nav a {
                padding: 8px 10px;
                font-size: 0.9em;
            }

            main {
                padding: 8px;
            }

            form input[type=submit], 
            form .btn {
                padding: 4px 8px;
                font-size: 0.75em;
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
                Update Customer Details
            </header>
            <section>
                <!-- Form to search for a customer by account number -->
                <form action="CustomerAccountsServlet" method="post">
                
                    <input type="hidden" name="action" value="view" />
                    <div class="form-group">
                        <label for="searchAccountNo">Enter Account Number :</label>
                        <input type="text" id="searchAccountNo" name="accountNo" required />
                    </div>
                    
                    <button class="back-btn" onclick="history.back()"><i class="fas fa-arrow-left"></i>Back</button>
                    <button type="submit" class="btn"><i class="fas fa-search"></i> Search</button>
                    
                </form>
            </section>

            <section class="update-details">
                <!-- Form to update customer details if a customer is found -->
                <%
                    Customer customer = (Customer) request.getAttribute("customer");
                    if (customer != null) {
                %>
                <form action="CustomerAccountsServlet" method="post">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>" />

                    <div class="form-group">
                        <label for="fullName">Full Name:</label>
                        <input type="text" id="fullName" name="fullName" value="<%= customer.getFullName() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" value="<%= customer.getAddress() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="mobileNo">Mobile No:</label>
                        <input type="text" id="mobileNo" name="mobileNo" value="<%= customer.getMobileNo() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="emailId">Email ID:</label>
                        <input type="email" id="emailId" name="emailId" value="<%= customer.getEmailId() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="accountType">Account Type:</label>
                        <input type="text" id="accountType" name="accountType" value="<%= customer.getAccountType() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="initialBalance">Initial Balance:</label>
                        <input type="number" step="0.01" id="initialBalance" name="initialBalance" value="<%= customer.getInitialBalance() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="dateOfBirth">Date of Birth:</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" value="<%= customer.getDateOfBirth() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="idProof">ID Proof:</label>
                        <input type="text" id="idProof" name="idProof" value="<%= customer.getIdProof() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="tempPassword">Temporary Password:</label>
                        <input type="password" id="tempPassword" name="tempPassword" value="<%= customer.getTempPassword() %>" />
                    </div>

                    <div class="button-group">
                        <button type="submit" class="btn"><i class="fas fa-pencil-alt"></i> Update Customer</button>
                        <button type="submit" class="btn delete-btn" formaction="CustomerAccountsServlet?action=delete"><i class="fas fa-trash-alt"></i> Delete Customer</button>
                    </div>
                </form>
                <%
                    } else if (request.getAttribute("errorMessage") != null) {
                %>
                <p><%= request.getAttribute("errorMessage") %></p>
                <% } %>
               
            </section>
        </main>
    </div>
</body>
</html>