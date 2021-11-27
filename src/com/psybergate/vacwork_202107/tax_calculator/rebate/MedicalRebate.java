package com.psybergate.vacwork_202107.tax_calculator.rebate;

public class MedicalRebate extends Rebate{
    private int amountOfDependants;

    public MedicalRebate(int amountOfDependants) {
        super("M01", "Medical aid expense");
        setAmountOfDependants(amountOfDependants);
    }

    public double calculateRebate() {
        double taxableExpense = 0.0;
        if (getAmountOfDependants() > 0) {
            taxableExpense += 332*2+ (getAmountOfDependants()-1) * 224;
        } else {
            taxableExpense += 332;
        }
        return taxableExpense * 12;
    }

    public int getAmountOfDependants() {
        return amountOfDependants;
    }

    public void setAmountOfDependants(int amountOfDependants) {
        this.amountOfDependants = amountOfDependants;
    }
}
