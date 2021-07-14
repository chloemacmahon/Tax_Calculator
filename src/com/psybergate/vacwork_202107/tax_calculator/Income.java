package com.psybergate.vacwork_202107.tax_calculator;

public class Income {
    private double taxableIncome;
    private String incomeType;

    public Income(String incomeType) {
        setIncomeType(incomeType);
    }

    //Accessor and mutator method for taxableIncome
    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double getTaxableIncome() {
        return this.taxableIncome;
    }

    //Accessor and mutator method for incomeType
    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeType() {
        return this.incomeType;
    }

    public double calculateTaxableIncome() {
        return taxableIncome;
    }


}
