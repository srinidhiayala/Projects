import socket
import signal
import sys
import random

# Read a command line argument for the port where the server
# must run.
port = 8080
if len(sys.argv) > 1:
    port = int(sys.argv[1])
else:
    print("Using default port 8080")

# Start a listening server socket on the port
sock = socket.socket()
sock.bind(('', port))
sock.listen(2)

### Contents of pages we will serve.
# Login form
login_form = """
   <form action = "http://localhost:%d" method = "post">
   Name: <input type = "text" name = "username">  <br/>
   Password: <input type = "text" name = "password" /> <br/>
   <input type = "submit" value = "Submit" />
   </form>
""" % port
# Default: Login page.
login_page = "<h1>Please login</h1>" + login_form
# Error page for bad credentials
bad_creds_page = "<h1>Bad user/pass! Try again</h1>" + login_form
# Successful logout
logout_page = "<h1>Logged out successfully</h1>" + login_form
# A part of the page that will be displayed after successful
# login or the presentation of a valid cookie
success_page = """
   <h1>Welcome!</h1>
   <form action="http://localhost:%d" method = "post">
   <input type = "hidden" name = "password" value = "new" />
   <input type = "submit" value = "Click here to Change Password" />
   </form>
   <form action="http://localhost:%d" method = "post">
   <input type = "hidden" name = "action" value = "logout" />
   <input type = "submit" value = "Click here to logout" />
   </form>
   <br/><br/>
   <h1>Your secret data is here:</h1>
""" % (port, port)

new_password_page = """
   <form action="http://localhost:%d" method = "post">
   New Password: <input type = "text" name = "NewPassword" /> <br/>
   <input type = "submit" value = "Submit" />
</form>
""" % port

#### Helper functions
# Printing.
def print_value(tag, value):
    print "Here is the", tag
    print "\"\"\""
    print value
    print "\"\"\""
    print

# Signal handler for graceful exit
def sigint_handler(sig, frame):
    print('Finishing up by closing listening socket...')
    sock.close()
    sys.exit(0)
# Register the signal handler
signal.signal(signal.SIGINT, sigint_handler)


# TODO: put your application logic here!
# Read login credentials for all the users
# Read secret data of all the users
def readUserPass():
	with open("passwords.txt") as f:
		userPassDict = {}
		for i in f:
			contents = i.split(" ")
			username = contents[0]
			password = contents[1].strip("\n")
			userPassDict[username] = password
	return userPassDict
		
		
def readAccountSecret():
	with open("secrets.txt") as f:
		userPassDict = {}
		for i in f:
			contents = i.split(" ")
			username = contents[0]
			password = contents[1].strip("\n")
			userPassDict[username] = password
	return userPassDict

def findToken(headers):
	tokenNum = ""
	cookieIndex = headers.index("Cookie: token=")
    	tokenIndex = cookieIndex+14
    	tokenNum = headers[tokenIndex::]
    	tokenNum = tokenNum.split("\r")[0]
    	return tokenNum
def getUserInfo(body):
	if body == "":
		return ""
	else:
		userAndPassInfo = body
	    	listOfInfo = userAndPassInfo.split("=")
	    	listOfInfo[1] = listOfInfo[1].split("&")
	    	usernameInfo = listOfInfo[1][0]
	    	passwordInfo = listOfInfo[2]
	    	return usernameInfo
    	
def getPasswordInfo(body):
	if body == "":
		return ""
	else:
			
		userAndPassInfo = body
	    	listOfInfo = userAndPassInfo.split("=")
	    	listOfInfo[1] = listOfInfo[1].split("&")    	
	    	usernameInfo = listOfInfo[1][0]
	    	passwordInfo = listOfInfo[2]
	    	return passwordInfo

def findKey(cookieDict, username):
	for key in cookieDict:
		if cookieDict[key] == username:
			return key
    	return "No match"


cookieDict = {}
accountAuth = readUserPass()
accountSecrets = readAccountSecret()
usernameInfo = ""
passwordInfo = ""

