package com.psybergate.vacwork_202107.tax_calculator;

import com.psybergate.vacwork_202107.tax_calculator.database.TaxDatabase;
import com.psybergate.vacwork_202107.tax_calculator.expense.*;
import com.psybergate.vacwork_202107.tax_calculator.income.*;
import com.psybergate.vacwork_202107.tax_calculator.rebate.MedicalRebate;
import com.psybergate.vacwork_202107.tax_calculator.rebate.PrimaryRebate;
import com.psybergate.vacwork_202107.tax_calculator.rebate.Rebate;
import com.psybergate.vacwork_202107.tax_calculator.tax_payer.Individual;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxApp {

    public static void main(String[] args) {
        int age = getIntInput("Please enter your age");
        List<Income> incomes = getIncomeInput();
        List<Expense> expenses = getExpenseInput(incomes);
        List<Rebate> rebates = getRebates(age);
        int year = getIntInput("Please enter the current tax year");
        String firstName = getStringInput("Please enter your first name");
        String lastName = getStringInput("Please enter your last name");
        String taxNumber = getStringInput("Please enter your tax number");//Make tax number a primary key?
        Individual taxPayer = new Individual(taxNumber, incomes, expenses, rebates, age, firstName, lastName);
        for (Rebate rebate: taxPayer.getRebates()){
            System.out.println("rebate = " + rebate.calculateRebate());
        }
        TaxCalculator taxCalculation = new TaxCalculator(taxPayer, year);
        printOutput(taxCalculation);
        closeInput();
        TaxDatabase db = new TaxDatabase(); //TaxDataAccess
        db.setConnection();
        db.insertTaxPayer(taxPayer);
        db.insertTaxCalculation(taxCalculation);
        db.insertExpenses(taxCalculation);
        db.insertIncomes(taxCalculation);
        db.closeConnection();
    }

    private static Scanner input = new Scanner(System.in);

    private static String getStringInput(String userMessage) {
        System.out.println(userMessage);
        return input.next();
    }

    private static double getDoubleInput(String userMessage) {
        System.out.println(userMessage);
        return input.nextDouble();
    }

    private static int getIntInput(String userMessage) {
        System.out.println(userMessage);
        return input.nextInt();
    }

    private static int getIntInput(String userMessage, List<Integer> possibleValues){
        int value;
        do {
            System.out.println(userMessage);
            value = input.nextInt();
        } while(!possibleValues.contains(value));
        return value;
    }

    private static void closeInput() {
        input.close();
    }

    private static List<Income> getIncomeInput() {
        Scanner input = new Scanner(System.in);
        int incomesToEnter = getIntInput("Please enter the amount of incomes you'd like to enter");
        List<Income> incomes = new ArrayList<>(incomesToEnter);
        for (int i = 0; i < incomesToEnter; i++) {
            List<Integer> possibleValues = new ArrayList<>();
            possibleValues.add(1);
            possibleValues.add(2);
            int i1 = getIntInput("Please enter 1 of a salary income and 2 for a capital gains income",possibleValues);
            if (i1 == 1) {
                double salaryAmount = getDoubleInput("Please input the salary you earn per period");
                int salaryFrequency = getIntInput("Please input the periods per annum that you earn this salary");
                Salary salary = new Salary(salaryAmount, salaryFrequency);
                incomes.add(salary);
            } else if (i1 == 2){
                double initialPrice = getDoubleInput("Please input price you paid for the capital item");
                double sellingPrice = getDoubleInput("Please input price you sold the capital item for");
                double valueAdded = getDoubleInput("Please input the amount of money you invested into the item for permanent improvements");
                CapitalGainsIncome capitalGainsIncome = new CapitalGainsIncome(initialPrice, valueAdded, sellingPrice);
                incomes.add(capitalGainsIncome);
            }
        }
        return incomes;
    }

    private static Salary findSalaryIncome(List<Income> incomes) {
        for (Income income : incomes) {
            if (income instanceof Salary)
                return (Salary) income;
        }
        return new Salary(0, 0);
    }

    public static List<Expense> getExpenseInput(List<Income> incomes) {
        List<Expense> expenses = new ArrayList<>();
        System.out.println("Expenses");
        if (getIntInput("Please enter 1 to enter a pension expense, the fist salary you entered will be used") == 1) {
            double amountContributed = getDoubleInput("Please enter total contributed amount");
            Salary salary = findSalaryIncome(incomes);
            Pension pensionExpense = new Pension(amountContributed, salary);
            expenses.add(pensionExpense);
        }
        if (getIntInput("Please enter 1 to enter a travel expense") == 1) {
            double vehicleValue = getDoubleInput("Enter vehicle value");
            int kmDriven = getIntInput("Please enter total distance traveled for work in kms");
            TransportExpense transportExpense = new TransportExpense(kmDriven, vehicleValue);
            expenses.add(transportExpense);
        }
        return expenses;
    }

    private static List<Rebate> getRebates(int age){
        List<Rebate> rebates = new ArrayList<>();
        rebates.add(new PrimaryRebate(age));
        if (getIntInput("Enter 1 if you have and pay a medical aid") == 1){
            rebates.add(new MedicalRebate(getIntInput("Please enter the amount of dependents on your medical aid excluding you")));
        }
        return rebates;
    }


    private static void printOutput(TaxCalculator taxCalculator) {
        System.out.println("taxCalculator.calculateNetTaxableIncome() = " + taxCalculator.calculateNetTaxableIncome());
        System.out.println("taxCalculator.calculateTotalTaxableExpenses() = " + taxCalculator.calculateTotalTaxableExpenses());
        System.out.println("Your taxable income is " + taxCalculator.calculateTotalTaxableIncome());

        System.out.println("Your total tax is " + taxCalculator.calculateNetTax());
    }


}
