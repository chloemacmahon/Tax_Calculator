package com.psybergate.vacwork_202107.tax_calculator;

public class Tax {

    private static final int[] TAX_BRACKETS = {216200,337800,467500,613600,782200,165600}; //Top bracket declaration as a constant, useful when calculating other countries tax
    //Above 65 is 128650 is 0 % tax and 83100 for under 65
    private static final int[] TAX_PERCENTAGES = {18,26,31,36,39,41,45}; //Percentages associated with each tax bracket lat 45 is for anything higher than the largest tax bracket

    private Income[] incomes;

    private int age;

    public Tax(Income[] incomes, int age) {
        setIncomes(incomes);
        setAge(age);
    }

    public double calculateGrossTax () {
        int iTaxBracket = calculateTaxBracket();
        double dTaxableIncome = calculateTotalTaxableIncome();
        return calculateGrossTax(dTaxableIncome,0, iTaxBracket);
    }

    public double calculateGrossTax(double remainingIncome, int currentTaxBracket, int finalTaxBracket) {
        if (currentTaxBracket == finalTaxBracket) {
            return remainingIncome * TAX_PERCENTAGES[currentTaxBracket]/100;
        } else {
            return TAX_BRACKETS[currentTaxBracket] * TAX_PERCENTAGES[currentTaxBracket]/100 + calculateGrossTax(remainingIncome-TAX_BRACKETS[currentTaxBracket],currentTaxBracket+1, finalTaxBracket);
        }
    }

    public int calculateTaxBracket(){
        int iCounter = 0;
        double dRemaining = calculateTotalTaxableIncome();
        while (dRemaining > TAX_BRACKETS[iCounter] && iCounter < TAX_BRACKETS.length) {
            dRemaining = dRemaining - TAX_BRACKETS[iCounter];
            iCounter++;
        }
        System.out.println("iCounter = " + iCounter);
        return iCounter;
    }

    public double calculateTotalTaxableIncome(){
        double dTotalIncome = 0;
        double dTotalCapitalGainsIncome =0;
        for (int i = 0; i < incomes.length; i++) {
            if (incomes[i] instanceof CapitalGainsIncome)
                dTotalCapitalGainsIncome += incomes[i].calculateTaxableIncome();
            else
                dTotalIncome += incomes[i].calculateTaxableIncome();
        }
        if (dTotalCapitalGainsIncome > 0) {
            dTotalIncome += (dTotalCapitalGainsIncome - 40000) * .4;
        }
        return dTotalIncome;
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
