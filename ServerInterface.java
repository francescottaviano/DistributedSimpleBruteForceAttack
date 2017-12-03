import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	long crackPassword(long password) throws RemoteException;
	void join(int port) throws RemoteException;
}

