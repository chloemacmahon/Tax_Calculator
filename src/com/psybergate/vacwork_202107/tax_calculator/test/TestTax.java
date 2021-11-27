package com.psybergate.vacwork_202107.tax_calculator.test;
import com.psybergate.vacwork_202107.tax_calculator.database.TaxDatabase;
import com.psybergate.vacwork_202107.tax_calculator.expense.Expense;
import com.psybergate.vacwork_202107.tax_calculator.expense.Pension;
import com.psybergate.vacwork_202107.tax_calculator.expense.TransportExpense;
import com.psybergate.vacwork_202107.tax_calculator.income.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestTax {
    private final Salary salaryIncome = new Salary(7500,12);
    private final Expense pensionExpense = new Pension(6000,salaryIncome);
    private final Expense travelExpense = new TransportExpense(5000,20000);
    private List<Income> incomes = new ArrayList<>();
    private final TaxDatabase db = new TaxDatabase();
    //private final TaxCalculator calculator = new TaxCalculator(income1,expense1,65);

    @Test
    public void testPensionTaxableExpense(){
        Assertions.assertEquals(6000,pensionExpense.calculateTaxableExpense());
    }
    //Total over the year
    @Test
    public void testTravelTaxableExpense(){
        Assertions.assertEquals(36639,travelExpense.calculateTaxableExpense());
    }
    @Test
    public void testSalaryIncome(){
        Assertions.assertEquals(90000,salaryIncome.calculateTaxableIncome());
    }
    /*
    @Test
    public void testDbConnection(){ Assertions.assertEquals(,db.setConnection());}
    */
}
