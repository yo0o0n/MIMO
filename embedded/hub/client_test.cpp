#include "client_test.hpp"
#include <cstring>
#include <cstdlib>
#include "json.hpp"
#include <string>

using json = nlohmann::json;

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
		std::string json_t = light_response.begin()->second.back();

		json root = json::parse(json_t);
		std::string rgb = root["color"].get<std::string>();
		
		rgb[0] = 17;
		rgb[1] = 255;
		rgb[2] = 255;

		char buf[4096] = {0};
		char buf2[4096] = {0};
		int cnt = 0;
		for(int i = 2; i >= 0; i--){
			int ch[2];
			ch[0] = (rgb[i] & 0xF0) >> 4;
			ch[1] = rgb[i] & 0x0F;
			for(int j = 0; j < 2; j++){
				if(ch[j] < 10){
					buf2[cnt] = ch[j] + '0';
				}
				else{
					buf2[cnt] = ch[j] - 10 + 'A';
				}
				cnt++;
			}
		}
		std::cout << cnt << '\n';

		sprintf(buf, "/home/kyj/hub/gattlib/build/examples/read_write/read_write D4:8a:FC:a7:7B:76 write 6e400002-b5a3-f393-e0a9-e50e24dcca9e 0x%s", buf2);
		std::cout << buf << '\n';
		system(buf);
	}
}
