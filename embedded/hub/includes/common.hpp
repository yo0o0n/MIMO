#include <mutex>
#include <condition_variable>
#include <map>
#include <vector>
#include <string>

#define BUF_SIZE 2048	// send/recv buffer max size

extern std::mutex mtx_write;
extern std::condition_variable cv_write;
extern char buf_recv[BUF_SIZE];

extern std::mutex mtx_read;
extern std::condition_variable cv_read;
extern char buf_send[BUF_SIZE];

extern std::map<int, std::vector<std::string>> light_request;
extern std::map<int, std::vector<std::string>> lamp_request;
extern std::map<int, std::vector<std::string>> window_request;
extern std::map<int, std::vector<std::string>> curtain_request;
