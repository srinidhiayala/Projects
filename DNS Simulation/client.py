import threading
import time
import random
import sys
import socket

def client(host, port):
    try:
        cs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as err:
        exit()
        
    # Define the port on which you want to connect to the server
    
    server_binding = (host, port)
    cs.connect(server_binding)

    inputArr = []
      
    with open('PROJ2-HNS.txt') as f:
    	for line in f:
    		inputArr.append(line)

    
    x = str(len(inputArr))
    cs.send(x)
    cs.recv(200) 
    
    with open('PROJ2-HNS.txt') as f:
    	for line in f:
    		cs.send(line)
    		cs.recv(200)
    
    f = open("RESOLVED.txt", "w")	    
    size = len(inputArr)
    count = 0
    for count in range(size):
    	f.write(cs.recv(200) + "\n")
    	cs.send("good")

    # close the client sockets
    cs.close()
    
    exit() 

if __name__ == "__main__":
    host = sys.argv[1]
    port = int(sys.argv[2])
    client(host, port)

    print("Done.") 
