#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>


#define BUF_SIZE 1024
#define PORT 8090
#define IP "127.0.0.1"

void error_handling(char *s);
int byteToInt(char *s);
int reverseInt(int a);

int main(){
    int serv_sock, clnt_sock;
    char message[BUF_SIZE];
    int str_len, recv_len, recv_cnt;
    struct sockaddr_in serv_adr, clnt_adr;
    socklen_t clnt_adr_sz;

    serv_sock= socket(PF_INET, SOCK_STREAM,0);
    if(serv_sock == -1)
        error_handling("socket error");

    memset(message, 0, BUF_SIZE);

    memset(&serv_adr,0,sizeof(serv_adr));
    serv_adr.sin_family = AF_INET;
    serv_adr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_adr.sin_port = htons(PORT);

    if(bind(serv_sock, (struct sockaddr*)&serv_adr, sizeof(serv_adr)) == -1)
        error_handling("bind error");

    if(listen(serv_sock,5) == -1)
        error_handling("listen() error");
    clnt_adr_sz = sizeof(clnt_adr);
    
    clnt_sock = accept(serv_sock, (struct sockaddr*)&clnt_adr, &clnt_adr_sz);
    printf("connected client %d\n",clnt_sock);


    read(clnt_sock,message,6);
    printf("%s\n",message);
    InsertUser("dtd","1234",message,0,0,0);

    char msg[20] = "뭐";
    write(clnt_sock,msg,20);
/*
    recv_len =0;

    int sizeA=0;;
    read(clnt_sock, &sizeA,sizeof(int));
    
    sizeA = reverseInt(sizeA);
    
    printf("read first size %d(%d)\n",sizeA,sizeof(int));
    
    while( sizeA > recv_len){
         recv_cnt = read(clnt_sock, &message[recv_len], BUF_SIZE-1);
         recv_len += recv_cnt;
         printf("%d, %d\n",recv_cnt, recv_len);
    }
    
    printf("read message %s\n",message);
    InsertUser("aaa","1234",message,0,0,0);

    write(clnt_sock,message,recv_len);
   */
    close(clnt_sock);
    close(serv_sock);

    return 0;


}

void error_handling(char *s){
    fputs(s,stderr);
    fputc('\n',stderr);
    exit(1);
}

int byteToInt(char* a){
    return (a[0] & 0xff)<<24 | (a[1] & 0xff) <<16 | (a[2]&0xff)<<8 || (a[3]&0xff);
}


int reverseInt(int a){
    int temp = 0;
    temp += (a & 0xff000000)>>24;
    temp += (a & 0x00ff0000)>>8;
    temp += (a & 0x0000ff00)<<8;
    temp += (a & 0x000000ff)<<24;
    return temp;
}
