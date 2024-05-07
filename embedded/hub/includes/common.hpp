#pragma once

#include <mutex>
#include <condition_variable>
#include <map>
#include <vector>
#include <string>
#include <queue>

#define BUF_SIZE 2048	// send/recv buffer max size

// send request enum from device
enum RequestType {
	REQUEST_LIGHT,
	REQUEST_LAMP,
	REQUEST_WINDOW,
	REQUEST_CURTAIN
};

struct Request {
	RequestType request_type;
	int id;
	std::string request_data;
};

extern std::mutex mtx_interrupt;
extern std::condition_variable cv_interrupt;
extern bool is_end;

extern std::mutex mtx_read;
extern std::condition_variable cv_read;

// response json string to device
extern std::map<int, std::vector<std::string>> light_response;
extern std::map<int, std::vector<std::string>> lamp_response;
extern std::map<int, std::vector<std::string>> window_response;
extern std::map<int, std::vector<std::string>> curtain_response;

extern std::mutex mtx_write;
extern std::condition_variable cv_write;

// request data from device
extern std::queue<Request> request_list;
