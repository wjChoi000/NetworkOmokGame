package javaClientTest;

public class Test {

	public static void main(String[] args) {		
		String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost 본인
       int port = 5000;
		System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP);
        ClientThread ct1 = new ClientThread(serverIP,port);     
        ClientThread ct2 = new ClientThread(serverIP,port);
        ct1.run();
	}
}
