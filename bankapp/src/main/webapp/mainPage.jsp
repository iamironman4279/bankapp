<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Login</title>
    <style>
        @font-face {
            font-family: 'Roboto9000Italic';
            src: url('fonts/EvilTypewriter-8MVBz.ttf') format('truetype');
        }
        
        body {
            font-family: 'Roboto9000Italic', serif;
            background: url('images/bank1.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        
        .container {
            padding: 30px;
            backdrop-filter: blur(3px);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            background-color: transparent; /* Semi-transparent white background */
        }
        
        h1 {
            font-size: 36px;
            margin-bottom: 10px;
            color: white; /* Text color */
            background-color: #516485; /* Background color */
            padding: 10px 20px; /* Padding for background */
            border-radius: 8px; /* Rounded corners */
            font-family: 'Roboto9000Italic', serif; /* Custom font */
        }
        
        .button-container {
            display: flex;
            flex-direction: column;
            margin-top: 20px;
        }
        
        .button-container a {
            text-decoration: none;
            display: block;
            background-color: #516485; /* Button background color */
            color: white;
            padding: 20px; /* Increase padding for larger button */
            margin: 10px 0;
            border-radius: 50px; /* Make buttons circular */
            font-size: 26px;
            text-align: center;
            transition: background-color 0.3s ease; /* Smooth hover transition */
            font-family: 'Roboto9000Italic', serif; /* Custom font */
        }
        
        .button-container a:hover {
            background-color: #3d4f66; /* Darker shade on hover */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Our Bank</h1>
        <div class="button-container">
            <a href="adminLogin.jsp">Admin Login</a>
            <a href="customerLogin.jsp">Customer Login</a>
        </div>
    </div>
</body>
</html>
