package com.xiaozhe.comm.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;

import com.xiaozhe.comm.SerialPortManager;
import com.xiaozhe.comm.SerialPortSimpleRead;
import com.xiaozhe.comm.SerialPortSimpleWrite;

public class SerialPortManagerTest {
	
	static SerialPortManager manager = new SerialPortManager("COM5",115200,
			SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE) {
		//响应串口接收读数据
		public void serialEvent(SerialPortEvent event) {
			//应用程序，监听到数据读入
			System.out.println("应用程序，监听到数据读入");
			//读数据测试
			read();
		}
	};
	private static SerialPortSimpleWrite simpleWrite;
	private static SerialPortSimpleRead simpleRead;
	
	public static void main(String[] agrs){
		
		simpleWrite = manager.getPortSimpleWriteInstance();
		simpleRead = manager.getPortSimpleReadInstance();
		
		//获取可识别的串口
		getPortsList();
		
		
		if(getPortsList().isEmpty()){
			System.out.println("没有可识别com");
		}else{
			//一般写测试
			//write();
			
			//向单片机写测试，与下位机轮询时间对应
			//writeFileToSTM32F4();
			
			//断开后设置 再次连接
			changePara();
		}
		
		//关闭串口
		manager.close();
				
	} 
	
	/**
	 * 获取系统可以识别的串口
	 * @return 返回端口的字符串集合
	 */
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
	
	/**
	 * 发送数据
	 */
	public static void write(){
		System.out.println("写数据");
		System.out.println("simpleWrite:"+simpleWrite);
		int  i=10;
		
		while(i-->0){
			simpleWrite.writeStringToSTM32("##123123--", 1000);
			simpleWrite.writeStringToSTM32("##123123aa--",1000);
			//发送结束
			//simpleWrite.writeStringToSTM32("$$",1000);
		}

	} 
	
	/**
	 * 接收数据读取处理 测试，放在监听中
	 */
	public static void read(){
		System.out.println("读数据");
	}
	
	/**
	 * 发送文件给单片机的测试
	 */
	public static void writeFileToSTM32F4(){
		//开始发送时间
		long startime = System.currentTimeMillis();
		
		System.out.println("给单片机发文件测试");
		//System.out.println(System.getProperties().toString());
		//获取文件路径,获取失败则从桌面创建
		String filepath = System.getProperties().getProperty("java.class.path", "C:/Users/zhe/Desktop");
		System.out.println("文件路径"+filepath);
		//获取文件
		File file = new File(filepath,"/sendoutdata.txt");
		//	file.createNewFile();
		//第二个参数是下位机串口轮询的周期
		simpleWrite.writeFileToSTM32(file, 300,0);
		
		//结束发送时间
		long endtime = System.currentTimeMillis();

		//耗费时间
		long timecost = endtime - startime;

		System.out.println("文件传输耗费时间为："+timecost+"ms");

	}
	
	/**
	 * 修改串口参数后再次连接的测试
	 */
	static void changePara(){
		try {
			simpleWrite.writeStringToSTM32("11111111",1000);
			Thread.sleep(2000);
	
			manager.reOpenPort("COM5",9600,
				SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			simpleWrite.writeStringToSTM32("1111222",1000);
			
			Thread.sleep(2000);
			manager.reOpenPort("COM5",115200,
					SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			simpleWrite.writeStringToSTM32("1111333",1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
