package javaClientTest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class ClientThread extends Thread {
	Socket socket;
	OutputStream sender;
	 InputStream receiver;
	 ByteBuffer sendByteBuffer;
	 BufferedInputStream bufferInputStream;
	public ClientThread(String serverIP, int port){
		try {
			socket= new Socket(serverIP,port);
			sender = socket.getOutputStream();
			receiver = socket.getInputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendByteBuffer = ByteBuffer.allocate(16);
		sendByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		bufferInputStream = new BufferedInputStream(receiver);

	}
	public void run(){
		Scanner scan = new Scanner(System.in);
		int mode = 2;//NCAHT		
       int from = 0;
       int to = 1;
       System.out.println("INPUT MESSAGE");
       String message = scan.nextLine();      
       scan.close();      
       String recvMesg = "";
       byte[] mesgByte = null;
	
       int msgLength = message.length();
       
       
       sendByteBuffer.putInt(mode);       
       sendByteBuffer.putInt(from);
       sendByteBuffer.putInt(to);
      
       
       try {
   		mesgByte = message.getBytes("UTF-8");
   	} catch (UnsupportedEncodingException e1) {
   		// TODO Auto-generated catch block
   		e1.printStackTrace();
   	}
       msgLength =  mesgByte.length;
       sendByteBuffer.putInt(msgLength);
       try {
    	  
		sender.write(sendByteBuffer.array());	
		sender.flush();
	   sender.write(mesgByte);
	   sender.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
      
        
       mode = 0;      
       byte[] data = new byte[128];
        try {  
        	byte[] modeByte = new byte[4];        	
        	bufferInputStream.read(modeByte, 0, 4);        	
			mode = bytetoInt(modeByte);			
			System.out.println(mode);
			bufferInputStream.read(data, 0, 128);
			System.out.println("RECEIVED");			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
            
       try {
		recvMesg = new String(data,"UTF-8");
		System.out.println(recvMesg);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
          // 기본형 단위로 처리하는 보조스트림
          
         // 소켓으로 부터 받은 데이터를 출력한다.
       //System.out.println("mode :"+mode);
         System.out.println("서버로부터 받은 메세지 : " + recvMesg);
         System.out.println("연결을 종료합니다.");
          
         // 스트림과 소켓을 닫는다.
        
         try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int bytetoInt( byte[] a ) {
		return (a[0] & 0xff) | (a[1] & 0xff) <<8 | (a[2]&0xff)<<16 | (a[3]&0xff)<<24;
}
}
