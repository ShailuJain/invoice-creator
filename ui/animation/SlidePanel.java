/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.animation;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author jains
 */
public class SlidePanel {

    public SlidePanel(JComponent jc,int direction) {
        this.jc = jc;
        this.direction = direction;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public void startSliding(int delay,Point startLocation, Point stopLocation, int increment){
        setComponentLocation(startLocation);
        t = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                int x = (int)jc.getLocation().getX();
                int y = (int)jc.getLocation().getY();
                System.out.println(x + " : " + y + jc.getName());
                if(direction == SLIDE_RIGHT){
                    if(x<stopLocation.getX()){
                        jc.setLocation(new Point((x+=increment), y));
                        jc.revalidate();
                    }
                    else{
                        t.stop();
                        System.out.println(jc);
                    }
                }
                else if(direction == SLIDE_LEFT){
                    if(x>stopLocation.getX()){
                        jc.setLocation((x-=increment), y);
                    }
                    else{
                        t.stop();
                    }
                }   
            }
        });
        t.start();
    }
    private void setComponentLocation(Point startLocation){
        jc.setLocation((int)startLocation.getX(), (int)startLocation.getY());
        System.out.println(jc.getLocation());
        System.out.println(jc.getLocation());
    }
    private JComponent jc = null;
    private int direction = 0;
    public static final int SLIDE_RIGHT = 1;
    public static final int SLIDE_LEFT = 2;
    public Timer t = null;
}
