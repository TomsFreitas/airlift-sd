package interfaces;

import commInfra.ReturnObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface departureAirportInterface extends Remote {
    ReturnObject informPlaneReadyForBoarding() throws RemoteException;

    ReturnObject parkAtTransferGate(boolean endOfDay) throws RemoteException;

    ReturnObject flyToDestinationPoint() throws RemoteException;

    ReturnObject waitForNextFlight() throws RemoteException;

    ReturnObject waitInQueue(int id) throws RemoteException;

    ReturnObject prepareForPassBoarding() throws RemoteException;

    ReturnObject checkDocuments() throws RemoteException;

    ReturnObject waitForNextPassenger() throws RemoteException;

    void showDocuments(int id) throws RemoteException;

    boolean endOfDay() throws RemoteException;

    void disconnect() throws RemoteException;

    void waitShutdown() throws RemoteException;
}
