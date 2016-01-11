package com.xiaozhe.ncassistant.test;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.xiaozhe.ncassistant.panels.HomePanel;

public class HomePaneltest {
	
	public static void main (String [] args){
		Frame frame = new Frame("测试窗口");
		
		frame.setBounds(100, 100, 800, 600);
		
		frame.setLayout(new BorderLayout());
		
		frame.add(new HomePanel(frame));
		frame.setVisible(true);
		
		//监听窗口动作
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
	} 
}
