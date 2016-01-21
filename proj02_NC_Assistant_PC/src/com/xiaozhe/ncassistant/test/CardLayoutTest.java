package com.xiaozhe.ncassistant.test;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CardLayoutTest {
	public static void main(String[] args) {
	 new MyFrame1("���Դ���");
		
	}

}

class MyFrame1 extends Frame{

	Button bt_change;
	Panel  panel_pages; 
	
	Button bt_1;
	Button bt_2;
	Button bt_3;
	Button bt_4;
	
	
	
	CardLayout cardLayout = new CardLayout(0, 0);
	
	public MyFrame1(String title) throws HeadlessException {
		super(title);
		
		//��ʼ��
		init();
		//����
		setEvent();
		
		
		this.setVisible(true);
	}
	
	void init(){
		
		//����
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		
		this.setBounds(100, 100, 300, 300);
		this.setLayout(layout);
		
		bt_change = new Button("��һҳ");
		bt_1 = new Button("page1");
		bt_2 = new Button("page2");
		bt_3 = new Button("page3");
		bt_4 = new Button("page4");
		
		this.add(bt_change);
		this.add(bt_1);
		this.add(bt_2);
		this.add(bt_3);
		this.add(bt_4);
		
		
		//card���
		panel_pages = new Panel();
		panel_pages.setLayout(cardLayout);
		
		
		//���ҳ��
		panel_pages.add( new Label("��1������"),"1");
		panel_pages.add( new Label("��2������"),"2");
		panel_pages.add( new Label("��3������"),"3");
		panel_pages.add( new Label("��4������"),"4");

		
		this.add(panel_pages);
		
		
	}
	
	void setEvent(){
		//�رմ���
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				
				System.exit(0);
			}
			
		});
		
		//��ť�¼�
		bt_change.addActionListener(new MyBtnListener());
		bt_1.addActionListener(new MyBtnListener());
		bt_2.addActionListener(new MyBtnListener());
		bt_3.addActionListener(new MyBtnListener());
		bt_4.addActionListener(new MyBtnListener());
	}
	
	class MyBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Button bt = (Button) e.getSource();
			
			//���ݰ����л�ҳ��
			if(bt == bt_change){
				//cardLayout.show(panel_pages, "4");
				cardLayout.next(panel_pages);
			}else if(bt == bt_1){
				cardLayout.show(panel_pages, "1");
			}else if(bt == bt_2){
				cardLayout.show(panel_pages, "2");
			}else if(bt == bt_3){
				cardLayout.show(panel_pages, "3");
			}else if(bt == bt_4){
				cardLayout.show(panel_pages, "4");
			}
		}
		
	}
	
}
