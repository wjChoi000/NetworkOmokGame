package Soket;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Static.UtilSocketMode;
import model.User;

public class ClientSocket extends UtilSocketMode {
	private static Socket socket = null;
	private OutputStream os;
	private BufferedInputStream bis;
	private byte[] buf = new byte[BUFFERSIZE];
	private int  INTSIZE =4;
	//connect socket when java app started.
	public ClientSocket(){
		try{
			InetSocketAddress isa = new InetSocketAddress(ipAddress,port);	
			socket = new Socket();
			socket.connect(isa);
			System.out.println("connect server");
			
			os = socket.getOutputStream();
			bis = new BufferedInputStream(socket.getInputStream());
			
		}catch(ConnectException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//login and sign up
	public int sendSignupMessage(ClientMsgProtocol msg){
		try{
			ByteBuffer bbuf = msg.getByteBuffer();
			os.write( bbuf.array());
			os.flush();
			System.out.print("write : ");
			msg.print();
			///////////////////////////////////////////////
			int r;
			//ClientMsgProtocol result = new ClientMsgProtocol();
			int read = 0;
			read = bis.read(buf,0,BUFFERSIZE);
			if(read <0){
				System.out.println("read "+read);
				return -1;
			}
			r = ClientMsgProtocol.byteToInt(buf);
			//result.setByteBuffer(buf);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public int sendLoginMessage(ClientMsgProtocol msg, User user){
		try{
			System.out.println();
			ByteBuffer bbuf = msg.getByteBuffer();
			os.write( bbuf.array());
			os.flush();
			System.out.print("login write : ");
			msg.print();
			///////////////////////////////////////////////
			int r;
			//ClientMsgProtocol result = new ClientMsgProtocol();
			
			int read = 0;
			int l =0;
			
			read = bis.read(buf,0,128);
			
			System.out.println("login read: ");
			if(read <0){
				System.out.println("login read error");
				return -1;
			}
			
			byte[] tempArr;
			int length =0;
			tempArr = new byte[4];
			System.arraycopy(buf, length, tempArr, 0, INTSIZE);
			r = ClientMsgProtocol.byteToInt(tempArr);
			
			if(r != -1){
				int win;
				int lose;
				int draw;
				System.out.println("login success : mode("+r+") parse : size ("+read+")");
				length +=INTSIZE;
				
				System.arraycopy(buf, length, tempArr, 0, INTSIZE);
				length +=INTSIZE;
				win = ClientMsgProtocol.byteToInt(tempArr);
				
				System.arraycopy(buf, length, tempArr, 0, INTSIZE);
				length +=INTSIZE;
				lose = ClientMsgProtocol.byteToInt(tempArr);
					
				System.arraycopy(buf, length, tempArr, 0, INTSIZE);
				length +=INTSIZE;
				draw =ClientMsgProtocol.byteToInt(tempArr);
				
				
				tempArr = new byte[112];
				System.arraycopy(buf, length, tempArr, 0, 112);
				
				user.setWin(win);
				user.setLose(lose);
				user.setDraw(draw);
				user.setName(new String(tempArr));
				System.out.println(win+","+lose+","+draw+","+new String(tempArr));
			}
			System.out.println();
			//result.setByteBuffer(buf);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public int sendMessage(ClientMsgProtocol msg){
		try{
			ByteBuffer bbuf = msg.getBasicByteBuffer();
			os.write( bbuf.array());
			os.flush();
			System.out.print("write : ");
			msg.print();
			///////////////////////////////////////////////
			int r;
			//ClientMsgProtocol result = new ClientMsgProtocol();
			int read = 0;
			read = bis.read(buf,0,BUFFERSIZE);
			if(read <0){
				System.out.println("read "+read);
				return -1;
			}
			r = ClientMsgProtocol.byteToInt(buf);
			//result.setByteBuffer(buf);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	public void closeSocket(){
		try{
		os.close();
		bis.close();
		socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
