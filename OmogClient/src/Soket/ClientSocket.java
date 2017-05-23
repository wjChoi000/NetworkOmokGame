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
	
	public ClientMsgProtocol sendMessage(ClientMsgProtocol msg){
		try{
			ByteBuffer bbuf = msg.getByteBuffer();
			os.write( bbuf.array());
			os.flush();
			System.out.print("write : ");
			msg.print();
			///////////////////////////////////////////////
			
			ClientMsgProtocol result = new ClientMsgProtocol();
			int read = 0;
			read = bis.read(buf,0,BUFFERSIZE);
			if(read <0){
				System.out.println("read "+read);
				return null;
			}
			result.setByteBuffer(buf);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*//sender thread
	class ClientSender implements Runnable{
		private Socket socket;
		private OutputStream os;
		ClientSender(Socket socket){
			this.socket = socket;
			try{
				this.os = (OutputStream)socket.getOutputStream();
			}catch(Exception e){
				System.out.println("client sender, init error");
				e.printStackTrace();
			}
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				while(true){
					System.out.println("sender socket wait");
					this.wait();
					
				}
			}
			catch(Exception e){
				System.out.println("client sender, error");
				e.printStackTrace();
			}
		}
		
		public void sendMessage(int mod){
			this.notify();
			System.out.println("sender socket notify : "+mod);
			try{
				switch(mod){
				case LOGIN_MOD:
					
					String id = "swelo";
					User user = new User("swelo","1234","원준",0,0,0);
					os.write(user.getName().getBytes("UTF-8"));
					
					System.out.println("sender socket notify : login success");
					break;
				case SIGNUP_MOD:

					break;
				}
			}catch(ConnectException ce){
				ce.printStackTrace();
			}catch(IOException ie){
				ie.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}*/
	
}
