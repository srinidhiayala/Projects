

import time
import random
import sys
import socket
import select

def ls(port, ts1host, ts1port, ts2host, ts2port):

    # create server socket for client.py	
    try:
        ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as err:
        exit()

    server_binding = ('', port)
    ss.bind(server_binding)
    ss.listen(1)
    
    csockid, addr = ss.accept()
    
  
    arraylen = csockid.recv(200)
    csockid.send("ok")

    temp = int(arraylen)
    
    inputArr = []    
    while(temp > 0):
    	inputArr.append(csockid.recv(200).rstrip('\n'))
    	csockid.send("ok")
    	temp = temp - 1
    	
    # create client socket for ts1.py 
    try:
        cs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as err:
        exit()
        
    # Define the port on which you want to connect to the server    
    server_binding = (ts1host, ts1port)
    cs.connect(server_binding)
    cs.setblocking(0)
    
    try:
        cs2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as err:
        exit()
        
    # Define the port on which you want to connect to the server    
    server_binding = (ts2host, ts2port)
    cs2.connect(server_binding)
    cs2.setblocking(0)
    
    length = str(len(inputArr))
    cs.send(length)
    cs2.send(length)
    
    readable, writable, errors = select.select([cs], [], [], 5)
    
    for sock in readable:
    	if sock == cs:
    	    	temp = cs.recv(200)
    	  
    readable, writable, errors = select.select([cs2], [], [], 5)
    
    for sock in readable:
    	if sock == cs2:
    		temp = cs2.recv(200)
    		   
    for line in inputArr:
    	cs.send(line)
    	cs2.send(line)
 	readable, writable, errors =  select.select([cs], [], [], 5)
 	for sock in readable:
    		if sock == cs:
    			temp = cs.recv(200)
    	
    	readable, writable, errors =  select.select([cs2], [], [], 5)
    	for sock in readable:
    		if sock == cs2:
    			temp = cs2.recv(200)
    			
 	
    length = len(inputArr)
    ind = 0
    dataArr = []
    resArr = []
    while(length > 0):
    	readable, writable, errors = select.select([cs, cs2], [], [], 5)
    	for sock in readable:
    		if sock == cs:
    			dataArr.append(cs.recv(200))
    			ind = ind + 1
    		if sock == cs2:
    			dataArr.append(cs2.recv(200))
    			ind = ind + 1
    	length = length - 1
   
    
    for item in inputArr:
    	temp = item + " - TIMED OUT"
    	resArr.append(temp)
    
    ind = 0
    for item in inputArr:
    	for data in dataArr:
    		dataCop = data.lower().split(' ')
	    	if item.lower() == dataCop[0]:
	    		resArr.pop(ind)
	    		resArr.insert(ind, data)
	ind = ind + 1

    for item in resArr:
    	csockid.send(item)
    	csockid.recv(200)
    	
    ss.close()
    cs.close() 
    cs2.close()
    exit()
 
if __name__ == "__main__":   	
   port = int(sys.argv[1])
   ts1host = sys.argv[2]
   ts1port = int(sys.argv[3])
   ts2host = sys.argv[4]
   ts2port = int(sys.argv[5])
      
   ls(port, ts1host, ts1port, ts2host, ts2port)
