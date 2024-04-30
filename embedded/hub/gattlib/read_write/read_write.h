#pragma once

#include <assert.h>
#include <ctype.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#ifdef GATTLIB_LOG_BACKEND_SYSLOG
#include <syslog.h>
#endif

#include "gattlib.h"

#define BLE_SCAN_TIMEOUT   10

static struct {
	char *adapter_name;
	char* mac_address;
	enum { READ, WRITE } operation;
	uuid_t uuid;
	char value_data[100];
} m_argument;

int set_ble(int, char **);
