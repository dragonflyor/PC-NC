package com.xiaozhe.ncassistant;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class StatusBar extends Panel {  
    
    /** 
     * 用AWT实现的状态栏   版本：1.0  作者：鑫海   E-mail:lingdushanke@163.com     日期：2010.1.28 
     *  
     */  
    private static final long serialVersionUID = 7224543356553391436L;  
    private LinkedList<Label> list = new LinkedList<Label>();  
    Frame frame = null;  
  
    /* 
     * 用指定指示器个数初始化状态栏 
     */  
    public StatusBar(int ItemNum, Frame frame) {  
        this.frame = frame;  
        for (int ii = 0; ii < ItemNum; ii++) {  
            list.add(new Label());  
        }  
        InitStatusBar();  
    }  
  
    /* 
     * 用指定的字符串数组初始化状态栏 
     */  
    StatusBar(String[] str, Frame frame) {  
        this.frame = frame;  
        for (int ii = 0; ii < str.length; ii++) { 
        	Label label = new Label(str[ii]);
//        	label.setSize(label.getWidth()/2, label.getHeight());
       	label.setLocation(10,0);
            list.add(label);  
        }  
        InitStatusBar();  
    }  
  
    /* 
     * 改变一个指示器的文本 
     */  
    public void SetStatu(int index, String text) {  
        list.get(index).setText(text);  
    }  
  
    /* 
     * 获得一个指示器的内容 
     */  
    public String GetStatuText(int index) {  
        return GetStatu(index).getText();  
    }  
  
    /* 
     * 获得对一个指示器的引用 
     */  
    public Label GetStatu(int index) {  
        return list.get(index);  
    }  
      
      
    /* 
     * 添加一个新的指示器 
     */  
    public void AddStatu(int index, String text){  
        list.add(index, new Label(text));  
        InitStatusBar();  
    }  
      
      
    /* 
     * 移除一个指示器 
     */  
    public void RemoveStatu(int index){  
        list.remove(index);  
        InitStatusBar();  
    }  
      
  
    /* 
     * 对一个指示器增加鼠标消息响应 
     */  
    public void AddMouseListener(int index, MouseListener event) {  
        GetStatu(index).addMouseListener(event);  
    }  
      
      
    /* 
     * 对一个指示器增加鼠标右键菜单 
     */  
    public void AddPopupMenu(int index, final PopupMenu menu){  
        GetStatu(index).addMouseListener(new MouseAdapter(){  
            @Override  
            public void mouseClicked(MouseEvent e) {  
                // TODO Auto-generated method stub  
                if (e.getButton() == MouseEvent.BUTTON3){  
                    menu.show((Component) menu.getParent(), e.getX(), e.getY());  
                }  
                super.mouseClicked(e);  
            }  
        });  
    }  
  
    /* 
     * 初始化状态栏 
     */  
    public void InitStatusBar() {  
          
        this.setBackground(Color.lightGray);  
        this.setLayout(new FlowLayout(FlowLayout.LEFT));  
        //设置文字颜色
        //this.setFont(new Font(null, Font.ITALIC, 10));
        //this.setSize(this.getWidth(), 2);
  
        this.removeAll();  
        for (int ii = 0; ii < list.size(); ii++) {  
            this.add(GetStatu(ii));  
        }  
        frame.setVisible(true);  
    }  
  
    /* 
     * 窗口绘图(non-Javadoc) 
     *  
     * @see java.awt.Container#paint(java.awt.Graphics) 
     */  
    @Override  
    public void paint(Graphics g) {  
                g.setColor(Color.gray);  
        g.drawLine(3, 0, this.getWidth() - 3, 0);  
        for (int ii = 1; ii < list.size(); ii++) {  
            Label label = GetStatu(ii);  
            g.drawLine(label.getX() - 2, label.getY() + 2, label.getX() - 2,  
                    label.getY() + label.getHeight() - 2);  
        }  
//        g.drawLine(3, 0, this.getWidth() - 3, 0);  
//        for (int ii = 1; ii < list.size(); ii++) {  
//            Label label = GetStatu(ii);  
//            g.drawLine(label.getX() - 2, label.getY(), label.getX() - 2,  
//                    label.getY() + label.getHeight());  
//        }  
    }  
}  