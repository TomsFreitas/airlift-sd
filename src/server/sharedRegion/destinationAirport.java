package server.sharedRegion;

import commInfra.ReturnObject;
import commInfra.SimulPar;
import commInfra.states.pilotStates;
import interfaces.destinationAirportInterface;
import interfaces.genRepoInterface;

import java.rmi.RemoteException;

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
    private boolean running;
    private int clientDisconnected;


    /**
     * Destination Airport constructor
     * @param repo General Repository of information
     */
    public destinationAirport(genRepoInterface repo) {
        this.repo = repo;
        this.counter = 0;
        this.clientDisconnected = 0;
        this.running = true;

    }

    /**
     * Called by the pilot.
     *Pilot state is set to FLYING_BACK and this function pauses(suspend) the current thread execution for a random duration.
     * @return
     */
    @Override
    public synchronized ReturnObject flyToDeparturePoint() throws RemoteException {
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
    public synchronized void leave() throws RemoteException{
        this.counter++;
    }

    @Override
    public synchronized boolean endOfDay() throws RemoteException{
        if(this.counter >= 21){
            //repo.finalReport();
            return true;
        }
        return false;
    }

    @Override
    public synchronized void disconnect() throws RemoteException{
        this.clientDisconnected++;

        if(this.clientDisconnected == 22){
            this.running = false;
            notifyAll();
        }
    }
    @Override
    public synchronized void waitShutdown() throws RemoteException{
        while (this.running){
            try {
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }


}
