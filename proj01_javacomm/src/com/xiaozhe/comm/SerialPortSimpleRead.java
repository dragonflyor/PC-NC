package com.xiaozhe.comm;
/*
 * @(#)SimpleRead.java	1.12 98/06/25 SMI
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license 
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.io.*;
import java.util.*;

import javax.comm.*;

/**
 * * 该类用来接收串口数据，并在新线程中处理
 * * 使用时如果需要对接收的进行数据处理，必须重写serialEvent(SerialPortEvent event)方法
 * @author zhe
 *
 */
public class SerialPortSimpleRead implements Runnable, SerialPortEventListener {

    InputStream inputStream;
    SerialPort serialPort;
    public Thread readThread;
    
    //回传对象
    SerialPortManager serialPortManager;
    
    //串口参数
    int bautrate;
    int SerialPort_DATABIT;
    int SerialPort_STOPBIT;
    int SerialPort_PARITY;
   /**
    * 创建读取串口对象
    * @param serialPort 端口号的字符串，如:"COM5"
    * @param manager 传入管理器
    */
    public SerialPortSimpleRead(SerialPort serialPort,SerialPortManager serialPortManager) {
    	
    	 System.out.println("创建接收对象");
		this.serialPort = serialPort;
		this.serialPortManager = serialPortManager;
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {}
	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {}
        serialPort.notifyOnDataAvailable(true);
//        try {
//            serialPort.setSerialPortParams(115200,
//                SerialPort.DATABITS_8,
//                SerialPort.STOPBITS_1,
//                SerialPort.PARITY_NONE);
//        } catch (UnsupportedCommOperationException e) {}
        readThread = new Thread(this);
        readThread.start();
    }

	public void run() {
        try {
            Thread.sleep(20000);
            System.out.println("串口接收二十秒超时，退出……");
        } catch (InterruptedException e) {}
    }

    public void serialEvent(SerialPortEvent event) {
//        switch(event.getEventType()) {
//        case SerialPortEvent.BI:
//        case SerialPortEvent.OE:
//        case SerialPortEvent.FE:
//        case SerialPortEvent.PE:
//        case SerialPortEvent.CD:
//        case SerialPortEvent.CTS:
//        case SerialPortEvent.DSR:
//        case SerialPortEvent.RI:
//        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
//            break;
//        case SerialPortEvent.DATA_AVAILABLE:
//            byte[] readBuffer = new byte[1024];
//
//            try {
//                while (inputStream.available() > 0) {
//                    int numBytes = inputStream.read(readBuffer);
//                }
//                System.out.print(new String(readBuffer));
//            } catch (IOException e) {}
//            break;
//        }
//        
        serialPortManager.serialEvent(event);
    }
    
   /**
    * 释放掉资源
    */
    public void close(){
      
        if(readThread!=null){
        	readThread.interrupt();;
        	readThread = null;
        }
        if(serialPort!=null){
        	serialPort.close();
        	serialPort=null;
        }
        if(inputStream!=null){
        	try {
				inputStream.close();
				inputStream = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
   
}
