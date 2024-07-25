<%@ page import="javax.mail.MessagingException" %>
<%@ page import="com.bank.EmailUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Send Email</title>
</head>
<body>
    <h2>Send Email</h2>
    <form action="sendEmailAction.jsp" method="post">
        <label for="toEmail">To Email:</label>
        <input type="email" id="toEmail" name="toEmail" required><br><br>

        <label for="subject">Subject:</label>
        <input type="text" id="subject" name="subject" required><br><br>

        <label for="message">Message:</label><br>
        <textarea id="message" name="message" rows="10" cols="50" required></textarea><br><br>

        <input type="submit" value="Send Email">
    </form>
</body>
</html>
