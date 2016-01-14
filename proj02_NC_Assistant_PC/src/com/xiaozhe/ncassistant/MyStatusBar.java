package com.xiaozhe.ncassistant;

import java.awt.Color;
import java.awt.Frame;

public class MyStatusBar extends StatusBar {
	private static String text_com ="-";
	private static String text_bautrate="-";
	private static String text_databits="-";
	private static String text_status="δ����";
	
	private static String [] statusItems = {"COM��"+text_com,
			"�����ʣ�"+text_bautrate,
			"����λ����"+text_databits,
			"����״̬��"+text_status};

	//���췽��
	MyStatusBar(Frame frame) {
		super(statusItems, frame);
	}
	
	//���ò���
	public  void setText_com(String text_com) {
		MyStatusBar.text_com = text_com;
		//���±�ǩ����
		this.SetStatu(0, "COM��"+text_com);	
	}

	public void setText_bautrate(String text_bautrate) {
		MyStatusBar.text_bautrate = text_bautrate;
		//���±�ǩ����
		this.SetStatu(1, "�����ʣ�"+text_bautrate);
	}

	public void setText_databits(String text_databits) {
		MyStatusBar.text_databits = text_databits;
		//���±�ǩ����
		this.SetStatu(2, "����λ����"+text_databits);
	}

	public void setText_status(String text_status) {
		MyStatusBar.text_status = text_status;
		//�޸�״̬����ʾ��ɫ
		if(text_status.contains("�ɹ�")||text_status.contains("OK")){
			this.GetStatu(3).setBackground(Color.GREEN);
		}else{
			this.GetStatu(3).setBackground(Color.RED);
		}
		//���±�ǩ����
		this.SetStatu(3, "����״̬��"+text_status);
	}

	/**
	 * �����������в���
	 * ���������ʾ��ǩ�ַ�������
	 * @param statusItems
	 */
	public void setStatusItems(String[] statusItems) {
		setText_com(statusItems[0]);
		setText_bautrate(statusItems[1]);
		setText_databits(statusItems[2]);
		setText_status(statusItems[3]);
	}

//	//����
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
//		//�˳�����
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
