import java.io.*;
import java.rmi.Naming;
import java.util.Random;

public class Client {
	
	// Start the RMI client
	public static void main(String[] args)	{

		// Remote object data
		final int REGISTRYPORT = 1099;
		String registryHost = null;
		String serviceName = "Service";

		try {
			// Check for hostname argument 
			if (args.length != 1) { 
				System.out.println("Usage: Client <hostname>");
				System.exit(1); 
			}
			registryHost = args[0];
			
            // Construct the name to use to look up the remote object
			String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;

            // Look up the remote object by name in the server host's registry
			ServerInterface server = (ServerInterface)Naming.lookup(completeName);

			// Random numeric password between 0 and 100millions (8 positions, 10 digits) generator
			Random random = new Random();
			long password = (long)random.nextInt(100000000);
			System.out.format("Password generated: %d%n\n", password);

			// Call remote method
			long pswCracked = server.crackPassword(password);
			System.out.format("Remote method done! Password: %d%n\n", pswCracked);

		} catch (Exception e) {
			System.err.println("Client exception: ");
			e.printStackTrace(); 
		}
	}
}

