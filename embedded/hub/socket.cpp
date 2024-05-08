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
//	serv_addr.sin_port = htons(atoi("65432"));			// server port
	serv_addr.sin_port = htons(atoi("65431"));			// develop server port
//	serv_addr.sin_addr.s_addr = inet_addr("43.203.239.150");	// server ip
	serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");			// localhost server ip

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
	std::string buf_recv;
	char buf_cur_recv[BUF_SIZE];	// current recv string buffer
	
	while(true){
		memset(buf_cur_recv, 0, sizeof(buf_cur_recv));
		int read_len = read(serv_sock, buf_cur_recv, BUF_SIZE - 1);	// recv cur data
		if(read_len == 0){		// socket disconnect
			std::cout << "server disconnected\n";
			close(serv_sock);
			break;
		}

		if(recv_size == 0){		// recieve new data length
			recv_size = atoi(buf_cur_recv);
			buf_recv.clear();
		}
		else{					// recieve data
			buf_recv += buf_cur_recv;
			recv_size -= read_len;
		}

		if(recv_size == 0){		// end recieve data
			std::lock_guard<std::mutex> lk(mtx_read);	// get read mutex

			parse_json(buf_recv);		// parse json data
			
			cv_read.notify_all();		// notify wait read mutex
		}	// unlock read mutex
	}
}

// parsing recv json string
void parse_json(std::string &str_recv){
	json root = json::parse(str_recv);			// parse string to json
	std::string type = root["type"].get<std::string>();	// get device type

	if(type.compare("light") == 0){				// light
		int light_id = root["lightId"].get<int>();
		light_response.find(light_id)->second.push_back(root["data"].get<json>().dump());
	}
	else if(type.compare("lamp") == 0){			// lamp
		int lamp_id = root["lampId"].get<int>();
		lamp_response.find(lamp_id)->second.push_back(root["data"].get<json>().dump());
	}
	else if(type.compare("window") == 0){		// window
		int window_id = root["windowId"].get<int>();
		window_response.find(window_id)->second.push_back(root["data"].get<json>().dump());
	}
	else if(type.compare("curtain") == 0){		// curtain
		int curtain_id = root["curtainId"].get<int>();
		curtain_response.find(curtain_id)->second.push_back(root["data"].get<json>().dump());
	}
}

// send data to server
void send_msg(){
	std::unique_lock<std::mutex> lk(mtx_write);		// get write mutex
	std::string buf_send;

	write(serv_sock, HUB_SN, strlen(HUB_SN));
	while(true){
		cv_write.wait(lk);		// unlock write mutex && wait write data
		// get write mutex

		buf_send.clear();
		make_json(buf_send);	// making json string

		write(serv_sock, buf_send.c_str(), strlen(buf_send.c_str()));	// write data to server
	}
}

// making send json string
void make_json(std::string &str_send){
	Request cur_request = request_list.front();
	request_list.pop();
	
	json root = json::object();
	switch(cur_request.request_type){
		case REQUEST_LIGHT:			// light
			root["type"] = "light";
			root["lightId"] = cur_request.id;
			root["data"] = json::parse(cur_request.request_data);
			break;
		case REQUEST_LAMP:			// lamp
			root["type"] = "lamp";
			root["lampId"] = cur_request.id;
			root["data"] = json::parse(cur_request.request_data);
			break;
		case REQUEST_WINDOW:		// window
			root["type"] = "window";
			root["windowId"] = cur_request.id;
			root["data"] = json::parse(cur_request.request_data);
			break;
		case REQUEST_CURTAIN:		// curtain
			root["type"] = "curtain";
			root["curtainId"] = cur_request.id;
			root["data"] = json::parse(cur_request.request_data);
			break;
	}

	str_send = root.dump();		// make json to string
}
