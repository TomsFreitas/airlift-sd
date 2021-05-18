package server.interfaces;

import commInfra.states.passengerStates;

public interface Passenger {

    public void setState(passengerStates state);

    public int getID();


}
