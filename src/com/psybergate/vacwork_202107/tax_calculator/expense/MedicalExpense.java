package com.psybergate.vacwork_202107.tax_calculator.expense;

public class MedicalExpense extends Expense{

    private int amountOfDependants;

    private int age;

    private double feesPaid;

    public MedicalExpense(int amountOfDependants, int age, double feesPaid) {
        super("M01", "Medical aid expense", 100);
        setAmountOfDependants(amountOfDependants);
        setAge(age);
        setFeesPaid(feesPaid);
    }

    public double calculateTaxableExpense() {
        double taxableExpense = 0.0;
        if (getAmountOfDependants() > 0) {
            taxableExpense += 332*2+ (getAmountOfDependants()-1) * 224;
        } else {
            taxableExpense += 332;
        }

        return taxableExpense * 12;
    }

    public int getAmountOfDependants() {
        return amountOfDependants;
    }

    public void setAmountOfDependants(int amountOfDependants) {
        this.amountOfDependants = amountOfDependants;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(double feesPaid) {
        this.feesPaid = feesPaid;
    }
}
