package com.xiaozhe.ncassistant.uicomponont;

import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;


/**
 * �����Ͽؼ�
 * @author zhe
 *
 */
public class SettingsPanelUICompnonts{
	public static Label label_X;
	public static Label label_Y;
	public static Label label_Z;

	public static Label label_T;
	public static Label label_F;
	public static Label label_S;
	
	public static Label label_currentGcode;
	
	public static Button bt_saveImage;
	
	public static  TextArea receiveTextArea;
	
	static {
		label_X =new Label("0");
		label_Y =new Label("0");
		label_Z =new Label("0");
		
		label_T =new Label("0");
		label_F =new Label("0");
		label_S =new Label("0");

		label_currentGcode = new Label("�������� :NOT RUNNING", Label.LEFT);
		label_currentGcode.setFont(new Font("����", Font.BOLD, 15));

		
		//�ı��� ,20��20�ַ����
		receiveTextArea = new TextArea();
		receiveTextArea.setRows(1);
		receiveTextArea.setText("");
	}
	
}
