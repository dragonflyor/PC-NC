package com.xiaozhe.ncassistant.uicomponont;

import com.xiaozhe.comm.SerialPortManager;
import com.xiaozhe.comm.SerialPortSimpleRead;
import com.xiaozhe.comm.SerialPortSimpleWrite;
import com.xiaozhe.ncassistant.MyStatusBar;

public class MainFramUiCompononts {

	//����
	public static SerialPortSimpleWrite simpleWrite;
	public static SerialPortSimpleRead simpleRead;
	public static SerialPortManager manager;
	
//	static{
//		//����
//		manager = new SerialPortManager(SerialPortManager.getComStr(),
//				SerialPortManager.getBautrate(),
//				SerialPortManager.getSerialPort_DATABIT(),
//				SerialPortManager.getSerialPort_STOPBIT(),
//				SerialPortManager.getSerialPort_PARITY()) {
//			@Override
//			public void serialEvent(SerialPortEvent event) {
//				//Ӧ�ó��򣬼��������ݶ���
//				System.out.println("Ӧ�ó��򣬼��������ݶ���");
//				//�յ�����
//				onComRead(event);
//			}
//		};
//		
//		//�������ú�֮��ˢ��״̬
//		statusBar.setStatusItems(new String[]{
//			SerialPortManager.getComStr(),
//			SerialPortManager.getBautrate()+"",
//			SerialPortManager.getSerialPort_DATABIT()+"",
//			SerialPortManager.status
//		});
//		
//		//��ȡʵ��
//		simpleWrite = manager.getPortSimpleWriteInstance();
//		simpleRead = manager.getPortSimpleReadInstance();
//		
//		
//		
//	}
//	
//	/**
//	 * ���ڽ�������ʱ��Ӧ
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
//        	System.out.println("���ڴ���״̬���ǣ�"+event.getEventType());
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
//                //��ʾ���ؼ�
//                HomePanelUICompnonts.receiveTextArea.setText(HomePanelUICompnonts.receiveTextArea.getText()+msg);
//                //ˢ��
//                HomePanelUICompnonts.receiveTextArea.invalidate();
//                
//            } catch (IOException e) {}
//            break;
//        }
//        
//	}
	
}
