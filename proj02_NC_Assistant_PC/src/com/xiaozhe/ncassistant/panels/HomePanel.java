package com.xiaozhe.ncassistant.panels;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
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

import com.xiaozhe.ncassistant.MainFrame;
import com.xiaozhe.ncassistant.uicomponont.HomePanelUICompnonts;

public class HomePanel extends Panel {

	File file_open;
	Frame f;
	public HomePanel( Frame f) {
		super();
		//页面数据初始化
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
		this.add(new ReceivePanel());
	}



	
	/**
	 * 管理左边发送面板
	 * @author zhe
	 *
	 */
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
			//
			this.setBackground(Color.GRAY);
			
			//文件选择 与发送按钮
			Panel panel_bt = new Panel();
			panel_bt.setBackground(Color.darkGray);
			bt_send = new Button("发送给下位机");
			bt_openFile = new Button("打开文件");
			bt_saveFile = new Button("另存文本区内容");
			
			//添加按键
			GridBagLayout layout1 = new GridBagLayout();
			GridBagConstraints c1 = new GridBagConstraints();
			
			panel_bt.setLayout(layout1);
			c1.anchor = c1.WEST;
//			panel_bt.add(bt_send);
//			panel_bt.add(new Label("  "));
//			panel_bt.add(bt_openFile);
//			panel_bt.add(bt_saveFile);
			addComponent(bt_send, layout1, c1);
			addComponent(new Label("  "), layout1, c1);
			addComponent(bt_openFile, layout1, c1);
			addComponent(bt_saveFile, layout1, c1);
			
			
			//文本框 ,20行20字符宽度
			textArea = new TextArea();

			
			//按照布局添加
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//按照布局添加组件
			//本行最后一个组件
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=1;
			this.addComponent(panel_bt,layout,c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = c.SOUTH;
			c.weightx=1;
			c.weighty=1;
			Label label = new Label("文件预览/编辑区",Label.CENTER);
			label.setBackground(Color.LIGHT_GRAY);
			this.addComponent(label,layout,c);
			
			//横向纵向可以拉伸
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=20;
			this.addComponent(textArea,layout,c);
			
			//f.pack();
		}
		
