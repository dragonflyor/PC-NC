package com.xiaozhe.ncassistant.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class FontColorTest extends JFrame {

public FontColorTest() {
this.setLocation(100, 100);
this.setSize(1000, 1000);
this.setVisible(true);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}

public void paint(Graphics g) {
// public void actionPerformed(ActionEvent e)
// {   Graphics  g=getGraphics()； //获取当前组件的绘图环境
//    g.drawString("Java Text",x,y)；    //强制在当前组件内绘制输出
// }

Color mycolor = new Color(0, 0, 255);
Font myFont = new Font("Dialog", Font.BOLD, 20);
g.setFont(myFont);
g.drawString("这是Java中的Dialog字体的文字串", 100, 100);
g.setColor(mycolor);
g.drawString("颜色", 160, 160);

}

public static void main(String args[]) {
	new FontColorTest();
}

}
