package com.tugasmoop.projectsoftwareengineering;

public class Transaction {

    private int status, day, month, year, hour, minute;
    private String customerUsername;
    private String servicerUsername;
    private int idOrderCustomer;
    private int idOrderServicer;

    public Transaction(int status, String customerUsername, String servicerUsername) {
        this.status = status;
        this.customerUsername = customerUsername;
        this.servicerUsername = servicerUsername;
        this.day = 0;
        this.month = 0;
        this.year = 0;
        this.hour = 0;
        this.minute = 0;
        this.idOrderCustomer = 0;
        this.idOrderServicer = 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getServicerUsername() {
        return servicerUsername;
    }

    public void setServicerUsername(String servicerUsername) {
        this.servicerUsername = servicerUsername;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getIdOrderCustomer() {
        return idOrderCustomer;
    }

    public void setIdOrderCustomer(int idOrderCustomer) {
        this.idOrderCustomer = idOrderCustomer;
    }

    public int getIdOrderServicer() {
        return idOrderServicer;
    }

    public void setIdOrderServicer(int idOrderServicer) {
        this.idOrderServicer = idOrderServicer;
    }
}
