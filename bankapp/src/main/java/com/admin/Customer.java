package com.admin;

import java.time.LocalDate;

public class Customer {
    private int id;
    private String fullName;
    private String address;
    private String mobileNo;
    private String emailId;
    private String accountType;
    private double initialBalance;
    private LocalDate dateOfBirth;
    private String idProof;
    private String accountNo;
    private String password;
    private String tempPassword;
    private byte[] photoFileName;
    private String gender;

    // Default constructor
    public Customer() {}

    // Constructor with parameters
    public Customer(int id, String fullName, String address, String mobileNo, String emailId, String accountType,
                    double initialBalance, LocalDate dateOfBirth, String idProof, String accountNo, 
                    String password, String tempPassword, byte[] photoFileName, String gender) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.dateOfBirth = dateOfBirth;
        this.idProof = idProof;
        this.accountNo = accountNo;
        this.password = password;
        this.tempPassword = tempPassword;
        this.photoFileName = photoFileName;
        this.gender = gender;
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getIdProof() { return idProof; }
    public void setIdProof(String idProof) { this.idProof = idProof; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTempPassword() { return tempPassword; }
    public void setTempPassword(String tempPassword) { this.tempPassword = tempPassword; }

    public byte[] getPhotoFileName() { return photoFileName; }
    public void setPhotoFileName(byte[] photoFileName) { this.photoFileName = photoFileName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

	public int Customer() {
		// TODO Auto-generated method stub
		return 0;
	}
}