import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Math;
import java.util.*;

public class Server extends UnicastRemoteObject implements ServerInterface {

	// Initialize number of workers
	static int workers = 0;
	// 1k is max number of workers
	static int[] ports = new int[1000];

	//Constructor
	public Server() throws RemoteException {
		super();
	}

	// Implementation of the remote method join
	public void join(int port) throws RemoteException {
		System.out.println("Server method join invoked\n");
		ports[workers] = port;
		workers++;
		System.out.format("Port: %d\n", ports[workers-1]);
	}

	// Implementation of the remote method crackPassword
	public long crackPassword(long password) throws RemoteException {
		
		// Remote object data
		String registryHost = "localhost";
		String serviceName = "Worker";
		System.out.format("Running workers: %d\n", workers); 

		long psw = 0;

		// Block size: possible combinations/number of workers
		int blocksize = 100000000/workers;

		try {

			long end = 0;
			long start = 0;
			System.out.println(Integer.toString(ports[0]));
			for (int i = 0; i < workers; i++) {
				end = start + blocksize;
				// Construct the name to use to lookup the remote object
				String completeName = "//" + registryHost + ":" + Integer.toString(ports[i]) + "/" + serviceName;
				// Look up the remote object by name in the worker host's registry
				WorkerInterface worker = (WorkerInterface)Naming.lookup(completeName);
				psw += worker.findPassword(password, start, end);
				if (psw != 0) {
					return psw;
				}
				start = end + 1;
			}
		} catch(Exception e) {
			System.err.println("Client exception: ");
			e.printStackTrace(); 
		}
		return 0;
	}

	// Start the RMI Server	
	public static void main(String[] args) {

		// Remote object data
		final int REGISTRYPORT = 1099; 
		String registryHost = "localhost"; 
		String serviceName = "Service";

		try {
			// Construct the name to use to bind the remote object
			String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;

            // Create an instance of the server
            Server server = new Server();

            // Bind the server with the RMI registry
            Naming.rebind(completeName, server);
			System.out.println("Server bound!\n");

		} catch (Exception e) {
			System.err.println("Server exception: ");
			e.printStackTrace(); 
		}
	}
}