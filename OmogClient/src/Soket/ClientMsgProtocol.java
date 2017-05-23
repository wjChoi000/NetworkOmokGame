package Soket;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ClientMsgProtocol {
	private int mod;
	private int mySid;
	private int youSid;
	private int msgByteSize;
	private byte msg[];
	//private String message =null;
	
	private final int INTSIZE = 4;

	public ClientMsgProtocol() {}
	public ClientMsgProtocol(int mod, int mySid, int youSid, int msgByteSize, byte[] msg){
		this.mod = mod;
		this.mySid = mySid;
		this.youSid = youSid;
		this.msgByteSize = msgByteSize;
		this.msg =msg;
	}
	
	public ByteBuffer getByteBuffer(){
		ByteBuffer result = null;
		try{
		//byte[] msgB= message.getBytes("UTF-8");
		
		//byte[] msgB= message.getBytes();
		int length = INTSIZE*4+msg.length;
		result = ByteBuffer.allocate(length);
		result.putInt(this.mod);
		result.putInt(this.mySid);
		result.putInt(this.youSid);
		result.putInt(msg.length);
		result.put(msg);
		return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void setByteBuffer(byte[] b){
		byte[] tempArr;
		int length =0;
		tempArr = new byte[4];
		System.arraycopy(b, length, tempArr, 0, INTSIZE);
		length +=INTSIZE;
		this.mod = byteToInt(reverseByte(tempArr));
		
		System.arraycopy(b, length, tempArr, 0, INTSIZE);
		length +=INTSIZE;
		this.mySid = byteToInt(reverseByte(tempArr));
		
		System.arraycopy(b, length, tempArr, 0, INTSIZE);
		length +=INTSIZE;
		this.youSid = byteToInt(reverseByte(tempArr));
		
		System.arraycopy(b, length, tempArr, 0, INTSIZE);
		length +=INTSIZE;
		this.msgByteSize = byteToInt(reverseByte(tempArr));
		
		this.msg = new byte[msgByteSize];
		System.arraycopy(b, length, this.msg, 0, msgByteSize);
		
		//message = new String(tempArr);
	}
	private byte[] reverseByte(byte[] b){
		byte[] result = new byte[4];
		result[0] = b[3];
		result[1] = b[2];
		result[2] = b[1];
		result[3] = b[0];
		return result;
	}
	static private int byteToInt(byte[] arr){
		return (arr[0] & 0xff)<<24 | (arr[1] & 0xff)<<16 |
				(arr[2] & 0xff)<<8 | (arr[3] & 0xff);
	}
	
	public void print(){
		System.out.println(mod+", "+mySid+", "+youSid+", "+msgByteSize);
	}
	/*static public void main(String[] args){
		ClientMsgProtocol protocol = new ClientMsgProtocol(1,1,1,3,"a312312bc");
		protocol.print();
		
		ByteBuffer bbfer = protocol.getByteBuffer();
		ClientMsgProtocol newP = new ClientMsgProtocol();
		byte[] b = bbfer.array();
		newP.setByteBuffer(b);
		protocol.print();
	}*/
	public int getMod() {
		return mod;
	}
	public void setMod(int mod) {
		this.mod = mod;
	}
	public int getMySid() {
		return mySid;
	}
	public void setMySid(int mySid) {
		this.mySid = mySid;
	}
	public int getYouSid() {
		return youSid;
	}
	public void setYouSid(int youSid) {
		this.youSid = youSid;
	}
	public int getMsgByteSize() {
		return msgByteSize;
	}
	public void setMsgByteSize(int msgByteSize) {
		this.msgByteSize = msgByteSize;
	}
	public byte[] getMsg() {
		return msg;
	}
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	
	
}
