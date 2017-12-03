# SimpleBruteForceAttack

Open a terminal window, run the server:
- $ javac Client.java Server.java Worker.java ServerInterface.java WorkerInterface.java
- $ rmiregistry &
- $ java Server

In other terminal windows, run workers (max 1000):
- $ rmiregistry <port> &
- $ java Worker <hostname> <port>

In another terminal window, run client:
- $ java Client <hostname>