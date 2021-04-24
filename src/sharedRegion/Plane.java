package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

/**
 * Plane Shared Region
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Plane {
    /**
     * General Repository Shared Region
     * @serialField repo
     */
    private genRepo repo;

    /**
     * Number of occupied seats in the plane
     * @serialField occupiedSeats
     */
    private int occupiedSeats;

    /**
     * Number of passengers at destination
     * @serialField passengersAtDestination
     */
    private int passengersAtDestination;

    /**
     * True if plane has arrived to destination
     * @serialField timeToLeave
     */
    private boolean timeToLeave;

    /**
     * True if plane is ready to take off
     * @serialField planeReadyForTakeOff
     */
    private boolean planeReadyForTakeOff;

    /**
     * Plane Shared Region constructor
     * @param repo General Repository Shared Region
     */
    public Plane(genRepo repo){
        this.repo = repo;
        this.occupiedSeats = 0;
        this.passengersAtDestination = 0;
        this.timeToLeave = false;
    }


    /**
     * Called by a passenger this function sets the passenger state to IN_FLIGHT and increments the plane's seat counter.
     */
    public synchronized void boardThePlane() {
        this.occupiedSeats++;
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_FLIGHT);
        repo.setPassengerState(passenger.getID(), passengerStates.IN_FLIGHT.getState());
        repo.setPassengerInFlight(occupiedSeats);
        repo.reportStatus();
        System.out.println("Passenger entered the plane ID: " + passenger.getID());
        notifyAll();

    }

    /**
     * Called by a passenger thread this function blocks the passenger until the pilot announces the flight has landed.
     */
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
     */
    public synchronized void informPlaneReadyForTakeOff() {
        Hostess hostess = (Hostess) Thread.currentThread();


        while (this.occupiedSeats != hostess.getPassengersInFlight()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hostess.setState(hostessStates.READY_TO_FLY);
        repo.reportFlightDeparted();
        repo.setHostessState(hostessStates.READY_TO_FLY.getState());
        repo.reportStatus();
        this.planeReadyForTakeOff = true;
        notifyAll();

    }

    /**
     * Called by the pilot, this function sets the state to WAIT_FOR_BOARDING and blocks until the hostess lets the pilot know it's time to take off.
     */
    public synchronized void waitForAllInBoard() {
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.WAIT_FOR_BOARDING);
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

    }

    /**
     * Called by the pilot, this function set the state to DEBOARDING and warns all the passengers that the plane has landed.
     * It blocks until all the passengers have left the plane
     */
    public synchronized void announceArrival(){

        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.DEBOARDING);
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

    }

    /**
     * Called by a passenger, this function sets the state to AT_DESTINATION and decrements the occupied seats counter.
     * When called by the last passenger inside the plane, it warns the pilot that the plane is empty.
     */
    public synchronized void leaveThePlane() {
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.AT_DESTINATION);
        this.passengersAtDestination++;
        this.occupiedSeats--;
        repo.setPassengerState(passenger.getID(), passengerStates.AT_DESTINATION.getState());
        repo.setNumberOfPassengersArrived(this.passengersAtDestination);
        this.repo.setPassengerInFlight(this.occupiedSeats);
        repo.reportStatus();
        if(this.occupiedSeats == 0) {
            notifyAll();
        }
        System.out.println("Passenger left the plane ID: " + passenger.getID() );


    }

}
