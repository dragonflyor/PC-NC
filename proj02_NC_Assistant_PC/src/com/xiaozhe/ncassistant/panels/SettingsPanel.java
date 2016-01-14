package com.xiaozhe.ncassistant.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;

public class SettingsPanel extends Panel {

	//�������
	Panel contentPane;
	
	public SettingsPanel() {
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
		Label headingLabel = new Label("��     ��",Label.CENTER);
		headingLabel.setFont(new Font("����", Font.BOLD, 36));
		headingLabel.setBackground(Color.GRAY);
		
		//2.1�������
		contentPane = new Panel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints(); 
		
		//3��ӿؼ�
		this.add(headingLabel,BorderLayout.NORTH);
	}
	
	/**
	 * ���ü���
	 */
	void setEvent(){
		
	}

	
}
