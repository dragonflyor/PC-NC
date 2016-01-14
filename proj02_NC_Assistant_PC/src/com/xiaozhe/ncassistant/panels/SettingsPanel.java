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

	//内容面板
	Panel contentPane;
	
	public SettingsPanel() {
		super();

		
		//初始化界面
		init();
		//事件监听
		setEvent();
		//可见
		this.setVisible(true);
	}
	
	/**
	 * 初始化界面
	 */
	void init(){
		//1布局
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		//2.1标题
		Label headingLabel = new Label("设     置",Label.CENTER);
		headingLabel.setFont(new Font("宋体", Font.BOLD, 36));
		headingLabel.setBackground(Color.GRAY);
		
		//2.2内容面板
		Panel contentParent = new Panel(new FlowLayout());
		
		contentParent.setFont(new Font("楷体", Font.BOLD, 15));
		contentPane = new Panel();
		contentParent.add(contentPane);
		GridBagLayout bagLayout = new GridBagLayout();
		contentPane.setLayout(bagLayout);
		GridBagConstraints c = new GridBagConstraints(); 
		//2.2.1 串口设置
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		//c.weighty = 1;

		NCUtils.addComponentTo(contentPane, new Label("串口号:",Label.LEFT), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;

		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_comStr, bagLayout, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		NCUtils.addComponentTo(contentPane, new Label("波特率:"), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;

		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_bautRateStr, bagLayout, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		NCUtils.addComponentTo(contentPane, new Label("数据位数:"), bagLayout, c);
		c.weightx = 3;
		//c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.cs_databitsStr, bagLayout, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		NCUtils.addComponentTo(contentPane, SettingsPanelUICompnonts.bt_reopen, bagLayout, c);
		
		
		
		//3添加控件
		this.add(headingLabel,BorderLayout.NORTH);

		this.add(contentParent,BorderLayout.CENTER);
		
		//读取系统的Com口添加到com选项框
		ArrayList<String> comlist_t = MainFramUiCompononts.manager.getAvailablePorts();
		for (String string : comlist_t) {
			SettingsPanelUICompnonts.cs_comStr.add(string);
		}
		
	}
	
	/**
	 * 设置监听
	 */
	void setEvent(){
		SettingsPanelUICompnonts.cs_comStr.addItemListener(new MyItemListener());
		SettingsPanelUICompnonts.cs_bautRateStr.addItemListener(new MyItemListener());
		SettingsPanelUICompnonts.cs_databitsStr.addItemListener(new MyItemListener());
		
		//重新连接的按键
		SettingsPanelUICompnonts.bt_reopen.addActionListener(new MyBtnActionListener());
	}
	
	
	/**
	 * 监听下来框的选项
	 * @author zhe
	 *
	 */
	class MyItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			Choice cc = (Choice) e.getSource();
			//获取选中项
			String select = cc.getSelectedItem();
			
	//		System.out.print("下拉菜单："+cc.getName());
			//串口下拉
			if(cc == SettingsPanelUICompnonts.cs_comStr){
				System.out.println("选择串口:"+select);
				MainFrame.manager.setComStr(select);
			}
			else if(cc == SettingsPanelUICompnonts.cs_bautRateStr){
				System.out.println("选择波特率:"+select);
				//切换波特率
				if("9600".equals(select)){
					MainFrame.manager.setBautrate(9600);
				}else if("115200".equals(select)){
					MainFrame.manager.setBautrate(115200);
				}
			}
			else if(cc == SettingsPanelUICompnonts.cs_databitsStr){
				System.out.println("选择数据位数"+select);
				//切换波特率
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
			
			System.out.println("按键响应："+bt.getName());
			//先关闭
			MainFramUiCompononts.manager.close();
			//重新开启
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
