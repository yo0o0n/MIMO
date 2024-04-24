project(
		embedded LANGUAGES CXX
	   )

add_executable(
		ble_hub
		ble_test.cpp
		)

target_link_libraries(
		ble_hub
		bluetooth
		)
