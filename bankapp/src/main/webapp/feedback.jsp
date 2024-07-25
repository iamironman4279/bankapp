<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to top, #c4c5c7 0%, #dcdddf 52%, #ebebeb 100%);
        }
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            text-align: center;
        }
        nav {
            background-color: #555;
            padding: 10px;
            text-align: center;
        }
        nav a {
            color: #fff;
            text-decoration: none;
            padding: 8px 16px;
            margin: 0 5px;
        }
        nav a:hover {
            background-color: #777;
        }
        section {
            padding: 20px;
            margin: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        footer {
            text-align: center;
            padding: 10px;
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #333;
            color: #fff;
        }
        .feedback-list {
            margin-top: 20px;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .feedback-item {
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
    </style>
    <script>
        function fetchFeedbacks() {
            fetch('FeedbackServlet?action=fetchAll')
                .then(response => response.json())
                .then(data => {
                    const feedbackList = document.getElementById('feedbackList');
                    feedbackList.innerHTML = '';
                    data.forEach(feedback => {
                        const feedbackItem = `
                            <div class="feedback-item">
                                <p><strong>Account Number:</strong> ${feedback.accountNumber}</p>
                                <p><strong>Name:</strong> ${feedback.name}</p>
                            </div>
                        `;
                        feedbackList.innerHTML += feedbackItem;
                    });
                });
        }

        window.onload = fetchFeedbacks;
    </script>
</head>
<body>
    <header>
        <h1>Welcome to Admin Home Page</h1>
    </header>

    <nav>
        <a href="adminDashboard.jsp">Home</a>
        <a href="registerCustomer.html">New Openings</a>
        <a href="customerAccounts.jsp">Get Customer Details</a>
        <a href="accountHolders.jsp">Account Holders</a>
        <a href="feedback.jsp">Feedback</a>
        <a href="adminLogin.html">Logout</a>
    </nav>

    <section class="feedback-list" id="feedbackListSection">
        <h2>Customer Feedbacks</h2>
        <div id="feedbackList"></div>
    </section>

    <footer>
        <p>&copy; 2023 Admin Home Page. All Rights Reserved.</p>
    </footer>
</body>
</html>