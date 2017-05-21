package Soket;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//port org.omg.CORBA.portable.InputStream;

import model.User;

public class Test {

	
	private static final String ipAddress ="127.0.0.1";
	private static final int port = 8090;
	private static final int BUFFERSIZE= 1024;
	
	

	public static void main(String[] args){
		Socket socket = null;
		
		try{
			InetSocketAddress isa = new InetSocketAddress(ipAddress,port);	
			socket = new Socket();
			socket.connect(isa);
			
			//write
			OutputStream os = (OutputStream) socket.getOutputStream();
			User user = new User("swelo","1234","원준",0,0,0);
			byte[] b = user.getName().getBytes("UTF-8");
			os.write(user.getName().getBytes("UTF-8"));
			//read
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			byte[] buf = new byte[BUFFERSIZE]; 
			bis.read(buf,0,BUFFERSIZE);
			System.out.println(new String(buf,"UTF-8"));
			
			os.close();
			bis.close();
			socket.close();
		}catch(ConnectException ce){
			ce.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	static public int byteToint(byte[] arr){
		return (arr[0] & 0xff)<<24 | (arr[1] & 0xff)<<16 |
				(arr[2] & 0xff)<<8 | (arr[3] & 0xff);
	}
}

/*
//use byte buffer
String sid ="";
String status = "";
ByteBuffer sendByteBuffer = null;

sid="가나다";
status = "마바사";
byte[] Bsid;
byte[] Bstatus;

Bsid = sid.getBytes();
Bstatus = status.getBytes();
int length = Bsid.length+Bstatus.length;

sendByteBuffer = ByteBuffer.allocate(length+4);
sendByteBuffer.order(ByteOrder.BIG_ENDIAN);

sendByteBuffer.putInt(length);
sendByteBuffer.put(Bsid);
sendByteBuffer.put(Bstatus);

System.out.println(sendByteBuffer.array());

byte[] buff = new byte[length+4];
buff = sendByteBuffer.array();


byte[] tempArr = new byte[4];
System.arraycopy(buff, 0, tempArr, 0, 4);
for(int i =0; i <4; i++){
	System.out.print(tempArr[i]);
}
System.out.println();

length = byteToint(tempArr);

tempArr = new byte[Bsid.length];
System.arraycopy(buff, 4, tempArr, 0, Bsid.length);
sid = new String(tempArr);

tempArr = new byte[Bstatus.length];
System.arraycopy(buff,4+Bsid.length,tempArr,0,Bstatus.length);
status = new String(tempArr);

System.out.println("length : "+length+", sid : "+sid+", status : "+status);

*/


//DataOutputStream dos = new DataOutputStream(os);
//dos.writeUTF("원준");
//dos.flush();
/*ByteBuffer sendByteBuffer = null;

sid="가나다";
status = "마바사";
byte[] Bsid;
byte[] Bstatus;

Bsid = sid.getBytes();
Bstatus = status.getBytes();
int length = Bsid.length+Bstatus.length;

sendByteBuffer = ByteBuffer.allocate(length+8);
sendByteBuffer.order(ByteOrder.BIG_ENDIAN);

sendByteBuffer.putInt(length);
sendByteBuffer.put(Bsid);
sendByteBuffer.put(Bstatus);

System.out.println("send byte : "+sendByteBuffer.array());
os.write(sendByteBuffer.array());
os.flush();*/

// read
//BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());


/*byte[] buff = new byte[BUFFERSIZE];
int read = 0;
			while(true){
	read = bis.read(buff,0,1024);
	if(read <0){
		break;
	}
	int l = 0;
	int lengthA, lengthB;
	byte[] tempArr;

	lengthA = Bsid.length;
	tempArr = new byte[lengthA];
	System.arraycopy(buff, l, tempArr, 0, lengthA);
	sid = new String(tempArr);
	l += lengthA;

	lengthB = Bstatus.length;
	tempArr = new byte[lengthB];
	System.arraycopy(buff,l,tempArr,0,lengthB);
	status = new String(tempArr);
	
	System.out.println("sid("+lengthA+"): "+sid+", status("+lengthB+") : "+ status);
}*/