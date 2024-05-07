#include <iostream>
#include <thread>
#include <signal.h>

#include "socket.hpp"
#include "client_test.hpp"
#include "ble.hpp"

extern "C" {
//#include "read_write.h"
}

void signalHandler(int signo){
	std::cout << "interrupt\n";
	stop_ble();

	std::unique_lock<std::mutex> lk(mtx_interrupt);
	if(!is_end){
		cv_interrupt.wait(lk);
	}

	quick_exit(0);
}

int main(int argc, char *argv[]){
	signal(SIGINT, signalHandler);

	std::thread thread_socket = std::thread(set_socket);	// socket thread
//	std::thread thread_client = std::thread(client);
	std::thread thread_ble = std::thread(set_ble, argc, argv);

	thread_socket.join();	// wait socket thread
//	thread_client.join();
	thread_ble.join();

	return 0;
}
