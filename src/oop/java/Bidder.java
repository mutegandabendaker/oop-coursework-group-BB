package oop.java;

public class Bidder {

    private String name;
    private double bidAmount;

    public Bidder(String name, double bidAmount) {
        this.name = name;
        this.bidAmount = bidAmount;
    }

    public String getName() {
        return name;
    }

    public double getBidAmount() {
        return bidAmount;
    }
}
