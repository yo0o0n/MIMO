#include "common.hpp"

std::mutex mtx_interrupt;
std::condition_variable cv_interrupt;
bool is_end = false;

std::mutex mtx_read;
std::condition_variable cv_read;

std::map<int, std::vector<std::string>> light_response;
std::map<int, std::vector<std::string>> lamp_response;
std::map<int, std::vector<std::string>> window_response;
std::map<int, std::vector<std::string>> curtain_response;

std::mutex mtx_write;
std::condition_variable cv_write;

std::queue<Request> request_list;
