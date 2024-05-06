#include "ble.hpp"

struct {
	char *adapter_name;
	char *mac_address;
	uuid_t uuid;
	char value_data[100];
} m_argument;

GMainLoop *m_main_loop;
GSList *m_adapter_list = NULL;
GMutex mtx_loop;
GCond cond_loop;

int main(int argc, char *argv[]){
	if(argc < 4){
		std::cout << "ble_test mac_address uuid data\n";
		exit(1);
	}

	m_argument.adapter_name = NULL;
	m_argument.mac_address = argv[1];

	if(string_to_uuid(argv[2], strlen(argv[2]) + 1, &m_argument.uuid) < 0){
		std::cout << "ble_test mac_address uuid data\n";
		exit(1);
	}

	struct _execute_task_arg execute_task_arg = {
		.task = ble_task,
		.arg = NULL
	};
	GError *error;

	m_main_loop = g_main_loop_new(NULL, FALSE);
	
	GThread *task_thread = g_thread_try_new("main_task", _execute_task, &execute_task_arg, &error);
	if(task_thread == NULL){
		std::cout << "Could not create task for main loop: " << error->message << '\n';
		g_error_free(error);
		exit(1);
	}

	g_main_loop_run(m_main_loop);
	g_main_loop_unref(m_main_loop);

	g_thread_join(task_thread);
	g_thread_unref(task_thread);

	m_main_loop = NULL;

	return 0;
}

int string_to_uuid(const char *str, size_t n, uuid_t *uuid){
	bt_uuid_t bt_uuid;
	
	int ret = bt_string_to_uuid(&bt_uuid, str);
	if(ret == 0){
		memcpy(&uuid->value, &bt_uuid.value, sizeof(uuid->value));
		if(bt_uuid.type == 16){
			uuid->type = SDP_UUID16;
		}
		else if(bt_uuid.type == 32){
			uuid->type = SDP_UUID32;
		}
		else if(bt_uuid.type == 128){
			uuid->type = SDP_UUID128;
		}
		else{
			uuid->type = SDP_UUID_UNSPEC;
		}
	}

	return ret;
}

void *_execute_task(void *arg){
	struct _execute_task_arg *execute_task_arg = (struct _execute_task_arg*)arg;
	execute_task_arg->task(execute_task_arg->arg);
	g_main_loop_quit(m_main_loop);
	return NULL;
}

void *ble_task(void *arg){
	char *addr = (char*)arg;
	ble_adapter *adapter;
	int ret;

	ret = adapter_open(m_argument.adapter_name, &adapter);
	if(ret){
		std::cout << "Failed to open adapter\n";
		exit(1);
	}

	ret = adapter_scan_enable(
			adapter,
			NULL, 0, 0,
			ble_discovered_device, 10, addr);

	return NULL;
}

int adapter_open(const char *adapter_name, ble_adapter **adapter){
	char object_path[20];
	ble_adapter *new_adapter;
	OrgBluezAdapter1 *adapter_proxy;
	GError *error = NULL;
	
	if(adapter == NULL){
		return BLE_ERROR;
	}

	if(adapter_name == NULL){
		adapter_name = DEFAULT_ADAPTER;
	}

	snprintf(object_path, sizeof(object_path), "/org/bluez/%s", adapter_name);

	adapter_proxy = org_bluez_adapter1_proxy_new_for_bus_sync(
			G_BUS_TYPE_SYSTEM, G_DBUS_PROXY_FLAGS_NONE,
			"org.bluez",
			object_path,
			NULL, &error);
	if(adapter_proxy == NULL){
		int ret = BLE_ERROR;
		if(error){
			std::cout << "Failed to get adapter " << object_path << ": " << error->message << '\n';
			g_error_free(error);
		}
		else{
			std::cout << "Failed to get adapter " << object_path << '\n';
		}
		return ret;
	}

	org_bluez_adapter1_set_powered(adapter_proxy, TRUE);

	new_adapter = (ble_adapter*)calloc(1, sizeof(ble_adapter));
	if(new_adapter == NULL){
		return BLE_ERROR;
	}

	new_adapter->id = strdup(object_path);
	new_adapter->name = strdup(adapter_name);
	new_adapter->reference_counter = 1;
	new_adapter->adapter_proxy = adapter_proxy;

	m_adapter_list = g_slist_append(m_adapter_list, new_adapter);
	*adapter = new_adapter;

	return BLE_SUCCESS;
}

