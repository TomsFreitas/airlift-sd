package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface genRepoInterface extends Remote {

    void setFlightNumber(int flightNumber) throws RemoteException;

    void setPilotState(String state) throws RemoteException;

    void setHostessState(String state) throws RemoteException;

    void setPassengerState(int id, String state) throws RemoteException;

    void setNumberOfPassengersArrived(Integer numberOfPassengersArrived) throws RemoteException;

    void setPassengerInFlight(int passengerInFlight) throws RemoteException;

    void setPassengerCheckedId(int passengerCheckedId) throws RemoteException;

    void setPassengersInQueue(int passengersInQueue) throws RemoteException;

    void reportBoardingStarted() throws RemoteException;

    void reportPassengerChecked() throws RemoteException;

    void reportFlightDeparted() throws RemoteException;

    void reportFlightArrived() throws RemoteException;

    void reportFlightReturning() throws RemoteException;

    void finalReport() throws RemoteException;

    void reportStatus() throws RemoteException;

    void disconnect() throws RemoteException;

    void waitShutdown() throws RemoteException;
}
