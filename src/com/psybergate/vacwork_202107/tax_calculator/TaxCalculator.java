package com.psybergate.vacwork_202107.tax_calculator;

import java.util.List;
import com.psybergate.vacwork_202107.tax_calculator.expense.*;
import com.psybergate.vacwork_202107.tax_calculator.income.*;

public class TaxCalculator {

    private static final int[] TAX_BRACKETS = {216200, 337800, 467500, 613600, 782200, 1_656_600}; //Top bracket declaration as a constant, useful when calculating other countries tax
    //Above 65 is 128650 is 0 % tax and 83100 for under 65
    private static final int[] TAX_PERCENTAGES = {18, 26, 31, 36, 39, 41, 45}; //Percentages associated with each tax bracket lat 45 is for anything higher than the largest tax bracket

    private List<Income> incomes;

    private List<Expense> expenses;

    private int age;

    public TaxCalculator(List<Income> incomes, List<Expense> expenses, int age) {
        setIncomes(incomes);
        setExpenses(expenses);
        setAge(age);
    }

    public double calculateNetTax() {
        double grossTax = Math.round(calculateGrossTax()) ;
        double rebates = calculateRebates();
        double netTax;
        if (grossTax >= rebates)
            netTax = grossTax - rebates;
        else
            netTax = 0;

        return netTax;
    }

    public double calculateGrossTax() {
        return calculateGrossTax(calculateTotalTaxableIncome(),0);
    }
    private double calculateGrossTax(double totalIncome, int currentTaxBracket) {
        if (currentTaxBracket == 0 && totalIncome <= TAX_BRACKETS[currentTaxBracket] ) {
            if (getAge() >= 75 && totalIncome <= 151100) // calculates minimum tax amounts
                return 0;
            else if (getAge() >= 65 && totalIncome <= 135150)
                return 0;
            else if (totalIncome <= 87300)
                return 0;
            else {
                return totalIncome * TAX_PERCENTAGES[currentTaxBracket]/100.0;
            }
        } else if (currentTaxBracket < 6 && totalIncome <= TAX_BRACKETS[currentTaxBracket]) {
            return (totalIncome-TAX_BRACKETS[currentTaxBracket-1]) * TAX_PERCENTAGES[currentTaxBracket]/100.0;
        } else if (currentTaxBracket >= 6) {
            return (totalIncome-TAX_BRACKETS[currentTaxBracket-1]) * TAX_PERCENTAGES[currentTaxBracket]/100.0;
        }else {
            if (currentTaxBracket < 1)
                return (TAX_BRACKETS[currentTaxBracket]) * TAX_PERCENTAGES[currentTaxBracket]/100.0 + calculateGrossTax(totalIncome,currentTaxBracket+1);
            else
                return (TAX_BRACKETS[currentTaxBracket]-TAX_BRACKETS[currentTaxBracket-1]) * TAX_PERCENTAGES[currentTaxBracket]/100.0 + calculateGrossTax(totalIncome,currentTaxBracket+1);
        }
    }

    public double calculateTotalTaxableIncome() {
        double dTotalIncome = 0;
        double dTotalCapitalGainsIncome = calculateTotalCapitalGainsTax();
        for (Income income : incomes) {
            if (!(income.getIncomeType().equals("Capital gains")))
                dTotalIncome += income.calculateTaxableIncome() * income.getIncludedPercentageTax() / 100;
        }
        if (dTotalCapitalGainsIncome > 0) {
            dTotalIncome += dTotalCapitalGainsIncome; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        }
        return dTotalIncome - calculateTotalTaxableExpenses(dTotalIncome);
    }

    public double calculateTotalCapitalGainsTax() {
        double dTotalCapitalGainsIncome = 0;
        double dTaxableCapitalGainsIncome = 0;
        for (Income income : incomes) {
            if (income.getIncomeType().equals("Capital gains"))
                dTotalCapitalGainsIncome += income.calculateTaxableIncome();
        }
        if (dTotalCapitalGainsIncome > 40000) {
            dTaxableCapitalGainsIncome = (dTotalCapitalGainsIncome - 40000) * .4; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        }
        return dTaxableCapitalGainsIncome;
    }

    public double calculateTotalTaxableExpenses(double taxableIncome) {
        double totalExpense = 0;
        for (Expense expense: getExpenses()) {
            totalExpense += expense.calculateTaxableExpense();
        }
        return totalExpense;
    }

    private double calculateRebates() {
        double dRebates = 15714;
        if (getAge() >= 65)
            dRebates += 8613;
        if (getAge() >= 75)
            dRebates += 2871;
        return dRebates;
    }

    private int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    private void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
