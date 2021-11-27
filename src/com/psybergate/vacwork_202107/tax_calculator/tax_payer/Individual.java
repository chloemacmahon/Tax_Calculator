package com.psybergate.vacwork_202107.tax_calculator.tax_payer;

import com.psybergate.vacwork_202107.tax_calculator.expense.Expense;
import com.psybergate.vacwork_202107.tax_calculator.income.Income;
import com.psybergate.vacwork_202107.tax_calculator.rebate.Rebate;

import java.util.List;

public class Individual extends TaxPayer{
    private int age;
    private String firstName;
    private String lastName;

    public Individual(String taxNumber, List<Income> incomes, List<Expense> expenses, List<Rebate> rebates, int age, String firstName, String lastName) {
        super(taxNumber, incomes, expenses, rebates);
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
