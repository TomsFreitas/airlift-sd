package server.interfaces;

import commInfra.states.hostessStates;

/**
 * Hostess Interface
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */


public interface Hostess {

    /** Set Hostess State
     * 
     * @param state New state
     */
    public void setState(hostessStates state);

    /** Set number of passengers in flight
     * 
     * @param passengersInFlight Number of passengers
     */
    public void setPassengersInFlight(int passengersInFlight);

    /** Get number of passengers in current flight
     * 
     * @return Number of passengers in current flight
     */
    public int getPassengersInFlight();
}
