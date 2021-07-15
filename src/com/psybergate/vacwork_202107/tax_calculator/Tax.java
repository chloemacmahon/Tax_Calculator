package com.psybergate.vacwork_202107.tax_calculator;

public class Tax {

    private static final int[] TAX_BRACKETS = {216200, 337800, 467500, 613600, 782200, 165600}; //Top bracket declaration as a constant, useful when calculating other countries tax
    //Above 65 is 128650 is 0 % tax and 83100 for under 65
    private static final int[] TAX_PERCENTAGES = {18, 26, 31, 36, 39, 41, 45}; //Percentages associated with each tax bracket lat 45 is for anything higher than the largest tax bracket

    private Income[] incomes;

    private int age;

    public Tax(Income[] incomes, int age) {
        setIncomes(incomes);
        setAge(age);
    }

    public double calculateGrossTax() {
        int iTaxBracket = calculateTaxBracket();
        double dTaxableIncome = calculateTotalTaxableIncome();
        return calculateGrossTax(dTaxableIncome, 0, iTaxBracket); // 1
    }

    private double calculateGrossTax1(double remainingIncome, int currentTaxBracket, int finalTaxBracket) {

        if (currentTaxBracket == finalTaxBracket) {
            return remainingIncome * TAX_PERCENTAGES[currentTaxBracket] / 100;
        } else { //2
            return TAX_BRACKETS[currentTaxBracket] * TAX_PERCENTAGES[currentTaxBracket] / 100.0 + calculateGrossTax(remainingIncome - TAX_BRACKETS[currentTaxBracket], currentTaxBracket + 1, finalTaxBracket);
        }
    }

    //Calculate tax bracket is there because remaining income gets reduced every iteration and tax bracket increases in size thus checking if a remaining amount is less than a tax bracket will always be true
    private double calculateGrossTax(double remainingIncome, int currentTaxBracket, int finalTaxBracket) {
        if (currentTaxBracket == finalTaxBracket) {
            return remainingIncome * TAX_PERCENTAGES[currentTaxBracket] / 100;
        } else { //2
            return TAX_BRACKETS[currentTaxBracket] * TAX_PERCENTAGES[currentTaxBracket] / 100.0 + calculateGrossTax(remainingIncome - TAX_BRACKETS[currentTaxBracket], currentTaxBracket + 1, finalTaxBracket);
        }
    }

    //calculate tax bracket in the calculateGrossTax
    private int calculateTaxBracket() {
        int iCounter = 0;
        double dRemaining = calculateTotalTaxableIncome();
        while (dRemaining > TAX_BRACKETS[iCounter] && iCounter < TAX_BRACKETS.length) {
            dRemaining = dRemaining - TAX_BRACKETS[iCounter];
            iCounter++;
        }
        return iCounter;
    }

    public double calculateTotalTaxableIncome() {
        double dTotalIncome = 0;
        double dTotalCapitalGainsIncome = calculateTotalCapitalGainsTax();
        for (Income income : incomes) {
            if (income.getIncomeType().compareTo("Capital gains") != 0)
                dTotalIncome += income.calculateTaxableIncome() * income.getIncludedPercentageTax() / 100;
        }
        if (dTotalCapitalGainsIncome > 0) {
            dTotalIncome += dTotalCapitalGainsIncome; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        }
        return dTotalIncome;
    }

    public double calculateTotalCapitalGainsTax() {
        double dTotalCapitalGainsIncome = 0;
        double dTaxableCapitalGainsIncome = 0;
        for (Income income : incomes) {
            if (income.getIncomeType().compareTo("Capital gains") == 0)
                dTotalCapitalGainsIncome += income.calculateTaxableIncome();
        }
        if (dTotalCapitalGainsIncome > 0) {
            dTaxableCapitalGainsIncome = (dTotalCapitalGainsIncome - 40000) * .4; // (100000-40000)*.4 = 24000; (200000+ 100000 -40000)*.4 != (200000-40000)*.4 +(100000-40000)*.4       }
        }
        return dTaxableCapitalGainsIncome;
    }

    public double calculateRebates() {
        double dRebates = 15714;
        if (getAge() >= 65)
            dRebates += 8613;
        if (getAge() >= 75)
            dRebates += 2871;
        return dRebates;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Income[] getIncomes() {
        return incomes;
    }

    public void setIncomes(Income[] incomes) {
        this.incomes = incomes;
    }
}
