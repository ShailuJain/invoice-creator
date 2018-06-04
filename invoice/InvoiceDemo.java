/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

/**
 *
 * @author jains
 */
public class InvoiceDemo {
    public static void main(String[] args) {
       Invoice myInvoice = new Invoice("C:\\Users\\Shailesh\\Documents\\invoices");
       myInvoice.setCustomerDetails("default", "7894561223", "default@gmail.com");
       myInvoice.addProduct("defaultBrand", "d12", 100,5);
       myInvoice.addProduct("Samsung Galaxy", "Samsung S8", 100,10);
       myInvoice.addProduct("defaultBrand", "d12", 100,5);
       myInvoice.addProduct("Samsung Galaxy", "Samsung S8", 100,10);
       myInvoice.addProduct("defaultBrand", "d12", 100,5);
       myInvoice.addProduct("Samsung Galaxy", "Samsung S8", 100,10);
       myInvoice.addProduct("defaultBrand", "d12", 100,5);
       myInvoice.addProduct("Samsung Galaxy", "Samsung S8", 100,10);
       myInvoice.createInvoice();
    }
}