void wait_for_scan(ble_adapter *adapter){
	g_mutex_lock(&mtx_loop);
	if(adapter->ble_scan.is_scanning){
		g_cond_wait(&cond_loop, &mtx_loop);
	}
	g_mutex_unlock(&mtx_loop);
}

int stricmp(char const *a, char const *b){
	for(;; a++, b++){
		int d = tolower((unsigned char)*a) - tolower((unsigned char)*b);
		if(d != 0 || !*a){
			return d;
		}
	}
	return 0;
}

void ble_discovered_device(ble_adapter *adapter, const char *addr, const char *name, void *user_data){
	int ret;

	if(stricmp(addr, m_argument.mac_address) != 0){
		return;
	}

	std::cout << "Found bluetooth device " << m_argument.mac_address << '\n';

	ret = ble_connect(adapter, addr, 0, on_device_connect, NULL);
	if(ret != BLE_SUCCESS){
		std::cout << "Failed to connect to the bluetooth device '" << addr << "'\n";
	}
}

gint _compare_device_with_device_id(gconstpointer a, gconstpointer b){
	const ble_device *device = (ble_device*)a;
	const char *device_id = (char*)b;

	return g_ascii_strcasecmp(device->device_id, device_id);
}

int device_set_state(ble_adapter *adapter, const char *device_id, enum device_state new_state){
	enum device_state old_state;

	GSList *adapter_entry = g_slist_find(m_adapter_list, adapter);
	if(adapter_entry == NULL){
		std::cout << "device_set_state: Adapter not valid\n";
		return BLE_ERROR;
	}

	GSList *item = g_slist_find_custom(adapter->devices, device_id, _compare_device_with_device_id);
	if(item == NULL || item->data == NULL){
		old_state = NOT_FOUND;
	}
	else{
		ble_device *device = (ble_device*)item->data;
		if(device == NULL){
			old_state = NOT_FOUND;
		}
		else{
			old_state = device->state;
		}
	}
	
	if(old_state == NOT_FOUND){
		if(new_state != NOT_FOUND){
			ble_device *device = (ble_device*)calloc(sizeof(ble_device), 1);
			if(device == NULL){
				std::cout << "device_set_state: Cannot allocate device\n";
				return BLE_ERROR;
			}

			device->reference_counter = 1;
			device->adapter = adapter;
			device->device_id = g_strdup(device_id);
			device->state = new_state;

			adapter->devices = g_slist_append(adapter->devices, device);
		}
	}
	else if(new_state == NOT_FOUND){
		item = g_slist_find_custom(adapter->devices, device_id, _compare_device_with_device_id);
		if(item == NULL){
			std::cout << "The device is not present. It is not expected\n";
			return BLE_ERROR;
		}

		ble_device *device = (ble_device*)item->data;

		if(device->state == DISCONNECTED){
			adapter->devices = g_slist_remove(adapter->devices, device);
			device->reference_counter--;
			if(device->reference_counter <= 0){
				free(device);
			}
		}
	}
	else{
		item = g_slist_find_custom(adapter->devices, device_id, _compare_device_with_device_id);
		ble_device *device;
		if(item == NULL){
			device = NULL;
		}
		else{
			device = (ble_device*)item->data;
		}
		device->state = new_state;
	}

	return BLE_SUCCESS;
}

