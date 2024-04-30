#include <iostream>
#include <stdlib.h>
#include <unistd.h>
#include <bluetooth/bluetooth.h>
#include <bluetooth/hci.h>
#include <bluetooth/hci_lib.h>
#include <bluetooth/sdp.h>
#include <bluetooth/sdp_lib.h>
#include <uuid.h>
#include <btio.h>
#include <gattrib.h>
#include <poll.h>
#include <ctype.h>
#include <cassert>

int ble(int, char**);
static char* parse_name(uint8_t*, size_t);
static int stricmp(char const*, char const*);
