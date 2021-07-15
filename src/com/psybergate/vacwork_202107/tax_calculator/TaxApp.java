package com.psybergate.vacwork_202107.tax_calculator;

import java.util.Scanner;

public class TaxApp {
    public static void main(String[] args) {
        System.out.println("Enter a monthly salary");
        Scanner input = new Scanner(System.in);
        double monthlySalary = input.nextDouble();
        Income i1 = new Salary(monthlySalary,1);

        Income[] incomes = {i1};

        Tax t1 = new Tax(incomes,65);

        System.out.println("t1.calculateTax() = " + t1.calculateGrossTax());
    }
}
