package Soket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

import Static.UtilSocketMode;
import model.User;

public class ClientSenderRunable extends UtilSocketMode implements Runnable {
	private Socket socket;
	private OutputStream os;
	ClientSenderRunable(Socket socket){
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
				Thread.sleep(100000);
				sendMessage(LOGIN_MOD);
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
				
				String id = "sw";
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
}