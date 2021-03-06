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
			case LOGIN_MOD:
				
				login(from,message);

				break;
			case SIGNUP_MOD:
				
				signup(from,message);
				break;
			/*			
			case MODE_NCHAT:
				printf("mode : NCHATIN\n");
				chatting(mode,from,to,message);
				break;
			
			case MODE_1CHAT:
				chatting(mode,from,to,message);
				break;

			case MODE_GAME:
				playGame(mode,from,to,message);
*/
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


//login & sign up
void parseIDAndPW(char *parse, char* id, char* pw){
	

	char* i = index(parse,'$');
	int length = i-parse;	
	memcpy(id,parse,length);
	char* i2 = index(i,0);
	length = i2- i;
	memcpy(pw,i+1,length);
	
}

void parseIDAndPWAndName(char *parse, char* id, char* pw, char* name){
	char* i = index(parse,'$');
	int length = i-parse;	
	memcpy(id,parse,length);

	char* i2 = index(i+1,'$');
	length = i2- i-1;
	memcpy(pw,i+1,length);

	char* i3 = index(i2+1,0);
	length = i3- i2;
	memcpy(name,i2+1,length-1);
}
void login(int from,char* message)
{
printf("from %d :mode : login\n",from);
	pthread_mutex_lock(&mutex);

	int mod = -1;
	char id[20],pw[20];
        memset(id,0,20);
	memset(pw,0,20);	
	
	parseIDAndPW(message,id,pw);
	User* user = SearchUserByID(id);
	char send[128];
	memset(send,0,128);

	if(user == NULL){
		printf("from %d : no suer\n",from);
		mod =-1;

		memcpy(send, &mod,sizeof(int));	
	}	
	else{
		if(!strcmp(pw,user->password)){
			memcpy( userData[from].id, id,strlen(userData[from].id));			
			
			int win =user->win;
			int lose = user->lose;
			int draw = user->draw;
			char d ='$';
			printf("from %d : success : %s,%s,%d,%d,%d\n",from,user->id,user->name,win,lose,draw);
			mod = from;
			
			//sprintf(send,"%d%c%d%c%d%c%d%c%s%c",mod,d,win,d,lose,d,draw,d,message,d);
						
			memcpy(send, &mod,sizeof(int));
			memcpy(send+4, &win,sizeof(int));
			memcpy(send+8, &lose,sizeof(int));
			memcpy(send+12, &draw,sizeof(int));
			memcpy(send+16, user->name,strlen(user->name));
			
			printf("%d %d %d %s\n",*((int *)(send+sizeof(int)*1)),*((int *)(send+sizeof(int)*2)),*((int *)(send+sizeof(int)*3)),send+sizeof(int)*4);
			
			//printf("%s \n",send);	
		}
		else{
			printf("from %d : worng password(%s/%s)\n",from,pw,user->password);
			mod =-1;
			memcpy(send, &mod,sizeof(int));
		}
	}



	write(userData[from].socket,send,128);		
	pthread_mutex_unlock(&mutex);
}

void signup(int from,char* message)
{
printf("mod : signup\n");
	pthread_mutex_lock(&mutex);
	int mod =SIGNUP_MOD;
	char id[20],pw[20],name[20];
        memset(id,0,20);
	memset(pw,0,20);	
	memset(name,0,20);
	
	parseIDAndPWAndName(message,id,pw,name);

	User* user = SearchUserByID(id);
	
	if(user != NULL){
		printf("signup false \n");
		mod =0;		
	}		
	else{	printf("signup sccess\n");
		InsertUser(id,pw,name,0,0,0);	
	}
	write(userData[from].socket,&mod,sizeof(int));

	pthread_mutex_unlock(&mutex);
}


//gameing
void putStone (int from,int to,char* message){	
	pthread_mutex_lock(&mutex);

	printf("%d mod put Stone \n",from);	
	int mod =PUT_STONE_MOD;
	
	char send[128];
	memset(send,0,128);

	memcpy(send, &mod,sizeof(int));
	memcpy(send+4, &from ,sizeof(int));
	memcpy(send+8, &to,sizeof(int));
	memcpy(send+12, message,strlen(message));

	write(userData[to].socket,send,strlen(message)+sizeof(int)*3);

	pthread_mutex_unlock(&mutex);
}

void endGame (int from,int to,char* message){	
	pthread_mutex_lock(&mutex);

	printf("%d mod put Stone \n",from);	
	int mod =END_GAME_MOD;

	//update DB	
	WinUserByID(userData[from].id);
	LoseUserByID(userData[to].id);	

	char send[128];
	memset(send,0,128);
	memcpy(send, &mod,sizeof(int));
	memcpy(send+4, &from ,sizeof(int));
	memcpy(send+8, &to,sizeof(int));
	memcpy(send+12, message,strlen(message));

	write(userData[to].socket,send,strlen(message)+sizeof(int)*3);

	pthread_mutex_unlock(&mutex);
}



void readyGame(int from,int to){
pthread_mutex_lock(&mutex);

	printf("%d mod read Game \n",from);	
	int mod =READY_GAME_MOD;
	
	char send[12];
	memset(send,0,128);

	memcpy(send, &mod,sizeof(int));
	memcpy(send+4, &from ,sizeof(int));
	memcpy(send+8, &to,sizeof(int));
	
	write(userData[to].socket,send,sizeof(int)*3);

	pthread_mutex_unlock(&mutex);

}

void waitGame(int from,int to){
	pthread_mutex_lock(&mutex);

	printf("%d mod wait game \n",from);	
	int mod =WAIT_GAME_MOD;
	
	char send[12];
	memset(send,0,128);

	memcpy(send, &mod,sizeof(int));
	memcpy(send+4, &from ,sizeof(int));
	memcpy(send+8, &to,sizeof(int));
	
	write(userData[to].socket,send,sizeof(int)*3);

	pthread_mutex_unlock(&mutex);

}

void goOutGame(int from,int to){
	pthread_mutex_lock(&mutex);

	printf("%d mod wait game \n",from);	
	int mod =GO_OUT_ROOM_MOD;
	

	//leave room




	//leave room

	char send[12];
	memset(send,0,128);
	
	memcpy(send, &mod,sizeof(int));
	memcpy(send+4, &from ,sizeof(int));
	memcpy(send+8, &to,sizeof(int));
	
	write(userData[to].socket,send,sizeof(int)*3);

	pthread_mutex_unlock(&mutex);

}
