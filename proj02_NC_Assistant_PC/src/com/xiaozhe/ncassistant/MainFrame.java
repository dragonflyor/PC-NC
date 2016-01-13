package com.xiaozhe.ncassistant;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;

import com.xiaozhe.comm.SerialPortManager;
import com.xiaozhe.comm.SerialPortSimpleRead;
import com.xiaozhe.comm.SerialPortSimpleWrite;
import com.xiaozhe.ncassistant.panels.DrawwingPanel;
import com.xiaozhe.ncassistant.panels.HomePanel;
import com.xiaozhe.ncassistant.panels.InternetPanel;
import com.xiaozhe.ncassistant.panels.SettingsPanel;
import com.xiaozhe.ncassistant.uicomponont.HomePanelUICompnonts;

public class MainFrame extends Frame {

	//窗口的宽和高
	static int FRAME_WIDTH = 800;
	static int FRAME_HEIGHT = 500;
	
	//卡片页面
	private static Panel contentPanel;
	CardLayout cardLayout ;
	//子页面
	static public  HomePanel homePanel;
	static public SettingsPanel settingsPanel;
	static public DrawwingPanel drawingPanel;
	static public InternetPanel internetPanel;
	
	//组件
	StatusBar statusBar;
	MyMenu myMenu;
	
	//串口
	public SerialPortSimpleWrite simpleWrite;
	public SerialPortSimpleRead simpleRead;
	static SerialPortManager manager;
	
	//构造
	public MainFrame(String title) throws HeadlessException {
		super(title);

		//串口设置
		init();
		//设置控件的监听
		setEvent();
		//设置菜单
		setMenus();
		//可见
		this.setVisible(true);
	}
	
	


	
	/**
	 * 初始化 
	 * 1 设置页面
	 * 2 创建控件
	 * 3 页面添加控件
	 */
	public void init(){
		//指定fram弹出时，相对桌面左上角的位置
				this.setLocation(100,100);
				//设置窗口的宽高
				this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
				//大小不可变
				this.setResizable(false);
				//设置布局样式
				//this.setLayout(new CardLayout());
				BorderLayout borderLayout = new BorderLayout();
				this.setLayout(new BorderLayout());
				
				//添加状态栏
				String [] statusItems = {"COM:","波特率:","数据位数:","串口状态:"};
				statusBar = new StatusBar(statusItems, this);
				this.add(statusBar, BorderLayout.SOUTH); 
				
				//setMenus();
				//按键菜单
				Panel menu = new MyMenu(this);
				this.add(menu, BorderLayout.NORTH);
				
				//页面1------------------------------------------
				homePanel = new HomePanel(this);
				//this.add(homePanel,BorderLayout.CENTER);
				
				
				//串口
				manager = new SerialPortManager("COM5",115200,
						SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE) {
					//响应串口接收读数据
					public void serialEvent(SerialPortEvent event) {
						//应用程序，监听到数据读入
						System.out.println("应用程序，监听到数据读入");
						//收到数据
						MainFrame.this.onComRead(event);
					}
				};
				
				//获取实例
				simpleWrite = manager.getPortSimpleWriteInstance();
				simpleRead = manager.getPortSimpleReadInstance();
				
				//页面2------------------------------------------
				settingsPanel = new SettingsPanel();
				
				//页面3
				drawingPanel =new DrawwingPanel();
				
				//页面4
				internetPanel = new InternetPanel();
				
				//----------------子页面添加到到卡片页-------------
				contentPanel = new Panel();
				cardLayout = new CardLayout(0, 0);
				contentPanel.setLayout(cardLayout);
				//添加卡片子页
				contentPanel.add(homePanel, "1");
				contentPanel.add(settingsPanel, "2");
				contentPanel.add(drawingPanel, "3");
				contentPanel.add(internetPanel, "4");
				
				//添加到框架
				this.add(contentPanel,BorderLayout.CENTER);
				

	}
	
