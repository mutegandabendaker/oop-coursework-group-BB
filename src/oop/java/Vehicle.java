package oop.java;

public class Vehicle {

    private String registrationNumber;
    private double costPrice;
    private double deposits;
    private double expenses;

    public Vehicle(String registrationNumber, double costPrice) {
        this.registrationNumber = registrationNumber;
        this.costPrice = costPrice;
        this.deposits = 0;
        this.expenses = 0;
    }

    public void addDeposit(double amount) {
        deposits += amount;
    }

    public void addExpense(double amount) {
        expenses += amount;
    }

    public double getBalance() {
        return deposits - expenses;
    }

    public double calculateProfitLoss(double sellingPrice) {
        return sellingPrice - costPrice - expenses;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public double getCostPrice() {
        return costPrice;
    }
}
