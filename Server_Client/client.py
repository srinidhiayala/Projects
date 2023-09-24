import threading
import time
import random

import socket
FORMAT = "utf-8"
SIZE = 200

def sizeOfFile(cs):
	with open("in-proj.txt") as f:
	    	sizeOfFile = len(f.readlines())
	    	cs.send((str(sizeOfFile)).encode(FORMAT))
	f.close()
	
def storeFileInput(cs):
	print("These are the messages sent by the client:")
	with open("in-proj.txt") as f:
		for line in f:
	    		data = (line.rstrip("\r\n"))
	    		print(data)
    			cs.send(data.encode(FORMAT))
	f.close()

def client():
    try:
        cs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print("[C]: Client socket created")
    except socket.error as err:
        print('socket open error: {} \n'.format(err))
        exit()
        
    # Define the port on which you want to connect to the server
    port = 50008
    localhost_addr = socket.gethostbyname(socket.gethostname())

    # connect to the server on local machine
    server_binding = (localhost_addr, port)
    cs.connect(server_binding)
    
    # Receive data from the server
    data_from_server=cs.recv(200)
    print("[C]: Data received from server: {}".format(data_from_server.decode('utf-8')))
	
    # part 3,4,5
    sizeOfFile(cs)
    storeFileInput(cs)
    
    # recieve all the messages after being updated
    numberOfMessages=cs.recv(200)
    print("These are the updated/changes messages that are being recieved by the client:")
    for i in range(int(numberOfMessages)):
    	changedData = cs.recv(200)
    	if changedData:
	    	print(changedData)
    
    # close the client socket
    cs.close()
    exit()
    
if __name__ == "__main__":
    t2 = threading.Thread(name='client', target=client)
    t2.start()

    time.sleep(5)
    print("Done.")
