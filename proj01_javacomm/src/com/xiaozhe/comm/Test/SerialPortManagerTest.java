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
			System.out.println("û�п�ʶ��com");
		}else{
			//write();
			read();
			
			writeFileToSTM32F4();
		}
				
	} 
	
	//��ȡϵͳ����ʶ��Ĵ���
	public static ArrayList getPortsList(){
		ArrayList list = SerialPortManager.getAvailablePorts();
		Iterator iterator = list.iterator();
		System.out.println("��ʶ���COM�ţ�");
		while(iterator.hasNext()){
			
			String port = (String)iterator.next();
			System.out.println(port);
		}
		
		return list;
	} 
	
	//��������
	public static void write(){
		System.out.println("д����");
		//simpleWrite = manager.getPortSimpleWriteInstance();
		System.out.println("simpleWrite:"+simpleWrite);
		int  i=10;
		
		while(i-->0){
			simpleWrite.writeStringToSTM32("##123123--", 1000);
			simpleWrite.writeStringToSTM32("##123123aa--",1000);
			//���ͽ���
			simpleWrite.writeStringToSTM32("$$",1000);
		}

	} 
	
	//�������ݲ���
	public static void read(){
		System.out.println("������");
		//simpleWrite = manager.getPortSimpleWriteInstance();
		
	}
	
	//�����ļ�����Ƭ���Ĳ���
	public static void writeFileToSTM32F4(){
		//��ʼ����ʱ��
//		long startime = System.currentTimeMillis();
		
		System.out.println("����Ƭ�����ļ�����");
		//System.out.println(System.getProperties().toString());
		//��ȡ�ļ�·��,��ȡʧ��������洴��
		String filepath = System.getProperties().getProperty("java.class.path", "C:/Users/zhe/Desktop");
		System.out.println("�ļ�·��"+filepath);
		//��ȡ�ļ�
		File file = new File(filepath,"/sendoutdata.txt");
		//	file.createNewFile();
		//�ڶ�����������λ��������ѯ������
		simpleWrite.writeFileToSTM32(file, 300);
		
//		//��������ʱ��
//		long endtime = System.currentTimeMillis();
//		
//
//		//�ķ�ʱ��
//		long timecost = endtime - startime;
//		
//		//��ʱ
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("�ļ�����ķ�ʱ��Ϊ��"+timecost+"ms");
		
		
	}

}