### Loop to accept incoming HTTP connections and respond.
while True:
    client, addr = sock.accept()
    req = client.recv(1024)
    
    # Let's pick the headers and entity body apart
    header_body = req.split('\r\n\r\n')
    headers = header_body[0]
    body = '' if len(header_body) == 1 else header_body[1]
    print_value('headers', headers)
    print_value('entity body', body)
   
    if "username=" in body and "password=" in body:
	    usernameInfo = getUserInfo(body)
	    passwordInfo = getPasswordInfo(body)
    
    html_content_to_send = login_page
    # TODO: Put your application logic here!
    # Parse headers and body and perform various actions
    cookieHeader = ""
    tokenNum = ""
    if "Cookie: token=" in headers and headers[0:4] == "POST":
    	tokenNum = findToken(headers)
    	
    if usernameInfo == "" and passwordInfo == "":
	html_content_to_send = login_page
   #el
    elif headers[0:4] == "POST" and "action=logout" in body:
    	if "Cookie:" in headers:
	    	cookieHeader = "Set-Cookie: token=; expires=Thu, 01 Jan 1970 00:00:00 GMT\r\n"
		tokenNum = findToken(headers)
		cookieDict.pop(tokenNum)
	    	html_content_to_send = logout_page
	elif "Cookie:" not in headers:
		cookieHeader = "Set-Cookie: token=; expires=Thu, 01 Jan 1970 00:00:00 GMT\r\n"
		tokenNum = findKey(cookieDict, usernameInfo)
		cookieDict.pop(tokenNum)
	    	html_content_to_send = logout_page
    elif headers[0:4] == "POST" and body == "password=new":
    	html_content_to_send = new_password_page
    elif headers[0:4] == "POST" and "NewPassword" in body:
    	tokenNum = findToken(headers)
    	accountAuth[cookieDict[tokenNum]] = body.split("=")[1]
    	secret = accountSecrets[usernameInfo]
	html_content_to_send = success_page + secret
    elif "Cookie:" in headers and headers[0:4] == "POST" and tokenNum in cookieDict.keys(): 
    		username = cookieDict[tokenNum]
    		secret = accountSecrets[username]
    		html_content_to_send = success_page + secret
    elif headers[0:4] == "POST":
    	usernameInfo = getUserInfo(body)
    	passwordInfo = getPasswordInfo(body)
	if usernameInfo in cookieDict.values():
		html_content_to_send = bad_creds_page
	elif usernameInfo in accountAuth.keys() and accountAuth[usernameInfo] == passwordInfo:
	    	secret = accountSecrets[usernameInfo]
	   	html_content_to_send = success_page + secret
	   	if usernameInfo not in cookieDict.values():
		   	randCookieVal = random.getrandbits(64)
		   	cookieHeader = 'Set-Cookie: token=' + str(randCookieVal) + '\r\n'
		   	cookieVal = str(randCookieVal)
		   	cookieDict[cookieVal] = usernameInfo
	elif usernameInfo == "" and passwordInfo == "":
		html_content_to_send = login_page
	elif usernameInfo == "" or passwordInfo == "":
	   	cookieHeader = ""
	   	html_content_to_send = bad_creds_page
	elif usernameInfo not in accountAuth.keys() or accountAuth[usernameInfo] != passwordInfo or passwordInfo not in accountAuth.values():
	   	cookieHeader = ""
	   	html_content_to_send = bad_creds_page
	
	
	
    # You need to set the variables:
    # (1) `html_content_to_send` => add the HTML content you'd
    # like to send to the client.
    # Right now, we just send the default login page.
    
    # But other possibilities exist, including
    # html_content_to_send = success_page + <secret>
    # html_content_to_send = bad_creds_page
    # html_content_to_send = logout_page
    
    # (2) `headers_to_send` => add any additional headers
    # you'd like to send the client?
    # Right now, we don't send any extra headers.
    #headers_to_send = ""
    #cookieHeader = "Set-Cookie: token=; expires=Thu, 01 Jan 1970 00:00:00 GMT\r\n"
    headers_to_send = cookieHeader
    
    # Construct and send the final response
    response  = 'HTTP/1.1 200 OK\r\n'
    response += headers_to_send
    response += 'Content-Type: text/html\r\n\r\n'
    response += html_content_to_send
    print_value('response', response)   
    client.send(response)
    client.close()
    
    print "Served one request/connection!"
    print

# We will never actually get here.
# Close the listening socket
sock.close()
