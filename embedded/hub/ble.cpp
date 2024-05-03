#include "ble.hpp"

GRecMutex g_mutex;
GSList *m_adapter_list;

int main(int argc, char *argv[]){
	char *addr = NULL;
	gpointer adapter;
	int ret;

	ret = adapter_open(NULL, &adapter);
}

int adapter_open(const char *adapter_name, gpointer *adapter){
	char object_path[20];
	gpointer new_adapter;
	OrgBluezAdapter1 *adapter_proxy;
	GError *error = NULL;
	
	if(adapter == NULL){
		return 1;
	}

	if(adapter_name == NULL){
		adapter_name = DEFAULT_ADAPTER;
	}

	snprintf(object_path, sizeof(object_path), "/org/bluez/%s", adapter_name);

	return 0;	
}
