/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import static constants.MyColorConstants.MYWHITE;
import database.CategoryTable;
import database.CustomerTable;
import database.InvoiceTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author jains
 */

public class DashPanel extends javax.swing.JPanel {

    /**
     * Creates new form DashPanel
     */
    private Animation a;
    private JLabel lblRotate[] = new JLabel[3];
    private int count;
    private interface ColorConstants{
        //For Panel 1
        static final Color TOTALCUSTOMERSPANELLIGHTCOLOR = new Color(138,29,162);
        static final Color TOTALCUSTOMERSPANELDARKCOLOR = new Color(138,29,162);
        
        //For Panel 2
        static final Color TOTALPRODUCTSPANELLIGHTCOLOR = new Color(255,103,0);
        static final Color TOTALPRODUCTSPANELDARKCOLOR = new Color(28,232,171);
        
        //For Panel 3
        static final Color TOTALVALUEPANELLIGHTCOLOR = new Color(233,49,72);
        static final Color TOTALVALUEPANELDARKCOLOR = new Color(233,49,72);
        
        //For Panel 4
        static final Color TOTALTAXPANELLIGHTCOLOR = new Color(84,102,180);
        static final Color TOTALTAXPANELDARKCOLOR = new Color(84,102,180);
    }
    class Animation{
        JLabel labelToAnimate = null;
        Animation(JLabel labelToAnimate){
            this.labelToAnimate = labelToAnimate;
        }
        public void startGrow(){
            new Thread(()->{
                
                    boolean isGrown = false;
                    while(!isGrown){
                        try {
                            Thread.sleep(20);
                        } catch (Exception e) {
                        }
                        Dimension d = labelToAnimate.getSize();
                        if(d.height < 550){
                            d.height += 10;

                            synchronized(labelToAnimate){
                                labelToAnimate.setSize(d);
                                labelToAnimate.setLocation((labelToAnimate.getLocation().x), labelToAnimate.getLocation().y - 5);
                            }
                        }else{
                            isGrown = true;
//                            lblRotate[0] = lblRotate[2];
                            lblRotate[1] = labelToAnimate;
                        }
                    }
            }).start();
        }
        public void startSlide(int end){
            new Thread(() -> {
                
                    boolean hasSlided = false;
                    while(!hasSlided){
                        try {
                            Thread.sleep(20);
                        } catch (Exception e) {
                        }
                        synchronized(labelToAnimate){
                            Point p = labelToAnimate.getLocation();
                            if(p.x < end){
                                p.x += 20;
                                labelToAnimate.setLocation(p);
                            }else{
                                hasSlided = true;
                            }
                        }
                }
            }).start();
        }
        public void startShrink(){
            new Thread(()->{
                boolean isShrinked = false;
                while(!isShrinked){
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                    }
                    Dimension d = labelToAnimate.getSize();
                    if(d.height > 130){
                        d.height -= 10;
                        synchronized(labelToAnimate){
                            labelToAnimate.setSize(d);
                            labelToAnimate.setLocation((labelToAnimate.getLocation().x), labelToAnimate.getLocation().y + 5);
                        }
                    }else{
                        isShrinked = true;
                        lblRotate[0] = lblRotate[2];
                        lblRotate[2] = labelToAnimate;
                    }
                }
            }).start();
        }
    }
    public DashPanel() {
        initComponents();
        setPanelColors();
        IconFontSwing.register(FontAwesome.getIconFont());
        setIconsOnLables();
        updateCardPanelData();
//        lblRotate1.setVisible(false);
//        lblRotate2.setVisible(false);
//        lblRotate3.setVisible(false);
        lblRotate[0] = lblRotate1;
        lblRotate[1] = lblRotate2;
        lblRotate[2] = lblRotate3;
        lblRotate1.setLocation(-680,lblRotate1.getLocation().y);
        lblRotate3.setLocation(1400, lblRotate3.getLocation().y);
        startAnimation();

    }
    private void startAnimation(){
        new SwingWorker<Void,Void>(){
            Animation a = null,a1 = null;
            boolean tp = false;
            @Override
            protected Void doInBackground(){
                while(true){
                    try {
                        Thread.sleep(8000);
                    } catch (Exception e) {
                    }
                    new Thread(()->{
                        a = new Animation(lblRotate[0]);
                        a.startGrow();
                        a.startSlide(350);
                    }).start();
                    new Thread(()->{
                        lblRotate[2].setLocation(-680, lblRotate[2].getLocation().y);
                        a1 = new Animation(lblRotate[1]);
                        a1.startShrink();
                        a1.startSlide(1400);
                    }).start();
                }
            }
        }.execute();
    }
    private void setPanelColors(){
        totalCustomerPanel.setBackground(ColorConstants.TOTALCUSTOMERSPANELDARKCOLOR);
        totalProductsPanel.setBackground(ColorConstants.TOTALPRODUCTSPANELDARKCOLOR);
        totalValuePanel.setBackground(ColorConstants.TOTALVALUEPANELDARKCOLOR);
        totalTaxPanel.setBackground(ColorConstants.TOTALTAXPANELDARKCOLOR);
    }
    private void setIconsOnLables(){
        Icon ic;
        ic = IconFontSwing.buildIcon(FontAwesome.USERS, ICONSIZE, MYWHITE);
        lblTotalCustomers.setIcon(ic);
        ic = IconFontSwing.buildIcon(FontAwesome.DROPBOX, ICONSIZE, MYWHITE);
        lblTotalProducts.setIcon(ic);
        ic = IconFontSwing.buildIcon(FontAwesome.USD, ICONSIZE, MYWHITE);
        lblTotalValue.setIcon(ic);
        ic = IconFontSwing.buildIcon(FontAwesome.PERCENT, ICONSIZE, MYWHITE);
        lblTotalTax.setIcon(ic);
    }
    private void updateCardPanelData(){
        lblTotalCustomers.setText(CustomerTable.getTotalCustomers() + "");
        lblTotalProducts.setText(CategoryTable.getAllTotalProducts() + "");
        lblTotalValue.setText(CategoryTable.getAllTotalValueOfProducts() + "");
        lblTotalTax.setText(InvoiceTable.getGSTOfAllInvoices() + "");
    }
    public void updateChanges(){
        updateCardPanelData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        totalCustomerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotalCustomers = new javax.swing.JLabel();
        totalValuePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        totalTaxPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalTax = new javax.swing.JLabel();
        totalProductsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalProducts = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblRotate1 = new javax.swing.JLabel();
        lblRotate2 = new javax.swing.JLabel();
        lblRotate3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 250, 255));

        cardPanel.setBackground(new java.awt.Color(255, 250, 255));
        cardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 27, 24), 8));

        totalCustomerPanel.setBackground(new java.awt.Color(138, 29, 162));
        totalCustomerPanel.setToolTipText("Total Customers");
        totalCustomerPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        totalCustomerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                totalCustomerPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                totalCustomerPanelMouseExited(evt);
            }
        });
        totalCustomerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(161, 74, 180));
        jLabel1.setOpaque(true);
        totalCustomerPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 300, 30));

        lblTotalCustomers.setFont(new java.awt.Font("Copse", 1, 24)); // NOI18N
        lblTotalCustomers.setForeground(new java.awt.Color(255, 250, 255));
        lblTotalCustomers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalCustomers.setIconTextGap(100);
        totalCustomerPanel.add(lblTotalCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 180));

        totalValuePanel.setBackground(new java.awt.Color(233, 49, 72));
        totalValuePanel.setToolTipText("Total Inventory Products Value");
        totalValuePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                totalValuePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                totalValuePanelMouseExited(evt);
            }
        });
        totalValuePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(237, 90, 108));
        jLabel3.setOpaque(true);
        totalValuePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 300, 30));

        lblTotalValue.setFont(new java.awt.Font("Copse", 1, 24)); // NOI18N
        lblTotalValue.setForeground(new java.awt.Color(255, 250, 255));
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalValue.setIconTextGap(100);
        totalValuePanel.add(lblTotalValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 180));

        totalTaxPanel.setBackground(new java.awt.Color(84, 102, 180));
        totalTaxPanel.setToolTipText("Total Tax Collected");
        totalTaxPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                totalTaxPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                totalTaxPanelMouseExited(evt);
            }
        });
        totalTaxPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(135, 148, 202));
        jLabel4.setOpaque(true);
        totalTaxPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 300, 30));

        lblTotalTax.setFont(new java.awt.Font("Copse", 1, 24)); // NOI18N
        lblTotalTax.setForeground(new java.awt.Color(255, 250, 255));
        lblTotalTax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalTax.setIconTextGap(100);
        totalTaxPanel.add(lblTotalTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 180));

        totalProductsPanel.setBackground(new java.awt.Color(28, 232, 171));
        totalProductsPanel.setToolTipText("Total Inventory Products");
        totalProductsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                totalProductsPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                totalProductsPanelMouseExited(evt);
            }
        });
        totalProductsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(132, 255, 218));
        jLabel2.setOpaque(true);
        totalProductsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 300, 30));

        lblTotalProducts.setFont(new java.awt.Font("Copse", 1, 24)); // NOI18N
        lblTotalProducts.setForeground(new java.awt.Color(255, 250, 255));
        lblTotalProducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalProducts.setIconTextGap(100);
        totalProductsPanel.add(lblTotalProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 180));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1420, 613));
        jPanel1.setLayout(null);

        lblRotate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/qrinvoices.png"))); // NOI18N
        lblRotate1.setText("Try");
        lblRotate1.setOpaque(true);
        jPanel1.add(lblRotate1);
        lblRotate1.setBounds(0, 230, 700, 130);

        lblRotate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/qrabout.png"))); // NOI18N
        lblRotate2.setOpaque(true);
        jPanel1.add(lblRotate2);
        lblRotate2.setBounds(356, 30, 700, 550);

        lblRotate3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/qrrateus.png"))); // NOI18N
        lblRotate3.setOpaque(true);
        jPanel1.add(lblRotate3);
        lblRotate3.setBounds(720, 230, 690, 130);

        javax.swing.GroupLayout cardPanelLayout = new javax.swing.GroupLayout(cardPanel);
        cardPanel.setLayout(cardPanelLayout);
        cardPanelLayout.setHorizontalGroup(
            cardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(cardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(cardPanelLayout.createSequentialGroup()
                        .addComponent(totalCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(totalProductsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(totalValuePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(totalTaxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        cardPanelLayout.setVerticalGroup(
            cardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(cardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalProductsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalTaxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalValuePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

     
    private void raisePanel(JPanel jp){
        if(jp!=null){
            int currX = (int)jp.getLocation().getX();
            int currY = (int)jp.getLocation().getY();
            int currWidth = (int)jp.getSize().getWidth();
            int currHeight = (int)jp.getSize().getHeight();
            jp.setLocation(currX - nextX,currY - nextY);
            jp.setSize(currWidth + nextWidth,currHeight + nextWidth);
            Component allChilds[]  = jp.getComponents();
            for(Component child : allChilds){
                int curWidth = (int)jp.getSize().getWidth();
                int curHeight = (int)child.getSize().getHeight();
                child.setSize(curWidth, curHeight + nextHeight);
            }
        }
    }
    private void lowerPanel(JPanel jp){
        if(jp!=null){
            int currX = (int)jp.getLocation().getX();
            int currY = (int)jp.getLocation().getY();
            int currWidth = (int)jp.getSize().getWidth();
            int currHeight = (int)jp.getSize().getHeight();
            jp.setLocation(currX + nextX,currY + nextY);
            jp.setSize(currWidth - nextWidth,currHeight - nextHeight);
            Component allChilds[]  = jp.getComponents();
            for(Component child : allChilds){
                int curWidth = (int)jp.getSize().getWidth();
                int curHeight = (int)child.getSize().getHeight();
                child.setSize(curWidth, curHeight - nextHeight);
            }
        }
    }
    private void totalCustomerPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalCustomerPanelMouseEntered
        raisePanel(totalCustomerPanel);
    }//GEN-LAST:event_totalCustomerPanelMouseEntered

    private void totalCustomerPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalCustomerPanelMouseExited
        lowerPanel(totalCustomerPanel);
    }//GEN-LAST:event_totalCustomerPanelMouseExited

    private void totalProductsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalProductsPanelMouseEntered
        raisePanel(totalProductsPanel);
    }//GEN-LAST:event_totalProductsPanelMouseEntered

    private void totalProductsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalProductsPanelMouseExited
        lowerPanel(totalProductsPanel);
    }//GEN-LAST:event_totalProductsPanelMouseExited

    private void totalValuePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalValuePanelMouseEntered
        raisePanel(totalValuePanel);
    }//GEN-LAST:event_totalValuePanelMouseEntered

    private void totalValuePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalValuePanelMouseExited
        lowerPanel(totalValuePanel);
    }//GEN-LAST:event_totalValuePanelMouseExited

    private void totalTaxPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalTaxPanelMouseEntered
        raisePanel(totalTaxPanel);
    }//GEN-LAST:event_totalTaxPanelMouseEntered

    private void totalTaxPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalTaxPanelMouseExited
        lowerPanel(totalTaxPanel);
    }//GEN-LAST:event_totalTaxPanelMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblRotate1;
    private javax.swing.JLabel lblRotate2;
    private javax.swing.JLabel lblRotate3;
    private javax.swing.JLabel lblTotalCustomers;
    private javax.swing.JLabel lblTotalProducts;
    private javax.swing.JLabel lblTotalTax;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JPanel totalCustomerPanel;
    private javax.swing.JPanel totalProductsPanel;
    private javax.swing.JPanel totalTaxPanel;
    private javax.swing.JPanel totalValuePanel;
    // End of variables declaration//GEN-END:variables

    private static final int ICONSIZE = 60;
    private final int nextX = 10;
    private final int nextY = 10;
    private final int nextWidth = 20;
    private final int nextHeight = 20;
    //8A1DA2 Violet Light Shade
    //7F0799  Violet Dark Shade
    //A14AB4 Violet Light Shade For Bottom
    
    //1EFFBC Turquoise Light Shade
    //19D19A Turquoise Dark Shade
    //84FFDA Turquoise Light Shade for Bottom
    
    //E93148 Red Light Shade
    //E71D36 Red Dark Shade
    //ED5A6C Red Light Shade For Bottom
    
    //5466B4 Blue Light Shade
    //4357AD Blue Dark Shade
    //8794CA Blue Light Shade For Bottom
}
