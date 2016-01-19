package com.xiaozhe.comm;
/*
 * @(#)SimpleWrite.java	1.12 98/06/25 SMI
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.comm.SerialPort;

import com.xiaozhe.ncassistant.MainFrame;
import com.xiaozhe.ncassistant.uicomponont.MainFramUiCompononts;

import sun.rmi.runtime.Log;

public class SerialPortSimpleWrite {
    SerialPort serialPort;
    public static OutputStream outputStream;


    
/**
 * 创建串口写数据对象
 * @param serialPort 窗口号字符串，如："COM5"
 */
public SerialPortSimpleWrite(SerialPort serialPort) {
	//super();
	this.serialPort = serialPort;
	System.out.println(serialPort);
	try {
		
		this.outputStream = serialPort.getOutputStream();
	} catch (IOException e) {
		e.printStackTrace();
		
	}
}


/**
 * 串口输出一个字符
 * @param ch
 */
public void write(int ch){
	
	try {
		outputStream.write(ch);
	} catch (IOException e) {
		//e.printStackTrace();
	}
}
/**
 * 给下位机单片机发送一行字符
 * @param datastr
 */
public void writeString(String datastr){

	try {
		
		outputStream.write(datastr.getBytes());
	} catch (IOException e) {
		e.printStackTrace();
	}
}

/**
 * 发送字符串给STM32F4
 * @param datastr
 * @param loopdelay 下位机串口轮询的周期，单位ms
 */
public void writeStringToSTM32(String datastr,int loopdelay){
	if(outputStream != null){
		boolean ok =true;
		try {
			//下位机串口接收的字符串必须以"\r\n"结尾
			datastr=datastr+"\r\n";
			//发送
			//System.out.println("writeStringToSTM32获取串口输出流:"+outputStream);
			outputStream.write(datastr.getBytes());
			//延时
			Thread.sleep(loopdelay);
		} catch (Exception e) {
			e.printStackTrace();
			
			ok = false;
		} finally{
			if(ok == false){
				System.out.println("写错误:"+"writeStringToSTM32");
				MainFramUiCompononts.manager.status = "串口断线";
				MainFrame.statusBar.setText_status(MainFramUiCompononts.manager.status);
			}
		
		}
	}else
	{
		System.out.println("writeStringToSTM32获取串口输出流为空");
	}
}

/**
 * 发送文件给STM32，读出文本内容分行发给STM32
 * @param file 要传送文本文件
 * @param loopdelay 轮询时间
 * @param sendmode 发送模式 0：表示只发送 非0：表示发送后执行
 */
public void writeFileToSTM32(final File file , final int loopdelay, final int sendmode){

		Thread sendfileThread = new Thread(){

			public void run() {
				String  cmdheader;
				if(sendmode == 0){
					cmdheader=">>01";
				}else{
					cmdheader=">>02";
				}
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					
					String line = null;
					//发送文件开始发送的协议码：>>01+filename
					SerialPortSimpleWrite.this.writeString(cmdheader+file.getName()+"\r\n");
					Thread.sleep(loopdelay);
					while((line = br.readLine())!=null){
						System.out.println("发送："+line);
						//发送一行
						writeStringToSTM32(line,loopdelay);
					}
					Thread.sleep(loopdelay);
					//发送结束标志"$$"
					SerialPortSimpleWrite.this.writeString("$$01 \r\n");
					//延时
					Thread.sleep(loopdelay);
					
					br.close();
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			}
		};
		
		sendfileThread.start();
		
}


/**
 * 关闭串口资源
 */
public void close(){
    if(outputStream != null){
    	try {
			outputStream.close();
			outputStream = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

    if(serialPort != null){
    	serialPort.close();

    }
}

    
}
