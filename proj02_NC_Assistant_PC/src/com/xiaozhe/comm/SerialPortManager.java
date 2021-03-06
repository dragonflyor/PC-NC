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
	
	static Enumeration<?> portList;
	

	static CommPortIdentifier portId;
	static SerialPort serialPort;
	
	//通讯参数 默认值如下
    static int bautrate = 115200;
    static int serialPort_DATABIT = SerialPort.DATABITS_8;
    static int serialPort_STOPBIT = SerialPort.STOPBITS_1;
    static int serialPort_PARITY = SerialPort.PARITY_NONE;
    static String comStr = "COM5";
  
	//状态
    public static String status = "未连接";
    //COM号
    


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
			portSimpleRead = new SerialPortSimpleRead(serialPort,this);
			//portSimpleRead = new SerialPortSimpleRead(serialPort);
			return portSimpleRead;
		}
		return null;
	}
	//获取写对象
	public SerialPortSimpleWrite getPortSimpleWriteInstance() {
		if(portSimpleWrite==null && serialPort!= null){
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
	public static ArrayList<String> getAvailablePorts(){

		//重新获取系统COM列表
		portList = CommPortIdentifier.getPortIdentifiers();
		//列表保存成字符串集合
		ArrayList<String> portstrList = new ArrayList<String>();
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
		status = "portList"+portList;
		//System.out.println("openPort(String comPort)--portList"+portList);
		if(portList.hasMoreElements()!=true){
			System.out.println("打开端口失败，检测不到Com");
			status = "打开端口失败，检测不到Com";
			return null;
		}
		
		SerialPort serialPort = null;
		  //遍历集合
		//连接成功的标志
		boolean b_cennected = false;
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
							   
							   System.out.println("已连接的串口serialPort："+serialPort);
							   status = "连接成功";
							 //连接成功
							   b_cennected = true;
							   
						} catch (PortInUseException e) {
							System.out.println("端口被占用");
							status = "端口被占用";
						} catch (UnsupportedCommOperationException e) {
							System.out.println("串口设置的参数不支持");
							status = "串口设置的参数不支持";
						}
                }
            }

        }
        
        if(b_cennected == false){
     	   System.out.println("设置的端口不可用");
		   status = "设置的端口不可用";
        }
		return serialPort;
	}
	
	/**
	 * 根据新参数再次打开 串口
	 * @param comPort
	 * @param bautrate
	 * @param serialPort_DATABIT
	 * @param serialPort_STOPBIT
	 * @param serialPort_PARITY
	 * @return
	 */
	public SerialPort reOpenPort(String comPort, int bautrate, int serialPort_DATABIT,
			int serialPort_STOPBIT, int serialPort_PARITY){
		
		//关闭当前
		this.close();

		
		//新参数
		this.bautrate = bautrate;
		this.serialPort_DATABIT = serialPort_DATABIT;
		this.serialPort_STOPBIT = serialPort_STOPBIT;
		this.serialPort_PARITY = serialPort_PARITY;
		SerialPortManager.serialPort = openPort(comPort);
		
		//再次获取新读写对象
		
		//开启一个接收线程
		portSimpleRead = new SerialPortSimpleRead(serialPort,this);
		portSimpleWrite = new SerialPortSimpleWrite(serialPort);
		
		System.out.println("串口配置后重新打开----");
		
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
	
	
	/**
	 * 关闭串口管理的资源
	 */
	public void close(){
		System.out.println("串口管理关闭----");
		if(SerialPortManager.serialPort!=null){
			SerialPortManager.serialPort.close();
			serialPort = null;
		}
		portSimpleRead.close();
		portSimpleWrite.close();
		
	}
	
	/**
	 * 获取串口对象
	 * @return
	 */
	public static SerialPort getSerialPort() {
		return serialPort;
	}

	/**
	 * 获取数据位数
	 * @return
	 */
    public static int getSerialPort_DATABIT() {
		return serialPort_DATABIT;
	}

    /**
     * 获取串口通讯停止位
     * @return
     */
	public static int getSerialPort_STOPBIT() {
		return serialPort_STOPBIT;
	}

	/**
	 * 获取校验模式
	 * @return
	 */
	public static int getSerialPort_PARITY() {
		return serialPort_PARITY;
	}

	/**
	 * 获取波特率
	 * @return
	 */
	public static int getBautrate() {
		return bautrate;
	}

	/**
	 * 获取串口号字符串，如"COM5"
	 * @return
	 */
	public static String getComStr() {
		return comStr;
	}
	
	/**
	 * 设置波特率
	 * @param bautrate
	 */
	public static void setBautrate(int bautrate) {
		SerialPortManager.bautrate = bautrate;
	}


	/**
	 * 设置串口号
	 * @param comStr
	 */
	public static void setComStr(String comStr) {
		SerialPortManager.comStr = comStr;
	}

	/**
	 * 设置串口数据位数
	 * @param serialPort_DATABIT
	 */
	public static void setSerialPort_DATABIT(int serialPort_DATABIT) {
		SerialPortManager.serialPort_DATABIT = serialPort_DATABIT;
	}

	/**
	 * 设置串口停止位
	 * @param serialPort_STOPBIT
	 */
	public static void setSerialPort_STOPBIT(int serialPort_STOPBIT) {
		SerialPortManager.serialPort_STOPBIT = serialPort_STOPBIT;
	}

	/**
	 * 设置串口检验协议
	 * @param serialPort_PARITY
	 */
	public static void setSerialPort_PARITY(int serialPort_PARITY) {
		SerialPortManager.serialPort_PARITY = serialPort_PARITY;
	}



	
	/**
	 * 处理串口读到数据后怎么处理
	 * @param event
	 * @see SerialPortSimpleRead 
	 */
	public abstract void serialEvent(SerialPortEvent event);
	
	
}
