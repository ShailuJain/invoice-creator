/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import database.CustomerTable;
import database.DatabaseManagement;
import database.InvoiceTable;
import static invoice.OffsetConstants.CGSTAMOUNTX;
import static invoice.OffsetConstants.CGSTAMOUNTY;
import static invoice.OffsetConstants.CUSTOMERDETAILSX;
import static invoice.OffsetConstants.CUSTOMERDETAILSY;
import static invoice.OffsetConstants.INVOICENOX;
import static invoice.OffsetConstants.INVOICENOY;
import static invoice.OffsetConstants.PRODUCTNAMEX;
import static invoice.OffsetConstants.PRODUCTNAMEY;
import static invoice.OffsetConstants.SGSTAMOUNTX;
import static invoice.OffsetConstants.SGSTAMOUNTY;
import static invoice.OffsetConstants.TOTALPRICEX;
import static invoice.OffsetConstants.TOTALPRICEY;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author jains
 */


public class Invoice {
    
    public Invoice(String path){
        this.path = path;
        init();
    }
    public Invoice(){
        init();
    }
    
    //Initialization
    private void init(){
        products = new ArrayList<>();
        invoiceNo = getNextInvoiceNo();
        file = new File(this.path + "\\Invoice" + invoiceNo + ".pdf");
        loadPDFFile(new File("C:\\Users\\Shailesh\\Documents\\NetBeansProjects\\Login1\\src\\resources\\Invoice.pdf"));
        CGSTAmount = 0;
        SGSTAmount = 0;
        totalGSTAmount = 0;
        totalPriceExcludingGST = 0;
        totalPriceIncludingGST = 0;
        writeDate();
    }
//    private Object readInvoiceNo(){;
//        try {
//            ois = new ObjectInputStream(new FileInputStream(objPath));
//            return ois.readObject();
//        } catch (Exception e) {
//            System.out.println("ex in " + e);
//        }
//        return null;
//    }
//    private void write(){
//        try {
//            oos = new ObjectOutputStream(new FileOutputStream(objPath));
//            oos.writeObject(invoiceNo+"");
//        } catch (Exception e) {
//            System.out.println("Ex in " + e);
//        }
//    }
    private void writeDate(){
        try {
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(370, 600);
            contentStream.showText(new Date().toString());
            contentStream.endText();
        } catch (IOException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setPath(String path) {
        this.path = path;
    }
    
    // Getters and Setters
       public float getCGSTAmount() {
        return CGSTAmount;
    }

    public void setCGSTAmount(float CGSTAmount) {
        this.CGSTAmount = CGSTAmount;
    }

    
    public float getSGSTAmount() {
        return SGSTAmount;
    }

    public void setSGSTAmount(float SGSTAmount) {
        this.SGSTAmount = SGSTAmount;
    }

    
    public float getTotalPriceExcludingGST() {
        return totalPriceExcludingGST;
    }

    public float getTotalPriceIncludingGST() {
        return totalPriceIncludingGST;
    }
    
    
    
    public float getTotalGSTAmount(){
        if(totalGSTAmount>0)
            return totalGSTAmount;
        return -1;
    }
    
    
    //Calculation work
    private void calculateCGSTAmount(){
        CGSTAmount = calculateTax(totalPriceExcludingGST, CGST);
    }
    private void calculateSGSTAmount(){
        SGSTAmount = calculateTax(totalPriceExcludingGST, SGST);
    }
    
    private float calculateTax(float totalAmount, float percent){
        float tax = 0;
        tax = (totalAmount * percent)/100;
        return tax;
    }
    private float calculateTotalTax(){
        return (totalGSTAmount = CGSTAmount + SGSTAmount);
    }
    private float calculateTotalPriceFromProducts(){
        float totalPriceExcludingGST = 0;
        for (Product p : products){
            totalPriceExcludingGST += (p.price * p.quantity);
        }
        return totalPriceExcludingGST;
    }
    private float calculateTotalPriceIncludingGST(){
        if(totalPriceExcludingGST > 0 && CGSTAmount > 0 && SGSTAmount > 0){
            return (totalPriceIncludingGST = totalPriceExcludingGST + calculateTotalTax());
        }
        return -1;
    }
    
    
    @Override
    public String toString() {
        return "Invoice{" + "path=" + path + ", invoiceNo=" + invoiceNo + '}';
    }
    private int getCustomerId(){
        int cId = -1;
        if(customerDetails != null){
            cId = CustomerTable.getCustomerId(customerDetails.phoneNo);
        }
        return cId;
    }
    public int getInvoiceNo() {
        return invoiceNo;
    }
    public CustomerDetails getCustomerDetails(){
        return new CustomerDetails(customerDetails);
    }
    public void setCustomerDetails(String name, String phoneNo, String email){
      customerDetails = new CustomerDetails(name,phoneNo,email);
    }
    public void setCustomerDetails(CustomerDetails cd){
        customerDetails = cd;
    }
    public void setCustomerDetails(ResultSet rs){
        try {
            while(rs.next()){
                customerDetails.name = rs.getString("name");
                customerDetails.phoneNo = rs.getString("phoneno");
                customerDetails.email = rs.getString("email");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "in setCustomerDetails");
        }
    }
    public void addProduct(String brandName,String productModel,double price,int quantity){
         products.add(new Product(brandName, productModel, price, quantity));
    }
    public void addProduct(Product pd){
        addProduct(pd.brandName,pd.productModel,pd.price,pd.quantity);
    }
    public void addProduct(ResultSet rs){
        try {
            while(rs.next()){
                addProduct(rs.getString("brandName"),rs.getString("productModel"),rs.getDouble("price"),rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "in addProductDetails");
        }
    }

    private int getNextInvoiceNo() {
        try {
            ResultSet rs = DatabaseManagement.select("SELECT * FROM invoice");
            if(rs.last())
                return rs.getInt("invoiceno");
            else
                return 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "in getNextInvoiceNo");
        }
        return -1;
//        return DatabaseManagement.getNumRows("invoice");
    }
    private class Product{

        private String brandName;
        private String productModel;
        private int quantity;
        private double price;

        public Product(String brandName, String productModel, double price, int quantity) {
            this.brandName = brandName;
            this.productModel = productModel;
            this.price = price;
            this.quantity = quantity;
        }
    }
    private class CustomerDetails{
        private String name;
        private String phoneNo;
        private String email;
        
        CustomerDetails(CustomerDetails cd){
            this(cd.name,cd.phoneNo,cd.email);
        }
        CustomerDetails(String name, String phoneNo, String email){
            this.name = name;
            this.phoneNo = phoneNo;
            this.email = email;
        }
    }
    
    
    //PDF Writing
    private void loadPDFFile(File file){
        try {
            templatePDF = PDDocument.load(file);
            contentStream = new PDPageContentStream(templatePDF,templatePDF.getPage(0),PDPageContentStream.AppendMode.APPEND,false);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e + " in loadPDFFile");
        }
    }
    public boolean createInvoice(){
        if(customerDetails != null && products != null){
            try {
                
                writeInvoiceNo();
                
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.setLeading(22);
                
                writeCustomerDetails();
                int count = 0;
                for(Product p : products){
                    writeProductDetails(p,count++);
                }
                totalPriceExcludingGST = calculateTotalPriceFromProducts();
                writeCGSTAmount();
                writeSGSTAmount();
                writeTotalPriceIncludingGST();
                
                saveAndClose();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e + " in createInvoice");
            }
            return true;
        }
        return false;
    }
    private void writeCGSTAmount(){
        calculateCGSTAmount();
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(CGSTAMOUNTX, CGSTAMOUNTY);
            contentStream.showText("" + getCGSTAmount());
            contentStream.endText();
        } catch (Exception e) {
            System.out.println(e + " in writeCGST");
        }
    }
    private void writeSGSTAmount(){
        calculateSGSTAmount();
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(SGSTAMOUNTX, SGSTAMOUNTY);
            contentStream.showText("" + getSGSTAmount());
            contentStream.endText();
        } catch (Exception e) {
            System.out.println(e + " in writeSGST");
        }
    }
    private void writeTotalPriceIncludingGST(){
        calculateTotalPriceIncludingGST();
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(TOTALPRICEX, TOTALPRICEY);
            contentStream.showText("" + getTotalPriceIncludingGST());
            contentStream.endText();
        } catch (Exception e) {
            System.out.println(e + " in writeSGST");
        }
    }
    private void writeProductDetails(Product p, int productNum) throws IOException{
        /**
         *once a newLineOffset method is called from (0,0) location between a text block i.e in between beginText and endText method it sets the location from the current position when it is recalled
         * Once a text block is finished and restarted the offset is reset to (0,0)
         */
        if(p != null && !isInvoiceEnded(productNum)){
            contentStream.beginText();
            contentStream.newLineAtOffset(PRODUCTNAMEX,PRODUCTNAMEY - (productNum * 22));
            contentStream.showText(p.brandName + " ");
            contentStream.showText(p.productModel);
            contentStream.newLineAtOffset(PRODUCTNAMEX + 230, 0);
            contentStream.showText(p.quantity + "");
            contentStream.newLineAtOffset(PRODUCTNAMEX + 160, 0);
            contentStream.showText(p.price + "");
            contentStream.endText();
        }
    }
    private void writeCustomerDetails() throws IOException{
            contentStream.beginText();
            contentStream.newLineAtOffset(CUSTOMERDETAILSX,CUSTOMERDETAILSY);
            contentStream.showText(customerDetails.name);
            contentStream.newLine();
            contentStream.showText(customerDetails.phoneNo);
            contentStream.newLine();
            contentStream.showText(customerDetails.email);
            contentStream.endText();
    }
    private boolean isInvoiceEnded(int numOfProduct){
        if(numOfProduct >= 16)
            return true;
        return false;
    }
    private void writeInvoiceNo() throws IOException{
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.newLineAtOffset(INVOICENOX,INVOICENOY);
        contentStream.showText(" " + invoiceNo);
        contentStream.endText();
    }
    private void saveAndClose(){
        try {
            contentStream.close();
            templatePDF.save(file);
            InvoiceTable.insert(customerDetails.email, (int)getTotalGSTAmount(), (int)totalPriceIncludingGST);
            templatePDF.close();
        } catch (IOException e) {
            System.out.println(e+ " in saveAndClose");
        }
    }
    private PDDocument templatePDF = null;
    private PDPageContentStream contentStream = null;
    private String path = null;
    private int invoiceNo = -1;
    private CustomerDetails customerDetails = null;
    private List<Product> products = null;
    private File file = null;
    public static final float CGST = 8;
    public static final float SGST = 8;
    private float CGSTAmount;
    private float SGSTAmount;
    private float totalGSTAmount;
    private float totalPriceExcludingGST;
    private float totalPriceIncludingGST;
}