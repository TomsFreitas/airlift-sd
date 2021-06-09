package interfaces;

import commInfra.ReturnObject;

public interface planeInterface {

    ReturnObject boardThePlane(int id);

    void waitForEndOfFlight();

    ReturnObject informPlaneReadyForTakeOff(int passengersInFlight);

    ReturnObject waitForAllInBoard();

    ReturnObject announceArrival();

    ReturnObject leaveThePlane(int id);
}
