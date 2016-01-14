package com.xiaozhe.ncassistant.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.comm.SerialPort;

import com.xiaozhe.ncassistant.MainFrame;
import com.xiaozhe.ncassistant.uicomponont.MainFramUiCompononts;
import com.xiaozhe.ncassistant.uicomponont.SettingsPanelUICompnonts;
import com.xiaozhe.utils.NCUtils;

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
		
		//2.2�������
		Panel contentParent = new Panel(new FlowLayout());
		
		contentParent.setFont(new Font("����", Font.BOLD, 15));
		contentPane = new Panel();
		contentParent.add(contentPane);
		GridBagLayout bagLayout = new GridBagLayout();
		contentPane.setLayout(bagLayout);
		GridBagConstraints c = new GridBagConstraints(); 
		//2.2.1 ��������
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		//c.weighty = 1;

		NCUtils.addComponentTo(contentPane, new Label("���ں�:",Label.LEFT), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;

		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_comStr, bagLayout, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		NCUtils.addComponentTo(contentPane, new Label("������:"), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;

		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_bautRateStr, bagLayout, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		NCUtils.addComponentTo(contentPane, new Label("����λ��:"), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_databitsStr, bagLayout, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.bt_reopen, bagLayout, c);
		
		
		
		//3��ӿؼ�
		this.add(headingLabel,BorderLayout.NORTH);

		this.add(contentParent,BorderLayout.CENTER);
		
		//��ȡϵͳ��Com����ӵ�comѡ���
		ArrayList<String> comlist_t = MainFramUiCompononts.manager.getAvailablePorts();
		for (String string : comlist_t) {
			SettingsPanelUICompnonts.cs_comStr.add(string);
		}
		
	}
	
	/**
	 * ���ü���
	 */
	void setEvent(){
		SettingsPanelUICompnonts.cs_comStr.addItemListener(new MyItemListener());
		SettingsPanelUICompnonts.cs_bautRateStr.addItemListener(new MyItemListener());
		SettingsPanelUICompnonts.cs_databitsStr.addItemListener(new MyItemListener());
		
		//�������ӵİ���
		SettingsPanelUICompnonts.bt_reopen.addActionListener(new MyBtnActionListener());
	}
	
	
	/**
	 * �����������ѡ��
	 * @author zhe
	 *
	 */
	class MyItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			Choice cc = (Choice) e.getSource();
			//��ȡѡ����
			String select = cc.getSelectedItem();
			
	//		System.out.print("�����˵���"+cc.getName());
			//��������
			if(cc == SettingsPanelUICompnonts.cs_comStr){
				System.out.println("ѡ�񴮿�:"+select);
				MainFrame.manager.setComStr(select);
			}
			else if(cc == SettingsPanelUICompnonts.cs_bautRateStr){
				System.out.println("ѡ������:"+select);
				//�л�������
				if("9600".equals(select)){
					MainFrame.manager.setBautrate(9600);
				}else if("115200".equals(select)){
					MainFrame.manager.setBautrate(115200);
				}
			}
			else if(cc == SettingsPanelUICompnonts.cs_databitsStr){
				System.out.println("ѡ������λ��"+select);
				//�л�������
				if("8".equals(select)){
					MainFrame.manager.setBautrate(SerialPort.DATABITS_8);
				}else if("7".equals(select)){
					MainFrame.manager.setBautrate(SerialPort.DATABITS_7);
				}
			}
			
		}
		
	}
	
	class MyBtnActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			Button bt = (Button) e.getSource();
			
			System.out.println("������Ӧ��"+bt.getName());
			//�ȹر�
			MainFramUiCompononts.manager.close();
			//���¿���
			if(bt == SettingsPanelUICompnonts.bt_reopen){
				MainFramUiCompononts.manager.reOpenPort(
						MainFramUiCompononts.manager.getComStr(), 
						MainFramUiCompononts.manager.getBautrate(), 
						MainFramUiCompononts.manager.getSerialPort_DATABIT(), 
						MainFramUiCompononts.manager.getSerialPort_STOPBIT(), 
						MainFramUiCompononts.manager.getSerialPort_PARITY());
			}
			MainFrame.statusBar.setStatusItems(new String[]{
					MainFramUiCompononts.manager.getComStr(), 
					MainFramUiCompononts.manager.getBautrate()+"", 
					MainFramUiCompononts.manager.getSerialPort_DATABIT()+"", 
					MainFramUiCompononts.manager.status, 
			});
		}
		
	}
	

	
}
