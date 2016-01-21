package com.xiaozhe.Filelist;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import com.xiaozhe.bean.Resources;
import com.xiaozhe.myhttputils.MyHttpUtils;
import com.xiaozhe.ncassistant.uicomponont.NetPanelComponents;


public class HttpUtilsTest {
	
	public static void main(String [] args){
		//String msg = MyHttpUtils.getServletData("http://172.26.164.3:8080/day22_upload_download/ReturnFileListServlet");
		//System.out.println(msg);
		//创建httputils对象
		//HttpUtils http = new HttpUtils(10);
		//下载文件
//		System.out.println(System.getProperties());
//		System.out.println(System.getProperty("user.dir"));
		//http.download("", System.getProperty(key), callback);

		
		final Frame f = new Frame();
		
		f.setLayout(new FlowLayout());
		f.setSize(500, 500);
		
		
		f.add(NetPanelComponents.textField);
		final Button button = NetPanelComponents.bt_connect;
		
		f.add(button);
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Button bt = (Button) e.getSource();
				if(bt == button){
					//ArrayList<Resources> arrayList = MyHttpUtils.getFileResources("http://172.26.164.3:8080/day22_upload_download/ReturnFileListServlet");
					ArrayList<Resources> arrayList = MyHttpUtils.getFileResources(NetPanelComponents.textField.getText());
					
					for (Resources resources : arrayList) {
						System.out.println("----------------------");
						System.out.println("名称："+resources.getRealname());
						System.out.println("描述："+resources.getDescription());
						System.out.println("保存路径："+resources.getSavepath());
						System.out.println("Uuidname："+resources.getUuidname());
					}
					
					if(NetPanelComponents.filesPanel !=null){
						f.remove(NetPanelComponents.filesPanel);
					}
					NetPanelComponents.filesPanel= new FilesPanel(arrayList);
					f.add(NetPanelComponents.filesPanel);
					f.invalidate();
					f.setVisible(true);
				}
			}
		});
		

		//监听
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		
		f.setVisible(true);
		
		
		
	} 
}
