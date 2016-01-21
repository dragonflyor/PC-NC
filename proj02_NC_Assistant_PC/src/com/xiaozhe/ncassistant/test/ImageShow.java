package com.xiaozhe.ncassistant.test;
//import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class ImageShow{
    public static void main(String args[]){
        JFrame f = new JFrame("ImageShow");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 200);
        ImageIcon image= new ImageIcon("snap077.jpg") ;
        f.add(new JLabel(image));
        f.setVisible(true);
    }
}

