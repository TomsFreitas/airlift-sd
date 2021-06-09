package server.sharedRegion;

import commInfra.ReturnObject;
import commInfra.SimulPar;
import commInfra.states.pilotStates;
import interfaces.destinationAirportInterface;
import interfaces.genRepoInterface;

/**
 * Implementation of the Destination Airport Shared Memory
 * The passengers arrive after the travel
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public class destinationAirport implements destinationAirportInterface {

    /**
     * General Repository Shared Region
     */
    private genRepoInterface repo;

    /**
     * Passenger counter
     */
    private int counter;


    /**
     * Destination Airport constructor
     * @param repo General Repository of information
     */
    public destinationAirport(genRepoInterface repo) {
        this.repo = repo;
        this.counter = 0;

    }

    /**
     * Called by the pilot.
     *Pilot state is set to FLYING_BACK and this function pauses(suspend) the current thread execution for a random duration.
     * @return
     */
    @Override
    public synchronized ReturnObject flyToDeparturePoint(){
        repo.reportFlightReturning();
        repo.setPilotState(pilotStates.FLYING_BACK.getState());
        repo.reportStatus();
        long duration = (long) (SimulPar.Pilot_MinSleep + SimulPar.Pilot_MaxSleep * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ReturnObject(pilotStates.FLYING_BACK);
    }

    @Override
    public synchronized void leave(){
        this.counter++;
    }

    @Override
    public synchronized boolean endOfDay(){
        if(this.counter >= 21){
            //repo.finalReport();
            return true;
        }
        return false;
    }


}
