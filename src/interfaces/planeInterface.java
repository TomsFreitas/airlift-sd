package interfaces;

import commInfra.ReturnObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface planeInterface extends Remote {

    ReturnObject boardThePlane(int id) throws RemoteException;

    void waitForEndOfFlight() throws RemoteException;

    ReturnObject informPlaneReadyForTakeOff(int passengersInFlight) throws RemoteException;

    ReturnObject waitForAllInBoard() throws RemoteException;

    ReturnObject announceArrival() throws RemoteException;

    ReturnObject leaveThePlane(int id) throws RemoteException;

    void disconnect() throws RemoteException;

    void waitShutdown() throws RemoteException;
}
