package interfaces;

import commInfra.ReturnObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface destinationAirportInterface extends Remote {
    ReturnObject flyToDeparturePoint() throws RemoteException;

    void leave() throws RemoteException;

    boolean endOfDay() throws RemoteException;

    void disconnect() throws RemoteException;

    void waitShutdown() throws RemoteException;
}
