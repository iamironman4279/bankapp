<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction History</title>
</head>
<body>
    <h2>Download Transaction History</h2>
    <form action="downloadTransaction" method="get">
        <label for="accountNo">Enter Account Number:</label>
        <input type="text" id="accountNo" name="accountNo" required>
        <br>
        <input type="radio" id="pdf" name="type" value="pdf" checked>
        <label for="pdf">Download as PDF</label><br>
        <input type="radio" id="csv" name="type" value="csv">
        <label for="csv">Download as CSV</label><br><br>
        <button type="submit">Download</button>
    </form>
</body>
</html>
