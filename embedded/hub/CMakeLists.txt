cmake_minimum_required(VERSION 3.10)

project(
		embedded LANGUAGES CXX
	   )

include_directories(
		${CMAKE_SOURCE_DIR}
		${CMAKE_SOURCE_DIR}/includes
		${CMAKE_SOURCE_DIR}/gattlib/read_write
		)

link_directories(
		${CMAKE_SOURCE_DIR}/includes
		${CMAKE_SOURCE_DIR}/gattlib/read_write
		)

add_subdirectory(gattlib)

add_executable(
		program
		main.cpp
		common.cpp
		socket.cpp
		client_test.cpp
		)

target_link_libraries(
		program
		PUBLIC pthread
		PRIVATE read_write
		)

add_executable(
		server
		server_test.cpp
		)

target_link_libraries(
		server
		pthread
		)
