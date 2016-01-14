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
	
	static Enumeration<?> portList;
	

	static CommPortIdentifier portId;
	static SerialPort serialPort;
	
	//ͨѶ���� Ĭ��ֵ����
    int bautrate = 115200;
    int serialPort_DATABIT = SerialPort.DATABITS_8;
    int serialPort_STOPBIT = SerialPort.STOPBITS_1;
    int serialPort_PARITY = SerialPort.PARITY_NONE;
    //״̬
    public static String status = "δ����";


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
			portSimpleRead = new SerialPortSimpleRead(serialPort,this);
			//portSimpleRead = new SerialPortSimpleRead(serialPort);
			return portSimpleRead;
		}
		return null;
	}
	//��ȡд����
	public SerialPortSimpleWrite getPortSimpleWriteInstance() {
		if(portSimpleWrite==null && serialPort!= null){
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
	public static ArrayList<String> getAvailablePorts(){

		//���»�ȡϵͳCOM�б�
		portList = CommPortIdentifier.getPortIdentifiers();
		//�б�����ַ�������
		ArrayList<String> portstrList = new ArrayList<String>();
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
		status = "portList"+portList;
		//System.out.println("openPort(String comPort)--portList"+portList);
		if(portList.hasMoreElements()!=true){
			System.out.println("�򿪶˿�ʧ�ܣ���ⲻ��Com");
			status = "�򿪶˿�ʧ�ܣ���ⲻ��Com";
			return null;
		}
		
		SerialPort serialPort = null;
		  //��������
		//���ӳɹ��ı�־
		boolean b_cennected = false;
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
							   
							   System.out.println("�����ӵĴ���serialPort��"+serialPort);
							   status = "���ӳɹ�";
							 //���ӳɹ�
							   b_cennected = true;
							   
						} catch (PortInUseException e) {
							System.out.println("�˿ڱ�ռ��");
							status = "�˿ڱ�ռ��";
						} catch (UnsupportedCommOperationException e) {
							System.out.println("�������õĲ�����֧��");
							status = "�������õĲ�����֧��";
						}
                }
            }

        }
        
        if(b_cennected == false){
     	   System.out.println("���õĶ˿ڲ�����");
		   status = "���õĶ˿ڲ�����";
        }
		return serialPort;
	}
	
	/**
	 * �����²����ٴδ� ����
	 * @param comPort
	 * @param bautrate
	 * @param serialPort_DATABIT
	 * @param serialPort_STOPBIT
	 * @param serialPort_PARITY
	 * @return
	 */
	public SerialPort reOpenPort(String comPort, int bautrate, int serialPort_DATABIT,
			int serialPort_STOPBIT, int serialPort_PARITY){
		
		//�رյ�ǰ
		this.close();

		
		//�²���
		this.bautrate = bautrate;
		this.serialPort_DATABIT = serialPort_DATABIT;
		this.serialPort_STOPBIT = serialPort_STOPBIT;
		this.serialPort_PARITY = serialPort_PARITY;
		SerialPortManager.serialPort = openPort(comPort);
		
		//�ٴλ�ȡ�¶�д����
		
		//����һ�������߳�
		portSimpleRead = new SerialPortSimpleRead(serialPort,this);
		portSimpleWrite = new SerialPortSimpleWrite(serialPort);
		
		System.out.println("�������ú����´�----");
		
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
	
	
	/**
	 * �رմ��ڹ������Դ
	 */
	public void close(){
		System.out.println("���ڹ���ر�----");
		if(SerialPortManager.serialPort!=null){
			SerialPortManager.serialPort.close();
			serialPort = null;
		}
		portSimpleRead.close();
		portSimpleWrite.close();
		
	}
	
	/**
	 * ��ȡ���ڶ���
	 * @return
	 */
	public static SerialPort getSerialPort() {
		return serialPort;
	}

	
	/**
	 * �����ڶ������ݺ���ô����
	 * @param event
	 * @see SerialPortSimpleRead 
	 */
	public abstract void serialEvent(SerialPortEvent event);
	
	
}
