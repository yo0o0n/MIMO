#include "common.hpp"

std::mutex mtx_write;
std::condition_variable cv_write;
char buf_recv[BUF_SIZE];

std::mutex mtx_read;
std::condition_variable cv_read;
char buf_send[BUF_SIZE];

std::map<int, std::vector<std::string>> light_request;
std::map<int, std::vector<std::string>> lamp_request;
std::map<int, std::vector<std::string>> window_request;
std::map<int, std::vector<std::string>> curtain_request;
