/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
final public class ProductTable {
    
    public static boolean checkProductExists(String brandName,String productModel){
        try{
            rs = DatabaseManagement.select(DatabaseTableNameConstants.PRODUCTTABLE, ("WHERE brandname = '" + brandName + "' AND productmodel = '" + productModel + "'"), null);
            if(rs.next())
                return true; 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return false;
    }
    
    public static int getTotalQuantity(int categoryId){
        try {
            int totalQuantity = 0;
            ResultSet rs1 = DatabaseManagement.select("SELECT quantity FROM product WHERE categoryid = " + categoryId);
            while(rs1.next()){
                totalQuantity+=rs1.getInt("quantity");
            }
            return totalQuantity;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getTotalQuantity: " + e);
        }
        return -1;
    }
    
    public static double getTotalPrice(int categoryId){
        try {
            double totalPrice = 0;
            int quantity = 0;
            ResultSet rs1 = DatabaseManagement.select("SELECT price,quantity FROM product WHERE categoryid = " + categoryId);
            while(rs1.next()){
                quantity = rs1.getInt("quantity");
                totalPrice+=(rs1.getDouble("price") * quantity);
            }
            return totalPrice;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getTotalPrice: " + e);
        }
        return -1;
    }
    public static int getCategoryId(int productId){
//        System.out.println(productId + "in categoryid pro");
        try {
            rs = DatabaseManagement.select(DatabaseTableNameConstants.PRODUCTTABLE,"WHERE productid = " + productId + "",null);
            if(rs.next()){
                int id = rs.getInt(1);
//                System.out.println(id + "id in category");
                return id;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return -1;
    }
    public static int getProductId(String value1, String value2){
        int id = 0;
        ResultSet rs = DatabaseManagement.select("product", "WHERE brandname = '" + value1 + "' AND productmodel = '" + value2 + "'", "productid");
        try {
            if(rs.next()){
                id = rs.getInt("productid");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getting productid");
        }
        return id;
    }
    public static String getBrandName(String columnName, String value){
        String s = null;
        ResultSet rs = DatabaseManagement.select("product", "WHERE " + columnName + " = '" + value + "'", "brandname");
        try {
            if(rs.next()){
                 s = rs.getString("brandname");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getting brandname");
        }
        return s;
    }
    public static String getProductModel(String columnName, String value){
        String s = null;
        ResultSet rs = DatabaseManagement.select("product", "WHERE " + columnName + " = '" + value + "'", "productmodel");
        try {
            if(rs.next()){
                 s = rs.getString("productmodel");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getting productmodel");
        }
        return s;
    }
    public static double getPrice(String columnName, String value){
        double d = 0;
        ResultSet rs = DatabaseManagement.select("product", "WHERE " + columnName + " = '" + value + "'", "price");
        try {
            if(rs.next()){
                 d = rs.getDouble("price");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getting price" + e);
        }
        return d;
    }
    public static int getQuantity(String columnName, String value){
        int d = 0;
        ResultSet rs = DatabaseManagement.select("product", "WHERE " + columnName + " = '" + value + "'", "quantity");
        try {
            if(rs.next()){
                 d = rs.getInt("quantity");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in getting quantity");
        }
        return d;
    }
    public static void productSold(String brandName, String productModel, int quantity){
        int productId = getProductId(brandName, productModel);
        int existingQuantity = getQuantity("productid",productId + "");
        double price = getPrice("productid",productId + "");
        if(quantity>0)
            update(productId, brandName,productModel, price, existingQuantity - quantity);
        else if(quantity == 0){
            delete("productid", productId);
        }
    }
    public static boolean insert(int categoryId, String productModel, String brandName, double price, int quantity){
        if(!checkProductExists(brandName, productModel)){
            String sql = "INSERT INTO product(categoryid, productmodel, brandname, price, quantity) VALUES (?,?,?,?,?)";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, categoryId);
                preparedStatement.setString(2, productModel);
                preparedStatement.setString(3, brandName);
                preparedStatement.setDouble(4, price);
                preparedStatement.setInt(5, quantity);
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Some Error in insert: " + e);            
            }
        }
        return false;
    }
    
    public static void update(int productId,String brandName,String productModel, double price, int quantity){
        if(productModel!=null && brandName!=null && price>0 && quantity>0){
            try {
                String sql = "UPDATE product SET brandname = ?, productmodel = ?, price = ?, quantity = ? WHERE productid = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, brandName);
                preparedStatement.setString(2, productModel);
                preparedStatement.setDouble(3, price);
                preparedStatement.setInt(4, quantity);
                preparedStatement.setInt(5, productId);
                preparedStatement.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while updating! " + e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error while updating! ProductTable");
        } 
           
    }
    public static void delete(String columnName,Object name){
        String sql = "DELETE FROM product WHERE " +  columnName + " = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, name);
            preparedStatement.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in delete: " + e);
        }
    }
    private static final Connection conn = MySqlConnect.connectDB();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet rs = null;
}
