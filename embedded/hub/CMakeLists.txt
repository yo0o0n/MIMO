cmake_minimum_required(VERSION 3.10)

project (MyLibrary LANGUAGES C)

project(
		embedded LANGUAGES CXX
	   )

find_package(PkgConfig REQUIRED)

pkg_search_module(GLIB REQUIRED glib-2.0)

include_directories(
		${CMAKE_SOURCE_DIR}
		${CMAKE_SOURCE_DIR}/includes
		${BLUEZ_INCLUDE_DIRS}
		${GLIB_INCLUDE_DIRS}
		)

link_directories(
		${CMAKE_SOURCE_DIR}/includes
		${BLUEZ_LIBRARY_DIRS}
		)

add_library(
		MyLibrary uuid.c btio.c gattrib.c att.c
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
