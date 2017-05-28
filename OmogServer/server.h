#ifndef __SERVER_H__
#define __SERVER_H__


#define MAXBUF 1024 //버퍼 최대 길이
#define LISTENQ 20 //Listen Queue 최대 크기
#define MAX_THREAD 20//동시 접속 가능한 클라이언트의 수
#define MAX_NAME 20

#define STATUS_WAIT 0
#define STATUS_PLAY 1

//wj

#define LOGIN_MOD 1
#define SIGNUP_MOD 2

#define CHATTING_MTM_MOD 21
#define MAKEROOM_MOD 22
#define ENTERROOM_MOD 23
#define UPDATEROOM_MOD 24
#define UPDATEUSERS_MOD 25
#define LOGOUT_MOD 26
#define GETROOM_DOM 27
	
#define READY_GAME_MOD 31
#define WAIT_GAME_MOD 32
#define GO_OUT_ROOM_MOD 33
#define START_GAME_MOD 34
#define DRAW_REQUEST_MOD 35
#define DROP_OUT_GAME_MOD 36
#define PUT_STONE_MOD 37
#define END_GAME_MOD 38

//wj
#define MODE_LOGIN 1
#define MODE_NCHAT 2
#define MODE_1CHAT 3
#define MODE_MAKEROOM 4
#define MODE_ENTERROOM 5
#define MODE_GAME 6
#define MODE_QUIT 7

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <pthread.h>

#include "userDAO.h"

typedef struct clientData{
	int socket;
	int status;
	char id[MAX_NAME];
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


void parseIDAndPW(char *parse, char* id, char* pw);
void parseIDAndPWAndName(char *parse, char* id, char* pw, char* name);
void login(int from,char* message);
void signup(int from,char* message);

#endif
