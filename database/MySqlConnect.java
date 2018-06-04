package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
public class MySqlConnect {
    public static Connection connectDB(){
        try {
            Connection conn = DriverManager.getConnection(CONNECTIONSTRING, "jainshailesh91", "shailujain123");
            //JOptionPane.showMessageDialog(null, "Connection Successful!","Connection", JOptionPane.INFORMATION_MESSAGE);
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection Unsuccessful : " + e.getMessage() ,"Connection", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Something went wrong : " + e.getMessage() ,"Connection", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    private static final String CONNECTIONSTRING = "jdbc:mysql://localhost:3306/mobiledb";
}
