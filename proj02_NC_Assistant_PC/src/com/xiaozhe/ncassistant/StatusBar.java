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
     * ��AWTʵ�ֵ�״̬��   �汾��1.0  ���ߣ��κ�   E-mail:lingdushanke@163.com     ���ڣ�2010.1.28 
     *  
     */  
    private static final long serialVersionUID = 7224543356553391436L;  
    private LinkedList<Label> list = new LinkedList<Label>();  
    Frame frame = null;  
  
    /* 
     * ��ָ��ָʾ��������ʼ��״̬�� 
     */  
    public StatusBar(int ItemNum, Frame frame) {  
        this.frame = frame;  
        for (int ii = 0; ii < ItemNum; ii++) {  
            list.add(new Label());  
        }  
        InitStatusBar();  
    }  
  
    /* 
     * ��ָ�����ַ��������ʼ��״̬�� 
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
     * �ı�һ��ָʾ�����ı� 
     */  
    public void SetStatu(int index, String text) {  
        list.get(index).setText(text);  
    }  
  
    /* 
     * ���һ��ָʾ�������� 
     */  
    public String GetStatuText(int index) {  
        return GetStatu(index).getText();  
    }  
  
    /* 
     * ��ö�һ��ָʾ�������� 
     */  
    public Label GetStatu(int index) {  
        return list.get(index);  
    }  
      
      
    /* 
     * ���һ���µ�ָʾ�� 
     */  
    public void AddStatu(int index, String text){  
        list.add(index, new Label(text));  
        InitStatusBar();  
    }  
      
      
    /* 
     * �Ƴ�һ��ָʾ�� 
     */  
    public void RemoveStatu(int index){  
        list.remove(index);  
        InitStatusBar();  
    }  
      
  
    /* 
     * ��һ��ָʾ�����������Ϣ��Ӧ 
     */  
    public void AddMouseListener(int index, MouseListener event) {  
        GetStatu(index).addMouseListener(event);  
    }  
      
      
    /* 
     * ��һ��ָʾ����������Ҽ��˵� 
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
     * ��ʼ��״̬�� 
     */  
    public void InitStatusBar() {  
          
        this.setBackground(Color.lightGray);  
        this.setLayout(new FlowLayout(FlowLayout.LEFT));  
        //����������ɫ
        //this.setFont(new Font(null, Font.ITALIC, 10));
        //this.setSize(this.getWidth(), 2);
  
        this.removeAll();  
        for (int ii = 0; ii < list.size(); ii++) {  
            this.add(GetStatu(ii));  
        }  
        frame.setVisible(true);  
    }  
  
    /* 
     * ���ڻ�ͼ(non-Javadoc) 
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