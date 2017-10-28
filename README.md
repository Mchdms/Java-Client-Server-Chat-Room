# Java-Client-Server-Chat-Room

This is a work in progress Chat room program built in Java.
It is meant purely as a "research" project (assinged in a programming class)

WIP
===========
This program is currently a work in progress, and currently only supports one client at a time.

USAGE
===========
Compile both ChatClient.java and ChatServer.java in either terminal or command prompt. 
Run the server from the command line one one computer, and the client on another. 
The client needs the server's computer IP address and port to connect. 
The port can be set in the servers code.

TO-DO
===========
High Priority
Organize classes

ChatClient - interface to provide the format for the TextChatClient and the GuiChatClient classes
TextChatClient - stores information about the client, has a startConnection method that will that will connect to the server and prompt the user for input until they disconnect.
GUIChatClient - same as text, but inputs and outputs everything in a GUI
RunChatClient (ChatClientDriver) - runs a ChatClient by prompting the user if they want text or GUI, asking their name, and asking for the server IP and port, and creating a new TextChatClient or GUIChatClient
ChatServer - starts a server, the server needs to make a new ChatServerThread for each client that connects. Has an arraylist of each ChatServerThread it has made.
ChatServerThread - this is how the server communicates with a client. Accesses the ChatServer arraylist of ChatServerThreads to relay its own client's message to all the other clients.

Medium Priority
Create the GUI interface with Swing.
