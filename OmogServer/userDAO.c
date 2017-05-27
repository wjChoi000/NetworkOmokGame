#include "userDAO.h"

MYSQL *db_connection=NULL, conn;
MYSQL_RES   *sql_result;
MYSQL_ROW   sql_row;
int query_stat;

User* createUser(){
	User* user =(User*)malloc(sizeof(User));
	return user;
}

int InsertUser(char* id, char* password, char* name, int win, int lose, int draw){
    mysql_init(&conn);
    db_connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(db_connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return 0;
    }
    mysql_options(&conn, MYSQL_SET_CHARSET_NAME, "utf8");
    mysql_options(&conn, MYSQL_INIT_COMMAND, "SET NAMES utf8");
    
    char query[255];
    sprintf(query,"insert into user values ('%s', '%s', '%s', %d, %d,%d)",id,password,name,win,lose,draw);

    query_stat = mysql_query(db_connection,query);

    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s\n", mysql_error(&conn));
        return 0;
    }
    mysql_close(db_connection);

}

User* SearchUserByID(char *id){
    mysql_init(&conn);
    db_connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(db_connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return NULL;
    }
  
    char query[255];
    sprintf(query,"select * from user where id = '%s'",id);
    query_stat = mysql_query(db_connection,query);
     
    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s\n", mysql_error(&conn));
	return NULL;
    }
    User* user = createUser();

    sql_result = mysql_store_result(db_connection);
    while ( (sql_row = mysql_fetch_row(sql_result)) != NULL )
    {
      	strcpy(user->id,sql_row[0]);
     	strcpy(user->password,sql_row[1]);
    	strcpy(user->name, sql_row[2]);
	user-> win =atoi(sql_row[3]);
	user-> lose = atoi(sql_row[4]);
	user-> draw = atoi(sql_row[5]);
 	mysql_close(db_connection);
      
        return user;
    }

    mysql_close(db_connection);
      
    return NULL;
}

int WinUserByID(char* id){
	mysql_init(&conn);
	db_connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(db_connection == NULL){
	   fprintf(stderr,"win user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set win = win+1  where id='%s'",id);	         
	query_stat = mysql_query(db_connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"win user, Mysql qyery error(%s): %s\n",query, mysql_error(&conn));
	    return 0;
	}
	mysql_close(db_connection);    
	return 1;

}

int DrawUserByID(char* id){
	mysql_init(&conn);
	db_connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(db_connection == NULL){
	   fprintf(stderr,"draw user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set draw = draw+1  where id='%s'",id);	         
	query_stat = mysql_query(db_connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"draw user, Mysql qyery error: %s\n", mysql_error(&conn));
	    return 0;
	}
	mysql_close(db_connection);    
	return 1;

}

int LoseUserByID(char* id){

	mysql_init(&conn);
	db_connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(db_connection == NULL){
	   fprintf(stderr,"lose user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set lose = lose+1  where id='%s'",id);	         
	query_stat = mysql_query(db_connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"lose user, Mysql qyery error: %s\n", mysql_error(&conn));
	    return 0;
	}
	mysql_close(db_connection);    
	return 1;

}

void printUser(User* user){
	printf("%s %s %s %d %d %d\n",user->id, user->password, user->name, user->win, user->draw, user->lose);
}
/*
int main(){
    InsertUser("ssss","1234","가나",0,0,0);
    // User* u = SearchUserByID("dd");
    //printUser(u);
}
*/
