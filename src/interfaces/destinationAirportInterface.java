package interfaces;

import commInfra.ReturnObject;

import java.rmi.Remote;

public interface destinationAirportInterface extends Remote {
    ReturnObject flyToDeparturePoint();

    void leave();

    boolean endOfDay();

    void disconnect();

    void waitShutdown();
}
