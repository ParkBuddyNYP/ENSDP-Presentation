package com.example.a163363s.parkbuddy.DataModels;

public class ParkingLotData {



    String carparkID;
    String carparkName;
    String carparkLat;
    String carparkLng;
    String totalNumberOfLotsAvailable;
    String carparkStatus;
    String totalNumberOfLots;
    String carparkStatusMessage;
    String companyName;
    String company;
    String carparkLots;


    public String getCarparkID() {
        return carparkID;
    }

    public String getCarparkName() {
        return carparkName;
    }

    public String getCarparkLat() {
        return carparkLat;
    }

    public String getCarparkLng() {
        return carparkLng;
    }

    public String getTotalNumberOfLotsAvailable() {
        return totalNumberOfLotsAvailable;
    }

    public String getCarparkStatus() {
        return carparkStatus;
    }

    public String getTotalNumberOfLots() {
        return totalNumberOfLots;
    }

    public String getCarparkStatusMessage() {
        return carparkStatusMessage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompany() {
        return company;
    }

    public String getCarparkLots() {
        return carparkLots;
    }




    public ParkingLotData(String carparkID, String carparkName, String carparkLat, String carparkLng, String totalNumberOfLotsAvailable, String carparkStatus, String totalNumberOfLots, String carparkStatusMessage, String companyName, String company, String carparkLots) {
        this.carparkID = carparkID;
        this.carparkName = carparkName;
        this.carparkLat = carparkLat;
        this.carparkLng = carparkLng;
        this.totalNumberOfLotsAvailable = totalNumberOfLotsAvailable;
        this.carparkStatus = carparkStatus;
        this.totalNumberOfLots = totalNumberOfLots;
        this.carparkStatusMessage = carparkStatusMessage;
        this.companyName = companyName;
        this.company = company;
        this.carparkLots = carparkLots;
    }
}