discovered_device_args *_discovered_device_args_allocator(va_list args){
	ble_adapter *adapter = va_arg(args, ble_adapter*);
	OrgBluezDevice1 *device1 = va_arg(args, OrgBluezDevice1*);

	discovered_device_args *thread_args = (discovered_device_args*)calloc(sizeof(discovered_device_args), 1);
	thread_args->adapter = adapter;
	thread_args->mac_address = strdup(org_bluez_device1_get_address(device1));
	const char *device_name = org_bluez_device1_get_name(device1);
	if(device_name != NULL){
		thread_args->name = strdup(device_name);
	}
	else{
		thread_args->name = NULL;
	}

	return thread_args;
}

void *_discovered_device_thread(gpointer data){
	discovered_device_args *args = (discovered_device_args*)data;

	args->adapter->reference_counter++;

	args->adapter->discovered_device_callback.callback.discovered_device(
			args->adapter,
			args->mac_address,
			args->name,
			args->adapter->discovered_device_callback.user_data);

	args->adapter->reference_counter--;

	free(args->mac_address);
	if(args->name != NULL){
		free(args->name);
		args->name = NULL;
	}
	free(args);

	return NULL;
}

void on_discovered_device(ble_adapter *adapter, discovered_device_args* (*args_allocator)(va_list), ...){
	GError *error = NULL;

	va_list args;
	va_start(args, args_allocator);
	discovered_device_args *thread_args = args_allocator(args);
	va_end(args);

	adapter->discovered_device_callback.thread = g_thread_try_new("discovered_device", _discovered_device_thread, thread_args, &error);
	if(adapter->discovered_device_callback.thread == NULL){
		std::cout << "Failed to create thread 'discovered_device': " << error->message << '\n';
		g_error_free(error);
		return;
	}
}

void device_manager_on_added_device1_signal(const char *device1_path, ble_adapter *adapter){
	GError *error = NULL;
	OrgBluezDevice1 *device1 = org_bluez_device1_proxy_new_for_bus_sync(
			G_BUS_TYPE_SYSTEM,
			G_DBUS_PROXY_FLAGS_NONE,
			"org.bluez",
			device1_path,
			NULL,
			&error);
	if (error) {
		std::cout << "Failed to connection to new DBus Bluez Device: " << error->message << '\n';
		g_error_free(error);
	}

	if (device1) {
		const gchar *address = org_bluez_device1_get_address(device1);
		int ret;

		if (address == NULL) {
			g_object_unref(device1);
			return;
		}

		GSList *adapter_entry = g_slist_find(m_adapter_list, adapter);
		if (adapter_entry == NULL) {
			std::cout << "device_manager_on_added_device1_signal: Adapter not valid\n";
			g_object_unref(device1);
			return;
		}

		ret = device_set_state(adapter, device1_path, DISCONNECTED);
		if (ret == BLE_SUCCESS) {
			on_discovered_device(adapter, _discovered_device_args_allocator, adapter, device1);
		}

		g_object_unref(device1);
	}
}

void on_dbus_object_added(GDBusObjectManager *device_manager, GDBusObject *object, ble_adapter *user_data){
	const char *object_path = g_dbus_object_get_object_path(G_DBUS_OBJECT(object));

	GDBusInterface *interface = g_dbus_object_manager_get_interface(device_manager, object_path, "org.bluez.Device1");
	if(!interface){
		std::cout << "DBUS: on_object_added: " << object_path << " (has 'org.bluez.Device1')\n";
		return;
	}

	std::cout << "DBUS: on_object_added: " << object_path << " (has 'org.bluez.Device1')\n";

	device_manager_on_added_device1_signal(object_path, user_data);

	g_object_unref(interface);
}

void on_dbus_object_removed(GDBusObjectManager *device_manager, GDBusObject *object, ble_adapter *adapter){
	const char *object_path = g_dbus_object_get_object_path(G_DBUS_OBJECT(object));

	device_set_state(adapter, object_path, NOT_FOUND);
}

