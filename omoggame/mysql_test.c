#include "/usr/include/mysql/mysql.h"
#include <string.h>
#include <stdio.h>


#define DB_HOST "127.0.0.1"
#define DB_USER "root"
#define DB_PASS "1234"
#define DB_NAME "omoggame"
#define CHOP(x) x[strlen(x) - 1] = ' '

int main(void)
{
    MYSQL *connection=NULL, conn;
    MYSQL_RES   *sql_result;
    MYSQL_ROW   sql_row;
    int       query_stat; 
    char id[20];
    char password[20];
    char name[20];
    int win;
    int lose;
    int draw;
    char query[255];            
    mysql_init(&conn);
    connection = mysql_real_connect(&conn, DB_HOST,
            DB_USER, DB_PASS, DB_NAME, 3306, (char *)NULL, 0);

    if (connection ==NULL){
        fprintf(stderr, "Mysql connection error : %s \n", mysql_error(&conn));
        return 1;
    }

    query_stat = mysql_query(connection, "select * from user");
    if (query_stat != 0)
    {
        fprintf(stderr, "Mysql query error : %s \n", mysql_error(&conn));
        return 1;
    }

    sql_result = mysql_store_result(connection);

    printf("%10s %10s %10s %s %s %s\n", "ID", "Password", "name","win","lose","draw");
    while ( (sql_row = mysql_fetch_row(sql_result)) != NULL )
    {
        printf("%10s %10s %10s %s %s %s\n", sql_row[0], sql_row[1], sql_row[2],sql_row[3], sql_row[4],sql_row[5]);
    }

    mysql_free_result(sql_result);
                                                                                                                       mysql_close(connection);
}
