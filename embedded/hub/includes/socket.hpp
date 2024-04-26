#include <iostream>
#include <sys/socket.h>
#include <cstdlib>
#include <unistd.h>
#include <arpa/inet.h>
#include <cstring>
#include <thread>

#include "common.hpp"
#include "json.hpp"

void set_socket();		// connect socket to server
void error_handling(const char *);	// error message handling
void recv_msg();		// recieve data from server
void parsing_json(std::string);
void send_msg();		// send data to server
