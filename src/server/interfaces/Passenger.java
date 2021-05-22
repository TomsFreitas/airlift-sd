package server.interfaces;

import commInfra.states.passengerStates;

/**
 * Passenger Interface
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public interface Passenger {

    /**
     * Set Passenger state
     * 
     * @param state New state
     */
    public void setState(passengerStates state);

    /**
     * Get passenger ID
     * 
     * @return Passenger ID
     */
    public int getID();


}
