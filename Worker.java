import java.io.*;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Worker extends UnicastRemoteObject implements WorkerInterface{

	public Worker() throws RemoteException{
		super();
	}

	// Implementation of the remote method
	public synchronized long findPassword(long password, long start, long end) throws RemoteException{

		for (long i = start; i < end; i++) {
			if (i == password) {
				System.out.println("Password found!\n");
				return i;
			}
		}
		System.out.println("Remote single worker method done!\n");
		return 0;
	}
	
	// Start the RMI worker
	public static void main(String[] args)	{

		// Remote object data
		final int REGISTRYPORT = 1099;
		String registryHost = null;
		String serviceName = "Service";
		String workerName = "Worker";
		int port = Integer.parseInt(args[1]);

		try {
			// Check for hostname argument 
			if (args.length < 1) { 
				System.out.println("Usage: Client <hostname>");
				System.exit(1); 
			}	
			registryHost = args[0];

			// Construct the name to use to bind the remote object
			String workerCompleteName = "//" + registryHost + ":" + port + "/" + workerName;

			// Create an instance of the worker
			Worker worker = new Worker();

			// Bind the worker with the RMI registry
			Naming.rebind(workerCompleteName, worker);
			System.out.println("Worker bound\n");
			
            // Construct the name to use to look up the remote object
			String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;

            // Look up the remote object by name in the worker host's registry
			ServerInterface server = (ServerInterface)Naming.lookup(completeName);

			//Call remote method
			server.join(port);
			System.out.println("Remote join method done!\n");
		
		} catch (Exception e) {
			System.err.println("Client exception: ");
			e.printStackTrace(); 
		}
	}
}

