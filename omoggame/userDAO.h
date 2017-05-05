#ifndef USERDAO_H
#define USERDAO_H


#include "/usr/include/mysql/mysql.h"
#include <string.h>
#include <stdio.h>

#define DB_HOST "127.0.0.1"
#define DB_USER "root"
#define DB_PASS "1234"
#define DB_NAME "omoggame"
#define DB_PORT 3306


typedef struct userDTO{
    char[20] id;
    char[20] password;
    char[20] name;
    int win;
    int lose;
    int draw;   
}User;

MYSQL *connection=NULL, conn;
MYSQL_RES   *sql_result;
MYSQL_ROW   sql_row;
int query_stat;

#endif
