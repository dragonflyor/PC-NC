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
 public abstract class SerialPortManager {
	//���崮���շ�����
	private SerialPortSimpleRead portSimpleRead;
	private SerialPortSimpleWrite portSimpleWrite;
	
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	
	//ͨѶ���� Ĭ��ֵ����
    int bautrate = 115200;
    int serialPort_DATABIT = SerialPort.DATABITS_8;
    int serialPort_STOPBIT = SerialPort.STOPBITS_1;
    int serialPort_PARITY = SerialPort.PARITY_NONE;


	static{
		//��ȡϵͳCOM�б�
		 portList = CommPortIdentifier.getPortIdentifiers();
	}
	
	/**
	 * ��ָ����������
	 * @param comPort
	 * @param bautrate
	 * @param serialPort_DATABIT
	 * @param serialPort_STOPBIT
	 * @param serialPort_PARITY
	 */
	public SerialPortManager(String comPort,int bautrate, int serialPort_DATABIT,
			int serialPort_STOPBIT, int serialPort_PARITY) {
		super();
		
//		   serialPort.setSerialPortParams(115200,
//                   SerialPort.DATABITS_8,
//                   SerialPort.STOPBITS_1,
//                   SerialPort.PARITY_NONE);
		
		this.bautrate = bautrate;
		this.serialPort_DATABIT = serialPort_DATABIT;
		this.serialPort_STOPBIT = serialPort_STOPBIT;
		this.serialPort_PARITY = serialPort_PARITY;
		SerialPortManager.serialPort = openPort(comPort);
	}
	
	/**
	 * Ĭ�ϲ�������
	 * @param comPort
	 */
	 public SerialPortManager(String comPort) {
		super();
//		//��ȡϵͳ�����п��ö˿ڵļ���
//        portList = CommPortIdentifier.getPortIdentifiers();
		//����Ҫ����Ķ˿�,�򿪴���
		SerialPortManager.serialPort = openPort(comPort);
	}
	
	/**
	 * ��ȡ��ʵ�����ڶ�����
	 * @return ��ȡ�ɹ����ش��ڶ������� ;��ȡʧ�ܷ���null
	 */
	public SerialPortSimpleRead getPortSimpleReadInstance() {
		if(portSimpleRead==null && serialPort!= null){
			//����һ�������߳�
			portSimpleRead = new SerialPortSimpleRead(serialPort,this) {
				
				//�����յ���Ϣ����Ӧ����
				public void serialEvent(SerialPortEvent event) {
					// TODO Auto-generated method stub
					super.serialEvent(event);
					//�������Ҫ����Ĵ���
					System.out.println("SerialPortManager����������Ӧ");
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
		//System.out.println("getPortSimpleWriteInstance():"+getPortSimpleWriteInstance());
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
	


	/**
	 * �򿪴���
	 * @param comPort �˿ں��ַ��� ����:"COM5"
	 * @return ���ش򿪵Ĵ���
	 */
	private SerialPort openPort(String comPort){
		//���»�ȡϵͳCOM�б�
		portList = CommPortIdentifier.getPortIdentifiers();
		if(portList.hasMoreElements()!=true){
			System.out.println("�򿪶˿�ʧ�ܣ���ⲻ��Com");
			return null;
		}
		
		SerialPort serialPort = null;
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
							
							   serialPort.setSerialPortParams(this.bautrate,
			                            this.serialPort_DATABIT,
			                            this.serialPort_STOPBIT,
			                           this.serialPort_PARITY);
							   
						} catch (PortInUseException e) {
							System.out.println("�˿ڱ�ռ��");
						} catch (UnsupportedCommOperationException e) {
							System.out.println("�������õĲ�����֧��");
						}
                }
            }

        }
		return serialPort;
	}
	
	/**
	 * ���ô��ڲ���
	 * @param bautrate
	 * @param serialPort_DATABIT
	 * @param serialPort_STOPBIT
	 * @param serialPort_PARITY
	 */
	void setCommPrams(    int bautrate,
    int serialPort_DATABIT,
    int serialPort_STOPBIT,
    int serialPort_PARITY){
		this.bautrate = bautrate;
		this.serialPort_DATABIT = serialPort_DATABIT;
		this.serialPort_STOPBIT = serialPort_STOPBIT;
		this.serialPort_PARITY = serialPort_PARITY;
		
	}
	
	
	public void close(){
		this.serialPort.close();
	}
	/**
	 * �����ڶ������ݺ���ô����
	 */
	public abstract void dealRead();
	

}