void on_interface_proxy_properties_changed(GDBusObjectManagerClient *device_manager, GDBusObjectProxy *object_proxy, GDBusProxy *interface_proxy, GVariant *changed_properties, const gchar *const *invalidated_properties, ble_adapter *adapter){
	const char *proxy_object_path = g_dbus_proxy_get_object_path(interface_proxy);

	size_t invalidated_properties_count = 0;
	if(invalidated_properties != NULL){
		const gchar *const *invalidated_properties_ptr = invalidated_properties;
		while(*invalidated_properties_ptr != NULL){
			invalidated_properties_count++;
			invalidated_properties_ptr++;
		}
	}

	if(strcmp(g_dbus_proxy_get_interface_name(interface_proxy), "org.bluez.Device1") == 0){
		GError *error = NULL;

		OrgBluezDevice1 *device1 = org_bluez_device1_proxy_new_for_bus_sync(
				G_BUS_TYPE_SYSTEM,
				G_DBUS_PROXY_FLAGS_NONE,
				"org.bluez",
				proxy_object_path, NULL, &error);
		if(error){
			std::cout << "Failed to connection to new DBus Bluez Device: " << error->message << '\n';
			g_error_free(error);
			return;
		}
		else if(device1 == NULL){
			std::cout << "Unexpected NULL device\n";
			return;
		}

		GVariantDict dict;
		g_variant_dict_init(&dict, changed_properties);
		GVariant *has_rssi = g_variant_dict_lookup_value(&dict, "RSSI", NULL);
		GVariant *has_manufacturer_data = g_variant_dict_lookup_value(&dict, "ManufacturerData", NULL);

		enum device_state old_device_state;
		GSList *item = g_slist_find_custom(adapter->devices, proxy_object_path, _compare_device_with_device_id);
		if(item == NULL){
			old_device_state = NOT_FOUND;
		}
		else{
			ble_device *device = (ble_device*)item->data;
			if(device == NULL){
				old_device_state = NOT_FOUND;
			}
			else{
				old_device_state = device->state;
			}
		}

		if(old_device_state == NOT_FOUND){
			if(has_rssi || has_manufacturer_data){
				int ret = device_set_state(adapter, proxy_object_path, DISCONNECTED);
				if(ret == BLE_SUCCESS){
					on_discovered_device(adapter, _discovered_device_args_allocator, adapter, device1);
				}
			}
		}

		g_variant_dict_end(&dict);
		g_object_unref(device1);
	}
}

