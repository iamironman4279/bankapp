<%@ page import="javax.mail.MessagingException" %>
<%@ page import="com.bank.EmailUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Email Status</title>
</head>
<body>
    <h2>Email Status</h2>
    <%
        String toEmail = request.getParameter("toEmail");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        try {
            EmailUtility.sendEmail(toEmail, subject, message);
            out.println("<p>Email sent successfully to " + toEmail + ".</p>");
        } catch (MessagingException e) {
            e.printStackTrace();
            out.println("<p>Failed to send email to " + toEmail + ".</p>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    %>
    <a href="sendEmail.jsp">Send Another Email</a>
</body>
</html>
