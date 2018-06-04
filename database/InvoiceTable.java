/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jains
 */
public class InvoiceTable {
    
    public static void insert(String customerEmail,int totalGST, int totalPrice){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO invoice(customeremail,GST,totalprice) VALUES(?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customerEmail);
            preparedStatement.setInt(2, totalGST);
            preparedStatement.setInt(3, totalPrice);
            
            preparedStatement.execute();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Some Error in insert InvoiceTable: " + e); 
        }
    }
    public static int getGSTOfAllInvoices(){
        int GSTOfAllInvoices = 0;
        try {
            ResultSet rs = DatabaseManagement.select("invoice", null, "GST");
            while(rs.next()){
                GSTOfAllInvoices += rs.getInt("GST");
            }
            return GSTOfAllInvoices;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Some Error in insert: " + e); 
        }
        return -1;
    }
    
    
    private static final Connection conn = MySqlConnect.connectDB();
}
