#define MAXBUF 1024 //버퍼 최대 길이
#define LISTENQ 20 //Listen Queue 최대 크기
#define MAX_THREAD 20//동시 접속 가능한 클라이언트의 수
#define MAX_NAME 8

#define STATUS_WAIT 0
#define STATUS_PLAY 1

#define MODE_LOGIN 1
#define MODE_NCHAT 2
#define MODE_1CHAT 3
#define MODE_MAKEROOM 4
#define MODE_ENTERROOM 5
#define MODE_GAME 6
#define MODE_QUIT 7

typedef struct clientData{
	int socket;
	int status;
	char name[MAX_NAME];
	int win;
	int lose;
	int draw;
}clientData;

int clientNum; //현재 클라이언트 수
clientData userData[MAX_THREAD];//

void *RunThread(void* arg);//쓰레드 동작함수
void sendMessage(int mode, char* mesg, int clntNum);//메시지 전송

void chatting(int mode, int from, int to, char* message);
void makeRoom(void* roomData);	//이 기능은 추후 다시 생각 필요
void enterRoom(void* roomData);//이 기능은 추후 다시 생각 필요
void playGame(int mode, int from, int to, char* message);
void quit(void);

int searchName(char* name);
void error_handling(char* str); //에러 출력
