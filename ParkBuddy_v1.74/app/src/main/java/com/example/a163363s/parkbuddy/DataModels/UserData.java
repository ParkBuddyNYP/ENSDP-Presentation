package com.example.a163363s.parkbuddy.DataModels;

public class UserData{

    String userID;
    String userName;
    String name;
    String passwordHash;
    String passwordSalt;
    String userProfileImage;
    String userRole;
    String userToken;
    String userEmail;
    String carparkName;
    String carpark;
    String userReports;
    String adminRFID;
    String companyName;
    String companies;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCarparkName() {
        return carparkName;
    }

    public String getCarpark() {
        return carpark;
    }

    public String getUserReports() {
        return userReports;
    }

    public String getAdminRFID() {
        return adminRFID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanies() {
        return companies;
    }

    public UserData(String userID, String userName, String name, String passwordHash, String passwordSalt, String userProfileImage, String userRole, String userToken, String userEmail, String carparkName, String carpark, String userReports, String adminRFID, String companyName, String companies) {
        this.userID = userID;
        this.userName = userName;
        this.name = name;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.userProfileImage = userProfileImage;
        this.userRole = userRole;
        this.userToken = userToken;
        this.userEmail = userEmail;
        this.carparkName = carparkName;
        this.carpark = carpark;
        this.userReports = userReports;
        this.adminRFID = adminRFID;
        this.companyName = companyName;
        this.companies = companies;
    }
}
