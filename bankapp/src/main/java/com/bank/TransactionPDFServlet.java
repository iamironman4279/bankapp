package com.bank;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@SuppressWarnings("unused")
@WebServlet(name = "TransactionPDFServlet", urlPatterns = {"/downloadTransaction"})
public class TransactionPDFServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");

        // Retrieve transaction history from DAO
        List<Transaction> transactions = TransactionDAO.getTransactions(accountNo);

        // Determine file type (PDF or CSV) based on request parameter
        String fileType = request.getParameter("type");

        if ("pdf".equals(fileType)) {
            // Generate and return PDF
            generatePDF(transactions, accountNo, response);
        } else if ("csv".equals(fileType)) {
            // Generate and return CSV
            generateCSV(transactions, accountNo, response);
        } else {
            // Default to PDF if no valid type provided
            generatePDF(transactions, accountNo, response);
        }
    }

    private void generatePDF(List<Transaction> transactions, String accountNo, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"transaction_history.pdf\"");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            document.add(new Paragraph("Transaction History for Account Number: " + accountNo));
            document.add(new Paragraph("\n"));

            for (Transaction transaction : transactions) {
                String transactionInfo = String.format("Date: %s, Type: %s, Amount: %.2f",
                        transaction.getTransactionDate().toString(),
                        transaction.getTransactionType(),
                        transaction.getAmount());
                document.add(new Paragraph(transactionInfo));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void generateCSV(List<Transaction> transactions, String accountNo, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transaction_history.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            // CSV header
            writer.println("Date,Type,Amount");

            // CSV data
            for (Transaction transaction : transactions) {
                String transactionInfo = String.format("%s,%s,%.2f",
                        transaction.getTransactionDate().toString(),
                        transaction.getTransactionType(),
                        transaction.getAmount());
                writer.println(transactionInfo);
            }
        }
    }
}
