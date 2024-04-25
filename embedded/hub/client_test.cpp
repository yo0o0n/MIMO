#include "client_test.hpp"

void client(){
	std::thread thread_write = std::thread(client_write);
	std::thread	thread_read = std::thread(client_read);

	thread_write.join();
	thread_read.join();
}

void client_write(){
	std::string str;
	while(true){
		std::cin >> str;

		std::lock_guard<std::mutex> lk(mtx_write);

		Request new_request;
		new_request.request_type = REQUEST_LIGHT;
		new_request.id = 123;
		new_request.request_data = str;

		request_list.push(new_request);

		cv_write.notify_all();
	}
}

void client_read(){
	std::unique_lock<std::mutex> lk(mtx_read);

	while(true){
		cv_read.wait(lk);

		std::cout << light_response.begin()->second.back() << '\n';
	}
}
