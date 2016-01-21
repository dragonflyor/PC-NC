package com.littlebigberry.httpfiledownloader;

public class Main implements FileDownloaderDelegate{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
	}
	
	public Main() {
		FileDownloader fileDownloader = new FileDownloader(this);
		fileDownloader.setUrl("http://172.26.164.3:8080/fg757p.exe");
		fileDownloader.setLocalLocation("C:/ppppppppp.exe");
		fileDownloader.beginDownload();
	}

	public void didStartDownload(FileDownloader fileDownloader) {
		System.out.println("Download started");
	}

	public void didProgressDownload(FileDownloader fileDownloader) {
		String kbPerSecond = "Calculating...";
		if (fileDownloader.getKbPerSecond() != null) {
			kbPerSecond = fileDownloader.getKbPerSecond() + " kb/s";
		}
		System.out.println(fileDownloader.getPercentComplete() + " at " + kbPerSecond);
	}

	public void didFinishDownload(FileDownloader fileDownloader) {
		// TODO Auto-generated method stub
		
	}

	public void didFailDownload(FileDownloader fileDownloader) {
		// TODO Auto-generated method stub
		
	}

}
