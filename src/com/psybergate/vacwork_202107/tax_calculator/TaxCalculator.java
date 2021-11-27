package com.psybergate.vacwork_202107.tax_calculator;

import java.util.List;

import com.psybergate.vacwork_202107.tax_calculator.expense.*;
import com.psybergate.vacwork_202107.tax_calculator.income.*;
import com.psybergate.vacwork_202107.tax_calculator.rebate.Rebate;
import com.psybergate.vacwork_202107.tax_calculator.tax_payer.Individual;

public class TaxCalculator {

    private static final int[] TAX_BRACKETS = {216200, 337800, 467500, 613600, 782200, 1_656_600}; //Top bracket declaration as a constant, useful when calculating other countries tax
    //Above 65 is 128650 is 0 % tax and 83100 for under 65
    private static final int[] TAX_PERCENTAGES = {18, 26, 31, 36, 39, 41, 45}; //Percentages associated with each tax bracket lat 45 is for anything higher than the largest tax bracket

    private Individual taxPayer;

    private int year;

    public TaxCalculator(Individual taxPayer, int year) {
        setTaxPayer(taxPayer);
        setYear(year);
    }

    public double calculateNetTax() {
        double grossTax = Math.round(calculateGrossTax());
        double rebates = calculateRebate();
        double netTax;
        if (grossTax >= rebates)
            netTax = grossTax - rebates;
        else
            netTax = 0;
        return netTax;
    }

    public double calculateGrossTax() {
        return calculateGrossTax(calculateTotalTaxableIncome(), 0);
    }

    private double calculateGrossTax(double totalIncome, int currentTaxBracket) {
        if (currentTaxBracket == 0 && totalIncome <= TAX_BRACKETS[currentTaxBracket]) {
            if (getTaxPayer().getAge() >= 75 && totalIncome <= 151100) // calculates minimum tax amounts
                return 0;
            else if (getTaxPayer().getAge() >= 65 && totalIncome <= 135150)
                return 0;
            else if (totalIncome <= 87300)
                return 0;
            else {
                return totalIncome * TAX_PERCENTAGES[currentTaxBracket] / 100.0;
            }
        } else if (currentTaxBracket < 6 && totalIncome <= TAX_BRACKETS[currentTaxBracket]) {
            return (totalIncome - TAX_BRACKETS[currentTaxBracket - 1]) * TAX_PERCENTAGES[currentTaxBracket] / 100.0;
        } else if (currentTaxBracket >= 6) {
            return (totalIncome - TAX_BRACKETS[currentTaxBracket - 1]) * TAX_PERCENTAGES[currentTaxBracket] / 100.0;
        } else {
            if (currentTaxBracket < 1)
                return (TAX_BRACKETS[currentTaxBracket]) * TAX_PERCENTAGES[currentTaxBracket] / 100.0 + calculateGrossTax(totalIncome, currentTaxBracket + 1);
            else
                return (TAX_BRACKETS[currentTaxBracket] - TAX_BRACKETS[currentTaxBracket - 1]) * TAX_PERCENTAGES[currentTaxBracket] / 100.0 + calculateGrossTax(totalIncome, currentTaxBracket + 1);
        }
    }

    public double calculateTotalTaxableIncome() {
        return calculateNetTaxableIncome() - calculateTotalTaxableExpenses();
    }

    public double calculateNetTaxableIncome() {
        double dTotalIncome = 0;
        double dTotalCapitalGainsIncome = calculateTotalCapitalGainsTax();
        for (Income income : getTaxPayer().getIncomes()) {
            if (!(income.getType().equals("Capital gains")))
                dTotalIncome += income.calculateTaxableIncome() * income.getIncludedPercentageTax() / 100;
        }
        if (dTotalCapitalGainsIncome > 0) {
            dTotalIncome += dTotalCapitalGainsIncome; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        }
        return dTotalIncome;
    }

    public double calculateTotalCapitalGainsTax() {
        double dTotalCapitalGainsIncome = 0;
        double dTaxableCapitalGainsIncome = 0;
        for (Income income : getTaxPayer().getIncomes()) {
            if (income.getType().equals("Capital gains"))
                dTotalCapitalGainsIncome += income.calculateTaxableIncome();
        }
        if (dTotalCapitalGainsIncome > 40000) {
            dTaxableCapitalGainsIncome = (dTotalCapitalGainsIncome - 40000) * .4; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        } else return 0;
        return dTaxableCapitalGainsIncome;
    }

    public double calculateTotalTaxableExpenses() {
        double totalExpense = 0;
        for (Expense expense : getTaxPayer().getExpenses()) {
            totalExpense += expense.calculateTaxableExpense();
        }
        return totalExpense;
    }

    private double calculateRebate() {
        double rebateTotal = 0;
        for (Rebate rebate : getTaxPayer().getRebates()){
            System.out.println("rebate = " + rebate.calculateRebate());
            rebateTotal += rebate.calculateRebate();
        }
        return rebateTotal;
    }

    public Individual getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(Individual taxPayer) {
        this.taxPayer = taxPayer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