		/**
		 * 添加控件监听
		 */
		void setEvent(){
			
			bt_openFile.addActionListener(new MYBtnListener());
			bt_saveFile.addActionListener(new MYBtnListener());
			bt_send.addActionListener(new MYBtnListener());
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
					MainFrame mf = (MainFrame) HomePanel.this.f;
					System.out.println("发送文本内容");
					mf.simpleWrite.writeFileToSTM32(file_open, 1000);
				}else if(bt == bt_openFile){
					//打开文件对话框
					FileDialog d1 = new FileDialog(f, "选择需要打开文件" , FileDialog.LOAD);
					d1.setVisible(true);
					file_open = new File(d1.getDirectory()+d1.getFile());
					System.out.println(file_open);
					if(file_open != null){
						//读取文件显示到文本框
						try {
							FileReader fr = new FileReader(file_open);
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
					File file = new File(d1.getDirectory()+d1.getFile());
					if(file != null){
						//获取本内容
						ByteArrayInputStream bis = new ByteArrayInputStream(textArea.getText().getBytes());
						System.out.println(textArea.getText());
						
						try {
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
	
	/**
	 * 管理右边发送面板
	 * @author zhe
	 *
	 */
	class ReceivePanel extends Panel{
		Button bt_send;
		Button bt_openFile;
		Button bt_saveFile;
		
		private GridBagLayout layout;
		private GridBagConstraints c;
		
		public ReceivePanel() {
			super();
			//初始化界面
			init();
			//设置监听
			setEvent();
		}
	
	    /**
	     * 给这个页面添加控件 
		 * @param component 要添加的控件
		 * @param gridbag 布局
		 * @param c 约束
	     */
		private void addComponent(Component component,
                GridBagLayout gridbag,
                GridBagConstraints c) {
			addComponentTo(this, component, gridbag, c);
		}
	
		/**
		 * 某个容器添加控件
		 * @param container 容器
		 * @param component 要添加的控件
		 * @param gridbag 布局
		 * @param c 约束
		 */
		private void addComponentTo( Container container,Component component,
                GridBagLayout gridbag,
                GridBagConstraints c){
			
			gridbag.setConstraints(component, c);
			container.add(component);
			
		}
		
		void init(){
			
			//布局
			layout = new GridBagLayout();
			this.setLayout(layout);
			this.setBackground(Color.WHITE);
			
			//按照布局添加
			layout = new GridBagLayout();
			c = new GridBagConstraints();
			this.setLayout(layout);
			
			
			//按照布局添加组件
			
			//第一行
			c.weightx=1;
			c.weighty=0;
			this.addComponent(new Label("控制坐标"),layout,c);
			//本行最后一个组件
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.weightx=1;
			c.weighty=0;
			this.addComponent(new Label("工艺参数"),layout,c);
		
			/**
			 * 处理UI排版：
			 * X:0
			 * Y:0
			 * Z:0
			 */
			//第二行,第一列,实时控制坐标区
			//第一个容器
			Panel panel_cord = new Panel();
			panel_cord.setBackground(Color.BLACK);
			panel_cord.setForeground(Color.WHITE);
			panel_cord.setFont(new Font("宋体", Font.BOLD, 20));
			
			GridBagLayout layout1 = new GridBagLayout();
			GridBagConstraints c1 = new GridBagConstraints();
			panel_cord.setLayout(layout1);
			
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("X:"), layout1, c1);
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_X, layout1, c1);
			
			c1.gridwidth = GridBagConstraints.RELATIVE;
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("Y:"), layout1, c1);	
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_Y, layout1, c1);
			
			c1.gridwidth = GridBagConstraints.RELATIVE;
			c1.weightx = 1;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,new Label("Z:"), layout1, c1);
			c1.gridwidth = GridBagConstraints.REMAINDER;
			c1.weightx = 2;
			c1.weighty = 1;
			this.addComponentTo(panel_cord,HomePanelUICompnonts.label_Z, layout1, c1);
			
			//添加到框架
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.weightx=1;
			c.weighty=1;
			c.anchor = GridBagConstraints.WEST;
			//水平可拉伸
			c.fill = GridBagConstraints.BOTH;
			this.addComponent(panel_cord, layout, c);
			
			//第二行，第二列 工艺
			//第一个容器
			Panel panel_cratf = new Panel();
			panel_cratf.setBackground(Color.lightGray);
			panel_cratf.setForeground(Color.DARK_GRAY);
			panel_cratf.setFont(new Font("宋体", Font.BOLD, 15));
			
			GridBagLayout layout2 = new GridBagLayout();
			GridBagConstraints c2 = new GridBagConstraints();
			panel_cratf.setLayout(layout2);
			
			
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("T:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_T, layout2, c2);
			
			c2.gridwidth = GridBagConstraints.RELATIVE;
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("F:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_F, layout2, c2);
			
			c2.gridwidth = GridBagConstraints.RELATIVE;
			c2.weightx = 1;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,new Label("S:"), layout2, c2);
			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.weightx = 2;
			c2.weighty = 1;
			this.addComponentTo(panel_cratf,HomePanelUICompnonts.label_S, layout2, c2);
			

			//添加到框架
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.BOTH;
			c.weightx=1;
			c.weighty=1;
			c.anchor = GridBagConstraints.WEST;
			//水平可拉伸
			this.addComponent(panel_cratf, layout, c);
			
			//第三行  当前执行的代码
			//横向纵向可以拉伸
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.anchor = GridBagConstraints.WEST;
			c.weightx=1;
			c.weighty=1;
			this.addComponent(HomePanelUICompnonts.label_currentGcode,layout,c);

			//第四行 文本框
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = GridBagConstraints.REMAINDER;
			
			Label label = new Label("串口接收的数据",Label.CENTER);
			label.setBackground(Color.GRAY);
			this.addComponent(label,layout,c);
			
			c.weightx=1;
			c.weighty=20;
			this.addComponent(HomePanelUICompnonts.receiveTextArea,layout,c);
			

			
			//f.pack();
		}
		
		/**
		 * 添加控件监听
		 */
		void setEvent(){
			
			
		}
		
		/**
		 * 对按键进行监听
		 * @author zhe
		 *
		 */
		class MYBtnListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				Button bt = (Button) e.getSource();
				
				
				
			}
			
		}

	}
	
}




///**
// * 控件的数据
// * @author zhe
// *
// */
//class HomePanelUIData{
//	static int X=0;
//	static int Y=0;
//	static int Z=0;
//	
//	static int T=0;
//	static int F=0;
//	static int S=0;
//}