int _adapter_scan_enable(ble_adapter *adapter, uuid_t **uuid_list, int16_t rssi_threshold, uint32_t enabled_filters, discovered_device_t discovered_device_cb, size_t timeout, void *user_data){
	GDBusObjectManager *device_manager;
	GError *error = NULL;
	GVariantBuilder arg_properties_builder;
	GVariant *rssi_variant = NULL;
	int ret;

	if((adapter == NULL) || (adapter->adapter_proxy == NULL)){
		std::cout << "Could not start BLE scan. No opened bluetooth adapter\n";
		return BLE_ERROR;
	}

	g_variant_builder_init(&arg_properties_builder, G_VARIANT_TYPE("a{sv}"));

	org_bluez_adapter1_call_set_discovery_filter_sync(adapter->adapter_proxy, g_variant_builder_end(&arg_properties_builder), NULL, &error);

	if(rssi_variant){
		g_variant_unref(rssi_variant);
	}

	if(error){
		std::cout << "Failed to set discovery filter: " << error->message << " (" << error->domain << "." << error->code << ")\n";
		g_error_free(error);
		return BLE_ERROR;
	}

	adapter->device_manager = g_dbus_object_manager_client_new_for_bus_sync(
			G_BUS_TYPE_SYSTEM,
			G_DBUS_OBJECT_MANAGER_CLIENT_FLAGS_NONE,
			"org.bluez",
			"/",
			NULL, NULL, NULL, NULL,
			&error);
	device_manager = adapter->device_manager;

	if(device_manager == NULL){
		if(error != NULL){
			g_error_free(error);
		}
		return BLE_ERROR;
	}

	memset(&adapter->ble_scan, 0, sizeof(adapter->ble_scan));
	adapter->ble_scan.enabled_filters = enabled_filters;
	adapter->ble_scan.ble_scan_timeout = timeout;
	adapter->discovered_device_callback.callback.discovered_device = discovered_device_cb;
	adapter->discovered_device_callback.user_data = user_data;

	adapter->ble_scan.added_signal_id = g_signal_connect(G_DBUS_OBJECT_MANAGER(device_manager),
	                    "object-added",
	                    G_CALLBACK(on_dbus_object_added),
	                    adapter);

	adapter->ble_scan.removed_signal_id = g_signal_connect(G_DBUS_OBJECT_MANAGER(device_manager),
	                    "object-removed",
	                    G_CALLBACK(on_dbus_object_removed),
	                    adapter);
	
	adapter->ble_scan.changed_signal_id = g_signal_connect(G_DBUS_OBJECT_MANAGER(device_manager),
					     "interface-proxy-properties-changed",
					     G_CALLBACK(on_interface_proxy_properties_changed),
					     adapter);

	org_bluez_adapter1_call_start_discovery_sync(adapter->adapter_proxy, NULL, &error);
	if(error){
		std::cout << "Failed to start discovery: " << error->message << '\n';
		g_error_free(error);
		return BLE_ERROR;
	}

	std::cout << "Bluetooth scan started\n";
	return BLE_SUCCESS;
}

gboolean _stop_scan_on_timeout(gpointer user_data){
	ble_adapter *adapter = (ble_adapter*)user_data;

	g_mutex_lock(&mtx_loop);

	if(adapter->ble_scan.is_scanning){
		adapter->ble_scan.is_scanning = false;
		g_cond_broadcast(&cond_loop);
	}
	adapter->ble_scan.ble_scan_timeout_id = 0;

	g_mutex_unlock(&mtx_loop);

	return FALSE;
}

int adapter_scan_disable(ble_adapter *adapter){
	GError *error = NULL;
	int ret = BLE_SUCCESS;

	g_mutex_lock(&mtx_loop);

	if(!org_bluez_adapter1_get_discovering(adapter->adapter_proxy)){
		std::cout << "No discovery is progress. We skip discovery stopping 1\n";
		ret = BLE_ERROR;
		goto EXIT;
	}
	else if(!adapter->ble_scan.is_scanning){
		std::cout << "No discovery is progress. We skip discovery stopping 2\n";
		ret = BLE_ERROR;
		goto EXIT;
	}

	org_bluez_adapter1_call_stop_discovery_sync(adapter->adapter_proxy, NULL, &error);
	if(error != NULL){
		if(((error->domain == 238) || (error->domain == 239)) && (error->code == 36)){
			std::cout << "No bluetooth scan has been started\n";
			goto EXIT;
		}
		else{
			std::cout << "Error while stopping BLE discovery: " << error->message << " (" << error->domain << "," << error->code << ")\n";
		}
	}

	if(adapter->ble_scan.is_scanning){
		adapter->ble_scan.is_scanning = false;
		g_cond_broadcast(&cond_loop);
	}

	if(adapter->ble_scan.ble_scan_timeout_id){
		g_source_remove(adapter->ble_scan.ble_scan_timeout_id);
		adapter->ble_scan.ble_scan_timeout_id = 0;
	}

EXIT:
	g_mutex_unlock(&mtx_loop);
	return ret;
}

