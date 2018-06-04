/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import constants.DatabaseColumnNameConstants;
import constants.DatabaseTableNameConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jains
 */
public class CategoryTable {
        public static void changeCategoryQuantity(int cId){
            CategoryTable.update(DatabaseColumnNameConstants.TOTALPRODUCTS, ProductTable.getTotalQuantity(cId), cId);
    }
    public static void changeCategoryPrice(int cId){
        CategoryTable.update(DatabaseColumnNameConstants.TOTALVALUEPRODUCTS, ProductTable.getTotalPrice(cId), cId);
    }
    public static boolean checkCategoryExists(String categoryName){
        try{
            rs = DatabaseManagement.select(DatabaseTableNameConstants.CATEGORYTABLE, ("WHERE categoryname = '" + categoryName + "'"), null);
            if(rs.next())
                return true; 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return false;
    }
    public static int getCategoryId(String columnName,String name){
        try {
            rs = DatabaseManagement.select(DatabaseTableNameConstants.CATEGORYTABLE,"WHERE " + columnName + " = '" + name + "'",null);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return -1;
    }
    public static String getCategoryName(String columnName, String name){
        try {
            rs = DatabaseManagement.select(DatabaseTableNameConstants.CATEGORYTABLE,"WHERE " + columnName + " = '" + name + "'",null);
            if(rs.next()){
                return rs.getString(2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return null;
    }
    public static int getTotalProducts(String columnName, String name){
        try {
            rs = DatabaseManagement.select(DatabaseTableNameConstants.CATEGORYTABLE,"WHERE " + columnName + " = '" + name + "'",null);
            if(rs.next()){
                return rs.getInt(3);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return -1;
    }
    public static int getTotalProducts(int categoryId){
        return getTotalProducts("categoryid",categoryId+"");
    }
    public static double getTotalValueOfProducts(String columnName, String name){
        try {
            rs = DatabaseManagement.select(DatabaseTableNameConstants.CATEGORYTABLE,"WHERE " + columnName + " = '" + name + "'",null);
            if(rs.next()){
                return rs.getDouble(4);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return -1;
    }
    public static double getTotalValueOfProducts(int categoryId){
        return getTotalValueOfProducts("categoryid",categoryId+"");
    }
    public static int getAllTotalProducts(){
        try {
            ResultSet rs = DatabaseManagement.select("category",null,null);
            int allTotalProducts = 0;
            while(rs.next()){
                allTotalProducts += rs.getInt("totalproducts");
            }
            return allTotalProducts;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getAllTotalProducts : " + e);
        }
        return -1;
    }
    public static double getAllTotalValueOfProducts(){
        try {
            ResultSet rs = DatabaseManagement.select("category",null,null);
            double allTotalValueOfProducts = 0;
            while(rs.next()){
                allTotalValueOfProducts += rs.getDouble("tvalueproducts");
            }
            return allTotalValueOfProducts;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getAllValueOfTotalProducts : " + e);
        }
        return -1;
    }
    public static void insert(String categoryName, int tQuantity, double tValue){
        if(conn!=null){
            try{
                if(!checkCategoryExists(categoryName)){
                    sql = "INSERT INTO category(categoryname, totalproducts, tvalueproducts) VALUES (?,?,?)";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, categoryName);
                    preparedStatement.setInt(2, tQuantity);
                    preparedStatement.setDouble(3, tValue);
                    preparedStatement.execute();
                    JOptionPane.showMessageDialog(null, "Record Saved Successfully");
                }else{
                    JOptionPane.showMessageDialog(null, "Category already exists!");
                }
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Some Error : " + e);            
            }
        }   
    }
    public static void update(String columnName, Object value, int categoryId){
        if(!columnName.equals("") && columnName!=null && value!=null){
            try {
                sql = "UPDATE category SET " + columnName + " = ? WHERE categoryid = ?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setObject(1, value);
                preparedStatement.setInt(2, categoryId);
                preparedStatement.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while updating! " + e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error while updating! CategoryTable");
        }
           
    }
    public static void delete(String columnName,Object name){
        sql = "DELETE FROM category WHERE " +  columnName + " = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, name);
            preparedStatement.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
    }
    private static Connection conn = MySqlConnect.connectDB();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet rs = null;
    private static String sql = null;
}
