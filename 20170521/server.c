#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <pthread.h>
#include "server.h"

/*mutex :	synchronization between Threads */
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER; //mutex Initilaize

int main(int argc, char* argv[])
{
	clientNum = 0;
	memset(userData,0,sizeof(clientData)*MAX_THREAD);

	int i;

	struct sockaddr_in servaddr;//Server socket address
	struct sockaddr_in clientaddr;//Client socket address
	int listen_sock; //listen socket (= server socket)
	int client_sock; //client socket
	int client_addrlen = sizeof(clientaddr);

	pthread_t tid;//Thread
	int status;//Thread make confirmed

	if(argc != 2){//Port Number PLEASE!!
		error_handling("INPUT PORTNUMBER");
	}
	
	//make listen sock
	if((listen_sock = socket(PF_INET,SOCK_STREAM,0)) < 0){
		error_handling("LISTEN SOCKET FAIL");
	}

	memset(&servaddr,0,sizeof(servaddr));	
	
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(atoi(argv[1]));
	
	//bind
	if(bind(listen_sock, (struct sockaddr *)&servaddr, sizeof(servaddr)) <0){
		error_handling("BIND_FAIL");
	}


	if(listen(listen_sock,LISTENQ) < 0)
			error_handling("LISTEN() ERROR");
	
	//server works
	while(1){
		client_sock = accept(listen_sock,(struct sockaddr *)&clientaddr, &client_addrlen);
		if(client_sock < 0)
			error_handling("ACCEPT() ERROR");	

		pthread_mutex_lock(&mutex);//resource lock
		userData[clientNum++].socket= client_sock;
		pthread_mutex_unlock(&mutex);//unlock
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
	int readByte = 0;

	int mode = 0;
	int from = 0;
	int to = 0;
	int msgLength=0;
	char message[128] ="";

	fputs("......THREAD START!!\n\n",stdout);
	while((readByte = read(acptSock,&mode,sizeof(int))) > 0){
		printf("read byte : %d\n",readByte);
		printf("\nMESSAGE RECIVED......! MODE = %d\n",mode);
		read(acptSock,&from,sizeof(int));
		read(acptSock,&to,sizeof(int));
		read(acptSock,&msgLength,sizeof(int));
		printf("from : %d to : %d length : %d\n",from,to,msgLength);
		read(acptSock,message,msgLength);
		printf("message : %s\n",message);
		from = 0; to = 0; msgLength = 0;
		switch(mode){
			case MODE_LOGIN:
				 /*parse message
				  search DB
				  send clinet
				  */
				break;
			case MODE_NCHAT:
				printf("mode : NCHATIN\n");
				chatting(mode,from,to,message);
				break;
			
			case MODE_1CHAT:
				chatting(mode,from,to,message);
				break;

			case MODE_GAME:
				/* Game Message Format : "x\ty"*/
				playGame(mode,from,to,message);
			default : 
				break;
		}
		memset(message,0,128);
		mode = 0;
	}
	
	pthread_mutex_lock(&mutex);
	for(int i=0;i<clientNum;i++){
		if(acptSock == userData[i].socket){
			for(;i<clientNum-1;i++)
				userData[i].socket = userData[i+1].socket;
			break;
		}
	}
	clientNum--;
	pthread_mutex_unlock(&mutex);
	
	close(acptSock);
}

void chatting(int mode, int from, int to, char* message)
{
	pthread_mutex_lock(&mutex);
	
	if(mode == MODE_NCHAT){
		printf("chatting in\n");
		printf("Sending message : %s\n",message);
		for(int i=0;i<clientNum;i++){
			sendMessage(mode,message,i);
		}
	}
	else{
		sendMessage(mode,message,from);
		sendMessage(mode,message,to);
	}

	pthread_mutex_unlock(&mutex);
}


void sendMessage(int mode,char* message, int to)
{
	printf("socket : %d\n",userData[to].socket);
	write(userData[to].socket,&mode,sizeof(int));
	write(userData[to].socket,message,strlen(message));
}


void playGame(int mode,int from, int to, char* message)
{

	pthread_mutex_lock(&mutex);
	sendMessage(mode,message,from);
	sendMessage(mode,message,to);
	pthread_mutex_unlock(&mutex);
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
