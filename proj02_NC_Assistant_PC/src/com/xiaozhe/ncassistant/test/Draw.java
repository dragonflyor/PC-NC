package com.xiaozhe.ncassistant.test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
      
@SuppressWarnings("serial")  
public class Draw extends JFrame{  
    public Color color=Color.BLACK;//���ó�ʼ��ɫ  
    private Color colorselector;  
    private JButton colorselectorbu[]=new JButton[6];  
    //��������ʱ��Ҫnewһ�����󣬷�������Ĭ��ֵ����NULL  
    private JPanel colorjp=new JPanel();  
    public static void main(String args[]){  
        Draw dr=new Draw();  
        dr.showframe();  
    }  
    public void showframe(){  
        JPanel north=new JPanel();        //�Ϸ����򣬴����ɫ��ť�Լ���ɫѡ����  
        JPanel west=new JPanel();         //������򣬴��ͼ�ΰ�ť   
        JPanel center=new JPanel();       //�м����򣬴�Ż���  
        JPanel south=new JPanel();        //��������������ʾ��ǰ�������ǰλ���Լ�����ͼ�εĴ�С  
        //-----------------�м�����---------------  
        center.setLayout(new FlowLayout(FlowLayout.LEFT));//���м�����Ĳ�������Ϊ�����  
        JPanel drawjp=new JPanel();  
        drawjp.setBackground(Color.white);  
        drawjp.setPreferredSize(new Dimension(600,500));  
        center.add(drawjp);  
        //-----------------�Ϸ�����----------------  
        JButton colorchooserbu=new JButton("������ɫ");  
        colorchooserbu.addActionListener(colorlistener);  
        colorjp.setPreferredSize(new Dimension(120,60));  
        colorjp.setLayout(new GridLayout(3,6,0,0));  
        //����һ����ɫ��  
        Color color[]=new Color[]{Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GRAY,Color.GREEN  
                ,Color.LIGHT_GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};        
        Dimension d=new Dimension(20,20);  
        for(int i=0;i<color.length;i++){  
            JButton colorbu=new JButton();  
            colorbu.setBackground(color[i]);  
            colorbu.setPreferredSize(d);  
            colorjp.add(colorbu);  
            colorbu.addActionListener(listener);  
        }  
        //��ťѡ�������Զ�����ɫ��  
        for(int j=0;j<colorselectorbu.length;j++){  
            colorselectorbu[j]=new JButton();  
            colorselectorbu[j].setPreferredSize(d);  
            colorjp.add(colorselectorbu[j]);  
            colorselectorbu[j].addActionListener(listener);  
        }  
        north.add(colorjp);  
        north.add(colorchooserbu);  
        //-----------------�������----------------  
        String img[]=new String[]{"Line", "oval", "rect", "roundrect", "eraser", "fill",  
                "pencil", "select"};   
        String img1[]=new String[]{"Line1","oval1","rect1","roundrect1","eraser1","fill1",  
                "pencil1","select1"};  
        ButtonGroup bg=new ButtonGroup();  
        for(int i=0;i<img.length;i++){  
            //����Icon��  
            ImageIcon shapeicon=new ImageIcon("back/"+img[i]+".jpg");  
            ImageIcon shapeicon1=new ImageIcon("back/"+img1[i]+".jpg");  
            JRadioButton shapebu=new JRadioButton(shapeicon);  
            bg.add(shapebu);  
            shapebu.setPressedIcon(shapeicon1);  
            //����ѡ��ʱ��ͼ��  
            shapebu.setSelectedIcon(shapeicon1);  
            shapebu.setActionCommand(img[i]);  
            west.add(shapebu);  
        }  
        //---------------�·�����-------------  
        JLabel la1=new JLabel();  
        JLabel la2=new JLabel();  
        JLabel la3=new JLabel("600x500����");  
        JLabel la4=new JLabel();  
        south.add(la4);  
        south.add(la1);  
        south.add(la2);  
        south.add(la3);  
        south.setLayout(new GridLayout(1,4,20,20));  
        north.setPreferredSize(new Dimension(200,80));  
        west.setPreferredSize(new Dimension(80,200));  
        south.setPreferredSize(new Dimension(200,20));  
        north.setBackground(Color.GRAY);  
        west.setBackground(Color.DARK_GRAY);  
        center.setBackground(Color.LIGHT_GRAY);  
        south.setBackground(Color.WHITE);  
        this.setLayout(new BorderLayout());  
        this.add(north,BorderLayout.NORTH);  
        this.add(west,BorderLayout.WEST);  
        this.add(south,BorderLayout.SOUTH);  
        this.add(center,BorderLayout.CENTER);  
        this.setTitle("��ͼ�帴ϰ");  
        this.setSize(900,700);  
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(3);  
        this.setVisible(true);  
        //�ڻ����ϻ�ȡ����Ȩ��  
        Graphics g=drawjp.getGraphics();  
        DrawListener listener=new DrawListener(g,bg,this,la1,la2);  
        drawjp.addMouseListener(listener);  
        drawjp.addMouseMotionListener(listener);  
    }  
    //��Ӱ�ť��������ȡ��ť�ı�����ɫ  
    ActionListener listener=new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            //��ȡ�¼�Դ�������¼��Ķ���  
            Object obj=e.getSource();  
            if(obj instanceof JButton){  
                color=((JButton) obj).getBackground();  
            }  
        }  
    };  
    ActionListener colorlistener=new ActionListener(){  
        //��ť�����ۼ���  
        int i = 0;  
        public void actionPerformed(ActionEvent e){  
                colorselector=JColorChooser.showDialog(null, "��ɫѡ����", Color.BLACK);  
                colorselectorbu[i].setBackground(colorselector);  
                i++;  
                if(i>=6){  
                    //����������ť,����0  
                    i=0;  
                }  
        }  
    };  
}


