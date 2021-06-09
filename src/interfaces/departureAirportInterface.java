package interfaces;

import commInfra.ReturnObject;

public interface departureAirportInterface {
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
}
