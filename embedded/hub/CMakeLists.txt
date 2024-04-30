cmake_minimum_required(VERSION 3.10)

project (MyLibrary LANGUAGES C)

project(
		embedded LANGUAGES CXX
	   )

include_directories(
		${CMAKE_SOURCE_DIR}
		${CMAKE_SOURCE_DIR}/includes
		${BLUEZ_INCLUDE_DIRS}
		)

link_directories(
		${CMAKE_SOURCE_DIR}/includes
		${BLUEZ_LIBRARY_DIRS}
		)

add_library(
		MyLibrary uuid.c
		)

add_executable(
		program
		main.cpp
		common.cpp
		socket.cpp
		ble.cpp
		client_test.cpp
		)

target_link_libraries(
		program
		pthread
		bluetooth
		MyLibrary
		)

add_executable(
		server
		server_test.cpp
		)

target_link_libraries(
		server
		pthread
		)