//----------------------------------------------------------


      
 class DrawListener extends MouseAdapter{  
    private int x1,x2,y1,y2;  
    private Graphics g;                   //���������󴫹���  
    private ButtonGroup bg;               //����ť�鴫����  
    private Draw dr;  
    private JLabel la1;  
    private JLabel la2;  
    public DrawListener(Graphics g,ButtonGroup bg,Draw dr,JLabel la1,JLabel la2){  
        this.g=g;  
        this.bg=bg;  
        this.dr=dr;  
        this.la1=la1;  
        this.la2=la2;  
    }  
    //��갴��  
    public void mousePressed(MouseEvent e){  
        x1=e.getX();  
        y1=e.getY();  
        g.setColor(dr.color);  
    }  
          
    //����ͷ�  
    public void mouseReleased(MouseEvent e){  
        x2=e.getX();  
        y2=e.getY();  
        ButtonModel bm=bg.getSelection();  
        String com=bm.getActionCommand();  
            if(com.equals("Line")){  
                g.drawLine(x1, y1, x2, y2);  
                //�ı�la2�е�����  
                la2.setText(Math.abs(x1-x2)+"x"+Math.abs(y1-y2));  
            }  
            else if(com.equals("oval")){  
                g.drawOval(Math.min(x1, x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2));  
                la2.setText(Math.abs(x1-x2)+"x"+Math.abs(y1-y2));  
            }  
            else if(com.equals("rect")){  
                g.drawRect(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x1-x2),Math.abs(y1-y2));  
                la2.setText(Math.abs(x1-x2)+"x"+Math.abs(y1-y2));  
            }  
            else if(com.equals("roundrect")){  
                g.drawRoundRect(Math.min(x1,x2), Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2), Math.abs(x1-x2)/4,Math.abs(y1-y2)/4);  
                la2.setText(Math.abs(x1-x2)+"x"+Math.abs(y1-y2));  
            }  
    }  
          
    //  
    public void mouseMoved(MouseEvent e){  
        int X=e.getX();  
        int Y=e.getY();  
        la1.setText(X+","+Y);  
    }  
          
    public void mouseExited(MouseEvent e) {  
        la1.setText("");  
    }  
    //����϶�  
    public void mouseDragged(MouseEvent e){  
        int X=e.getX();  
        int Y=e.getY();  
        la1.setText(X+","+Y);  
        x2=e.getX();  
        y2=e.getY();  
        String com=bg.getSelection().getActionCommand();  
        if(com.equals("pencil")){  
            g.drawLine(x1, y1, x2, y2);  
            x1=x2;  
            y1=y2;  
        }  
        else if(com.equals("eraser")){  
            g.setColor(Color.WHITE);  
            for(int i=-8;i<8;i++){  
                g.drawLine(x1+i,y1+i,x2+i,y2+i);  
            }  
            x1=x2;  
            y1=y2;  
        }  
        else if(com.equals("select")){  
            g.drawLine(x1,y1,x2,y2);  
        }  
        else if(com.equals("fill")){  
            Random ran=new Random();  
            int a=ran.nextInt(16)-8;  
            g.drawLine(x1+a, y1+a, x2+a, y2+a);  
            x1=x2;  
            y1=y2;  
        }  
    }  
}