package com.psybergate.vacwork_202107.tax_calculator.tax_payer;

import com.psybergate.vacwork_202107.tax_calculator.expense.Expense;
import com.psybergate.vacwork_202107.tax_calculator.income.Income;
import com.psybergate.vacwork_202107.tax_calculator.rebate.Rebate;

import java.util.List;

public class TaxPayer {
    private List<Income> incomes;
    private List<Expense> expenses;
    private List<Rebate> rebates;
    private String taxNum;

    public TaxPayer(String taxNum, List<Income> incomes, List<Expense> expenses, List<Rebate> rebates) {
        this.incomes = incomes;
        this.expenses = expenses;
        this.rebates = rebates;
        setTaxNum(taxNum);
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Rebate> getRebates() {
        return rebates;
    }

    public void setRebates(List<Rebate> rebates) {
        this.rebates = rebates;
    }
}
