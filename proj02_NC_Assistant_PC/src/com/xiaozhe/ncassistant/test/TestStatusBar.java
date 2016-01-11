package com.xiaozhe.ncassistant.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.xiaozhe.ncassistant.StatusBar;
  
  
public class TestStatusBar extends Frame{  
  
    TestStatusBar(){  
        this.setSize(400, 300);  
          
        this.setLayout(new BorderLayout());  
          
  
        final StatusBar statu = new StatusBar(4, this);  
        statu.GetStatu(0).setText("num1");  
        statu.SetStatu(1, "num2");  
        statu.GetStatu(2).setText("num3");  
          
          
        Button but = new Button("button");  
        this.add(but, BorderLayout.NORTH);  
        but.addMouseListener(new MouseAdapter(){  
            @Override  
            public void mouseClicked(MouseEvent e) {  
                // TODO Auto-generated method stub  
                statu.AddStatu(2, "tttttttt");  
                super.mouseClicked(e);  
            }  
        });  
          
          
        final PopupMenu menu = new PopupMenu("menu");  
        MenuItem mi = new MenuItem("Test");  
        menu.add(mi);  
        statu.GetStatu(3).add(menu);  
        statu.GetStatu(3).setText("hah");  
        statu.AddPopupMenu(3, menu);  
        this.add(statu, BorderLayout.SOUTH);  
          
    /*  String[] str = new String[2]; 
        str[0] = "num1"; 
        str[1] = "num2"; 
        StatusBar statu = new StatusBar(str, this); 
        this.add(statu, BorderLayout.SOUTH);*/  
          
        //this.pack();  
          
          
        this.addWindowListener(new WindowAdapter(){  
            @Override  
            public void windowClosing(WindowEvent e) {  
                // TODO Auto-generated method stub  
                System.exit(0);  
                super.windowClosing(e);  
            }  
        });  
        this.setVisible(true);  
    }  
      
      
    public static void main(String[] arge){  
        new TestStatusBar();  
    }  
}  