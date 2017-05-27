cc=gcc
server: main.o db.o
    $(cc) -o server main.o db.o
main.o: server.h server.c
    $(cc) -c server.c -lpthread;
db.o: userDAO.h userDAO.c
    $(cc) -c userDAO.c -mysqlclient
