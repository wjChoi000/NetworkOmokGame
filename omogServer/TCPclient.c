#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <pthread.h>
#include "TCPclient.h"

int main(int argc, char** argv)
{
	int sock;
	struct sockaddr_in serv_addr;
	pthread_t snd_thread, rcv_thread;
	void* thread_result;

	if(argc != 4){
		error_handling("INPUT IP PORT NAME");
	}

	sprintf(name,"[%s]",argv[3]);

	sock = socket(PF_INET,SOCK_STREAM,0);
	if(sock < 0)
		error_handling("SOCKET() ERROR");

	memset(&serv_addr,0,sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = inet_addr(argv[1]);
	serv_addr.sin_port = htons(atoi(argv[2]));

	if(connect(sock,(struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0)
		error_handling("CONNECT() ERROR");

	pthread_create(&snd_thread,NULL,send_message,(void*)&sock);
	pthread_create(&rcv_thread,NULL,recv_message,(void*)&sock);

	pthread_join(snd_thread,&thread_result);
	pthread_join(rcv_thread,&thread_result);

	close(sock);
	return 0;
}

void* send_message(void* arg)
{
	int* sockAddres = (int*) arg;
	int sock = *sockAddres;

	while(1){
		fgets(message,MAXBUF,stdin);
		if(!strcmp(message,"q\n")){
			close(sock);
			exit(0);
		}
		/* 입력 방식 */
		/*MODE FROM TO MESSAGE*/
		/*항상 하기 전에 로그인부터 해야한다. MODE = 1*/
		/*MESSAGE는 실험을 위해 문자열만*/
		
		loginMesg lgmesg;
		chatMesg chatmesg;
		gameMesg gamemesg;

		char from[8]={0};
		char to[8]={0};
		char chatMesg[128] ={0};

		char* ptr = strtok(message," ");
		printf("ptr1 : %s\n",ptr);
		int mode = atoi(ptr);
		lgmesg.mode = mode;
		chatmesg.mode = mode;
		gamemesg.mode = mode;
		
		ptr = strtok(NULL," ");
		printf("ptr2 : %s\n",ptr);
		if(mode == MODE_LOGIN)
			strcpy(lgmesg.id,ptr);
		else if(mode == MODE_CHAT)
			strcpy(chatmesg.from,ptr);
		else if(mode == MODE_GAME)
			strcpy(gamemesg.from,ptr);
		
		ptr = strtok(NULL," ");
		printf("ptr3 : %s\n",ptr);
		if(mode == MODE_LOGIN)
			strcpy(lgmesg.pwd,ptr);
		else if(mode == MODE_CHAT)
			strcpy(chatmesg.to,ptr);
		else if(mode == MODE_GAME)
			strcpy(gamemesg.to,ptr);

		ptr = strtok(NULL," ");
		printf("ptr4 : %s\n",ptr);
		if(mode == MODE_CHAT)
			strcpy(chatmesg.message,ptr);
		else if(mode == MODE_GAME){
			gamemesg.x = atoi(ptr);
			ptr = strtok(NULL," ");
			gamemesg.y = atoi(ptr);
		}

		write(sock,&mode,sizeof(int));
		
		if(mode == MODE_LOGIN)
			write(sock,&lgmesg,sizeof(loginMesg));
		else if(mode == MODE_CHAT)
			write(sock,&chatmesg,sizeof(chatMesg));
		else if(mode == MODE_GAME)
			write(sock,&gamemesg,sizeof(gameMesg));
		
	}
}
void* recv_message(void* arg)
{ 
	int* sockAddres = (int*) arg;
	int sock = *sockAddres;
	char name_message[MAXNAME + MAXBUF];
	int str_len; 
	while(1) {
		str_len = read(sock, name_message, MAXNAME+MAXBUF-1);
		if(str_len == -1)
			return (void*)1;
		name_message[str_len]=0;
		fputs(name_message,stdout);
	} 
}

void error_handling(char* str)
{
	fputs(str,stderr);
	fputs("\n",stderr);
	exit(1);
}
