package com.psybergate.vacwork_202107.tax_calculator.database;

import com.psybergate.vacwork_202107.tax_calculator.TaxCalculator;
import com.psybergate.vacwork_202107.tax_calculator.expense.Expense;
import com.psybergate.vacwork_202107.tax_calculator.expense.Pension;
import com.psybergate.vacwork_202107.tax_calculator.expense.TransportExpense;
import com.psybergate.vacwork_202107.tax_calculator.income.Income;
import com.psybergate.vacwork_202107.tax_calculator.tax_payer.Individual;
import com.psybergate.vacwork_202107.tax_calculator.tax_table.TaxTable;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class TaxDatabase {
    private final String jdbcURL = "jdbc:postgresql://localhost:5432/TAX";
    private final String username = "postgres";
    private final String password = "postgres";
    private Connection connection;

    public void setConnection() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connect to server");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void insertTaxPayer(Individual taxPayer) {
        try {
            String sql = "INSERT INTO tax_payer (tax_number,name,age) " + "VALUES ('" + taxPayer.getTaxNum() +
                    "','" + taxPayer.getFirstName() + " " + taxPayer.getLastName() + "'," + taxPayer.getAge() + ")";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println("Object added to database");
            }
        } catch (SQLException e) {
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
            System.out.println("Failed to add object to database");
        }
    }
    /*

            Income table
            salary table inheritss from income
            capital gains table
     */

    public int insertIntoTable(String sql){
        try {
            System.out.println("sql = " + sql);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
            return -1;
        }
    }


    public void insertExpenses(TaxCalculator taxCalculator) {
        for (Expense expense : taxCalculator.getTaxPayer().getExpenses()) {
            String mainExpenseInsertSQL = "INSERT INTO expense(tax_number,expense_type,year,taxable_expense)" +
                    "VALUES ('" + taxCalculator.getTaxPayer().getTaxNum() + "','" + expense.getType() +
                    "'," + taxCalculator.getYear() + "," + expense.calculateTaxableExpense() + ")";
            int expenseID = insertIntoTable(mainExpenseInsertSQL);
            int id = insertIntoTable(expense.insertString(expenseID));
        }
    }

    public void insertIncomes(TaxCalculator taxCalculator) {
        for (Income income : taxCalculator.getTaxPayer().getIncomes()) {
            String mainExpenseInsertSQL = "INSERT INTO income(tax_number,income_type,year,taxable_income)" +
                    "VALUES ('" + taxCalculator.getTaxPayer().getTaxNum() + "','" + income.getType() +
                    "'," + taxCalculator.getYear() + "," + income.calculateTaxableIncome() + ")";
            int incomeID = insertIntoTable(mainExpenseInsertSQL);
            int id = insertIntoTable(income.insertString(incomeID));
        }
    }

    public void insertTaxCalculation(TaxCalculator taxCalculator){
        try {
            boolean newUser = false;
            /*if (selectQuery("*","tax_payer","WHERE tax_number = '"+taxCalculator.getTaxPayer().getTaxNum()+"'") == 0){
               insertTaxPayer(taxCalculator.getTaxPayer());
            }*/
            String sql = "INSERT INTO yearly_tax (year,taxable_income,taxable_expense,tax_payable,tax_number) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,taxCalculator.getYear());
            pstmt.setDouble(2,taxCalculator.calculateNetTaxableIncome());
            pstmt.setDouble(3,taxCalculator.calculateTotalTaxableExpenses());
            pstmt.setDouble(4,taxCalculator.calculateGrossTax());
            pstmt.setString(5,taxCalculator.getTaxPayer().getTaxNum());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
        }
    }

    /*
    public void insertTaxTable(TaxTable taxTable) {
        try {
            String sql = "INSERT INTO tax_table (tax_brackets,tax_percentages,tax_year, country) " + "VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            Array arrTaxBrackets = (taxTable.getTaxBrackets()).toArray();
            pstmt.setArray(1, arrTaxBrackets);
            pstmt.setArray(2, taxTable.getTaxPercentages());
            pstmt.setInt(3, taxTable.getYear());
            pstmt.setString(4, taxTable.getCountry());
            pstmt.executeUpdate();
            connection.commit();
            System.out.println("Object added to database");
        } catch (SQLException e) {
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
            System.out.println("Failed to add object to database");
        }
    }*/

    public void selectTaxPayer() {
        try {
            String sql = "SELECT * FROM tax_payer";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("result.getString(\"tax_number\") = " + result.getString("tax_number"));
                System.out.println("result.getString(\"name\") = " + result.getString("name"));
                System.out.println("result.getInt(\"age\") = " + result.getInt("age"));
            }
        } catch (SQLException e) {
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
        }
    }

    public void selectTaxYear() {
        try {
            String sql = "SELECT * FROM yearly_tax";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("Tax_number "+ result.getString("tax_number") + " info for the year "
                        + result.getInt("Year") + "is: ");
                System.out.println("Taxable income: " + result.getDouble("taxable_income"));
                System.out.println("Taxable expense: " + result.getDouble("taxable_expense"));
                System.out.println("Tax payable: " + result.getDouble("tax_payable"));

            }
        } catch (SQLException e) {
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
        }
    }

    private int selectQuery(String fieldsToSelect, String tableName, String criteria){ //Add boolean print value
          String sql = "SELECT "+ fieldsToSelect +" FROM " + tableName+ criteria;
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.last();
            int itemCount = results.getRow();
            return itemCount;
        } catch(SQLException e){
            return 0;
        }
    }

    public void closeConnection(){
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e){
            System.out.println("Failed to close connection");
        }
    }
}