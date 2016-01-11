package com.xiaozhe.ncassistant.panels;

import java.awt.Button;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class HomePanel extends Panel {

	File file;
	Frame f;
	

	
	public HomePanel( Frame f) {
		super();
		this.f=f;
		//��ʼ��
		init();
		//���ü���
		
		//�ɼ�
		this.setVisible(true);
		
		System.out.println("������");
	}
	
	
	void init(){
		//һ�����еĲ���
		this.setLayout(new GridLayout(1,2));
		this.add(new SendPanel());
	}

	class SendPanel extends Panel{
		Button bt_send;
		Button bt_openFile;
		Button bt_saveFile;
		
		TextArea textArea;
		
		private GridBagLayout layout;
		private GridBagConstraints c;
		
		public SendPanel() {
			super();
			//��ʼ������
			init();
			//���ü���
			setEvent();
		}
	
	     
		private void addComponent(Component component,
                GridBagLayout gridbag,
                GridBagConstraints c) {
			gridbag.setConstraints(component, c);
			add(component);
			
		}
		
		void init(){
			//����
			layout = new GridBagLayout();
			this.setLayout(layout);
			
			//�ļ�ѡ�� �뷢�Ͱ�ť
			Panel panel_bt = new Panel();
			bt_send = new Button("���͸���λ��");
			bt_openFile = new Button("���ļ�");
			bt_saveFile = new Button("����ı�������");
			
			//��Ӱ���
			panel_bt.add(bt_send);
			panel_bt.add(new Label("  "));
			panel_bt.add(bt_openFile);
			panel_bt.add(bt_saveFile);
			
			//�ı��� ,20��20�ַ����
			textArea = new TextArea();

			
			//���ղ������
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//���ղ���������
			//�������һ�����
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=0;
			c.weighty=1;
			this.addComponent(panel_bt,layout,c);
			//���������������
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=5;
			this.addComponent(textArea,layout,c);
			
			//f.pack();
		}
		
		/**
		 * ��ӿؼ�����
		 */
		void setEvent(){
			
			bt_openFile.addActionListener(new MYBtnListener());
			bt_saveFile.addActionListener(new MYBtnListener());
		}
		
		/**
		 * �԰������м���
		 * @author zhe
		 *
		 */
		class MYBtnListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				Button bt = (Button) e.getSource();
				
				if(bt == bt_send){
					//�������̴߳�����
				}else if(bt == bt_openFile){
					//���ļ��Ի���
					FileDialog d1 = new FileDialog(f, "ѡ����Ҫ���ļ�" , FileDialog.LOAD);
					d1.setVisible(true);
					file = new File(d1.getDirectory()+d1.getFile());
					System.out.println(file);
					if(file != null){
						//��ȡ�ļ���ʾ���ı���
						try {
							FileReader fr = new FileReader(file);
							BufferedReader br = new BufferedReader(fr);
							
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							
							
							String line = null;
							while((line = br.readLine())!=null){
								bos.write(line.getBytes());
								bos.write("\r\n".getBytes());
							}
							System.out.println("�ı�����"+bos.toString());
							//��ʾ���ı���
							textArea.setText(bos.toString());
							System.out.println(bos.toString());
							
							br.close();
							bos.close();
							
						} catch (Exception e1) {
							//���ļ�����
							//e1.printStackTrace();
							System.out.println("ȡ����");
						}						
					}

					
				}else if(bt == bt_saveFile){
					//�����ļ��Ի���
					FileDialog d1 = new FileDialog(f, "�����ļ�" , FileDialog.SAVE);
					d1.setVisible(true);
					file = new File(d1.getDirectory()+d1.getFile());
					if(file != null){
						//��ȡ������
						ByteArrayInputStream bis = new ByteArrayInputStream(textArea.getText().getBytes());
						System.out.println(textArea.getText());
						
						try {
							//�����ļ�
							File file = new File(d1.getDirectory()+d1.getFile());
							FileOutputStream fos = new FileOutputStream(file);
							
							//����
							byte [] buf = new byte[1024];
							int len = -1;
							
							while((len = bis.read(buf)) != -1){
								fos.write(buf, 0, len);
							}
						} catch (IOException e1) {
							
							System.out.println("���ļ�����");
						}
					}
					
				}
				
			}
			
		}

	}
	
	
	
}
