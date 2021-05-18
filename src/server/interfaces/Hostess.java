package server.interfaces;

import commInfra.states.hostessStates;

public interface Hostess {

    public void setState(hostessStates state);

    public void setPassengersInFlight(int passengersInFlight);

    public int getPassengersInFlight();
}
