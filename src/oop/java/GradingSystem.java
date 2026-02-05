package oop.java;

import java.util.Scanner;

public class GradingSystem {

    public static int calculateGrade(int score) {
        if (score >= 85) return 1;
        else if (score >= 75) return 2;
        else if (score >= 66) return 3;
        else if (score >= 60) return 4;
        else if (score >= 50) return 5;
        else if (score >= 45) return 6;
        else if (score >= 35) return 7;
        else if (score >= 30) return 8;
        else return 9;
    }

    public static String getRemark(int grade) {
        String[] remarks = {"D1","D2","C3","C4","C5","C6","P7","P8","F"};
        return remarks[grade - 1];
    }

    public static void printSummary(int[] count) {
        System.out.println("\nSummary:");
        for (int i = 1; i <= 9; i++) {
            System.out.println("Grade " + i + ": " + count[i]);
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int[] gradeCount = new int[10];

        for (int i = 1; i <= 5; i++) {

            System.out.print("Enter score for student " + i + ": ");
            int score = input.nextInt();

            int grade = calculateGrade(score);
            String remark = getRemark(grade);

            gradeCount[grade]++;

            System.out.println("Grade: " + grade + "  Remark: " + remark);
        }

        printSummary(gradeCount);
        input.close();
    }
}
