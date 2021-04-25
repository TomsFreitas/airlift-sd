package sharedRegion;

import entity.Passenger;
import entity.Pilot;
import states.passengerStates;
import states.pilotStates;

/**
 * Implementation of the Destination Airport Shared Memory
 * The passengers arrive after the travel
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public class destinationAirport {

    /**
     * General Repository Shared Region
     */
    private genRepo repo;

    /**
     * Number of passengers at destination
     */
    private int passengersAtDestination;

    /**
     * Destination Airport constructor
     * @param repo General Repository of information
     */
    public destinationAirport(genRepo repo) {
        this.repo = repo;
        this.passengersAtDestination = 0;

    }

    /**
     * Called by the pilot.
     *Pilot state is set to FLYING_BACK and this function pauses(suspend) the current thread execution for a random duration.
     */
    public synchronized void flyToDeparturePoint(){
        repo.reportFlightReturning();
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.FLYING_BACK);
        repo.setPilotState(pilotStates.FLYING_BACK.getState());
        repo.reportStatus();
        long duration = (long) (1 + 200 * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
