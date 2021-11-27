package com.psybergate.vacwork_202107.tax_calculator.tax_table;

import java.util.List;

public class TaxTable {
    private int id;
    private List<Integer> taxBrackets;
    private List<Integer> taxPercentages;
    private int year;
    private String country;

    public TaxTable(int id, List<Integer> taxBrackets, List<Integer> taxPercentages, int year, String country) {
        this.id = id;
        this.taxBrackets = taxBrackets;
        this.taxPercentages = taxPercentages;
        this.year = year;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getTaxBrackets() {
        return taxBrackets;
    }

    public void setTaxBrackets(List<Integer> taxBrackets) {
        this.taxBrackets = taxBrackets;
    }

    public List<Integer> getTaxPercentages() {
        return taxPercentages;
    }

    public void setTaxPercentages(List<Integer> taxPercentages) {
        this.taxPercentages = taxPercentages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
