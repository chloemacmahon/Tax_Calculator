package com.psybergate.vacwork_202107.tax_calculator;

import com.psybergate.vacwork_202107.tax_calculator.expense.*;
import com.psybergate.vacwork_202107.tax_calculator.income.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxApp {

    private static Scanner input = new Scanner(System.in);

    private static double getDoubleInput(String userMessage) {
        System.out.println(userMessage);
        return input.nextDouble();
    }
    private static int getIntInput(String userMessage) {
        System.out.println(userMessage);
        return input.nextInt();
    }
    private static void closeInput(){
        input.close();
    }
    private static List<Income> getIncomeInput() {
        Scanner input = new Scanner(System.in);
        int incomesToEnter = getIntInput("Please enter the amount of incomes you'd like to enter");
        List<Income> incomes = new ArrayList<>(incomesToEnter);
        for (int i = 0; i < incomesToEnter; i++) {
            System.out.println("Please enter 1 of a salary income and 2 for a capital gains income");
            if (input.nextInt() == 1) {
                double salaryAmount = getDoubleInput("Please input the salary you earn per period");
                int salaryFrequency = getIntInput("Please input the periods per annum that you earn this salary");
                Salary salary = new Salary(salaryAmount,salaryFrequency);
                incomes.add(salary);
            } else {
                double initialPrice = getDoubleInput("Please input price you paid for the capital item");
                double sellingPrice = getDoubleInput("Please input price you sold the capital item for");
                double valueAdded = getDoubleInput("Please input the amount of money you invested into the item for permanent improvements");
                CapitalGainsIncome capitalGainsIncome = new CapitalGainsIncome(initialPrice,valueAdded,sellingPrice);
                incomes.add(capitalGainsIncome);
            }
        }
        return incomes;
    }
    private static Salary findSalaryIncome(List<Income> incomes){
        for (Income income : incomes) {
            if (income instanceof Salary)
                return (Salary) income;
        }
        return new Salary(0,0);
    }
    public static List<Expense> getExpenseInput(List<Income> incomes) {
        List<Expense> expenses = new ArrayList<>();
        System.out.println("Expenses");
        if (getIntInput("Please enter 1 to enter a medical expense") == 1){
            int dependants = getIntInput("Please enter the amount of dependants that you have on your medical aid");
            int age = getIntInput("Please enter your age");
            double feesPaid = getDoubleInput("Please enter the total contribution you made");
            MedicalExpense medicalExpense = new MedicalExpense(dependants,age,feesPaid);
            expenses.add(medicalExpense);
        }
        if (getIntInput("Please enter 1 to enter a pension expense, the fist salary you entered will be used") == 1){
            double amountContributed = getDoubleInput("Please enter total contributed amount");
            Salary salary = findSalaryIncome(incomes);
            Pension pensionExpense = new Pension(amountContributed,salary);
            expenses.add(pensionExpense);
        }
        if (getIntInput("Please enter 1 to enter a travel expense") == 1){
            double vehicleValue = getDoubleInput("Enter vehicle value");
            int kmDriven = getIntInput("Please enter total distance traveled for work in kms");
            TransportExpense transportExpense = new TransportExpense(kmDriven, vehicleValue);
            expenses.add(transportExpense);
        }
        return expenses;
    }
    private static void printOutput(TaxCalculator taxCalculator) {
        System.out.println("Your taxable income is " + taxCalculator.calculateTotalTaxableIncome());
        System.out.println("Your total tax is " + taxCalculator.calculateNetTax());
    }

    public static void main(String[] args) {
        int age = getIntInput("Please enter your age");
        List<Income> incomes = getIncomeInput();
        TaxCalculator taxCalculation = new TaxCalculator(incomes,getExpenseInput(incomes),age);
        printOutput(taxCalculation);
        closeInput();
    }
}
