package com.admin;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.EmailUtility;

@WebServlet("/CustomerAccountsServlet")
public class CustomerAccountsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("customerAccounts.jsp?error=Action parameter is missing");
            return;
        }

        switch (action.toLowerCase()) {
            case "add":
                addCustomer(request, response);
                break;
            case "update":
                updateCustomer(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            case "view":
                viewCustomer(request, response);
                break;
            default:
                response.sendRedirect("customerAccounts.jsp?error=Invalid action");
                break;
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Customer customer = new Customer(
                0, 
                request.getParameter("fullName"),
                request.getParameter("address"),
                request.getParameter("mobileNo"),
                request.getParameter("emailId"),
                request.getParameter("accountType"),
                Double.parseDouble(request.getParameter("initialBalance")),
                LocalDate.parse(request.getParameter("dateOfBirth")),
                request.getParameter("idProof"),
                request.getParameter("accountNo"),
                request.getParameter("password"),
                request.getParameter("tempPassword"),
                request.getParameter("photoFileName").getBytes(),
                request.getParameter("gender")
            );

            CustomerDAO customerDAO = new CustomerDAO();
            if (customerDAO.addCustomer(customer)) {
                sendEmail(customer, "Account Created", "Your account has been successfully created.");
                response.sendRedirect("success.jsp?message=Customer added successfully");
            } else {
                response.sendRedirect("customerAccounts.jsp?error=Failed to add customer");
            }
        } catch (SQLException | NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            throw new ServletException("Error adding customer.", e);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String accountNo = request.getParameter("accountNo");
            if (accountNo == null || accountNo.isEmpty()) {
                throw new ServletException("Account number is required.");
            }

            CustomerDAO customerDAO = new CustomerDAO();
            Customer oldCustomer = customerDAO.getCustomerByAccountNo(accountNo);
            
            if (oldCustomer == null) {
                throw new ServletException("Customer not found.");
            }

            Customer newCustomer = new Customer(
                oldCustomer.getId(), // Assuming ID is not updated here
                request.getParameter("fullName"),
                request.getParameter("address"),
                request.getParameter("mobileNo"),
                request.getParameter("emailId"),
                request.getParameter("accountType"),
                Double.parseDouble(request.getParameter("initialBalance")),
                LocalDate.parse(request.getParameter("dateOfBirth")),
                request.getParameter("idProof"),
                accountNo,
                null, // Password not updated
                request.getParameter("tempPassword"),
                null, // PhotoFileName not updated
                null // Gender not updated
            );

            boolean success = customerDAO.updateCustomer(newCustomer);
            if (success) {
                String changes = getChanges(oldCustomer, newCustomer);
                sendEmail(newCustomer, "Account Updated", "Your account details have been updated." + changes);
                request.setAttribute("successMessage", "Customer updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update customer.");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("customerAccounts.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("customerAccounts.jsp").forward(request, response);
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String accountNo = request.getParameter("accountNo");
            if (accountNo == null || accountNo.isEmpty()) {
                throw new ServletException("Account number is required.");
            }

            CustomerDAO customerDAO = new CustomerDAO();
            boolean success = customerDAO.deleteCustomerByAccountNo(accountNo);
            if (success) {
                response.sendRedirect("success.jsp?message=Customer deleted successfully");
            } else {
                response.sendRedirect("customerAccounts.jsp?error=Failed to delete customer. Customer account number might not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error occurred.", e);
        }
    }

    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");

        if (accountNo == null || accountNo.isEmpty()) {
            request.setAttribute("errorMessage", "Account Number is required");
            request.getRequestDispatcher("customerAccounts.jsp").forward(request, response);
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        try {
            Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("customerAccounts.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Customer not found");
                request.getRequestDispatcher("customerAccounts.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error occurred.", e);
        }
    }

    private void sendEmail(Customer customer, String subject, String body) {
        if (customer.getEmailId() != null && !customer.getEmailId().isEmpty()) {
            String emailBody = "<html><head><style>"
                             + "@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');"
                             + "body { font-family: Arial, sans-serif; }"
                             + "b { font-weight: bold; }"
                             + "span { font-family: 'Roboto', Arial, sans-serif; }"
                             + "</style></head><body>"
                             + "Dear " + customer.getFullName() + ",<br><br>"
                             + body + "<br><br>"
                             + "Thank you,<br>Bank"
                             + "</body></html>";

            try {
                EmailUtility.sendEmail(customer.getEmailId(), subject, emailBody);
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Failed to send email to " + customer.getEmailId());
            }
        }
    }

    private String getChanges(Customer oldCustomer, Customer newCustomer) {
        StringBuilder changes = new StringBuilder("<br><br><b>Changes:</b><br>");
        
        String fontStyle = "style='font-family:Roboto, Arial, sans-serif; font-weight:bold;'";
        
        if (!Objects.equals(oldCustomer.getFullName(), newCustomer.getFullName())) {
            changes.append("Full Name: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getFullName()).append(" TO ").append(newCustomer.getFullName())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getAddress(), newCustomer.getAddress())) {
            changes.append("Address: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getAddress()).append(" TO ").append(newCustomer.getAddress())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getMobileNo(), newCustomer.getMobileNo())) {
            changes.append("Mobile No: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getMobileNo()).append(" TO ").append(newCustomer.getMobileNo())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getEmailId(), newCustomer.getEmailId())) {
            changes.append("Email Id: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getEmailId()).append(" TO ").append(newCustomer.getEmailId())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getAccountType(), newCustomer.getAccountType())) {
            changes.append("Account Type:")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getAccountType()).append(" TO ").append(newCustomer.getAccountType())
                   .append("</span><br>");
        }
        if (oldCustomer.getInitialBalance() != newCustomer.getInitialBalance()) {
            changes.append("Initial Balance: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getInitialBalance()).append(" TO ").append(newCustomer.getInitialBalance())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getDateOfBirth(), newCustomer.getDateOfBirth())) {
            changes.append("Date of Birth:")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getDateOfBirth()).append(" TO ").append(newCustomer.getDateOfBirth())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getIdProof(), newCustomer.getIdProof())) {
            changes.append("ID Proof: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getIdProof()).append(" TO ").append(newCustomer.getIdProof())
                   .append("</span><br>");
        }
        if (!Objects.equals(oldCustomer.getTempPassword(), newCustomer.getTempPassword())) {
            changes.append("Temporary Password: ")
                   .append("<span ").append(fontStyle).append(">")
                   .append(oldCustomer.getTempPassword()).append(" TO ").append(newCustomer.getTempPassword())
                   .append("</span><br>");
        }

        return changes.toString();
    }
}