cmake_minimum_required(VERSION 3.10)

project(
		embedded LANGUAGES CXX
	   )

include_directories(
		${CMAKE_SOURCE_DIR}
		${CMAKE_SOURCE_DIR}/includes
		${CMAKE_SOURCE_DIR}/lib-bluez
		${PROJECT_BINARY_DIR}/lib-bluez
		)

link_directories(
		${CMAKE_SOURCE_DIR}/includes
		${CMAKE_SOURCE_DIR}/lib-bluez
		${PROJECT_BINARY_DIR}/lib-bluez
		)

add_subdirectory(lib-bluez)

add_executable(
		program
		main.cpp
		common.cpp
		socket.cpp
		client_test.cpp
		ble.cpp
		)

target_link_libraries(
		program
		pthread
		bluetooth
		pthread
		BluezDbusLib
		)

add_executable(
		server
		server_test.cpp
		)

target_link_libraries(
		server
		pthread
		)
