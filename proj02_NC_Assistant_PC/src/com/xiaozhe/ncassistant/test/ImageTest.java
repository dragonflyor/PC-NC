package com.xiaozhe.ncassistant.test;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest{

	public static void main(String[] args) {
		new MyFrame2("´°¿Ú");
	
	}

}


class MyFrame2  extends Frame {
	
	
	
	public MyFrame2(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		init();
		
		setEvent();

		
		
	}

	void init(){
		this.setLayout(new FlowLayout());
		this.setBounds(100, 100, 200, 200);
		
		int p_width =100;
		int p_height = 100;
		
//		//Í¼Æ¬
//		BufferedImage bImage = new BufferedImage(p_width, p_height, BufferedImage.TYPE_INT_RGB);
//		
//		//ÄÃ»­±Ê
//		Graphics g = bImage.getGraphics();
//		//»­±ß¿ò
//		g.setColor(Color.BLACK);
//		g.drawRect(0, 0, p_width, p_height);
//		//Ìî³äÉ«
//		g.setColor(Color.DARK_GRAY);
//		
//		Label pic_label = new Label();
//		
//		pic_label.set
		
		
		this.setVisible(true);
	}
	
	void setEvent()
	{
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		int p_width =300;
		int p_height = 300;
		//Í¼Æ¬
		BufferedImage bImage = null;
		//File imagefile = new File("E:/MyEclipse_pj/Project_github/PC-NC/proj02_NC_Assistant_PC/Êý¿ØÍ¼Æ¬/Êý¿ØÃæ°å11.jpg");
		bImage = new BufferedImage(p_width, p_width, BufferedImage.TYPE_INT_RGB);
		//ÄÃ»­±Ê
		Graphics g1 = bImage.getGraphics();
		//»­±ß¿ò
		g1.setColor(Color.BLACK);
		g1.drawRect(0, 0, p_width, p_height);
		//Ìî³äÉ«
		g1.setColor(Color.DARK_GRAY);
		//
		//g1.setPaintMode();
		
//		if(imagefile.exists() == false){
//			System.out.println("´´½¨Í¼Æ¬");
//			imagefile = new File("/pathImage.jpg");
//			//bImage = new BufferedImage(p_width, p_height, BufferedImage.TYPE_INT_RGB);
//			try {
//				imagefile.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//			}
//		}
		
//		try {
//			bImage=ImageIO.read(imagefile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//ÄÃ»­±Ê
//		Graphics g1 = bImage.getGraphics();
//		//»­±ß¿ò
//		g1.setColor(Color.BLACK);
//		g1.drawRect(0, 0, p_width, p_height);
//		//Ìî³äÉ«
//		g1.setColor(Color.DARK_GRAY);
		
//		int width =100;
//		int height = 100;
//		//»­±ß¿ò
//		g.setColor(Color.BLACK);
//		g.drawRect(0, 0, width, height);
//		g.fillRect(0, 0, width, height);
//		//Ìî³äÉ«
//		g.setColor(Color.DARK_GRAY);
		
		//Graphics g1 = bImage.getGraphics();
		g1.drawLine(-50, -20, 50, 50);
		
		g.drawImage(bImage,  0, 0, p_width, p_height,0, 0, bImage.getWidth(), bImage.getHeight(), null);
	}
	
	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		// TODO Auto-generated method stub
		super.addMouseMotionListener(l);
	}
	

}