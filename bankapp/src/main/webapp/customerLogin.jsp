<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Login</title>
    <link href="https://fonts.googleapis.com/css2?family=Zilla+Slab&display=swap" rel="stylesheet">
    <style>
        @font-face {
            font-family: 'Roboto9000Italic';
            src: url('fonts/EvilTypewriter-8MVBz.ttf') format('truetype');
        }

        body {
            font-family: 'Roboto9000Italic', serif;
            background-color: #1f456e;
            margin: 0;
            padding: 120px; /* Increased padding for larger body */
            box-sizing: border-box;
            text-align: center;
        }

        h2 {
            font-family: 'Roboto9000Italic', serif;
            color: black;
            text-align: center;
        }

        form {
            background-color: #cadffa;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            width: 100%; /* Responsive width */
            max-width: 350px; /* Maximum width for the form */
            margin: 0 auto; /* Centering the form */
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #2c3e50;
        }

        input[type="text"], input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            font-size: 1em;
            font-family: 'Roboto9000Italic', serif;
        }

        input[type="submit"], .home-btn, .set-password-btn {
            background-color: #13417d;
            color: white;
            border: none;
            padding: 8px 16px; /* Smaller button size */
            font-size: 1em; /* Adjusted font size for smaller buttons */
            cursor: pointer;
            border-radius: 5px;
            font-family: 'Roboto9000Italic', serif;
            display: inline-block; /* Ensure buttons are inline */
            text-decoration: none; /* Remove text decoration for home button */
        }

        input[type="submit"]:hover, .home-btn:hover, .set-password-btn:hover {
            background-color: #487ab0;
        }

        .home-btn {
            float: left; /* Align home button to the left */
            margin-right: 10px; /* Margin for spacing */
        }

        .set-password-btn {
            display: inline-block; /* Ensure button is inline with the form */
            margin-top: 10px; /* Space above the button */
        }
    </style>
    <script type="text/javascript">
        function showError(message) {
            alert(message);
        }
    </script>
</head>
<body>
    <form action="CustomerLoginServlet" method="post">
        <h2>Customer Login</h2>
        <label for="accountNo">Account No:</label>
        <input type="text" id="accountNo" name="accountNo" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>
        
        <input type="submit" value="Login">
        <a href="setNewPassword.html" class="set-password-btn">Set New Password</a> <!-- Added Set New Password Button -->
        <a href="mainPage.jsp" class="home-btn">Home</a> <!-- Updated Home Button -->
    </form>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <script type="text/javascript">
            showError("<%= errorMessage %>");
        </script>
    <%
        }
    %>
</body>
</html>
