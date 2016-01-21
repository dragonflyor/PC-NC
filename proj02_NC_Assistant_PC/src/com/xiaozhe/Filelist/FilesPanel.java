package com.xiaozhe.Filelist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.util.ArrayList;

import com.xiaozhe.bean.Resources;


public class FilesPanel extends ScrollPane{
	
	ArrayList<Resources> fileslist;
	Panel panel;
	
	public FilesPanel(ArrayList<Resources> fileslist) {
		// TODO Auto-generated constructor stub
		this.fileslist = fileslist;
		
		
		init();
		
	}
	
	void init(){
		panel = new Panel(new FlowLayout());
		
		
		
		this.setPreferredSize(new Dimension(500, 300));
		setBackground(Color.LIGHT_GRAY);
		int height = 0;
		for (Resources resources : fileslist) {
			height +=200;
			//Ìí¼Ó×é¼þ
			panel.add(new ItemPanel(resources));
		}
		panel.setPreferredSize(new Dimension(300,height));
		this.add(panel);
		

	}

}


