package com.bank;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/RegisterCustomerServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
                 maxFileSize = 1024 * 1024 * 10, // 10 MB
                 maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String emailId = request.getParameter("emailId");
        String accountType = request.getParameter("accountType");
        String initialBalanceStr = request.getParameter("initialBalance");
        double initialBalance = 0.0;
        if (initialBalanceStr != null && !initialBalanceStr.isEmpty()) {
            initialBalance = Double.parseDouble(initialBalanceStr);
        }
        String dateOfBirth = request.getParameter("dateOfBirth");
        String idProof = request.getParameter("idProof");

        String message = "";

        if (!isAbove18(dateOfBirth)) {
            message = "You must be at least 18 years old.";
        } else if (!CustomerDAO.isUniqueEmail(emailId)) {
            message = "Email already exists.";
        } else if (!CustomerDAO.isUniqueIdProof(idProof)) {
            message = "ID Proof already exists.";
        } else if (!CustomerDAO.isUniqueMobileNo(mobileNo)) {
            message = "Mobile Number already exists.";
        } else {
            // Process registration
            String accountNo = generateAccountNo();
            String tempPassword = generateTempPassword();

            // Handle file upload
            Part filePart = request.getPart("photo");
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = getFileExtension(originalFileName);
            String newFileName = accountNo + "." + fileExtension;

            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path filePath = Paths.get(uploadPath, newFileName);
            try {
                filePart.write(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
                message = "File upload failed.";
                out.print(message);
                out.flush();
                return;
            }

            Customer customer = new Customer(fullName, address, mobileNo, emailId, accountType, initialBalance, dateOfBirth, idProof, accountNo, tempPassword);

            try {
                if (CustomerDAO.register(customer, newFileName)) {
                    String subject = "Customer Registration Successful";
                    String messageBody = "<html><body>"
                                       + "Dear " + fullName + ",<br><br>"
                                       + "Your registration is successful. Here are your account details:<br>"
                                       + "<strong>Account Number: <span style='font-size:20px;'>" + accountNo + "</span></strong><br>"
                                       + "<strong>Temporary Password: <span style='font-size:20px;'>" + tempPassword + "</span></strong><br><br>"
                                       + "Please log in and change your password as soon as possible.<br><br>"
                                       + "Thank you,<br>Bank"
                                       + "</body></html>";

                    EmailUtility.sendEmail(emailId, subject, messageBody);
                    message = "Registration successful! Check your email for details.";
                } else {
                    message = "Registration successful!.";
                }
            } catch (MessagingException e) {
                e.printStackTrace();
                message = "Failed to send email.";
            }
        }

        out.print(message);
        out.flush();
    }

    // Other methods remain unchanged


    private boolean isAbove18(String dateOfBirth) {
        try {
            LocalDate dob = LocalDate.parse(dateOfBirth); // Assuming date format is yyyy-MM-dd
            LocalDate today = LocalDate.now();
            Period period = Period.between(dob, today);
            return period.getYears() >= 18;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateAccountNo() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(random.nextInt(1000000000));
    }

    private String generateTempPassword() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}
