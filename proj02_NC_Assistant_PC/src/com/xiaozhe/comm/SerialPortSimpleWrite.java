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

import sun.rmi.runtime.Log;

public class SerialPortSimpleWrite {
    SerialPort serialPort;
    public static OutputStream outputStream;


    
/**
 * ��������д���ݶ���
 * @param serialPort ���ں��ַ������磺"COM5"
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
 * �������һ���ַ�
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
 * ����λ����Ƭ������һ���ַ�
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
 * �����ַ�����STM32F4
 * @param datastr
 * @param loopdelay ��λ��������ѯ�����ڣ���λms
 */
public void writeStringToSTM32(String datastr,int loopdelay){

	try {
		//��λ�����ڽ��յ��ַ���������"\r\n"��β
		datastr=datastr+"\r\n";
		//����
		//System.out.println("writeStringToSTM32��ȡ���������:"+outputStream);
		outputStream.write(datastr.getBytes());
		//��ʱ
		Thread.sleep(loopdelay);
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("д����:"+"writeStringToSTM32");
	}
}

/**
 * �����ļ���STM32�������ı����ݷ��з���STM32
 * @param file Ҫ�����ı��ļ�
 * @param loopdelay 
 */
public void writeFileToSTM32(final File file , final int loopdelay){
			
		Thread sendfileThread = new Thread(){
			public void run() {
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					
					String line = null;
					while((line = br.readLine())!=null){
						System.out.println("���ͣ�"+line);
						//����һ��
						writeStringToSTM32(line,loopdelay);
					}
					//���ͽ�����־"$$"
					SerialPortSimpleWrite.this.writeString("$$sendover");
					//��ʱ
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
 * �رմ�����Դ
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
