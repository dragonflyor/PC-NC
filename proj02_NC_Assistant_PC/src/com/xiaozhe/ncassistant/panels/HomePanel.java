package com.xiaozhe.ncassistant.panels;

import java.awt.Button;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class HomePanel extends Panel {

	File file;
	Frame f;
	

	
	public HomePanel( Frame f) {
		super();
		this.f=f;
		//初始化
		init();
		//设置监听
		
		//可见
		this.setVisible(true);
		
		System.out.println("被创建");
	}
	
	
	void init(){
		//一行两列的布局
		this.setLayout(new GridLayout(1,2));
		this.add(new SendPanel());
	}

	class SendPanel extends Panel{
		Button bt_send;
		Button bt_openFile;
		Button bt_saveFile;
		
		TextArea textArea;
		
		private GridBagLayout layout;
		private GridBagConstraints c;
		
		public SendPanel() {
			super();
			//初始化界面
			init();
			//设置监听
			setEvent();
		}
	
	     
		private void addComponent(Component component,
                GridBagLayout gridbag,
                GridBagConstraints c) {
			gridbag.setConstraints(component, c);
			add(component);
			
		}
		
		void init(){
			//布局
			layout = new GridBagLayout();
			this.setLayout(layout);
			
			//文件选择 与发送按钮
			Panel panel_bt = new Panel();
			bt_send = new Button("发送给下位机");
			bt_openFile = new Button("打开文件");
			bt_saveFile = new Button("另存文本区内容");
			
			//添加按键
			panel_bt.add(bt_send);
			panel_bt.add(new Label("  "));
			panel_bt.add(bt_openFile);
			panel_bt.add(bt_saveFile);
			
			//文本框 ,20行20字符宽度
			textArea = new TextArea();

			
			//按照布局添加
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//按照布局添加组件
			//本行最后一个组件
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=0;
			c.weighty=1;
			this.addComponent(panel_bt,layout,c);
			//横向纵向可以拉伸
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=5;
			this.addComponent(textArea,layout,c);
			
			//f.pack();
		}
		
		/**
		 * 添加控件监听
		 */
		void setEvent(){
			
			bt_openFile.addActionListener(new MYBtnListener());
			bt_saveFile.addActionListener(new MYBtnListener());
		}
		
		/**
		 * 对按键进行监听
		 * @author zhe
		 *
		 */
		class MYBtnListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				Button bt = (Button) e.getSource();
				
				if(bt == bt_send){
					//建立新线程处理发送
				}else if(bt == bt_openFile){
					//打开文件对话框
					FileDialog d1 = new FileDialog(f, "选择需要打开文件" , FileDialog.LOAD);
					d1.setVisible(true);
					file = new File(d1.getDirectory()+d1.getFile());
					System.out.println(file);
					if(file != null){
						//读取文件显示到文本框
						try {
							FileReader fr = new FileReader(file);
							BufferedReader br = new BufferedReader(fr);
							
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							
							
							String line = null;
							while((line = br.readLine())!=null){
								bos.write(line.getBytes());
								bos.write("\r\n".getBytes());
							}
							System.out.println("文本内容"+bos.toString());
							//显示到文本框
							textArea.setText(bos.toString());
							System.out.println(bos.toString());
							
							br.close();
							bos.close();
							
						} catch (Exception e1) {
							//打开文件错误
							//e1.printStackTrace();
							System.out.println("取消打开");
						}						
					}

					
				}else if(bt == bt_saveFile){
					//保存文件对话框
					FileDialog d1 = new FileDialog(f, "保存文件" , FileDialog.SAVE);
					d1.setVisible(true);
					file = new File(d1.getDirectory()+d1.getFile());
					if(file != null){
						//获取本内容
						ByteArrayInputStream bis = new ByteArrayInputStream(textArea.getText().getBytes());
						System.out.println(textArea.getText());
						
						try {
							//创建文件
							File file = new File(d1.getDirectory()+d1.getFile());
							FileOutputStream fos = new FileOutputStream(file);
							
							//保存
							byte [] buf = new byte[1024];
							int len = -1;
							
							while((len = bis.read(buf)) != -1){
								fos.write(buf, 0, len);
							}
						} catch (IOException e1) {
							
							System.out.println("存文件错误");
						}
					}
					
				}
				
			}
			
		}

	}
	
	
	
}
