#include <iostream>
#include <thread>

#include "socket.hpp"
#include "client_test.hpp"

int main(){
	std::thread thread_socket = std::thread(set_socket);	// socket thread
	std::thread thread_client = std::thread(client);

	thread_socket.join();	// wait socket thread
	thread_client.join();

	return 0;
}
