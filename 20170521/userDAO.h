#ifndef __USERDAO_H__
#define __USERDAO_H__

#define DB_HOST "127.0.0.1"
#define DB_USER "root"
#define DB_PASS "1234"
#define DB_NAME "omoggame"
#define DB_PORT 3306

#include "/usr/include/mysql/mysql.h"
//#include <mysql.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>




typedef struct userDTO{
    char id[20];
    char password[20];
    char name[20];
    int win;
    int lose;
    int draw;   
}User;


User* createUser();
int InsertUser(char* id, char* password, char* name, int win, int lose, int draw);
User* SearchUserByID(char *id);
int WinUserByID(char* id);
int DrawUserByID(char* id);
int LoseUserByID(char* id);


#endif
