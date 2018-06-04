package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jains
 */
public class DatabaseManagement {
    private static String getNames(String... columnNames){
        /*
        This method returns a string of names concatinated with a comma(,) for the select query.
        */
        String names = "";
        for(int i = 0;i<columnNames.length - 1;i++){
            names += columnNames[i] + ", ";
        }
        names += columnNames[columnNames.length - 1];
        return names;
    }
    public static int getNumRows(ResultSet rs){
        /*
            This method returns the number of rows in a resultset by adjusting the resultset
            , at the end the resultset is brought back to the initial state
        */
        int numRows = 0;
        try {
            rs.last();
            numRows = rs.getRow();
            rs.beforeFirst();
            return numRows;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error In SQL query " + e.getMessage(), "SQLError", JOptionPane.ERROR_MESSAGE);
        }
        return numRows;  
    }
    public static int getNumRows(String tableName){
        int numRows = 0;
        ResultSet rs = DatabaseManagement.select("SELECT COUNT(*) from " + tableName);
        try {
            while(rs.next()){
                numRows = rs.getInt(1);
            }
            return numRows;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error In SQL query getNumRows" + e.getMessage(), "SQLError", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }
    
    public static ResultSet select(String sql){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
//            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return rs;
    }
    public static ResultSet select(String tableName, String condition, String... columnNames){
        /*
            This method returns the ResultSet for a specific table. It applies the select 
            Query on the database
        */
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        if(columnNames == null && condition == null){
            sql = "SELECT * FROM " + tableName;
        }
        else if(columnNames == null && condition != null){
            sql = "SELECT * FROM " + tableName + " " + condition;
        }
        else if(columnNames!=null && condition!=null){
            sql = "SELECT " + getNames(columnNames) + " FROM " + tableName + " " + condition;  
        }
        else if(columnNames!=null && condition == null){
            sql = "SELECT " + getNames(columnNames) + " FROM " + tableName;
        }
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error In SQL query " + e.getMessage(), "SQLError", JOptionPane.ERROR_MESSAGE);
        }
        return rs;
    }
    //Variable declarations
    private static Connection conn = MySqlConnect.connectDB();;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet rs = null;
    private static String sql = null;
    //end of variable declaration             
}
