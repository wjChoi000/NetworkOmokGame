#include "userDAO.h"

int InsertUser(char* id, char* password, char* name, int win, int lose, int draw){
    mysql_init(&conn);
    connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return 0;
    }
    char[255] query;
    sprintf(query,"insert into user values ('%s', '%s', '%s', %d, %d,%d)",id,password,name,win,lose,draw);

    query_stat = mysql_query(connection,query);

    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s", mysql_error(&conn));
        return 0;
    }
    mysql_close(connection);

}

User SearchUserByID(char *id){
    User userInf;

    mysql_init(&conn);
    connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
    if(connection == NULL){
        fprintf(stderr,"insert user, sql connection error: %s\n",mysql_error(&conn));
        return NULL;
    }
  
    char[255] query;
    sprintf(query,"select * from user where id = %s",id);
    query_stat = mysql_query(connection,query);
     
    if(query_stat!=0){
        fprintf(stderr,"insert user, Mysql qyery error: %s", mysql_error(&conn));
        return NULL;
    }

    sql_result = mysql_store_result(connection);
    while ( (sql_row = mysql_fetch_row(sql_result)) != NULL )
    {
        printf("%10s %10s %10s %s %s %s\n", sql_row[0], sql_row[1], sql_row[2],sql_row[3], sql_row[4],sql_row[5]);
	userInf = {sql_row[0], sql_row[1], sql_row[2],sql_row[3], sql_row[4],sql_row[5]};
    }

    mysql_close(connection);
      
    return userInf;
}

int WinUserByID(char* id){
	mysql_init(&conn);
	connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"win user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char[255] query;
        sprintf(query,"update user set win = win+1  where id= %s",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"win user, Mysql qyery error: %s", mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

int DrawUserByID(char* id){
	mysql_init(&conn);
	connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"draw user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char[255] query;
        sprintf(query,"update user set draw = draw+1  where id= %s",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"draw user, Mysql qyery error: %s", mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

int LoseUserByID(char* id){
	mysql_init(&conn);
	connection = mysql_real_connect(&coon,DB_HOST, DB_USER,DB_PASS,DB_NAME,DB_PORT,(char *)NULL,0);
	if(connection == NULL){
	   fprintf(stderr,"lose user, sql connection error: %s\n",mysql_error(&conn));
	   return 0;
	}
	char[255] query;
        sprintf(query,"update user set lose = lose+1  where id= %s",id);	         
	query_stat = mysql_query(connection,query);
		 
	if(query_stat!=0){
	    fprintf(stderr,"lose user, Mysql qyery error: %s", mysql_error(&conn));
	    return 0;
	}
	mysql_close(connection);    
	return 1;

}

int main(){

	InsertUser("c","c","c",0,0,0);
	WinUserByID("c");
	LoseUserByID("c");
	DrawUserByID("c");

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
