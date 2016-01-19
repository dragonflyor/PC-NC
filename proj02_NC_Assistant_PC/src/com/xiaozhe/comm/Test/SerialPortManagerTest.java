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
		//��Ӧ���ڽ��ն�����
		public void serialEvent(SerialPortEvent event) {
			//Ӧ�ó��򣬼��������ݶ���
			System.out.println("Ӧ�ó��򣬼��������ݶ���");
			//�����ݲ���
			read();
		}
	};
	private static SerialPortSimpleWrite simpleWrite;
	private static SerialPortSimpleRead simpleRead;
	
	public static void main(String[] agrs){
		
		simpleWrite = manager.getPortSimpleWriteInstance();
		simpleRead = manager.getPortSimpleReadInstance();
		
		//��ȡ��ʶ��Ĵ���
		getPortsList();
		
		
		if(getPortsList().isEmpty()){
			System.out.println("û�п�ʶ��com");
		}else{
			//һ��д����
			//write();
			
			//��Ƭ��д���ԣ�����λ����ѯʱ���Ӧ
			//writeFileToSTM32F4();
			
			//�Ͽ������� �ٴ�����
			changePara();
		}
		
		//�رմ���
		manager.close();
				
	} 
	
	/**
	 * ��ȡϵͳ����ʶ��Ĵ���
	 * @return ���ض˿ڵ��ַ�������
	 */
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
	
	/**
	 * ��������
	 */
	public static void write(){
		System.out.println("д����");
		System.out.println("simpleWrite:"+simpleWrite);
		int  i=10;
		
		while(i-->0){
			simpleWrite.writeStringToSTM32("##123123--", 1000);
			simpleWrite.writeStringToSTM32("##123123aa--",1000);
			//���ͽ���
			//simpleWrite.writeStringToSTM32("$$",1000);
		}

	} 
	
	/**
	 * �������ݶ�ȡ���� ���ԣ����ڼ�����
	 */
	public static void read(){
		System.out.println("������");
	}
	
	/**
	 * �����ļ�����Ƭ���Ĳ���
	 */
	public static void writeFileToSTM32F4(){
		//��ʼ����ʱ��
		long startime = System.currentTimeMillis();
		
		System.out.println("����Ƭ�����ļ�����");
		//System.out.println(System.getProperties().toString());
		//��ȡ�ļ�·��,��ȡʧ��������洴��
		String filepath = System.getProperties().getProperty("java.class.path", "C:/Users/zhe/Desktop");
		System.out.println("�ļ�·��"+filepath);
		//��ȡ�ļ�
		File file = new File(filepath,"/sendoutdata.txt");
		//	file.createNewFile();
		//�ڶ�����������λ��������ѯ������
		simpleWrite.writeFileToSTM32(file, 300,0);
		
		//��������ʱ��
		long endtime = System.currentTimeMillis();

		//�ķ�ʱ��
		long timecost = endtime - startime;

		System.out.println("�ļ�����ķ�ʱ��Ϊ��"+timecost+"ms");

	}
	
	/**
	 * �޸Ĵ��ڲ������ٴ����ӵĲ���
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
