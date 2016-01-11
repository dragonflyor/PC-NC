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
 public class SerialPortManager {
	//定义串口收发变量
	private SerialPortSimpleRead portSimpleRead;
	private SerialPortSimpleWrite portSimpleWrite;
	
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;


	static{
		//获取系统COM列表
		 portList = CommPortIdentifier.getPortIdentifiers();
	}
	/**
	 * 获取单实例串口读对象
	 * @return 获取成功返回串口读独对象 ;获取失败返回null
	 */
	public SerialPortSimpleRead getPortSimpleReadInstance() {
		if(portSimpleRead==null && serialPort!= null){
			//开启一个接收线程
			portSimpleRead = new SerialPortSimpleRead(serialPort) {
				
				//串口收到消息的响应函数
				public void serialEvent(SerialPortEvent event) {
					// TODO Auto-generated method stub
					super.serialEvent(event);
					//这里添加要处理的代码
					//System.out.println("SerialPortManager接收数据响应");
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
	
	public SerialPortManager(String comPort) {
		super();
//		//获取系统的所有可用端口的集合
//        portList = CommPortIdentifier.getPortIdentifiers();
		//设置要管理的端口
		setPort(comPort);
	}

	/**
	 * 设置串口通讯的端口号，调用前必须执行 portList =CommPortIdentifier.getPortIdentifiers()获取所有端口
	 * @param comPort 端口号字符串 ，如:"COM5"
	 */
	private void setPort(String comPort){
		
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
							
							   serialPort.setSerialPortParams(115200,
			                            SerialPort.DATABITS_8,
			                            SerialPort.STOPBITS_1,
			                            SerialPort.PARITY_NONE);
							   
						} catch (PortInUseException e) {
							System.out.println("端口被占用");
						} catch (UnsupportedCommOperationException e) {
							System.out.println("串口设置的参数不支持");
						}
                }
            }

        }
	}
	

}
