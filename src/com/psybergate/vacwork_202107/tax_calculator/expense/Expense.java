package com.psybergate.vacwork_202107.tax_calculator.expense;

public abstract class Expense {

    private int id;

    private String type;

    private int includedPercentage;

    public Expense(int id, String type, int includedPercentage) {
        setId(id);
        setType(type);
        setIncludedPercentage(includedPercentage);
    }

    public abstract double calculateTaxableExpense();

    public abstract String insertString(int expenseID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIncludedPercentage() {
        return includedPercentage;
    }

    public void setIncludedPercentage(int includedPercentage) {
        this.includedPercentage = includedPercentage;
    }
}
