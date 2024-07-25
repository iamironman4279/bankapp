<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            padding: 90px;
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
            width: 300px;
            margin: 20px auto;
            text-align: left;
        }

        .form-group {
            margin-bottom: 15px;
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

        input[type="submit"], .home-button {
            background-color: #13417d;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-top: 10px;
            font-size: 1.1em;
            cursor: pointer;
            border-radius: 5px;
            font-family: 'Roboto9000Italic', serif;
        }

        input[type="submit"]:hover, .home-button:hover {
            background-color: #487ab0;
        }

        .home-button {
            background-color: #13417d;
            float: right;
            margin-right: 20px;
            font-size: 1em;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            font-family: 'Roboto9000Italic', serif;
            border: none;
            text-decoration: none;
        }

        .button-group {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
    </style>
    <script type="text/javascript">
        function showError(message) {
            alert(message);
        }
    </script>
</head>
<body>
    <form action="AdminLoginServlet" method="post">
        <h2>Admin Login</h2>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <input type="checkbox" checked="checked" id="remember" name="remember">
            <label for="remember">Remember me</label>
        </div>
        <div class="button-group">
            <a href="mainPage.jsp" class="home-button">Home</a>
            <input type="submit" value="Login">
        </div>
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
