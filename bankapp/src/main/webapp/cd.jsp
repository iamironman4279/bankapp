<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Zero Balance Account</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 300px;
        }
        h1 {
            font-size: 1.5em;
            color: #333333;
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-size: 1em;
            margin-bottom: 8px;
            color: #333333;
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 20px;
            border: 1px solid #cccccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Delete Account</h1>
        <form action="DeleteAccountServlet" method="post">
            <label for="accountNo">Account Number:</label>
            <input type="text" id="accountNo" name="accountNo" required>
            <input type="submit" value="Delete Account">
        </form>
    </div>
</body>
</html>
