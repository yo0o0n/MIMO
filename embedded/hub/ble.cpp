#include "ble.hpp"

int ble(int argc, char *argv[]){
	if(argc < 3){
		std::cout << "./program mac_address uuid\n";
		exit(1);
	}

	char *mac_addr = argv[1];
	uuid_t uuid;
	bt_uuid_t bt_uuid;

	bt_string_to_uuid(&bt_uuid, argv[2]);
	memcpy(&uuid.value, &bt_uuid.value, sizeof(uuid.value));
	if (bt_uuid.type == 16) {
		uuid.type = SDP_UUID16;
	} else if (bt_uuid.type == 32) {
		uuid.type = SDP_UUID32;
	} else if (bt_uuid.type == 128) {
		uuid.type = SDP_UUID128;
	} else {
		uuid.type = SDP_UUID_UNSPEC;
	}

	int dev_id = hci_get_route(NULL);
	if(dev_id < 0){
		fprintf(stderr, "ERROR: Invalid device.\n");
		exit(1);
	}
	int *device_desc = (int*)malloc(sizeof(int));
	*device_desc = hci_open_dev(dev_id);
	if(*device_desc < 0){
		fprintf(stderr, "ERROR: Could not open device.\n");
		exit(1);
	}

	uint16_t interval = htobs(0x12);
	uint16_t window = htobs(0x12);
	uint8_t own_address_type = 0x00;
	uint8_t filter_policy = 0x00;

	if(hci_le_set_scan_parameters(*device_desc, 0x01, interval, window, own_address_type, filter_policy, 10000) < 0){
		fprintf(stderr, "ERROR: Set scan parameters failed (are you root?).\n");
		exit(1);
	}
	if(hci_le_set_scan_enable(*device_desc, 0x01, 1, 10000) < 0){
		fprintf(stderr, "ERROR: Set scan parameters failed (are you root?).\n");
		exit(1);
	}
	
	struct hci_filter old_options;
	socklen_t slen = sizeof(old_options);
	struct hci_filter new_options;
	unsigned char buffer[HCI_MAX_EVENT_SIZE];
	evt_le_meta_event* meta = (evt_le_meta_event*)(buffer + HCI_EVENT_HDR_SIZE + 1);
	le_advertising_info *info;
	char addr[18];
	int len;

	if(getsockopt(*device_desc, SOL_HCI, HCI_FILTER, &old_options, &slen) < 0){
		fprintf(stderr, "ERROR: Could not get socket options.\n");
		exit(1);
	}

	hci_filter_clear(&new_options);
	hci_filter_set_ptype(HCI_EVENT_PKT, &new_options);
	hci_filter_set_event(EVT_LE_META_EVENT, &new_options);

	if(setsockopt(*device_desc, SOL_HCI, HCI_FILTER, &new_options, sizeof(new_options)) < 0){
		fprintf(stderr, "ERROR: Could not set socket options.\n");
		exit(1);
	}

	while(1){
		struct pollfd fds;
		fds.fd = *device_desc;
		fds.events = POLLIN;

		int err = poll(&fds, 1, 10 * 1000);
		if(err <= 0){
			break;
		} else if((fds.revents & POLLIN) == 0){
			continue;
		}

		len = read(*device_desc, buffer, sizeof(buffer));
		if(len < 0){
			fprintf(stderr, "Read error\n");
			break;
		}

		if(meta->subevent != 0x02 || (uint8_t)buffer[0x05] != 0x04)
			continue;

		info = (le_advertising_info*)(meta->data + 1);
		ba2str(&info->bdaddr, addr);

		char *name = parse_name(info->data, info->length);
		if(name != NULL){
			std::cout << "name : " << name << '\n';
		}

		// callback func(adapter, addr, name, user_data);
		if(stricmp(addr, mac_addr) == 0){
			std::cout << "Found bluetooth device " << mac_addr << '\n';

			unsigned long options = 0b101;
			const char *adapter_mac_addr = NULL;
			int bt_io_sec_level;
			int psm, mtu;

			bt_io_sec_level = 1;
			psm = ((options >> 11) && 0x3FF);
			mtu = ((options >> 21) && 0x3FF);

			//initialize_gattlib_connection(adapter_mac_addr, mac_addr, 0x01, bt_io_sec_level, psm, mtu, on_device_connect, NULL);

			/*
			bdaddr_t sba, dba;
			GError *err = NULL;
			if(str2ba(mac_addr, &dba) < 0){
				fprintf(stderr, "Destination address '%s' is not valid.\n", dst);
				break;
			}
			bacpy(&sba, BDADDR_ANY);

//			gattlib_context_t *conn_context = calloc(sizeof(gattlib_context_t), 1);
//			gattlib_connection_t *conn = calloc(sizeof(gattlib_connection_t), 1);
//			conn->context = conn_context;
*/
			

			break;
		}


		if(name){
			free(name);
		}
	}

	setsockopt(*device_desc, SOL_HCI, HCI_FILTER, &old_options, sizeof(old_options));

	return 0;
}

static char* parse_name(uint8_t* data, size_t size) {
	size_t offset = 0;

	while (offset < size) {
		uint8_t field_len = data[0];
		size_t name_len;

		if (field_len == 0 || offset + field_len > size)
			return NULL;

		switch (data[1]) {
		case 0x08:
		case 0x09:
			name_len = field_len - 1;
			if (name_len > size)
				return NULL;

			return strndup((const char*)(data + 2), name_len);
		}

		offset += field_len + 1;
		data += field_len + 1;
	}

	return NULL;
}

static int stricmp(char const *a, char const *b) {
    for (;; a++, b++) {
        int d = tolower((unsigned char)*a) - tolower((unsigned char)*b);
        if (d != 0 || !*a)
            return d;
    }
}
