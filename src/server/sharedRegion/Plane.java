package server.sharedRegion;


import commInfra.ReturnObject;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import interfaces.genRepoInterface;
import interfaces.planeInterface;

/**
 * Plane Shared Region
 * Takes passengers to their destination
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Plane implements planeInterface {
    /**
     * General Repository Shared Region
     */
    private genRepoInterface repo;

    /**
     * Number of occupied seats in the plane
     */
    private int occupiedSeats;

    /**
     * Number of passengers at destination
     */
    private int passengersAtDestination;

    /**
     * True if plane has arrived to destination
     */
    private boolean timeToLeave;

    /**
     * True if plane is ready to take off
     */
    private boolean planeReadyForTakeOff;
    private int clientDisconnected;
    private boolean running;

    /**
     * Plane Shared Region constructor
     * @param repo General Repository Shared Region
     */
    public Plane(genRepoInterface repo){
        this.repo = repo;
        this.occupiedSeats = 0;
        this.passengersAtDestination = 0;
        this.timeToLeave = false;
        this.running = true;
        this.clientDisconnected = 0;
    }


    /**
     * Called by a passenger this function sets the passenger state to IN_FLIGHT and increments the plane's seat counter.
     * @return
     */
    @Override
    public synchronized ReturnObject boardThePlane(int id) {
        this.occupiedSeats++;
        repo.setPassengerState(id, passengerStates.IN_FLIGHT.getState());
        repo.setPassengerInFlight(occupiedSeats);
        repo.reportStatus();
        System.out.println("Passenger entered the plane ID: " + id);
        notifyAll();
        return new ReturnObject(passengerStates.IN_FLIGHT);

    }

    /**
     * Called by a passenger thread this function blocks the passenger until the pilot announces the flight has landed.
     */
    @Override
    public synchronized void waitForEndOfFlight(){
        while (!timeToLeave){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called by the hostess, this function blocks until all checked in passengers are effectively on board.
     * Hostess state is set to READY_TO_FLY and warns the pilot to take off.
     * @return
     */
    @Override
    public synchronized ReturnObject informPlaneReadyForTakeOff(int passengersInFlight) {

        System.out.println(passengersInFlight);
        while (this.occupiedSeats != passengersInFlight){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        repo.reportFlightDeparted();
        repo.setHostessState(hostessStates.READY_TO_FLY.getState());
        repo.reportStatus();
        this.planeReadyForTakeOff = true;
        notifyAll();
        return new ReturnObject(hostessStates.READY_TO_FLY);

    }

    /**
     * Called by the pilot, this function sets the state to WAIT_FOR_BOARDING and blocks until the hostess lets the pilot know it's time to take off.
     * @return
     */
    @Override
    public synchronized ReturnObject waitForAllInBoard() {

        repo.setPilotState(pilotStates.WAIT_FOR_BOARDING.getState());
        repo.reportStatus();
        System.out.println(this.planeReadyForTakeOff);
        while (!this.planeReadyForTakeOff){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("PLANE TAKING OFF WITH " + this.occupiedSeats);
        this.planeReadyForTakeOff = false;
        return new ReturnObject(pilotStates.WAIT_FOR_BOARDING);

    }

    /**
     * Called by the pilot, this function set the state to DEBOARDING and warns all the passengers that the plane has landed.
     * It blocks until all the passengers have left the plane
     * @return
     */
    @Override
    public synchronized ReturnObject announceArrival(){


        repo.reportFlightArrived();
        repo.setPilotState(pilotStates.DEBOARDING.getState());
        repo.reportStatus();
        this.timeToLeave = true;
        notifyAll();
        while(this.occupiedSeats != 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The plane is empty");
        this.timeToLeave = false;
        return new ReturnObject(pilotStates.DEBOARDING);

    }

    /**
     * Called by a passenger, this function sets the state to AT_DESTINATION and decrements the occupied seats counter.
     * When called by the last passenger inside the plane, it warns the pilot that the plane is empty.
     * @return
     */
    @Override
    public synchronized ReturnObject leaveThePlane(int id) {

        this.passengersAtDestination++;
        this.occupiedSeats--;
        repo.setPassengerState(id, passengerStates.AT_DESTINATION.getState());
        repo.setNumberOfPassengersArrived(this.passengersAtDestination);
        this.repo.setPassengerInFlight(this.occupiedSeats);
        repo.reportStatus();
        if(this.occupiedSeats == 0) {
            notifyAll();
        }
        System.out.println("Passenger left the plane ID: " + id);
        return new ReturnObject(passengerStates.AT_DESTINATION);


    }

    @Override
    public synchronized void disconnect(){
        this.clientDisconnected++;

        if(this.clientDisconnected == 23){
            this.running = false;
            notifyAll();
        }
    }
    @Override
    public synchronized void waitShutdown(){
        while (this.running){
            try {
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}
