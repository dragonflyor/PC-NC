package com.xiaozhe.comm.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.comm.SerialPort;

import com.xiaozhe.comm.SerialPortManager;
import com.xiaozhe.comm.SerialPortSimpleRead;
import com.xiaozhe.comm.SerialPortSimpleWrite;

public class SerialPortManagerTest {
	
	static SerialPortManager manager = new SerialPortManager("COM5",115200,
			SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE) {
		
		public void dealRead() {
			// TODO Auto-generated method stub
			
		}
	};
	private static SerialPortSimpleWrite simpleWrite;
	private static SerialPortSimpleRead simpleRead;
	
	public static void main(String[] agrs){
		
		simpleWrite = manager.getPortSimpleWriteInstance();
		simpleRead = manager.getPortSimpleReadInstance();
		
		getPortsList();
		
		
		if(getPortsList().isEmpty()){
			System.out.println("没有可识别com");
		}else{
			//write();
			read();
			
			writeFileToSTM32F4();
		}
				
	} 
	
	//获取系统可以识别的串口
	public static ArrayList getPortsList(){
		ArrayList list = SerialPortManager.getAvailablePorts();
		Iterator iterator = list.iterator();
		System.out.println("可识别的COM号：");
		while(iterator.hasNext()){
			
			String port = (String)iterator.next();
			System.out.println(port);
		}
		
		return list;
	} 
	
	//发送数据
	public static void write(){
		System.out.println("写数据");
		//simpleWrite = manager.getPortSimpleWriteInstance();
		System.out.println("simpleWrite:"+simpleWrite);
		int  i=10;
		
		while(i-->0){
			simpleWrite.writeStringToSTM32("##123123--", 1000);
			simpleWrite.writeStringToSTM32("##123123aa--",1000);
			//发送结束
			simpleWrite.writeStringToSTM32("$$",1000);
		}

	} 
	
	//接收数据测试
	public static void read(){
		System.out.println("读数据");
		//simpleWrite = manager.getPortSimpleWriteInstance();
		
	}
	
	//发送文件给单片机的测试
	public static void writeFileToSTM32F4(){
		//开始发送时间
//		long startime = System.currentTimeMillis();
		
		System.out.println("给单片机发文件测试");
		//System.out.println(System.getProperties().toString());
		//获取文件路径,获取失败则从桌面创建
		String filepath = System.getProperties().getProperty("java.class.path", "C:/Users/zhe/Desktop");
		System.out.println("文件路径"+filepath);
		//获取文件
		File file = new File(filepath,"/sendoutdata.txt");
		//	file.createNewFile();
		//第二个参数是下位机串口轮询的周期
		simpleWrite.writeFileToSTM32(file, 300);
		
//		//结束发送时间
//		long endtime = System.currentTimeMillis();
//		
//
//		//耗费时间
//		long timecost = endtime - startime;
//		
//		//延时
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("文件传输耗费时间为："+timecost+"ms");
		
		
	}

}