	/**
	 * 串口接收数据时响应
	 * @param event
	 */
	public void onComRead(SerialPortEvent event){
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
        	System.out.println("串口错误状态号是："+event.getEventType());
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[1024];

            try {
                while (simpleRead.inputStream.available() > 0) {
                    int numBytes = simpleRead.inputStream.read(readBuffer);
                }
                String msg = new String(readBuffer);
                System.out.print(msg);
                
                //显示到控件
                HomePanelUICompnonts.receiveTextArea.setText(HomePanelUICompnonts.receiveTextArea.getText()+msg);
                //刷新
                HomePanelUICompnonts.receiveTextArea.invalidate();
                
            } catch (IOException e) {}
            break;
        }
        
	}
	
	/**
	 * 设置监听
	 */
	public void setEvent(){
		
		//响应关闭程序按钮
		this.addWindowListener( new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//关闭程序
				System.exit(0);
				manager.close();
			}
			
		});
		
	}

	
	/**
	 * 设置菜单
	 */
	public void setMenus()
	{
		myMenu=new MyMenu(this);
	}
	
	
	/**
	 * 继承自Panel，包含Button的一个类，做菜单使用，应该BorderLayout的NORTH位置
	 * @author zhe
	 *
	 */
	class MyMenu extends Panel
	{
		Frame f;
		
		//按键
		Button bt0_HomePanel;
		Button bt1_SettingPanel;
		Button bt2_DrawwingPanel;
		Button bt3_InternetPanel;
		Button bt4_OtherPanel;
		
		//弹出菜单
		PopupMenu popupmenu;
		//菜单项
		private MenuItem mi_0;
		private MenuItem mi_1;
		private MenuItem mi_2;

		//构造
		MyMenu(Frame f)
		{
			this.f=f;	
			init();
			setEvent();
		}

		/**
		 * 初始化 
		 * 1 设置页面
		 * 2 创建控件
		 * 3 页面添加控件
		 */
		void init()
		{
			//布局
			this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			this.setBackground(Color.LIGHT_GRAY);
			
			//菜单按键
			bt0_HomePanel = new Button("主控制");
			bt1_SettingPanel = new Button("设置");
			bt2_DrawwingPanel = new Button("绘图");
			bt3_InternetPanel = new Button("网路");
			bt4_OtherPanel = new Button("其他");
			
			//给bt4添加菜单,其他功能用弹出菜单
	        popupmenu = new PopupMenu("popupmenu"); 
	        mi_0 = new MenuItem("其他功能1");  
	        mi_1 = new MenuItem("其他功能2");  
	        mi_2 = new MenuItem("其他功能3");  
	        popupmenu.add(mi_0);   
	        popupmenu.add(mi_1);   
	        popupmenu.add(mi_2);   
			bt4_OtherPanel.add(popupmenu);
			
			//添加到Panel菜单条
			this.add(bt0_HomePanel);
			this.add(bt1_SettingPanel);
			this.add(bt2_DrawwingPanel);
			this.add(bt3_InternetPanel);
			this.add(bt4_OtherPanel);
			
		}

		/**
		 * 给组件添加监听事件
		 */
		void setEvent()
		{
			System.out.println("设置菜单监听");
			
			//按键添加监听
			bt0_HomePanel.addActionListener(new MyBtMenuListener());
			bt1_SettingPanel.addActionListener(new MyBtMenuListener());
			bt2_DrawwingPanel.addActionListener(new MyBtMenuListener());
			bt3_InternetPanel.addActionListener(new MyBtMenuListener());
			bt4_OtherPanel.addActionListener(new MyBtMenuListener());
			
			//菜单项监听
			mi_0.addActionListener(new MyMenuItemsListener());
			mi_1.addActionListener(new MyMenuItemsListener());
			mi_2.addActionListener(new MyMenuItemsListener());

		}
		
		/**
		 * 监听菜单中的按钮
		 * @author zhe
		 *
		 */
		class MyBtMenuListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				//获取当前监听到的按键
				Button bt = (Button) e.getSource();
				
				if(bt==bt0_HomePanel){
					System.out.println("主控界面");
					//显示第一个界面	
					cardLayout.show(contentPanel, "1");
				}else if(bt == bt1_SettingPanel){
					System.out.println("设置界面");
					//显示设置界面	
					cardLayout.show(contentPanel, "2");
				}else if(bt == bt2_DrawwingPanel){
					System.out.println("绘图界面");
					//显示绘图界面	
					cardLayout.show(contentPanel, "3");
				}else if(bt == bt3_InternetPanel){
					System.out.println("网络界面");
					//显示网络界面
					cardLayout.show(contentPanel, "4");
				}else if(bt == bt4_OtherPanel){
					System.out.println("其他");
					//单出其他选项的菜单	
					popupmenu.show((Component) popupmenu.getParent(),0, bt.getY()+bt.getHeight());
					
//					String text ="";
//	                int i=5;
//	                while(i--!=0){
//	                	text = HomePanelUICompnonts.receiveTextArea.getText()+i;
//	                	  HomePanelUICompnonts.receiveTextArea.setText(text+i);
//	                      //刷新
//	                      HomePanelUICompnonts.receiveTextArea.invalidate();
//	                      try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//	                }
	                
				}else{
					
				}
				
			}
			
		}
		
		/**
		 * 监听菜单项中的项目
		 * @author zhe
		 *
		 */
		class MyMenuItemsListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				//获取当前触发监听的对象
				MenuItem item = (MenuItem) e.getSource();
				System.out.println("监听到菜单项:"+item.getName());
				
				if(item == mi_0){
					System.out.println("执行菜单项0");
					//此处添加菜单项mi_0,处理的代码
				}else if(item == mi_1){
					System.out.println("执行菜单项1");
					//此处添加菜单项mi_1,处理的代码
				}else if(item == mi_2){
					System.out.println("执行菜单项2");
					//此处添加菜单项mi_2,处理的代码
				}
				
			}
			
		}
	}

	
	
	
}




