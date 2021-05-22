package server.sharedRegion;

import server.interfaces.Pilot;
import commInfra.SimulPar;
import commInfra.states.pilotStates;
import server.stubs.genRepoStub;

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
    private genRepoStub repo;

    /**
     * Passenger counter
     */
    private int counter;


    /**
     * Destination Airport constructor
     * @param repo General Repository of information
     */
    public destinationAirport(genRepoStub repo) {
        this.repo = repo;
        this.counter = 0;

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
        long duration = (long) (SimulPar.Pilot_MinSleep + SimulPar.Pilot_MaxSleep * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void leave(){
        this.counter++;
    }

    public synchronized boolean endOfDay(){
        if(this.counter >= 21){
            //repo.finalReport();
            return true;
        }
        return false;
    }


}
