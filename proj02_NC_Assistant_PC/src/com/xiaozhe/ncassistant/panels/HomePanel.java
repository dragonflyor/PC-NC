package com.xiaozhe.ncassistant.panels;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
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

import com.xiaozhe.ncassistant.MainFrame;
import com.xiaozhe.ncassistant.uicomponont.HomePanelUICompnonts;

public class HomePanel extends Panel {

	File file_open;
	Frame f;
	public HomePanel( Frame f) {
		super();
		//ҳ�����ݳ�ʼ��
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
		this.add(new ReceivePanel());
	}



	
	/**
	 * ������߷������
	 * @author zhe
	 *
	 */
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
			//
			this.setBackground(Color.GRAY);
			
			//�ļ�ѡ�� �뷢�Ͱ�ť
			Panel panel_bt = new Panel();
			panel_bt.setBackground(Color.darkGray);
			bt_send = new Button("���͸���λ��");
			bt_openFile = new Button("���ļ�");
			bt_saveFile = new Button("����ı�������");
			
			//��Ӱ���
			GridBagLayout layout1 = new GridBagLayout();
			GridBagConstraints c1 = new GridBagConstraints();
			
			panel_bt.setLayout(layout1);
			c1.anchor = c1.WEST;
//			panel_bt.add(bt_send);
//			panel_bt.add(new Label("  "));
//			panel_bt.add(bt_openFile);
//			panel_bt.add(bt_saveFile);
			addComponent(bt_send, layout1, c1);
			addComponent(new Label("  "), layout1, c1);
			addComponent(bt_openFile, layout1, c1);
			addComponent(bt_saveFile, layout1, c1);
			
			
			//�ı��� ,20��20�ַ����
			textArea = new TextArea();

			
			//���ղ������
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//���ղ���������
			//�������һ�����
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=1;
			this.addComponent(panel_bt,layout,c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = c.SOUTH;
			c.weightx=1;
			c.weighty=1;
			Label label = new Label("�ļ�Ԥ��/�༭��",Label.CENTER);
			label.setBackground(Color.LIGHT_GRAY);
			this.addComponent(label,layout,c);
			
			//���������������
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=20;
			this.addComponent(textArea,layout,c);
			
			//f.pack();
		}
		
