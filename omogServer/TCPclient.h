#define MAXBUF 1024
#define MAXNAME 8

#define MODE_LOGIN 1
#define MODE_CHAT 2
#define MODE_MAKEROOM 10
#define MODE_ENTERROOM 20
#define MODE_GAME 100
#define MODE_QUIT -1



char name[MAXNAME];
char message[MAXBUF];

void* send_message(void* arg);
void* recv_message(void* arg);
void error_handling(char* str);

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

