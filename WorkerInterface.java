import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorkerInterface extends Remote {
	long findPassword(long password, long start, long end) throws RemoteException;
}