void *_ble_scan_loop_thread(gpointer user_data){
	ble_adapter *adapter = (ble_adapter*)user_data;

	if(adapter->ble_scan.ble_scan_timeout_id > 0){
		return NULL;
	}

	if(adapter->ble_scan.ble_scan_timeout > 0){
		adapter->ble_scan.ble_scan_timeout_id = g_timeout_add_seconds(adapter->ble_scan.ble_scan_timeout, _stop_scan_on_timeout, adapter);
	}

	wait_for_scan(adapter);

	g_signal_handler_disconnect(G_DBUS_OBJECT_MANAGER(adapter->device_manager), adapter->ble_scan.added_signal_id);
	g_signal_handler_disconnect(G_DBUS_OBJECT_MANAGER(adapter->device_manager), adapter->ble_scan.removed_signal_id);
	g_signal_handler_disconnect(G_DBUS_OBJECT_MANAGER(adapter->device_manager), adapter->ble_scan.changed_signal_id);

	adapter_scan_disable(adapter);

	return NULL;
}

int adapter_scan_enable(ble_adapter *adapter, uuid_t **uuid_list, int16_t rssi_threshold, uint32_t enabled_filters, discovered_device_t discovered_device_cb, size_t timeout, void *user_data){
	GError *error = NULL;
	int ret;

	GSList *adapter_entry = g_slist_find(m_adapter_list, adapter);
	if(adapter_entry == NULL){
		std::cout << "Adapter not valid\n";
		return BLE_ERROR;
	}

	ret = _adapter_scan_enable(adapter, uuid_list, rssi_threshold, enabled_filters, discovered_device_cb, timeout, user_data);
	if(ret != BLE_SUCCESS){
		return BLE_ERROR;
	}

	adapter->ble_scan.is_scanning = true;
	
	adapter->ble_scan.scan_loop_thread = g_thread_try_new("ble_scan", _ble_scan_loop_thread, adapter, &error);
	if(adapter->ble_scan.scan_loop_thread == NULL){
		std::cout << "Failed to create BLE scan thread: " << error->message << '\n';
		g_error_free(error);
		return BLE_ERROR;
	}

	wait_for_scan(adapter);	

	g_thread_unref(adapter->ble_scan.scan_loop_thread);
	adapter->ble_scan.scan_loop_thread = NULL;

	return BLE_SUCCESS;
}

int ble_connect(ble_adapter *adapter, const char *dst, unsigned long options, connect_cb_t connect_cb, void *user_data){
	const char *adapter_name = NULL;
	GError *error = NULL;
	char object_path[200];
	int ret = BLE_SUCCESS;
	char device_address_str[20 + 1];

	if(adapter == NULL){
		int ret_val = adapter_open(NULL, &adapter);
		if(ret_val == BLE_ERROR || adapter == NULL){
			return BLE_ERROR;
		}
	}
	else{
		adapter_name = adapter->name;
	}

	if(connect_cb == NULL){
		std::cout << "ble_connect: Missing connection callback\n";
		return BLE_ERROR;
	}

	if(!adapter_name){
		adapter_name = DEFAULT_ADAPTER;
	}

	strncpy(device_address_str, dst, sizeof(device_address_str) - 1);
	for(size_t i = 0; i < strlen(device_address_str); i++){
		if(device_address_str[i] == ':'){
			device_address_str[i] = '_';
		}
	}

	device_address_str[20] = '\0';

	snprintf(object_path, sizeof(object_path), "/org/bluez/%s/dev_%s", adapter_name, device_address_str);

	ble_device *device;
	GSList *item = g_slist_find_custom(adapter->devices, device_id, _compare_device_with_device_id);
	if(item == NULL){
		return BLE_ERROR;
	}
	else{
		device = (ble_device*)item->data;
		if(device == NULL){
			return BLE_ERROR;
		}
		else if(device->state != DISCONNECTED){
			std::cout << "ble_connect: Cannot connect to " << dst << '\n';
			return BLE_ERROR;
		}
	}
}
