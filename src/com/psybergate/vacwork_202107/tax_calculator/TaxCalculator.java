package com.psybergate.vacwork_202107.tax_calculator;

public class TaxCalculator {
    public static void main(String[] args) {
        Income i1 = new Salary(337800,1);

        Income[] incomes = {i1};

        Tax t1 = new Tax(incomes,65);

        System.out.println("t1.calculateTax() = " + t1.calculateGrossTax());
    }
}
