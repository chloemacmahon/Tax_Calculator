package com.psybergate.vacwork_202107.tax_calculator.expense;

import com.psybergate.vacwork_202107.tax_calculator.income.Salary;

public class Pension extends Expense{

    private double amountContributed;

    private Salary salary;

    private static final int MAX_CONTRIBUTION = 350000;

    public Pension(double amountContributed, Salary salary) {
        super("P01", "Pension", 100);
        setAmountContributed(amountContributed);
        setSalary(salary);
    }
    public double calculateTaxableExpense() {
        if (salary.calculateTaxableIncome()*.275 < amountContributed || MAX_CONTRIBUTION < amountContributed) {
            System.out.println("taxableIncome*.275 = " + salary.calculateTaxableIncome() * .275);
            return Math.min(salary.calculateTaxableIncome() * .275, MAX_CONTRIBUTION);
        } else
            return amountContributed;
    }

    public double getAmountContributed() {
        return amountContributed;
    }

    public void setAmountContributed(double amountContributed) {
        this.amountContributed = amountContributed;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
