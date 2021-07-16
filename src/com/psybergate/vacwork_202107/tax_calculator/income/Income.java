package com.psybergate.vacwork_202107.tax_calculator.income;

public abstract class Income {

    private String id;

    private String incomeType;

    private int includedPercentageTax;

    public Income(String id, String incomeType, int includedPercentageTax) {
        setId(id);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
