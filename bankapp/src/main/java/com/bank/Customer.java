package com.bank;

public class Customer {
    private String fullName;
    private String address;
    private String mobileNo;
    private String emailId;
    private String accountType;
    private double initialBalance;
    private String dateOfBirth;
    private String idProof;
    private String accountNo;
    private String tempPassword;
    private String password;

    // Constructor with parameters to initialize the fields
    public Customer(String fullName, String address, String mobileNo, String emailId, String accountType,
                    double initialBalance, String dateOfBirth, String idProof, String accountNo, String tempPassword) {
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.dateOfBirth = dateOfBirth;
        this.idProof = idProof;
        this.accountNo = accountNo;
        this.tempPassword = tempPassword;
    }

    // Getters and setters for each private field
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
