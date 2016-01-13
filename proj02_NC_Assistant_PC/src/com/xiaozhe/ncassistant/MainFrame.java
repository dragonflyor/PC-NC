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

	//���ڵĿ�͸�
	static int FRAME_WIDTH = 800;
	static int FRAME_HEIGHT = 500;
	
	//��Ƭҳ��
	private static Panel contentPanel;
	CardLayout cardLayout ;
	//��ҳ��
	static public  HomePanel homePanel;
	static public SettingsPanel settingsPanel;
	static public DrawwingPanel drawingPanel;
	static public InternetPanel internetPanel;
	
	//���
	StatusBar statusBar;
	MyMenu myMenu;
	
	//����
	public SerialPortSimpleWrite simpleWrite;
	public SerialPortSimpleRead simpleRead;
	static SerialPortManager manager;
	
	//����
	public MainFrame(String title) throws HeadlessException {
		super(title);

		//��������
		init();
		//���ÿؼ��ļ���
		setEvent();
		//���ò˵�
		setMenus();
		//�ɼ�
		this.setVisible(true);
	}
	
	


	
	/**
	 * ��ʼ�� 
	 * 1 ����ҳ��
	 * 2 �����ؼ�
	 * 3 ҳ����ӿؼ�
	 */
	public void init(){
		//ָ��fram����ʱ������������Ͻǵ�λ��
				this.setLocation(100,100);
				//���ô��ڵĿ��
				this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
				//��С���ɱ�
				this.setResizable(false);
				//���ò�����ʽ
				//this.setLayout(new CardLayout());
				BorderLayout borderLayout = new BorderLayout();
				this.setLayout(new BorderLayout());
				
				//���״̬��
				String [] statusItems = {"COM:","������:","����λ��:","����״̬:"};
				statusBar = new StatusBar(statusItems, this);
				this.add(statusBar, BorderLayout.SOUTH); 
				
				//setMenus();
				//�����˵�
				Panel menu = new MyMenu(this);
				this.add(menu, BorderLayout.NORTH);
				
				//ҳ��1------------------------------------------
				homePanel = new HomePanel(this);
				//this.add(homePanel,BorderLayout.CENTER);
				
				
				//����
				manager = new SerialPortManager("COM5",115200,
						SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE) {
					//��Ӧ���ڽ��ն�����
					public void serialEvent(SerialPortEvent event) {
						//Ӧ�ó��򣬼��������ݶ���
						System.out.println("Ӧ�ó��򣬼��������ݶ���");
						//�յ�����
						MainFrame.this.onComRead(event);
					}
				};
				
				//��ȡʵ��
				simpleWrite = manager.getPortSimpleWriteInstance();
				simpleRead = manager.getPortSimpleReadInstance();
				
				//ҳ��2------------------------------------------
				settingsPanel = new SettingsPanel();
				
				//ҳ��3
				drawingPanel =new DrawwingPanel();
				
				//ҳ��4
				internetPanel = new InternetPanel();
				
				//----------------��ҳ����ӵ�����Ƭҳ-------------
				contentPanel = new Panel();
				cardLayout = new CardLayout(0, 0);
				contentPanel.setLayout(cardLayout);
				//��ӿ�Ƭ��ҳ
				contentPanel.add(homePanel, "1");
				contentPanel.add(settingsPanel, "2");
				contentPanel.add(drawingPanel, "3");
				contentPanel.add(internetPanel, "4");
				
				//��ӵ����
				this.add(contentPanel,BorderLayout.CENTER);
				

	}
	
	/**
	 * ���ڽ�������ʱ��Ӧ
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
        	System.out.println("���ڴ���״̬���ǣ�"+event.getEventType());
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[1024];

            try {
                while (simpleRead.inputStream.available() > 0) {
                    int numBytes = simpleRead.inputStream.read(readBuffer);
                }
                String msg = new String(readBuffer);
                System.out.print(msg);
                
                //��ʾ���ؼ�
                HomePanelUICompnonts.receiveTextArea.setText(HomePanelUICompnonts.receiveTextArea.getText()+msg);
                //ˢ��
                HomePanelUICompnonts.receiveTextArea.invalidate();
                
            } catch (IOException e) {}
            break;
        }
        
	}
	
	/**
	 * ���ü���
	 */
	public void setEvent(){
		
		//��Ӧ�رճ���ť
		this.addWindowListener( new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//�رճ���
				System.exit(0);
				manager.close();
			}
			
		});
		
	}

	
	/**
	 * ���ò˵�
	 */
	public void setMenus()
	{
		myMenu=new MyMenu(this);
	}
	
	
	/**
	 * �̳���Panel������Button��һ���࣬���˵�ʹ�ã�Ӧ��BorderLayout��NORTHλ��
	 * @author zhe
	 *
	 */
	class MyMenu extends Panel
	{
		Frame f;
		
		//����
		Button bt0_HomePanel;
		Button bt1_SettingPanel;
		Button bt2_DrawwingPanel;
		Button bt3_InternetPanel;
		Button bt4_OtherPanel;
		
		//�����˵�
		PopupMenu popupmenu;
		//�˵���
		private MenuItem mi_0;
		private MenuItem mi_1;
		private MenuItem mi_2;

		//����
		MyMenu(Frame f)
		{
			this.f=f;	
			init();
			setEvent();
		}

		/**
		 * ��ʼ�� 
		 * 1 ����ҳ��
		 * 2 �����ؼ�
		 * 3 ҳ����ӿؼ�
		 */
		void init()
		{
			//����
			this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			this.setBackground(Color.LIGHT_GRAY);
			
			//�˵�����
			bt0_HomePanel = new Button("������");
			bt1_SettingPanel = new Button("����");
			bt2_DrawwingPanel = new Button("��ͼ");
			bt3_InternetPanel = new Button("��·");
			bt4_OtherPanel = new Button("����");
			
			//��bt4��Ӳ˵�,���������õ����˵�
	        popupmenu = new PopupMenu("popupmenu"); 
	        mi_0 = new MenuItem("��������1");  
	        mi_1 = new MenuItem("��������2");  
	        mi_2 = new MenuItem("��������3");  
	        popupmenu.add(mi_0);   
	        popupmenu.add(mi_1);   
	        popupmenu.add(mi_2);   
			bt4_OtherPanel.add(popupmenu);
			
			//��ӵ�Panel�˵���
			this.add(bt0_HomePanel);
			this.add(bt1_SettingPanel);
			this.add(bt2_DrawwingPanel);
			this.add(bt3_InternetPanel);
			this.add(bt4_OtherPanel);
			
		}

		/**
		 * �������Ӽ����¼�
		 */
		void setEvent()
		{
			System.out.println("���ò˵�����");
			
			//������Ӽ���
			bt0_HomePanel.addActionListener(new MyBtMenuListener());
			bt1_SettingPanel.addActionListener(new MyBtMenuListener());
			bt2_DrawwingPanel.addActionListener(new MyBtMenuListener());
			bt3_InternetPanel.addActionListener(new MyBtMenuListener());
			bt4_OtherPanel.addActionListener(new MyBtMenuListener());
			
			//�˵������
			mi_0.addActionListener(new MyMenuItemsListener());
			mi_1.addActionListener(new MyMenuItemsListener());
			mi_2.addActionListener(new MyMenuItemsListener());

		}
		
		/**
		 * �����˵��еİ�ť
		 * @author zhe
		 *
		 */
		class MyBtMenuListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				//��ȡ��ǰ�������İ���
				Button bt = (Button) e.getSource();
				
				if(bt==bt0_HomePanel){
					System.out.println("���ؽ���");
					//��ʾ��һ������	
					cardLayout.show(contentPanel, "1");
				}else if(bt == bt1_SettingPanel){
					System.out.println("���ý���");
					//��ʾ���ý���	
					cardLayout.show(contentPanel, "2");
				}else if(bt == bt2_DrawwingPanel){
					System.out.println("��ͼ����");
					//��ʾ��ͼ����	
					cardLayout.show(contentPanel, "3");
				}else if(bt == bt3_InternetPanel){
					System.out.println("�������");
					//��ʾ�������
					cardLayout.show(contentPanel, "4");
				}else if(bt == bt4_OtherPanel){
					System.out.println("����");
					//��������ѡ��Ĳ˵�	
					popupmenu.show((Component) popupmenu.getParent(),0, bt.getY()+bt.getHeight());
					
//					String text ="";
//	                int i=5;
//	                while(i--!=0){
//	                	text = HomePanelUICompnonts.receiveTextArea.getText()+i;
//	                	  HomePanelUICompnonts.receiveTextArea.setText(text+i);
//	                      //ˢ��
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
		 * �����˵����е���Ŀ
		 * @author zhe
		 *
		 */
		class MyMenuItemsListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				//��ȡ��ǰ���������Ķ���
				MenuItem item = (MenuItem) e.getSource();
				System.out.println("�������˵���:"+item.getName());
				
				if(item == mi_0){
					System.out.println("ִ�в˵���0");
					//�˴���Ӳ˵���mi_0,����Ĵ���
				}else if(item == mi_1){
					System.out.println("ִ�в˵���1");
					//�˴���Ӳ˵���mi_1,����Ĵ���
				}else if(item == mi_2){
					System.out.println("ִ�в˵���2");
					//�˴���Ӳ˵���mi_2,����Ĵ���
				}
				
			}
			
		}
	}

	
	
	
}




