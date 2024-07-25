package com.bank;

import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {
    private String accountNo;
    private double amount;
    private String transactionType;
    private Timestamp transactionDate;

    public Transaction(String accountNo, double amount, String transactionType, Timestamp transactionDate) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNo='" + accountNo + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
               accountNo.equals(that.accountNo) &&
               transactionType.equals(that.transactionType) &&
               transactionDate.equals(that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo, amount, transactionType, transactionDate);
    }
}
