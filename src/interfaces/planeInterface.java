package interfaces;

import commInfra.ReturnObject;

import java.rmi.Remote;

public interface planeInterface extends Remote {

    ReturnObject boardThePlane(int id);

    void waitForEndOfFlight();

    ReturnObject informPlaneReadyForTakeOff(int passengersInFlight);

    ReturnObject waitForAllInBoard();

    ReturnObject announceArrival();

    ReturnObject leaveThePlane(int id);
}
