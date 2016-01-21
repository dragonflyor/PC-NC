package com.xiaozhe.Filelist;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;
import com.xiaozhe.bean.Resources;
import com.xiaozhe.ncassistant.uicomponont.NetPanelComponents;

public class ItemPanel extends Panel {

	Label filename;
	Label fileDesc;
	Label downrogress;
	Button bt_download;
	Button bt_download_run;
	
	Resources resources;
	public ItemPanel(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
		init();
		
		setEvent();
	}
	
	void init( ){
		setLayout(new GridLayout(6, 1));
		
		setPreferredSize(new Dimension(600, 200));
		filename = new Label("文件名："+resources.getRealname());
		fileDesc = new Label("文件描述："+resources.getDescription());
		downrogress = new Label("下载进度："+"0%");
		bt_download = new Button("下载在本地桌面");
		bt_download_run = new Button("下载并加工");
		
		this.add(filename);
		this.add(fileDesc);
		this.add(downrogress);
		this.add(bt_download);
		this.add(bt_download_run);
	
		Label label = new Label("-------------");
		this.add(label );
		
	}
	
	void setEvent(){
		bt_download.addActionListener(new MyBtListner());
		bt_download_run.addActionListener(new MyBtListner());
	}
	
	//监听下载按钮
	class MyBtListner implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Button button = (Button) e.getSource();
			
			if(button == bt_download){
				//System.out.println(System.getProperties());
				System.out.println("下载："+resources.getRealname());
				
				FileDownloader fileDownloader = new FileDownloader(new FileDownloaderDelegate() {
					
					public void didStartDownload(FileDownloader fileDownloader) {
						// TODO Auto-generated method stub
						
					}
					
					public void didProgressDownload(FileDownloader fileDownloader) {
						// TODO Auto-generated method stub
						
						String kbPerSecond = "Calculating...";
						if (fileDownloader.getKbPerSecond() != null) {
							kbPerSecond = fileDownloader.getKbPerSecond() + " kb/s";
						}
						System.out.println(fileDownloader.getPercentComplete() + " at " + kbPerSecond);
						downrogress.setText("进度："+fileDownloader.getPercentComplete() + " at " + kbPerSecond);
					}
					
					public void didFinishDownload(FileDownloader fileDownloader) {
						// TODO Auto-generated method stub
						downrogress.setText("下载进度："+"下载完成！保存在"+System.getProperty("user.dir", "C:/Users/zhe/Desktop"));
						
					}
					
					public void didFailDownload(FileDownloader fileDownloader) {
						// TODO Auto-generated method stub
					}
				});
				///day22_upload_download/DownServlet?filename=d1fc88fa-70ee-4530-8adc-41ee21ccde3a鍦嗗姬鎻掕ˉ绠楁硶婕旂畻.xls
				String str = NetPanelComponents.textField.getText();
				//截取出请求的地址
				String localservlet = str.substring(0,str.lastIndexOf("/")+1);
				String downloadurl = localservlet+"DownServlet?filename="+resources.getUuidname();
				System.out.println(downloadurl);
				//连接并下载
				fileDownloader.setUrl(downloadurl);
				fileDownloader.setLocalLocation(System.getProperty("user.dir", "C:/Users/zhe/Desktop")+resources.getRealname());
				fileDownloader.beginDownload();
			}
			else if(button == bt_download_run){
				System.out.println("下载后执行："+resources.getRealname());
			}
			
		}
		
	}
	
	
//	public static void main(String [] args){
//		Frame f = new Frame();
//		
//		f.setLayout(new FlowLayout());
//		f.setSize(500, 500);
//		
//		Resources resources = new Resources();
//		resources.setRealname("NC.txt");
//		resources.setDescription("这是一个样件的加工代码");
//		
//		//添加组件
//		f.add(new ItemPanel(resources));
//		f.add(new ItemPanel(resources));
//		f.add(new ItemPanel(resources));
//		
//		
//		//监听
//		f.addWindowListener(new WindowAdapter() {
//
//			@Override
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//				super.windowClosing(e);
//				System.exit(0);
//			}
//			
//		});
//		
//		//显示
//		f.setVisible(true);
//		
//		
//
//	}
	

	
}
