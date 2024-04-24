#include "socket.hpp"

int serv_sock;
std::thread recv_thread, send_thread;

// connect socket to server
void set_socket(){
	// socket create
	serv_sock = socket(PF_INET, SOCK_STREAM, 0);
	if(serv_sock == -1){
		error_handling("socket() error");
	}

	struct sockaddr_in serv_addr;
	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(atoi("65431"));
	serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

	// connect server
	if(connect(serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) == -1){
		error_handling("connect() error");
	}

	std::cout << "server connected\n";

	recv_thread = std::thread(recv_msg);	// recv data from server thread
	send_thread = std::thread(send_msg);	// send data to server thread

	recv_thread.join();
	send_thread.join();

	close(serv_sock);
}

// error message handling
void error_handling(const char *message){
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}

// recieve data from server
void recv_msg(){
	int recv_size = 0;			// total data size from server
	char buf_cur_recv[BUF_SIZE];	// current recv string buffer
	
	while(true){
		memset(buf_cur_recv, 0, sizeof(buf_cur_recv));
		int read_len = read(serv_sock, buf_cur_recv, BUF_SIZE - 1);	// recv cur data
		if(read_len == 0){		// socket disconnect
			close(serv_sock);
			break;
		}

		if(recv_size == 0){		// recieve new data
			// recv_size = atoi(strncpy(recv_size, 3));
			// parsing data size
		}
		// parsing_buf += recv_buf    add new string to total string
		// recv_size -= read_len;

		if(recv_size == 0){		// end recieve data
			std::lock_guard<std::mutex> lk(mtx_read);	// get read mutex

			// parsing string

			strncpy(buf_recv, buf_cur_recv, BUF_SIZE);
			cv_read.notify_all();		// notify wait read mutex
		}	// unlock read mutex
	}
}

// send data to server
void send_msg(){
	std::unique_lock<std::mutex> lk(mtx_write);		// get write mutex

	while(true){
		cv_write.wait(lk);		// unlock write mutex && wait write data
		// get write mutex

		write(serv_sock, buf_send, strlen(buf_send));	// write data to server
	}
}
