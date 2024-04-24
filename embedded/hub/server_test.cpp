#include <iostream>
#include <sys/socket.h>
#include <cstdlib>
#include <unistd.h>
#include <arpa/inet.h>
#include <thread>
#include <string>
#include <cstring>

int clnt_sock;

void error_handling(const char *);
void recv_msg();
void send_msg();

int main(){
	int serv_sock = socket(PF_INET, SOCK_STREAM, 0);
	int option = 1;
	setsockopt(serv_sock, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));

	if(serv_sock == -1){
		error_handling("socket() error");
	}

	struct sockaddr_in serv_addr;
	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(atoi("65431"));
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);

	if(bind(serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) == -1){
		error_handling("bind() error");
	}

	if(listen(serv_sock, 5) == -1){
		error_handling("listen() error");
	}

	struct sockaddr_in clnt_addr;
	socklen_t clnt_addr_size = sizeof(clnt_addr);
	clnt_sock = accept(serv_sock, (struct sockaddr *)&clnt_addr, &clnt_addr_size);
	if(clnt_sock == -1){
		error_handling("accept() error");
	}

	std::thread thread_recv = std::thread(recv_msg);
	std::thread thread_send = std::thread(send_msg);

	thread_recv.join();
	thread_send.join();

	close(clnt_sock);
	close(serv_sock);

	return 0;
}

void error_handling(const char *message){
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}

void recv_msg(){
	char buf[2048];
	while(true){
		memset(buf, 0, sizeof(buf));
		int len = read(clnt_sock, buf, 2048);
		if(len == 0){
			close(clnt_sock);
			break;
		}
		std::cout << buf << '\n';
	}
}

void send_msg(){
	while(true){
		std::string str;
		std::getline(std::cin, str);

		write(clnt_sock, str.c_str(), str.length());
	}
}
