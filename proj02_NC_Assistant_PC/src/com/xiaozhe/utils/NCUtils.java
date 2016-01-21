package com.xiaozhe.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

public class NCUtils {
	
	/**
	 * 某个容器添加控件
	 * @param container 容器
	 * @param component 要添加的控件
	 * @param gridbag 布局
	 * @param c 约束
	 */
	public static void addComponentTo( Container container,Component component,
            GridBagLayout gridbag,
            GridBagConstraints c){
		
		gridbag.setConstraints(component, c);
		container.add(component);
		
	}
	
	
//	/**
//	 * 从一串字符串中解析出坐标字符串等参数，依次为X，Y，Z，T，F，S,放入集合
//	 * @param msg 输入的字符串
//	 */
//	public static HashMap<String,String> parseParamFromMsg(String msg)
//	{
//		String [] keys = new String[]{"X","Y","Z","T","F","S"};
//		HashMap<String, String> params = new HashMap<String, String>();
//		//解析的字符串类似与>>10#11#22#33#44#55#66
//		//截取只包含11#22#33的字串
//		 String tmp = msg.substring(msg.indexOf('#')+1);
//		//取的参数字符串
//		String [] strs = tmp.split("#");
//		//System.out.println("解析出来的参数："+strs.toString());		
//		for (int i= 0;i<strs.length;i++) {
//			//params.put(keys[i], strs[i]);
//		}
//		
//		return params;
//	}
//	
	
	/**
	 * 从一串字符串中解析出坐标字符串等参数，依次为X，Y，Z，T，F，S,放入集合
	 * @return 返回常熟数组
	 */
	public static String[] parseParamFromMsg1(String msg)
	{

		//去掉字符串前后的空格后回车换行
		msg=msg.trim();
		//截取只包含11#22#33的字串
		 String tmp = msg.substring(msg.indexOf('#')+1);
		//取的参数字符串
		String [] strs = tmp.split("#");
		System.out.println("解析出来的参数："+strs.toString());
		return strs;
	}
}
