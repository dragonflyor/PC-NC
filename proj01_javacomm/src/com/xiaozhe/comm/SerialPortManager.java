package com.xiaozhe.comm;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.UnsupportedCommOperationException;

/**
 * 该类是用来管理串口数据收发的类，通过该类的静态方法可以获取系统可识别的端口
 * 使用时如果需要对接收的数据进行处理，处理代码添加在getPortSimpleReadInstance()中的serialEvent(SerialPortEvent event)方法
 * @author zhe
 *
 */
 public abstract class SerialPortManager {
	//定义串口收发变量
	private SerialPortSimpleRead portSimpleRead;
	private SerialPortSimpleWrite portSimpleWrite;
	
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	
	//通讯参数 默认值如下
    int bautrate = 115200;
    int serialPort_DATABIT = SerialPort.DATABITS_8;
    int serialPort_STOPBIT = SerialPort.STOPBITS_1;
    int serialPort_PARITY = SerialPort.PARITY_NONE;


	static{
		//获取系统COM列表
		 portList = CommPortIdentifier.getPortIdentifiers();
	}
	
	/**
	 * 用指定参数构造
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
	 * 默认参数构造
	 * @param comPort
	 */
	 public SerialPortManager(String comPort) {
		super();
//		//获取系统的所有可用端口的集合
//        portList = CommPortIdentifier.getPortIdentifiers();
		//设置要管理的端口,打开串口
		SerialPortManager.serialPort = openPort(comPort);
	}
	
	/**
	 * 获取单实例串口读对象
	 * @return 获取成功返回串口读独对象 ;获取失败返回null
	 */
	public SerialPortSimpleRead getPortSimpleReadInstance() {
		if(portSimpleRead==null && serialPort!= null){
			//开启一个接收线程
			portSimpleRead = new SerialPortSimpleRead(serialPort,this) {
				
				//串口收到消息的响应函数
				public void serialEvent(SerialPortEvent event) {
					// TODO Auto-generated method stub
					super.serialEvent(event);
					//这里添加要处理的代码
					System.out.println("SerialPortManager接收数据响应");
				}
				
			};
			//portSimpleRead = new SerialPortSimpleRead(serialPort);
			return portSimpleRead;
		}
		return null;
	}
	//获取写对象
	public SerialPortSimpleWrite getPortSimpleWriteInstance() {
		if(portSimpleWrite==null && serialPort!= null){
			//开启一个接收线程
			portSimpleWrite = new SerialPortSimpleWrite(serialPort);
			return portSimpleWrite;
		}
		//System.out.println("getPortSimpleWriteInstance():"+getPortSimpleWriteInstance());
		return null;
	}

	/**
	 * 以字符串集合的方式返回当前系统可识别的串口（COM）号
	 * @return 端口号的字符集合
	 */
	public static ArrayList getAvailablePorts(){

		//重新获取系统COM列表
		portList = CommPortIdentifier.getPortIdentifiers();
		//列表保存成字符串集合
		ArrayList portstrList = new ArrayList();
		 //遍历集合
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            //如果端口是串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	System.out.println("获取到的串口ID："+portId.getName());
            	//添加到集合
            	portstrList.add(portId.getName());
            }
        }
		return portstrList;
	}
	


	/**
	 * 打开串口
	 * @param comPort 端口号字符串 ，如:"COM5"
	 * @return 返回打开的串口
	 */
	private SerialPort openPort(String comPort){
		//重新获取系统COM列表
		portList = CommPortIdentifier.getPortIdentifiers();
		if(portList.hasMoreElements()!=true){
			System.out.println("打开端口失败，检测不到Com");
			return null;
		}
		
		SerialPort serialPort = null;
		  //遍历集合
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	
                // if (portId.getName().equals("COM1")) {
            	System.out.println("获取到的串口ID："+portId.getName());
            	if (portId.getName().equals(comPort)){
                        try {
							serialPort = (SerialPort)
							    portId.open("SerialPortManager", 2000);
							
							   serialPort.setSerialPortParams(this.bautrate,
			                            this.serialPort_DATABIT,
			                            this.serialPort_STOPBIT,
			                           this.serialPort_PARITY);
							   
						} catch (PortInUseException e) {
							System.out.println("端口被占用");
						} catch (UnsupportedCommOperationException e) {
							System.out.println("串口设置的参数不支持");
						}
                }
            }

        }
		return serialPort;
	}
	
	/**
	 * 设置串口参数
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
	 * 处理串口读到数据后怎么处理
	 */
	public abstract void dealRead();
	

}
