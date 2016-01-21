package com.xiaozhe.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

public class NCUtils {
	
	/**
	 * ĳ��������ӿؼ�
	 * @param container ����
	 * @param component Ҫ��ӵĿؼ�
	 * @param gridbag ����
	 * @param c Լ��
	 */
	public static void addComponentTo( Container container,Component component,
            GridBagLayout gridbag,
            GridBagConstraints c){
		
		gridbag.setConstraints(component, c);
		container.add(component);
		
	}
	
	
//	/**
//	 * ��һ���ַ����н����������ַ����Ȳ���������ΪX��Y��Z��T��F��S,���뼯��
//	 * @param msg ������ַ���
//	 */
//	public static HashMap<String,String> parseParamFromMsg(String msg)
//	{
//		String [] keys = new String[]{"X","Y","Z","T","F","S"};
//		HashMap<String, String> params = new HashMap<String, String>();
//		//�������ַ���������>>10#11#22#33#44#55#66
//		//��ȡֻ����11#22#33���ִ�
//		 String tmp = msg.substring(msg.indexOf('#')+1);
//		//ȡ�Ĳ����ַ���
//		String [] strs = tmp.split("#");
//		//System.out.println("���������Ĳ�����"+strs.toString());		
//		for (int i= 0;i<strs.length;i++) {
//			//params.put(keys[i], strs[i]);
//		}
//		
//		return params;
//	}
//	
	
	/**
	 * ��һ���ַ����н����������ַ����Ȳ���������ΪX��Y��Z��T��F��S,���뼯��
	 * @return ���س�������
	 */
	public static String[] parseParamFromMsg1(String msg)
	{

		//ȥ���ַ���ǰ��Ŀո��س�����
		msg=msg.trim();
		//��ȡֻ����11#22#33���ִ�
		 String tmp = msg.substring(msg.indexOf('#')+1);
		//ȡ�Ĳ����ַ���
		String [] strs = tmp.split("#");
		System.out.println("���������Ĳ�����"+strs.toString());
		return strs;
	}
}
