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
    public Color color=Color.BLACK;//设置初始颜色  
    private Color colorselector;  
    private JButton colorselectorbu[]=new JButton[6];  
    //创建对象时需要new一个对象，否则他的默认值就是NULL  
    private JPanel colorjp=new JPanel();  
    public static void main(String args[]){  
        Draw dr=new Draw();  
        dr.showframe();  
    }  
    public void showframe(){  
        JPanel north=new JPanel();        //上方区域，存放颜色按钮以及颜色选择器  
        JPanel west=new JPanel();         //左边区域，存放图形按钮   
        JPanel center=new JPanel();       //中间区域，存放画板  
        JPanel south=new JPanel();        //下面区域，用来显示当前鼠标所在前位置以及所画图形的大小  
        //-----------------中间区域---------------  
        center.setLayout(new FlowLayout(FlowLayout.LEFT));//将中间区域的布局设置为左对齐  
        JPanel drawjp=new JPanel();  
        drawjp.setBackground(Color.white);  
        drawjp.setPreferredSize(new Dimension(600,500));  
        center.add(drawjp);  
        //-----------------上方区域----------------  
        JButton colorchooserbu=new JButton("更多颜色");  
        colorchooserbu.addActionListener(colorlistener);  
        colorjp.setPreferredSize(new Dimension(120,60));  
        colorjp.setLayout(new GridLayout(3,6,0,0));  
        //创建一个颜色组  
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
        //按钮选择器的自定义颜色组  
        for(int j=0;j<colorselectorbu.length;j++){  
            colorselectorbu[j]=new JButton();  
            colorselectorbu[j].setPreferredSize(d);  
            colorjp.add(colorselectorbu[j]);  
            colorselectorbu[j].addActionListener(listener);  
        }  
        north.add(colorjp);  
        north.add(colorchooserbu);  
        //-----------------左边区域----------------  
        String img[]=new String[]{"Line", "oval", "rect", "roundrect", "eraser", "fill",  
                "pencil", "select"};   
        String img1[]=new String[]{"Line1","oval1","rect1","roundrect1","eraser1","fill1",  
                "pencil1","select1"};  
        ButtonGroup bg=new ButtonGroup();  
        for(int i=0;i<img.length;i++){  
            //创建Icon组  
            ImageIcon shapeicon=new ImageIcon("back/"+img[i]+".jpg");  
            ImageIcon shapeicon1=new ImageIcon("back/"+img1[i]+".jpg");  
            JRadioButton shapebu=new JRadioButton(shapeicon);  
            bg.add(shapebu);  
            shapebu.setPressedIcon(shapeicon1);  
            //设置选中时的图案  
            shapebu.setSelectedIcon(shapeicon1);  
            shapebu.setActionCommand(img[i]);  
            west.add(shapebu);  
        }  
        //---------------下方区域-------------  
        JLabel la1=new JLabel();  
        JLabel la2=new JLabel();  
        JLabel la3=new JLabel("600x500像素");  
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
        this.setTitle("画图板复习");  
        this.setSize(900,700);  
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(3);  
        this.setVisible(true);  
        //在画板上获取画布权限  
        Graphics g=drawjp.getGraphics();  
        DrawListener listener=new DrawListener(g,bg,this,la1,la2);  
        drawjp.addMouseListener(listener);  
        drawjp.addMouseMotionListener(listener);  
    }  
    //添加按钮监听，获取按钮的背景颜色  
    ActionListener listener=new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            //获取事件源，发生事件的对象  
            Object obj=e.getSource();  
            if(obj instanceof JButton){  
                color=((JButton) obj).getBackground();  
            }  
        }  
    };  
    ActionListener colorlistener=new ActionListener(){  
        //按钮数组累加器  
        int i = 0;  
        public void actionPerformed(ActionEvent e){  
                colorselector=JColorChooser.showDialog(null, "颜色选择器", Color.BLACK);  
                colorselectorbu[i].setBackground(colorselector);  
                i++;  
                if(i>=6){  
                    //超出六个按钮,返回0  
                    i=0;  
                }  
        }  
    };  
}


//----------------------------------------------------------


      
 class DrawListener extends MouseAdapter{  
    private int x1,x2,y1,y2;  
    private Graphics g;                   //将画布对象传过来  
    private ButtonGroup bg;               //将按钮组传过来  
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
    //鼠标按下  
    public void mousePressed(MouseEvent e){  
        x1=e.getX();  
        y1=e.getY();  
        g.setColor(dr.color);  
    }  
          
    //鼠标释放  
    public void mouseReleased(MouseEvent e){  
        x2=e.getX();  
        y2=e.getY();  
        ButtonModel bm=bg.getSelection();  
        String com=bm.getActionCommand();  
            if(com.equals("Line")){  
                g.drawLine(x1, y1, x2, y2);  
                //改变la2中的数字  
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
    //鼠标拖动  
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