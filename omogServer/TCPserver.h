#define MAXBUF 1024 //버퍼 최대 길이
#define LISTENQ 20 //Listen Queue 최대 크기
#define MAX_THREAD 20//동시 접속 가능한 클라이언트의 수
#define MAX_NAME 8

#define STATUS_WAIT 0
#define STATUS_PLAY 1

#define MODE_LOGIN 1
#define MODE_CHAT 2
#define MODE_MAKEROOM 10
#define MODE_ENTERROOM 20
#define MODE_GAME 100
#define MODE_QUIT -1

typedef struct clientData{
	int socket;
	int status;
	char name[MAX_NAME];
	//char IP[32];
	//int portNum;
}clientData;

typedef struct loginMessage{
	int mode;
	char id[8];
	char pwd[16];
}loginMesg;

typedef struct chatMessage{
	int mode;
	char from[8];
	char to[8];
	char message[128];
}chatMesg;

typedef struct gameMessage{
	int mode;
	char from[8];
	char to[8];
	int x;
	int y;
}gameMesg;

int clientNum; //현재 클라이언트 수
clientData userData[MAX_THREAD];//

void *RunThread(void* arg);//쓰레드 동작함수
void sendMessage(char* mesg, int clntNum);//메시지 전송

void login(int clntNum,loginMesg lgmesg);
void chatting(chatMesg chatmesg);
void makeRoom(void* roomData);	//이 기능은 추후 다시 생각 필요
void enterRoom(void* roomData);//이 기능은 추후 다시 생각 필요
void playGame(gameMesg gamemesg);
void quit(void);

int searchName(char* name);
void error_handling(char* str); //에러 출력
