cmake_minimum_required(VERSION 3.10)

project(
		embedded LANGUAGES CXX
	   )

project(
		BluezDbusLib LANGUAGES C
	   )

include_directories(
		${CMAKE_SOURCE_DIR}
		${CMAKE_SOURCE_DIR}/includes
		)

link_directories(
		${CMAKE_SOURCE_DIR}/includes
		)

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
		)

add_executable(
		server
		server_test.cpp
		)

target_link_libraries(
		server
		pthread
		)

find_package(PkgConfig REQUIRED)
pkg_check_modules(GLIB REQUIRED IMPORTED_TARGET glib-2.0)
pkg_check_modules(GIO_UNIX REQUIRED IMPORTED_TARGET gio-unix-2.0)
add_custom_command(OUTPUT ${CMAKE_CURRENT_BINARY_DIR}/org-bluez-adaptater1.c
                   COMMAND gdbus-codegen --interface-prefix org.bluez.Adapter1. --generate-c-code ${CMAKE_CURRENT_BINARY_DIR}/org-bluez-adaptater1 ${CMAKE_CURRENT_SOURCE_DIR}/lib/org.bluez.Adapter1.xml
                   DEPENDS ${CMAKE_CURRENT_SOURCE_DIR}/lib/org.bluez.Adapter1.xml
                   COMMENT "Generate D-Bus 'org.bluez.Adapter1.xml'"
                   )

add_library(
		BluezDbusLib uuid.c org-bluez-adaptater1.c org-bluez-device1.c
		)

target_link_libraries(
		BluezDbusLib
		PkgConfig::GLIB
		PkgConfig::GIO_UNIX
		)

add_executable(
		ble_test
		ble.cpp
		)

target_link_libraries(
		ble_test
		bluetooth
		pthread
		PkgConfig::GLIB
		BluezDbusLib
		)
