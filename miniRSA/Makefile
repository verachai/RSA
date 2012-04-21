all: build

build:
	ant

server:
	cd bin;	java miniRSA.ChatServer

client:
	cd bin; java miniRSA.ChatClient localhost 8080
	
cracker:
	cd bin; java miniRSA.Cracker	

clean: 
	rm -rf bin/miniRSA *.class
