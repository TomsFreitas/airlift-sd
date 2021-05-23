package server.interfaces;

import commInfra.states.pilotStates;

/**
 * Pilot Interface
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public interface Pilot {

    /**
     * Set Pilot state
     * 
     * @param state New state
     */
    public void setState(pilotStates state);

}
