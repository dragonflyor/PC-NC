package com.xiaozhe.ncassistant.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

class GetFilesFrameDemo 
{


	public static void main(String[] args) 
	{
		new MyFrame();
		System.out.println("Hello World!");
	}
}


class MyFrame
{
	Frame f;
	TextField tf ;
	Button b ;
	TextArea ta;

	//������ʾ�Ի���
	Dialog dlg;
	Label lab;
	Button b_ok;

	MyFrame()
	{
		init();
		setEvent();
	}

	public void init()
	{
		f= new Frame("�ҵĴ���");
		tf = new TextField(30);
		b = new Button("ת��");
		ta = new TextArea(20,40);

		f.setBounds(100,100,400,500);
		f.setLayout(new FlowLayout());

		f.add(tf);
		f.add(b);
		f.add(ta);

		f.setVisible(true);

		//������ʾ��Ϣ
		dlg = new Dialog(f,"������ʾ",true);
		lab = new Label();
		b_ok = new Button("ȷ��");

		dlg.setBounds(200,200,350,100);
		dlg.setLayout(new FlowLayout());

		dlg.add(lab);
		dlg.add(b_ok);
	}

	public void setEvent()
	{
		f.addWindowListener(new WindowAdapter ()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		
		});

		b.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
					showDir();
			}
		});

		b_ok.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				dlg.setVisible(false);
			}

		});
		dlg.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dlg.setVisible(false);
			}
		});

		tf.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					showDir();
				}

			}
		});

	
	}

	private void showDir()
	{
		//��ȡ·��
				String dirpath = tf.getText();
				System.out.println(dirpath);
				//�õ�Ŀ¼����
				File dir = new File(dirpath);
				if(dir.exists()&&dir.isDirectory())
				{
					ta.setText("-------------------------FilesList-------------------------");
					String [] filelist = dir.list();

					for(String s:filelist)
					{
						System.out.println(s);
						ta.append(s+"\r\n");
					}
				}
				else
				{
					String info = tf.getText();
					lab.setText("�����Ŀ¼·��:"+info+" �Ǵ���ģ������䣡��");
					dlg.setVisible(true);
				}


	}


}