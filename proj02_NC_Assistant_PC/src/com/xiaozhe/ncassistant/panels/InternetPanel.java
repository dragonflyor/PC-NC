package com.xiaozhe.ncassistant.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.xiaozhe.Filelist.FilesPanel;
import com.xiaozhe.bean.Resources;
import com.xiaozhe.myhttputils.MyHttpUtils;
import com.xiaozhe.ncassistant.NCAssistant;
import com.xiaozhe.ncassistant.uicomponont.NetPanelComponents;
import com.xiaozhe.utils.NCUtils;

public class InternetPanel extends Panel {

	//�������
	Panel contentPane;
	Button button_connect ;
	
	GridBagLayout bagLayout;
	GridBagConstraints c; 
	
	public InternetPanel() {
		super();

		
		//��ʼ������
		init();
		//�¼�����
		setEvent();
		//�ɼ�
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ������
	 */
	void init(){
		//1����
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		//2.1����
		Label headingLabel = new Label("��  ��",Label.CENTER);
		headingLabel.setFont(new Font("����", Font.BOLD, 36));
		headingLabel.setBackground(Color.GRAY);
		
		//2.1�������
		contentPane = new Panel();
		bagLayout = new GridBagLayout();
		c = new GridBagConstraints(); 
		
		//����������
		contentPane.setLayout(bagLayout);
		contentPane.setPreferredSize(new Dimension(400, 400));
		contentPane.setMaximumSize(new Dimension(400, 400));
		
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 3;
		c.weighty = 1;
		NCUtils.addComponentTo(contentPane, NetPanelComponents.textField, bagLayout, c);	
		//contentPane.add(NetPanelComponents.textField);
		
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		button_connect = NetPanelComponents.bt_connect;
		NCUtils.addComponentTo(contentPane, button_connect, bagLayout, c);
		
		
		
		
		//3��ӿؼ�
		this.add(headingLabel,BorderLayout.NORTH);
		this.add(contentPane,BorderLayout.CENTER);
	}
	
	/**
	 * ���ü���
	 */
	void setEvent(){
		button_connect.addActionListener(new MyBtListner());
	}
	
	
	class MyBtListner implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Button bt = (Button) e.getSource();
			if(bt == button_connect){
				//ArrayList<Resources> arrayList = MyHttpUtils.getFileResources("http://172.26.164.3:8080/day22_upload_download/ReturnFileListServlet");
				ArrayList<Resources> arrayList = MyHttpUtils.getFileResources(NetPanelComponents.textField.getText());
				
				for (Resources resources : arrayList) {
					System.out.println("----------------------");
					System.out.println("���ƣ�"+resources.getRealname());
					System.out.println("������"+resources.getDescription());
					System.out.println("����·����"+resources.getSavepath());
					System.out.println("Uuidname��"+resources.getUuidname());
				}
				
				if(NetPanelComponents.filesPanel !=null){
					contentPane.remove(NetPanelComponents.filesPanel);
				}
				NetPanelComponents.filesPanel= new FilesPanel(arrayList);
	
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.anchor = GridBagConstraints.NORTH;
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 10;
				c.weighty = 10;
				
				NCUtils.addComponentTo(contentPane, NetPanelComponents.filesPanel, bagLayout, c);
				//contentPane.add(NetPanelComponents.filesPanel);
				contentPane.invalidate();
				contentPane.setVisible(true);
				//ˢ��������
				NCAssistant.mainFrame.invalidate();
				NCAssistant.mainFrame.setVisible(true);
			}
		}
		
	}

	
}
