#include "common.hpp"

std::mutex mtx_write;
std::condition_variable cv_write;
char buf_recv[BUF_SIZE];

std::mutex mtx_read;
std::condition_variable cv_read;
char buf_send[BUF_SIZE];
