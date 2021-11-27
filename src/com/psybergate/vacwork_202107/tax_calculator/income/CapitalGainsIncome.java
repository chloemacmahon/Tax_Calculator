package com.psybergate.vacwork_202107.tax_calculator.income;
public class CapitalGainsIncome extends Income{

    private double initialValue;

    private double valueAdded;

    private double sellingPrice;

    private boolean primaryResidence;

    public CapitalGainsIncome(double initialValue, double valueAdded, double sellingPrice) {
        super(1,"Capital gains", 40);
        setInitialValue(initialValue);
        setValueAdded(valueAdded);
        setSellingPrice(sellingPrice);
    }

    @Override
    public double calculateTaxableIncome() {
        double tmpTaxableIncome = getSellingPrice() - (getInitialValue()+getValueAdded());
        if (isPrimaryResidence() && tmpTaxableIncome < 2000000 && tmpTaxableIncome > 0)
            return 0;
        else if (isPrimaryResidence() && tmpTaxableIncome < 0) {
            return -1 * tmpTaxableIncome; //Accounts for loss to be deducted from the profits
        }
        else if (isPrimaryResidence())
            return tmpTaxableIncome - 2000000;
        else
            return tmpTaxableIncome;
    }

    public String insertString(int incomeID){
        String sql = "INSERT INTO capital_gains(income_id,initial_property_value,value_added,selling_value,primary_residence)"
                + "VALUES ("+incomeID+","+getInitialValue()+","+getValueAdded()+","+getSellingPrice()+","+isPrimaryResidence()+")";
        return sql;
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
