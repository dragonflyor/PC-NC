package com.xiaozhe.myhttputils;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.xiaozhe.bean.Resources;
import com.xiaozhe.ncassistant.MainFrame;
import com.xiaozhe.ncassistant.NCAssistant;

public class MyHttpUtils {


	/**
	 * �������������
	 * @param url ���������Servlet��ַ����http://192.168.42.1:8080/myweb/demo.html
	 * @return
	 */
	 static String getServletData(String urlstr){
//		String msg = "";
//		try
//		{
//			
//			//String url = tf.getText();
//			//����url http://192.168.42.1:8080/myweb/demo.html
//			int index1 = url.indexOf("//")+2;
//			int index2 = url.indexOf("/" ,index1);
//
//			String str = url.substring(index1,index2);
//			String[] arr =str.split(":");
//			String hostIp =arr[0];
//			int port = Integer.parseInt(arr[1]);
//			String filePath = url.substring(index2);
//
//			//������������
//			Socket s= new Socket(hostIp,port);
//
//
//			PrintWriter pr = new PrintWriter(s.getOutputStream(),true);
//			pr.println("GET "+filePath+" HTTP/1.1");
//			pr.println("Accept-Language: zh-CN");
//			pr.println("Accept-Encoding: gzip, deflate");
//			pr.println("Host: "+str);
//			//pr.println("Connection: Keep-Alive");
//			pr.println("Connection: closed");
//			pr.println("");
//			pr.println("");
//			
//			
//			
//			//�������ݣ���ӵ�ta
//			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			String data = null;
//			while((data = br.readLine()) != null)
//			{
//				//System.out.println(data);
//				//ta.append(data+"\r\n");
//				msg = msg +data;
//			}
//
//		}
//		catch (Exception exc)
//		{
//			throw new RuntimeException("ת������ʧ��");
//		}
//		
//		return msg;
		
		
		StringBuffer strbuf = new StringBuffer(); 
		URL url;
		try {
			url = new URL(urlstr);
			
			URLConnection urlcn = url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(urlcn.getInputStream()));

			String data = null;

			while((data=br.readLine()) != null)
			{
					//System.out.println(data);
					//ta.append(data+"\r\n");
				strbuf.append(data);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strbuf.toString();
	}
	
	static public ArrayList<Resources> getFileResources(String urlstr){
		ArrayList<Resources> filelist = new ArrayList<Resources>();
		String msgstr = MyHttpUtils.getServletData(urlstr);
		//ȥ����Ϣͷ
		String sub1 = msgstr.substring(msgstr.indexOf("###")+3);
		System.out.println("ȥ����Ϣͷ��"+sub1);
		
		//ȡ������ÿ���ļ���Ϣ���ַ���
		
		String files[] = sub1.split("###");
		for (String string : files) {
			System.out.println("file:"+string);
			//��ȡ�ļ���Ϣ
			String []fileItems = string.split("#");
			//����bean����
			Resources resource = new Resources();
			/*����ʱ��Э��
			 * 			out.println("###"+resources.getRealname());
						out.println("#"+resources.getUuidname());
						out.println("#"+resources.getDescription());
						out.println("#"+resources.getSavepath());
						out.println("#"+resources.getUploadtime());
			*/
			resource.setRealname(fileItems[0]);
			resource.setUuidname(fileItems[1]);
			resource.setDescription(fileItems[2]);
			resource.setSavepath(fileItems[3]);
			//resource.setUploadtime(new Timestamp(Date.parse(fileItems[4])));
			filelist.add(resource);
		}
		return filelist;
	}
	
	
}
