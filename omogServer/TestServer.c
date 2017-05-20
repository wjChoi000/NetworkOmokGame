#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <string.h>
#include <stdlib.h>


#define PORT 8090
#define IPADDRESS "127.0.0.1"
#define MAX_QUEUE_LEN 5


int main(int argc, char* argv[])
{
    int server_sockfd, client_sockfd;
    socklen_t server_len, client_len;

    struct sockaddr_in server_address;
    struct sockaddr_in client_address;

    int ret;
    fd_set testfds, readfds;

    server_sockfd = socket(AF_INET, SOCK_STREAM, 0);

    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = htonl(INADDR_ANY);
    server_address.sin_port = htons(PORT);
    server_len = sizeof(server_address);

    ret = bind(server_sockfd, (struct sockaddr *)&server_address, server_len);
    if (ret != 0) {
        perror("Error: fail to bind\n");
        exit(1);
    }

    ret = listen(server_sockfd, MAX_QUEUE_LEN);

    if (ret != 0) {
        perror("Error: fail to listen\n");
        exit(1);
    }


    while(1)
    {
        
        char str[512] = "";

        ret = read(server_sockfd, str, sizeof(str));
        printf("str: %s ",str);
        strcat(str,"\r\n");
        int n = write(server_sockfd, str, sizeof(str));
        printf("str: %s ",str);

    }
    return 0;

}

