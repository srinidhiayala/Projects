import threading
import time
import random
import sys
import socket

def ts1(ts1port):
    try:
        ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as err:
        exit()

    server_binding = ('', ts1port)
    ss.bind(server_binding)
    ss.listen(1)
    csockid, addr = ss.accept()

    arraylen = csockid.recv(200)
    csockid.send("received")

    temp = int(arraylen)
    
    inputArr = []    
    while(temp > 0): 
    	inputArr.append(csockid.recv(200).rstrip('\n'))
    	csockid.send("recvd")
    	temp = temp - 1
    	
    arrDom = []
    arrIP = []
   
    count = 0
    with open('PROJ2-DNSTS1.txt') as f:
    	for line in f:
    		word = line.split(' ')
    		arrDom.append(word[0])
    		arrIP.append(word[1])
    
    arrDomCopy = []
    for line in arrDom:
    	arrDomCopy.append(line.lower())
    
    		
    count = 0
    for item in inputArr:
    	for i in range(len(arrDom)):
          if item.lower() == arrDomCopy[i]:
          	element = arrDom[i] + " " + arrIP[i] + " A IN"
		csockid.send(element)
		time.sleep(0.5)
   		  
    ss.close()
    exit()
   
 
if __name__ == "__main__":   	
   
   ts1port = int(sys.argv[1])
   ts1(ts1port)
    
   
