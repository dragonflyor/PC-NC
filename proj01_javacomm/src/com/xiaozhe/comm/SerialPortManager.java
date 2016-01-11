package com.xiaozhe.comm;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.UnsupportedCommOperationException;

/**
 * ���������������������շ����࣬ͨ������ľ�̬�������Ի�ȡϵͳ��ʶ��Ķ˿�
 * ʹ��ʱ�����Ҫ�Խ��յ����ݽ��д���������������getPortSimpleReadInstance()�е�serialEvent(SerialPortEvent event)����
 * @author zhe
 *
 */
 public class SerialPortManager {
	//���崮���շ�����
	private SerialPortSimpleRead portSimpleRead;
	private SerialPortSimpleWrite portSimpleWrite;
	
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;


	static{
		//��ȡϵͳCOM�б�
		 portList = CommPortIdentifier.getPortIdentifiers();
	}
	/**
	 * ��ȡ��ʵ�����ڶ�����
	 * @return ��ȡ�ɹ����ش��ڶ������� ;��ȡʧ�ܷ���null
	 */
	public SerialPortSimpleRead getPortSimpleReadInstance() {
		if(portSimpleRead==null && serialPort!= null){
			//����һ�������߳�
			portSimpleRead = new SerialPortSimpleRead(serialPort) {
				
				//�����յ���Ϣ����Ӧ����
				public void serialEvent(SerialPortEvent event) {
					// TODO Auto-generated method stub
					super.serialEvent(event);
					//�������Ҫ����Ĵ���
					//System.out.println("SerialPortManager����������Ӧ");
				}
				
			};
			//portSimpleRead = new SerialPortSimpleRead(serialPort);
			return portSimpleRead;
		}
		return null;
	}
	//��ȡд����
	public SerialPortSimpleWrite getPortSimpleWriteInstance() {
		if(portSimpleWrite==null && serialPort!= null){
			//����һ�������߳�
			portSimpleWrite = new SerialPortSimpleWrite(serialPort);
			return portSimpleWrite;
		}
		return null;
	}

	/**
	 * ���ַ������ϵķ�ʽ���ص�ǰϵͳ��ʶ��Ĵ��ڣ�COM����
	 * @return �˿ںŵ��ַ�����
	 */
	public static ArrayList getAvailablePorts(){

		//���»�ȡϵͳCOM�б�
		portList = CommPortIdentifier.getPortIdentifiers();
		//�б�����ַ�������
		ArrayList portstrList = new ArrayList();
		 //��������
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            //����˿��Ǵ���
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	System.out.println("��ȡ���Ĵ���ID��"+portId.getName());
            	//��ӵ�����
            	portstrList.add(portId.getName());
            }
        }
		return portstrList;
	}
	
	public SerialPortManager(String comPort) {
		super();
//		//��ȡϵͳ�����п��ö˿ڵļ���
//        portList = CommPortIdentifier.getPortIdentifiers();
		//����Ҫ����Ķ˿�
		setPort(comPort);
	}

	/**
	 * ���ô���ͨѶ�Ķ˿ںţ�����ǰ����ִ�� portList =CommPortIdentifier.getPortIdentifiers()��ȡ���ж˿�
	 * @param comPort �˿ں��ַ��� ����:"COM5"
	 */
	private void setPort(String comPort){
		
		  //��������
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	
                // if (portId.getName().equals("COM1")) {
            	System.out.println("��ȡ���Ĵ���ID��"+portId.getName());
            	if (portId.getName().equals(comPort)){
                        try {
							serialPort = (SerialPort)
							    portId.open("SerialPortManager", 2000);
							
							   serialPort.setSerialPortParams(115200,
			                            SerialPort.DATABITS_8,
			                            SerialPort.STOPBITS_1,
			                            SerialPort.PARITY_NONE);
							   
						} catch (PortInUseException e) {
							System.out.println("�˿ڱ�ռ��");
						} catch (UnsupportedCommOperationException e) {
							System.out.println("�������õĲ�����֧��");
						}
                }
            }

        }
	}
	

}
