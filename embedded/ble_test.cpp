#include <iostream>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <stdlib.h>
#include <unistd.h>
#include <bluetooth/bluetooth.h>
#include <bluetooth/l2cap.h>

int main(){
	int ble_serv_sock = socket(PF_BLUETOOTH, SOCK_SEQPACKET, BTPROTO_L2CAP);

	struct sockaddr_l2 serv_addr;
	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.l2_family = AF_BLUETOOTH;
	serv_addr.l2_cid = htobs(4);
	serv_addr.l2_bdaddr_type = BDADDR_LE_PUBLIC;
	memset(&serv_addr.l2_bdaddr, 0, sizeof(serv_addr.l2_bdaddr));
	
	if(bind(ble_serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0){
		std::cout << "Failed to bind L2CAP socket\n";
	}

	struct bt_security btsec;
	memset(&btsec, 0, sizeof(btsec));
	btsec.level = BT_SECURITY_LOW;
	
	if(setsockopt(ble_serv_sock, SOL_BLUETOOTH, BT_SECURITY, &btsec, sizeof(btsec)) != 0){
		std::cout << "Failed to set L2CAP security level\n";
	}

	if(listen(ble_serv_sock, 10) < 0){
		std::cout << "Listening on socket failed\n";
	}

	std::cout << "started listening on att channel\n";

	struct sockaddr_l2 clnt_addr;
	memset(&clnt_addr, 0, sizeof(clnt_addr));
	socklen_t optlen = sizeof(clnt_addr);
	int clnt_sock = accept(ble_serv_sock, (struct sockaddr *)&clnt_addr, &optlen);
	if(clnt_sock < 0){
		std::cout << "Accept failed\n";
	}

	char ba[18] = {0,};
	ba2str(&clnt_addr.l2_bdaddr, ba);
	std::cout << "connect from " << ba << '\n';

	close(clnt_sock);
	close(ble_serv_sock);

	return 0;
}
