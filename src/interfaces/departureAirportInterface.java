package interfaces;

import commInfra.ReturnObject;

import java.rmi.Remote;

public interface departureAirportInterface extends Remote {
    ReturnObject informPlaneReadyForBoarding();

    ReturnObject parkAtTransferGate(boolean endOfDay);

    ReturnObject flyToDestinationPoint();

    ReturnObject waitForNextFlight();

    ReturnObject waitInQueue(int id);

    ReturnObject prepareForPassBoarding();

    ReturnObject checkDocuments();

    ReturnObject waitForNextPassenger();

    void showDocuments(int id);

    boolean endOfDay();

    void disconnect();

    void waitShutdown();
}
