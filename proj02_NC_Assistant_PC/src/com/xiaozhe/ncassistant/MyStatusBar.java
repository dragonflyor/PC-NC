package com.xiaozhe.ncassistant;

import java.awt.Color;
import java.awt.Frame;

public class MyStatusBar extends StatusBar {
	private static String text_com ="-";
	private static String text_bautrate="-";
	private static String text_databits="-";
	private static String text_status="未连接";
	
	private static String [] statusItems = {"COM："+text_com,
			"波特率："+text_bautrate,
			"数据位数："+text_databits,
			"串口状态："+text_status};

	//构造方法
	MyStatusBar(Frame frame) {
		super(statusItems, frame);
	}
	
	//重置参数
	public  void setText_com(String text_com) {
		MyStatusBar.text_com = text_com;
		//更新标签内容
		this.SetStatu(0, "COM："+text_com);	
	}

	public void setText_bautrate(String text_bautrate) {
		MyStatusBar.text_bautrate = text_bautrate;
		//更新标签内容
		this.SetStatu(1, "波特率："+text_bautrate);
	}

	public void setText_databits(String text_databits) {
		MyStatusBar.text_databits = text_databits;
		//更新标签内容
		this.SetStatu(2, "数据位数："+text_databits);
	}

	public void setText_status(String text_status) {
		MyStatusBar.text_status = text_status;
		//修改状态条显示颜色
		if(text_status.contains("成功")||text_status.contains("OK")){
			this.GetStatu(3).setBackground(Color.GREEN);
		}else{
			this.GetStatu(3).setBackground(Color.RED);
		}
		//更新标签内容
		this.SetStatu(3, "串口状态："+text_status);
	}

	/**
	 * 重新设置所有参数
	 * 传入参数显示标签字符串数组
	 * @param statusItems
	 */
	public void setStatusItems(String[] statusItems) {
		setText_com(statusItems[0]);
		setText_bautrate(statusItems[1]);
		setText_databits(statusItems[2]);
		setText_status(statusItems[3]);
	}

//	//测试
//	public static void main(String[] args) {
//		Frame frame = new Frame();
//		frame .setLayout(new FlowLayout());
//		frame.setBounds(100, 100, 300, 300);
//		
//		MyStatusBar myStatusBar = new MyStatusBar(frame);
//		
//		frame.add(myStatusBar);
//		
//		frame.setVisible(true);
//		
//		//退出程序
//		frame.addWindowListener(new WindowAdapter() {
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
//		
//		int a1 =0;
//		int a2 =0;
//		int a3 =0;
//		int a4 =0;
//		
//		while(true){
//			a1+=1;
//			a2+=2;
//			a3+=3;
//			a4+=4;
//			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			myStatusBar.setStatusItems(new String[]{a1+"",a2+"",a3+"",a4+""});
//			
//		}
//		
//
//	}
	
}
