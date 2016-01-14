package com.xiaozhe.ncassistant.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;

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
		
		//2.1内容面板
		contentPane = new Panel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints(); 
		
		//3添加控件
		this.add(headingLabel,BorderLayout.NORTH);
	}
	
	/**
	 * 设置监听
	 */
	void setEvent(){
		
	}

	
}
