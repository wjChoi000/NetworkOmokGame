package Soket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

	
	private static final String ipAddress ="127.0.0.1";
	private static final int port = 8090;
	private static final int BUFFERSIZE= 1024;
	
	

	public static void main(String[] args){
		Socket socket = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		
		try{
			socket = new Socket(ipAddress,port);
			bos = new BufferedOutputStream(socket.getOutputStream());
			byte[] c = new byte[BUFFERSIZE];
			c = "abc".getBytes();
			bos.write(c);
			bos.flush();
			
			bis = new BufferedInputStream(socket.getInputStream());
			byte[] s = new byte[BUFFERSIZE];
			System.out.println(s.toString());
			
			bos.close();
			bis.close();
		}catch(ConnectException ce){
			ce.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
