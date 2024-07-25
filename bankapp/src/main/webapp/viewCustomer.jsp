<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Customer Details</title>
</head>
<body>
    <h2>Customer Details</h2>
    <c:if test="${not empty customer}">
        <p>ID: ${customer.id}</p>
        <p>Name: ${customer.fullName}</p>
        <p>Address: ${customer.address}</p>
        <!-- Display other fields as needed -->
    </c:if>
    <a href="${request.contextPath}/editCustomer?id=${customer.id}">Edit</a>
    <a href="${request.contextPath}/deleteCustomer?id=${customer.id}">Delete</a>
</body>
</html>
