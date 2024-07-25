<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&family=Lobster&display=swap">
    <style>
        @font-face {
            font-family: 'Typographica';
            src: url('fonts/EvilTypewriter-8MVBz.ttf') format('truetype');
            font-weight: normal;
            font-style: normal;
        }
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            height: 100vh;
            overflow: hidden;
            background: linear-gradient(to top, #2b5876 0%, #4e4376 100%);
        }
        .wrapper {
            display: flex;
            height: 100%;
            background: rgba(255, 255, 255, 0.9);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            border-radius: 10px;
            overflow: hidden;
        }
        header {
            background-color: #34495e;
            color: #fff;
            padding: 15px 30px;
            text-align: center;
            width: 100%;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            animation: fadeInDown 1s ease-in-out;
            font-family: 'Lobster', cursive;
        }
        nav {
            background-color: #2c3e50;
            padding: 20px 0;
            text-align: center;
            width: 220px;
            height: calc(100% - 30px);
            position: relative;
            transition: width 0.3s;
            overflow-x: hidden;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            margin-top: 15px;
            margin-bottom: 15px;
            animation: fadeInLeft 1s ease-in-out;
            font-family: 'Open Sans', sans-serif;
        }
        nav:hover {
            width: 260px;
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
            padding: 20px;
            flex-grow: 1;
            transition: margin-left 0.3s;
            margin-top: 15px;
            margin-bottom: 15px;
            font-family: 'Roboto', sans-serif;
            overflow-y: auto;
            height: calc(100% - 30px);
        }
        nav:hover + main {
            margin-left: 40px;
        }
        section {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            animation: fadeInUp 1s ease-in-out;
            font-family: 'Open Sans', sans-serif;
        }
        section h2 {
            font-family: 'Lobster', cursive;
        }
        footer {
            text-align: center;
            padding: 15px;
            background-color: #34495e;
            color: #fff;
            box-shadow: 0 -2px 4px rgba(0,0,0,0.1);
            margin-top: 15px;
            animation: fadeInUp 1s ease-in-out;
            font-family: 'Roboto', sans-serif;
        }
        @keyframes fadeInDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        @keyframes fadeInLeft {
            from {
                opacity: 0;
                transform: translateX(-20px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
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
        .card {
            display: inline-block;
            width: 30%;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin: 10px;
            padding: 20px;
            text-align: center;
            transition: transform 0.3s;
        }
        .card:hover {
            transform: translateY(-10px);
        }
        .card i {
            font-size: 2em;
            margin-bottom: 10px;
            color: #34495e;
        }
        .card h3 {
            font-family: 'Open Sans', sans-serif;
            margin-bottom: 5px;
            font-size: 1.5em;
        }
        .admin-qualifications {
            font-family: 'Typographica', sans-serif;
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
                <h1>Welcome to Admin Home Page</h1>
            </header>
            <section>
                <h2>Admin Dashboard</h2>
                <p>Welcome, Admin! Here you can manage customers, generate reports, and configure system settings.</p>
                <p>Use the navigation links on the left to access different sections.</p>
                <div class="admin-qualifications">
                <h3>Qualifications for Admin</h3>
                <ul>
                    <li>Educational Background: A minimum of a Bachelor's degree in Business Administration, Information Technology, or a related field.</li>
                    <li>Experience: At least 3-5 years of experience in a managerial or supervisory role, preferably in the banking or financial sector.</li>
                    <li>Technical Skills: Proficiency in using banking software, databases, and customer management systems.</li>
                    <li>Leadership Skills: Strong leadership qualities with the ability to manage and motivate a team effectively.</li>
                    <li>Communication Skills: Excellent verbal and written communication skills to interact with staff, customers, and stakeholders.</li>
                    <li>Problem-Solving Skills: Ability to identify issues, develop solutions, and implement them efficiently.</li>
                    <li>Attention to Detail: Meticulous attention to detail to ensure accuracy in all tasks and processes.</li>
                    <li>Ethical Standards: High ethical standards and integrity to handle sensitive information and make sound decisions.</li>
                </ul>
                
                    <p>Being an admin requires a unique combination of skills, experience, and personal qualities.<br> It is essential to stay updated with the latest industry trends and continuously enhance your skills through professional development.<br> Effective communication and leadership are critical as you will be responsible for guiding your team and making strategic decisions that impact the entire organization.<br> Always maintain high ethical standards and ensure that all actions and decisions are made with integrity and transparency.</p>
                </div>
            </section>
            <footer>
                &copy; 2024 Admin Dashboard. All rights reserved.
            </footer>
        </main>
    </div>
</body>
</html>
