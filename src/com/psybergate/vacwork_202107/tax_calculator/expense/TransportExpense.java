package com.psybergate.vacwork_202107.tax_calculator.expense;

import java.util.List;

public class TransportExpense extends Expense{

    private int kmDriven;

    private double carValue;

    private static final int[] VEHICLE_VALUE = {0,95001,190001,285001,380001,475001,570001}; // Full capitalised?

    private static final int[] FIXED_ANNUAL_COST = {29504,52226,75039,94871,114781,135746,156711};

    private static final double[] FUEL_COST = {1.041,1.162,1.263,1.358,1.453,1.667,1.724};

    private static final double[] MAINTENANCE_COST = {.386,.483,.532,.581,.683,.802,.996};

    public TransportExpense(int kmDriven, double carValue) {
        super("T01", "Travel", 80);
        setKmDriven(kmDriven);
        setCarValue(carValue);
    }

    public double calculateTaxableExpense(){
        int counter = 0;
        while (counter < VEHICLE_VALUE.length && getCarValue() > VEHICLE_VALUE[counter]) {
            counter++;
        }
        return (FIXED_ANNUAL_COST[counter] + FUEL_COST[counter] * getKmDriven() + MAINTENANCE_COST[counter] * getKmDriven())*getIncludedPercentage()/100.0;
    }

    public int getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
    }

    public double getCarValue() {
        return carValue;
    }

    public void setCarValue(double carValue) {
        this.carValue = carValue;
    }
}
