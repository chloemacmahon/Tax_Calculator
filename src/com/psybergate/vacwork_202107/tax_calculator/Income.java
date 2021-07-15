package com.psybergate.vacwork_202107.tax_calculator;

public abstract class Income {

    private String incomeType;

    private int includedPercentageTax;

    public Income(String incomeType, int includedPercentageTax) {
        setIncomeType(incomeType);
        setIncludedPercentageTax(includedPercentageTax);
    }

    public abstract double calculateTaxableIncome();

    //Accessor and mutator method for incomeType
    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeType() {
        return this.incomeType;
    }

    public int getIncludedPercentageTax() {
        return includedPercentageTax;
    }

    public void setIncludedPercentageTax(int includedPercentageTax) {
        this.includedPercentageTax = includedPercentageTax;
    }




}
