package com.psybergate.vacwork_202107.tax_calculator.rebate;

public class PrimaryRebate extends Rebate{

    private int age;

    public PrimaryRebate(int age) {
        super("P01", "Primary rebate");
        this.age = age;
    }

    @Override
    public double calculateRebate() {
        double rebates = 15714;
        if (getAge() >= 65)
            rebates += 8613;
        if (getAge() >= 75)
            rebates += 2871;
        return rebates;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
