package com.xiaozhe.ncassistant.uicomponont;

import java.awt.Button;
import java.awt.Choice;

public class SettingsPanelUICompnonts {

	public static Choice cs_comStr;
	public static Choice cs_bautRateStr;
	public static Choice cs_databitsStr;
	
	public static Button bt_reopen;
	
	static{
		//���ں�
		cs_comStr = new Choice();
		//������ѡ��
		cs_bautRateStr = new Choice();
		cs_bautRateStr.add("115200");
		cs_bautRateStr.add("9600");
		//����λѡ��
		cs_databitsStr = new Choice();
		cs_databitsStr.add("8");
		cs_databitsStr.add("7");
		
		//��ť
		bt_reopen = new Button("ȷ�����ò����³�������");
	}
	
}

