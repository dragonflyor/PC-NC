package com.xiaozhe.ncassistant.uicomponont;

import java.awt.Button;
import java.awt.TextField;

import com.xiaozhe.Filelist.FilesPanel;

public class NetPanelComponents {
	public static TextField textField ;
	public static Button bt_connect;
	
	public static FilesPanel filesPanel =null;
	
	
	static {
		textField =new  TextField(30);
		textField.setText("http://172.26.164.3:8080/day22_upload_download/ReturnFileListServlet");
		bt_connect = new Button("连接并获取资源");
	}

}
