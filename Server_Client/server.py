import threading
import time
import random

import socket
FORMAT = "utf-8"
SIZE = 200

def allFunc(csockid, lengthOfFile, allMessages):
	messages = []
	for i in range(int(lengthOfFile)):
		data = csockid.recv(SIZE).decode(FORMAT)
		messages.append(data)
	part3Message(messages, allMessages)
	reversedMessage(messages, allMessages)
	upperMessage(messages, allMessages)
	
def reversedMessage(messages, allMessages):
	f = open("outr-proj.txt", "w+")
    	for i in messages:
    		dataReversed = i[::-1]
    		allMessages.append(dataReversed)
    		f.write(dataReversed+"\n")
	f.close()
	
def part3Message(messages, allMessages):
	f = open("out-proj.txt", "w+")
    	for i in messages:
    		allMessages.append(i)
    		f.write(i+"\n")
	f.close()
	
def upperMessage(messages, allMessages):
	f = open("outup-proj.txt", "w+")
    	for i in messages:
    		dataUpper = i.upper()
    		allMessages.append(dataUpper)
    		f.write(dataUpper+"\n")
	f.close()

def server():
    try:
        ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print("[S]: Server socket created")
    except socket.error as err:
        print('socket open error: {}\n'.format(err))
        exit()

    server_binding = ('', 50008)
    ss.bind(server_binding)
    ss.listen(1)
    host = socket.gethostname()
    print("[S]: Server host name is {}".format(host))
    localhost_ip = (socket.gethostbyname(host))
    print("[S]: Server IP address is {}".format(localhost_ip))
    csockid, addr = ss.accept()
    print ("[S]: Got a connection request from a client at {}".format(addr))
    
    # send a intro message to the client.  
    msg = "Welcome to CS 352!"
    csockid.send(msg.encode('utf-8'))
    
    # part 3,4,5
    allMessages = []
    lengthOfFile = csockid.recv(SIZE).decode(FORMAT)
    allFunc(csockid, lengthOfFile, allMessages)
    
    # Send all the changed/updated messages back to the client
    lengthOfAllMessages = str(len(allMessages))
    csockid.send(lengthOfAllMessages.encode(FORMAT))
    for i in allMessages:
    	csockid.send(i.encode(FORMAT))
    	print(i)
    
    # Close the server socket
    ss.close()
    exit()
   
if __name__ == "__main__":
    t1 = threading.Thread(name='server', target=server)
    t1.start()

    time.sleep(random.random() * 5)
