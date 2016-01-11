package com.xiaozhe.ncassistant.test;


import java.awt.*; 
import java.awt.event.*; 
public class FileDialogTest {  
	
	Frame f = new Frame("����"); // ���������ļ��Ի���  
	FileDialog d1 = new FileDialog(f   , "ѡ����Ҫ���ļ�" , FileDialog.LOAD);  
	FileDialog d2 = new FileDialog(f   , "ѡ�񱣴��ļ���·��" , FileDialog.SAVE);  
	Button b1 = new Button("���ļ�");  Button b2 = new Button("�����ļ�");
	
	public void init()  {   
		
		b1.addActionListener(new ActionListener()   {    
			public void actionPerformed(ActionEvent e)    {     
				d1.setVisible(true);     // ��ӡ���û�ѡ����ļ�·�����ļ���     
				System.out.println(d1.getDirectory() + d1.getFile());    
				}   
			}
		);   
		b2.addActionListener(new ActionListener()   {    
			public void actionPerformed(ActionEvent e)    {     
				d2.setVisible(true);     // ��ӡ���û�ѡ����ļ�·�����ļ���    
				System.out.println(d2.getDirectory()      + d2.getFile());    
				}   
			}

		);   
		//�������ڶ���
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		
		f.add(b1);   
		f.add(b2 , BorderLayout.SOUTH);   
		f.pack();   
		f.setVisible(true);  
		}  
	
	public static void main(String[] args)  {   
			new FileDialogTest().init();  
	}
}