#include <iostream>
#include <glib-2.0/glib.h>

#define DEFAULT_ADAPTER "hci0"

int main(int, char**);
int adapter_open(const char*, gpointer*);
