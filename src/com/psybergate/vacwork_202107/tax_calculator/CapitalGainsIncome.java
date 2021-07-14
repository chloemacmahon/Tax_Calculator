package com.psybergate.vacwork_202107.tax_calculator;
public class CapitalGainsIncome extends Income{

    private double initialValue;

    private double valueAdded;

    private double sellingPrice;

    private boolean primaryResidence;

    public CapitalGainsIncome(double initialValue, double valueAdded, double sellingPrice) {
        super("Capital gains");
        setTaxableIncome(calculateTaxableIncome());
        setInitialValue(initialValue);
        setValueAdded(valueAdded);
        setSellingPrice(sellingPrice);
    }

    @Override
    public double calculateTaxableIncome() {
        double tmpTaxableIncome = getSellingPrice() - (getInitialValue()+getValueAdded());
        if (isPrimaryResidence() && tmpTaxableIncome < 2000000)
            return 0;
        else if (isPrimaryResidence())
            return tmpTaxableIncome - 2000000;
        else
            return tmpTaxableIncome;
    }


    //Accessor and mutator method for initialValue
    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public double getInitialValue() {
        return this.initialValue;
    }

    //Accessor and mutator method for valueAdded
    public void setValueAdded(double valueAdded) {
        this.valueAdded = valueAdded;
    }

    public double getValueAdded() {
        return this.valueAdded;
    }

    //Accessor and mutator method for sellingPrice
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getSellingPrice() {
        return this.sellingPrice;
    }

    public boolean isPrimaryResidence() {
        return primaryResidence;
    }

    public void setPrimaryResidence(boolean primaryResidence) {
        this.primaryResidence = primaryResidence;
    }
}
