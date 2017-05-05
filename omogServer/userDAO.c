#include "userDAO.h"

User* createUser(){
	User* user =(User*)malloc(sizeof(User));
	return user;
}

int InsertUser(char* id, char* password, char* name, int win, int lose, int draw){
    mysql_init(&conn);
    connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return 0;
    }
    char query[255];
    sprintf(query,"insert into user values ('%s', '%s', '%s', %d, %d,%d)",id,password,name,win,lose,draw);

    query_stat = mysql_query(connection,query);

    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s\n", mysql_error(&conn));
        return 0;
    }
    mysql_close(connection);

}

User* SearchUserByID(char *id){
    mysql_init(&conn);
    connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return NULL;
    }
  
    char query[255];
    sprintf(query,"select * from user where id = '%s'",id);
    query_stat = mysql_query(connection,query);
     
    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s\n", mysql_error(&conn));
	return NULL;
    }
    User* user = createUser();

    sql_result = mysql_store_result(connection);
    while ( (sql_row = mysql_fetch_row(sql_result)) != NULL )
    {
      	strcpy(user->id,sql_row[0]);
     	strcpy(user->password,sql_row[1]);
    	strcpy(user->name, sql_row[2]);
	user-> win =atoi(sql_row[3]);
	user-> lose = atoi(sql_row[4]);
	user-> draw = atoi(sql_row[5]);
 	mysql_close(connection);
      
        return user;
    }

    mysql_close(connection);
      
    return NULL;
}

int WinUserByID(char* id){
	mysql_init(&conn);
	connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"win user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set win = win+1  where id='%s'",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"win user, Mysql qyery error(%s): %s\n",query, mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

int DrawUserByID(char* id){
	mysql_init(&conn);
	connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"draw user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set draw = draw+1  where id='%s'",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"draw user, Mysql qyery error: %s\n", mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

int LoseUserByID(char* id){

	mysql_init(&conn);
	connection = mysql_real_connect(&conn,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"lose user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char query[255];
        sprintf(query,"update user set lose = lose+1  where id='%s'",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"lose user, Mysql qyery error: %s\n", mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

void printUser(User* user){
	printf("%s %s %s %d %d %d\n",user->id, user->password, user->name, user->win, user->draw, user->lose);
}
int main(){

	User* user = SearchUserByID("d");
	if(user !=NULL){	
		printUser(user);
	}
	else
		printf("no search\n");
}


/*
   init

mysql_init(&conn);
connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
if(connection == NULL){
   fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
   return NULL;
}
                 
query_stat = mysql_query(connection,query);
         
if(query_stat!=0){
    fprintf(stderr,"insert user, Mysql qyery error: %s", mysql_error(&conn));
    return NULL;
}
mysql_close(connection);
*/
