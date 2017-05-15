#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <pthread.h>
#include "TCPserver.h"

/*mutex : 쓰레드간 동기화를 위한 자원 잠금 */
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER; //mutex 초기화

int main(int argc, char* argv[])
{
	clientNum = 0;
	memset(userData,0,sizeof(clientData)*MAX_THREAD);

	int i;

	struct sockaddr_in servaddr;//서버소켓주소
	struct sockaddr_in clientaddr;//클라이언트 소켓주소
	int listen_sock; //listen 소켓 (= 서버 소켓)
	int client_sock; //클라이언트 소켓
	int client_addrlen = sizeof(clientaddr);

	pthread_t tid;//쓰레드
	int status;//쓰레드 생성 확인

	if(argc != 2){//서버 시작시 포트 넘버필요
		error_handling("INPUT PORTNUMBER");
	}
	
	//listen sock 생성
	if((listen_sock = socket(PF_INET,SOCK_STREAM,0)) < 0){
		error_handling("LISTEN SOCKET FAIL");
	}

	memset(&servaddr,0,sizeof(servaddr)); //서버소켓주소  초기화
	
	/*서버 주소 설정*/
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(atoi(argv[1]));
	
	//bind
	if(bind(listen_sock, (struct sockaddr *)&servaddr, sizeof(servaddr)) <0){
		error_handling("BIND_FAIL");
	}


	if(listen(listen_sock,LISTENQ) < 0)
			error_handling("LISTEN() ERROR");
	
	//서버 동작 구현
	while(1){
		client_sock = accept(listen_sock,(struct sockaddr *)&clientaddr, &client_addrlen);
		if(client_sock < 0)
			error_handling("ACCEPT() ERROR");	

		pthread_mutex_lock(&mutex);//자원 잠금
		userData[clientNum++].socket= client_sock;
		pthread_mutex_unlock(&mutex);//자원 잠금해제
		printf("clientNum : %d\n",clientNum);
		printf("sock : %d\n",client_sock);
		int clntNum = clientNum-1;
		status = pthread_create(&tid,NULL,RunThread,(void*)&clntNum);
		if(status != 0){
			error_handling("THREAD CREAT FAIL");
		}
	}

	return 0;
}

void *RunThread(void* arg)
{
	int num = *((int*) arg);
	int acptSock = userData[num].socket;
	int strlen = 0;

	int mode;	

	fputs("HIHI\n",stdout);
	printf("num : %d\n",num);
	printf("socket : %d\n",acptSock);
	while((strlen = read(acptSock,&mode,sizeof(int))) != 0){
		loginMesg lgmesg;
		chatMesg chatmesg;
		gameMesg gamemesg;
		printf("mode : %d\n",mode);

		switch(mode){
			case MODE_LOGIN:
				read(acptSock,&lgmesg,sizeof(loginMesg));
				printf("LOGIN START\n");
				printf("ID : %s\n",lgmesg.id);
				printf("password : %s\n",lgmesg.pwd);
				login(num,lgmesg);
				printf("LOGIN OK\n");
				for(int i=0;i<clientNum;i++)
					printf("userData[%d]: socket : %d\nstatus : %d\nname: %s\n",i,userData[i].socket,userData[i].status,userData[i].name);
				break;
			case MODE_CHAT:
				read(acptSock,&chatmesg,sizeof(chatMesg));
				chatting(chatmesg);
				break;
			case MODE_GAME:
				read(acptSock,&gamemesg,sizeof(gameMesg));
				playGame(gamemesg);
				userData[num].status = STATUS_PLAY;
			default : 
				break;
		}
	}
	
	pthread_mutex_lock(&mutex);//자원 잠금
	for(int i=0;i<clientNum;i++){
		if(acptSock == userData[i].socket){
			for(;i<clientNum-1;i++)
				userData[i].socket = userData[i+1].socket;
			break;
		}
	}
	clientNum--;
	pthread_mutex_unlock(&mutex);//자원 잠금해제
	
	close(acptSock);
}


void login(int clntNum, loginMesg lgmesg)
{
	
	pthread_mutex_lock(&mutex);//자원 잠금

	char userName[8] = {0}; //실제로는 데이터베이스에서 id를 이용해 사용자 닉네임을 가져와야 한다.
	strcpy(userName,lgmesg.id);
	strcpy(userData[clntNum].name,userName);

	/*
	struct sockaddr_in testaddr;
	int size = sizeof(testaddr);
	
	getpeername(userData[clntNum].socket,(struct sockaddr*)&testaddr,&size);
	strcpy(userData[clntNum].IP,inet_ntoa(testaddr.sin_addr));
	userData[clntNum].portNum = testaddr.sin_port;
	*/
	userData[clntNum].status = STATUS_WAIT;

	pthread_mutex_unlock(&mutex);//자원 잠금해제
}

void chatting(chatMesg chatmesg)
{
	pthread_mutex_lock(&mutex);//자원 잠금
	
	int toNum = -1 ;
	char mesg[128] ={0};
	char from[8] ={0};
	char to[8] ={0};
	strcpy(from,chatmesg.from);
	strcpy(to, chatmesg.to);
	strcpy(mesg, chatmesg.message);
	printf("message : %s\n",mesg);
	printf("TO : %s\n",to);
	if(strcpy(chatmesg.to,"-") == 0){
		for(int i=0;i<clientNum;i++)
			write(userData[i].socket,mesg,strlen(mesg));
	}
	else{
		toNum = searchName(to);
		printf("toNum : %d\n",toNum);
		if(toNum < 0){
			int fromNum = searchName(from);
			sendMessage("NO SUCH USER",fromNum);
		}else
			sendMessage(mesg,toNum);
	}

	pthread_mutex_unlock(&mutex);//자원 잠금해제
}


void sendMessage(char* mesg, int clntNum)
{
	printf("clntNum : %d\n",clntNum);
	printf("mesage : %s\n",mesg);
	write(userData[clntNum].socket,mesg,strlen(mesg));
}


void playGame(gameMesg gamemesg)
{

	pthread_mutex_lock(&mutex);//자원 잠금
	int x=-1;
	int y = -1;
	x = gamemesg.x;
	y = gamemesg.y;
	char message[128]={0};
	sprintf(message,"X : %d, Y : %d\n",x,y);
	sendMessage(message,searchName(gamemesg.to));
	pthread_mutex_unlock(&mutex);//자원 잠금해제
}

void error_handling(char* str)
{
	fputs(str,stderr);
	fputs("\n",stderr);
	exit(1);
}

int searchName(char* searching_name)
{
	int index = 0;
	printf("searching name : %s\n",searching_name);
	for(index = 0; index < clientNum;index++){
		if(strncmp(searching_name,userData[index].name,strlen(userData[index].name)) == 0)
			return index;
	}
	return -1;
}
