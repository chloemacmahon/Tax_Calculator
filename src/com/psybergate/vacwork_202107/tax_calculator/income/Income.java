package com.psybergate.vacwork_202107.tax_calculator.income;

public abstract class Income {

    private int id;

    private String type;

    private int includedPercentageTax;

    public Income(int id, String incomeType, int includedPercentageTax) {
        setId(id);
        setType(incomeType);
        setIncludedPercentageTax(includedPercentageTax);
    }

    public abstract double calculateTaxableIncome();

    public abstract String insertString(int incomeID);

    //Accessor and mutator method for incomeType
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public int getIncludedPercentageTax() {
        return includedPercentageTax;
    }

    public void setIncludedPercentageTax(int includedPercentageTax) {
        this.includedPercentageTax = includedPercentageTax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