		/**
		 * ��ӿؼ�����
		 */
		void setEvent(){
			
			bt_openFile.addActionListener(new MYBtnListener());
			bt_saveFile.addActionListener(new MYBtnListener());
			bt_send.addActionListener(new MYBtnListener());
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
					MainFrame mf = (MainFrame) HomePanel.this.f;
					System.out.println("�����ı�����");
					mf.simpleWrite.writeFileToSTM32(file_open, 1000);
				}else if(bt == bt_openFile){
					//���ļ��Ի���
					FileDialog d1 = new FileDialog(f, "ѡ����Ҫ���ļ�" , FileDialog.LOAD);
					d1.setVisible(true);
					file_open = new File(d1.getDirectory()+d1.getFile());
					System.out.println(file_open);
					if(file_open != null){
						//��ȡ�ļ���ʾ���ı���
						try {
							FileReader fr = new FileReader(file_open);
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
					File file = new File(d1.getDirectory()+d1.getFile());
					if(file != null){
						//��ȡ������
						ByteArrayInputStream bis = new ByteArrayInputStream(textArea.getText().getBytes());
						System.out.println(textArea.getText());
						
						try {
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
	
	/**
	 * �����ұ߷������
	 * @author zhe
	 *
	 */
	class ReceivePanel extends Panel{
		Button bt_send;
		Button bt_openFile;
		Button bt_saveFile;
		
		private GridBagLayout layout;
		private GridBagConstraints c;
		
		public ReceivePanel() {
			super();
			//��ʼ������
			init();
			//���ü���
			setEvent();
		}
	
	    /**
	     * �����ҳ����ӿؼ� 
		 * @param component Ҫ��ӵĿؼ�
		 * @param gridbag ����
		 * @param c Լ��
	     */
		private void addComponent(Component component,
                GridBagLayout gridbag,
                GridBagConstraints c) {
			addComponentTo(this, component, gridbag, c);
		}
	
		/**
		 * ĳ��������ӿؼ�
		 * @param container ����
		 * @param component Ҫ��ӵĿؼ�
		 * @param gridbag ����
		 * @param c Լ��
		 */
		private void addComponentTo( Container container,Component component,
                GridBagLayout gridbag,
                GridBagConstraints c){
			
			gridbag.setConstraints(component, c);
			container.add(component);
			
		}
		
		void init(){
			
			//����
			layout = new GridBagLayout();
			this.setLayout(layout);
			this.setBackground(Color.WHITE);
			
			//���ղ������
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//���ղ���������
			
			//��һ��
			c.weightx=1;
			c.weighty=0;
			this.addComponent(new Label("��������"),layout,c);
			//�������һ�����
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=0;
			this.addComponent(new Label("���ղ���"),layout,c);
		
			/**
			 * ����UI�Ű棺
			 * X:0
			 * Y:0
			 * Z:0
			 */
			//�ڶ���,��һ��,ʵʱ����������
			//��һ������
			Panel panel_cord = new Panel();
			panel_cord.setBackground(Color.BLACK);
			panel_cord.setForeground(Color.WHITE);
			panel_cord.setFont(new Font("����", Font.BOLD, 20));
			
			GridBagLayout layout1 = new GridBagLayout();
			GridBagConstraints c1 = new GridBagConstraints();
			panel_cord.setLayout(layout1);
			
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("X:"), layout1, c1);
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_X, layout1, c1);
			
			c1.gridwidth = GridBagConstraints.RELATIVE;
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("Y:"), layout1, c1);	
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_Y, layout1, c1);
			
			c1.gridwidth = GridBagConstraints.RELATIVE;
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("Z:"), layout1, c1);
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_Z, layout1, c1);
			
			//��ӵ����
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.weightx=1;
			c.weighty=1;
			c.anchor = GridBagConstraints.WEST;
			//ˮƽ������
			c.fill = GridBagConstraints.BOTH;
			this.addComponent(panel_cord, layout, c);
			
			//�ڶ��У��ڶ��� ����
			//��һ������
			Panel panel_cratf = new Panel();
			panel_cratf.setBackground(Color.lightGray);
			panel_cratf.setForeground(Color.DARK_GRAY);
			panel_cratf.setFont(new Font("����", Font.BOLD, 15));
			
			GridBagLayout layout2 = new GridBagLayout();
			GridBagConstraints c2 = new GridBagConstraints();
			panel_cratf.setLayout(layout2);
			
			
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("T:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_T, layout2, c2);
			
			c2.gridwidth = GridBagConstraints.RELATIVE;
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("F:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_F, layout2, c2);
			
			c2.gridwidth = GridBagConstraints.RELATIVE;
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("S:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_S, layout2, c2);
			

			//��ӵ����
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.BOTH;
			c.weightx=1;
			c.weighty=1;
			c.anchor = GridBagConstraints.WEST;
			//ˮƽ������
			this.addComponent(panel_cratf, layout, c);
			
			//������  ��ǰִ�еĴ���
			//���������������
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.anchor = GridBagConstraints.WEST;
			c.weightx=1;
			c.weighty=1;
			this.addComponent(HomePanelUICompnonts.label_currentGcode,layout,c);

			//������ �ı���
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			
			Label label = new Label("���ڽ��յ�����",Label.CENTER);
			label.setBackground(Color.GRAY);
			this.addComponent(label,layout,c);
			
			c.weightx=1;
			c.weighty=20;
			this.addComponent(HomePanelUICompnonts.receiveTextArea,layout,c);
			

			
			//f.pack();
		}
		
		/**
		 * ��ӿؼ�����
		 */
		void setEvent(){
			
			
		}
		
		/**
		 * �԰������м���
		 * @author zhe
		 *
		 */
		class MYBtnListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				Button bt = (Button) e.getSource();
				
				
				
			}
			
		}

	}
	
}




///**
// * �ؼ�������
// * @author zhe
// *
// */
//class HomePanelUIData{
//	static int X=0;
//	static int Y=0;
//	static int Z=0;
//	
//	static int T=0;
//	static int F=0;
//	static int S=0;
//}

