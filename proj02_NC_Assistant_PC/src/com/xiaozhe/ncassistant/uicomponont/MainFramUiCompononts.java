package com.xiaozhe.ncassistant.uicomponont;

import com.xiaozhe.comm.SerialPortManager;
import com.xiaozhe.comm.SerialPortSimpleRead;
import com.xiaozhe.comm.SerialPortSimpleWrite;
import com.xiaozhe.ncassistant.MyStatusBar;

public class MainFramUiCompononts {

	//串口
	public static SerialPortSimpleWrite simpleWrite;
	public static SerialPortSimpleRead simpleRead;
	public static SerialPortManager manager;
	
//	static{
//		//串口
//		manager = new SerialPortManager(SerialPortManager.getComStr(),
//				SerialPortManager.getBautrate(),
//				SerialPortManager.getSerialPort_DATABIT(),
//				SerialPortManager.getSerialPort_STOPBIT(),
//				SerialPortManager.getSerialPort_PARITY()) {
//			@Override
//			public void serialEvent(SerialPortEvent event) {
//				//应用程序，监听到数据读入
//				System.out.println("应用程序，监听到数据读入");
//				//收到数据
//				onComRead(event);
//			}
//		};
//		
//		//串口设置好之后刷新状态
//		statusBar.setStatusItems(new String[]{
//			SerialPortManager.getComStr(),
//			SerialPortManager.getBautrate()+"",
//			SerialPortManager.getSerialPort_DATABIT()+"",
//			SerialPortManager.status
//		});
//		
//		//获取实例
//		simpleWrite = manager.getPortSimpleWriteInstance();
//		simpleRead = manager.getPortSimpleReadInstance();
//		
//		
//		
//	}
//	
//	/**
//	 * 串口接收数据时响应
//	 * @param event
//	 */
//	public static void onComRead(SerialPortEvent event){
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
//        	System.out.println("串口错误状态号是："+event.getEventType());
//            break;
//        case SerialPortEvent.DATA_AVAILABLE:
//            byte[] readBuffer = new byte[1024];
//
//            try {
//                while (MainFramUiCompononts.simpleRead.inputStream.available() > 0) {
//                    int numBytes = MainFramUiCompononts.simpleRead.inputStream.read(readBuffer);
//                }
//                String msg = new String(readBuffer);
//                System.out.print(msg);
//                
//                //显示到控件
//                HomePanelUICompnonts.receiveTextArea.setText(HomePanelUICompnonts.receiveTextArea.getText()+msg);
//                //刷新
//                HomePanelUICompnonts.receiveTextArea.invalidate();
//                
//            } catch (IOException e) {}
//            break;
//        }
//        
//	}
	
}
