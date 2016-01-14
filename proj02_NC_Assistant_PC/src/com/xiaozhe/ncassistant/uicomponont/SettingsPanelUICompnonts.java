package com.xiaozhe.ncassistant.uicomponont;

import java.awt.Button;
import java.awt.Choice;

public class SettingsPanelUICompnonts {

	public static Choice cs_comStr;
	public static Choice cs_bautRateStr;
	public static Choice cs_databitsStr;
	
	public static Button bt_reopen;
	
	static{
		//串口号
		cs_comStr = new Choice();
		//波特率选项
		cs_bautRateStr = new Choice();
		cs_bautRateStr.add("115200");
		cs_bautRateStr.add("9600");
		//数据位选项
		cs_databitsStr = new Choice();
		cs_databitsStr.add("8");
		cs_databitsStr.add("7");
		
		//按钮
		bt_reopen = new Button("确认设置并重新尝试连接");
	}
	
}

