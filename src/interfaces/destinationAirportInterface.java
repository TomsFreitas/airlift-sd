package interfaces;

import commInfra.ReturnObject;

public interface destinationAirportInterface {
    ReturnObject flyToDeparturePoint();

    void leave();

    boolean endOfDay();
}
