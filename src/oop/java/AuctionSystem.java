package oop.java;

import java.util.Scanner;

public class AuctionSystem {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // ================= VEHICLE DETAILS =================
        System.out.println("=== VEHICLE DETAILS ===");

        System.out.print("Enter registration number: ");
        String reg = input.nextLine();

        System.out.print("Enter vehicle cost price: ");
        double cost = input.nextDouble();

        Vehicle vehicle = new Vehicle(reg, cost);

        System.out.print("Enter deposit amount: ");
        vehicle.addDeposit(input.nextDouble());

        System.out.print("Enter expense amount: ");
        vehicle.addExpense(input.nextDouble());

        // ================= BIDDERS =================
        System.out.println("\n=== ENTER 3 BIDDERS ===");

        Bidder[] bidders = new Bidder[3];

        for (int i = 0; i < 3; i++) {

            input.nextLine(); // clear buffer

            System.out.print("Bidder name: ");
            String name = input.nextLine();

            System.out.print("Bid amount: ");
            double bid = input.nextDouble();

            bidders[i] = new Bidder(name, bid);
        }

        // ================= FIND HIGHEST BID =================
        Bidder highest = bidders[0];

        for (Bidder b : bidders) {
            if (b.getBidAmount() > highest.getBidAmount()) {
                highest = b;
            }
        }

        // ================= RESULTS =================
        System.out.println("\n=== RESULT ===");

        System.out.println("Vehicle: " + vehicle.getRegistrationNumber());
        System.out.println("Winner: " + highest.getName());
        System.out.println("Winning Bid: " + highest.getBidAmount());
        System.out.println("Balance: " + vehicle.getBalance());

        double profitLoss = vehicle.calculateProfitLoss(highest.getBidAmount());

        if (profitLoss >= 0) {
            System.out.println("Profit: " + profitLoss);
        } else {
            System.out.println("Loss: " + profitLoss);
        }

        input.close();
    }
}
