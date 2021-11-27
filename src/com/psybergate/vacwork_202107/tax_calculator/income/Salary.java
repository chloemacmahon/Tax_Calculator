package com.psybergate.vacwork_202107.tax_calculator.income;

import java.sql.PreparedStatement;

public class Salary extends Income {

    private double amount;

    private int frequency; //Amount amounts paid for the period, ex monthly would be 12

    public Salary(double amount, int frequency) {
        super(1,"Salary",100);
        setAmount(amount);
        setFrequency(frequency);
    }

    @Override
    public double calculateTaxableIncome() {
        return getAmount() * getFrequency();
    }

    public String insertString(int incomeID){
        String sql = "INSERT INTO salary(income_id,income_per_period,period)"
                + "VALUES ("+incomeID+","+getAmount()+","+getFrequency()+")";
        return sql;
    }

    //Accessor and mutator method for amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    //Accessor and mutator method for frequency
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return this.frequency;
    }

}
