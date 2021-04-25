package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the Departure Airport Shared Memory
 * The passengers arrive at random times to check in for the transfer flight
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public class departureAirport {

    /**
     * General Repository Shared Region
     * @serialField repo
     */
    private genRepo repo;

    /**
     * List of passengers in the transfer gate
     * @serialField passengerQueue
     */
    private BlockingQueue<Passenger> passengerQueue;

    /**
     * Number of passengers in the departure airport
     * @serialField passengerInAirport
     */
    private int passengersInAirport;

    /**
     * Condition variable that allows the hostess start with the documentation check
     * @serialField ReadyForBoarding
     */
    private boolean ReadyForBoarding;

    /**
     * Id of the passenger called by the hostess for the documentation check
     * @serialField passengerToCheck
     */
    private int passengerToCheck;

    /**
     * Condition variable that allows the passenger board the flight
     * @serialField boardThePlane
     */
    private boolean boardThePlane;

    /**
     * ID of the passenger currently showing the documents
     * @serialField documentsgiven
     */
    private int documentsgiven;

    /**
     * Condition variable that allows the plane takeoff
     * @serialField planeReadyForTakeOff
     */
    private boolean planeReadyForTakeOff;

    /**
     * Number of passengers already checked and already seated in the plane
     * @serialField passengersChecked
     */
    private int passengersChecked;

    /**
     * Total number of passengers who have already flown
     * @serialField passengersFlown
     */
    private int passengersFlown;

    /**
     * Number of the current flight
     * @serialField flightNumber
     */
    private int flightNumber;

    /**
     * Departure Airport constructor
     * @param repo General Repository of information
     */
    public departureAirport(genRepo repo){
        int maxPeopleInAirport = 30;
        this.repo = repo;
        this.passengerQueue = new LinkedBlockingQueue<>();
        this.passengersInAirport = 0;
        this.ReadyForBoarding = false;
        this.passengerToCheck = 0;
        this.boardThePlane = false;
        this.documentsgiven = 0;
        this.planeReadyForTakeOff = false;
        this.passengersChecked = 0;
        this.passengersFlown = 0;
        this.flightNumber = 0;

    }

    /**
     * Called by the pilot.
     * Pilot state is set to READY_FOR_BOARDING and warns the hostess to start calling passengers for the documentation check.
     */
    public synchronized void informPlaneReadyForBoarding(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.READY_FOR_BOARDING);
        repo.setPilotState(pilotStates.READY_FOR_BOARDING.getState());
        this.ReadyForBoarding = true;
        this.flightNumber++;
        repo.setFlightNumber(this.flightNumber);
        repo.reportBoardingStarted();
        repo.reportStatus();
        notifyAll();

    }

    /**
     * Called by the pilot.
     * Pilot state is set to AT_TRANSFER_GATE.
     */
    public synchronized void parkAtTransferGate() {
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.AT_TRANSFER_GATE);
        repo.setPilotState(pilotStates.AT_TRANSFER_GATE.getState());
        repo.reportStatus();
    }

    /**
     * Called by the pilot.
     * Pilot state is set to FLYING_FORWARD and this function pauses(suspend) the current thread execution for a random duration.
     */
    public synchronized void flyToDestinationPoint(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.FLYING_FORWARD);
        repo.setPilotState(pilotStates.FLYING_FORWARD.getState());
        repo.reportStatus();
        long duration = (long) (1 + 200 * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by a hostess.
     * Hostess state is set to WAIT_FOR_NEXT_FLIGHT.
     * This function blocks until the pilot announces that the plane is ready to board.
     */
    public synchronized void waitForNextFlight() {
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.WAIT_FOR_NEXT_FLIGHT);
        repo.setHostessState(hostessStates.WAIT_FOR_NEXT_FLIGHT.getState());
        repo.reportStatus();
        if(this.endOfDay()){
            return;
        }
        while(!this.ReadyForBoarding){
            System.out.println(this.ReadyForBoarding);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.ReadyForBoarding = false;

    }

    /**
     * Called by a passenger
     * Passenger state is set to IN_QUEUE.
     * This function blocks until the id of the passenger called by the hostess for the documentation check matches to the current passenger
     */
    public synchronized void waitInQueue(){

        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_QUEUE);
        repo.setPassengerState(passenger.getID(), passengerStates.IN_QUEUE.getState());
        try {
            this.passengerQueue.put(passenger);
            repo.setPassengersInQueue(this.passengerQueue.size());
            repo.reportStatus();
            System.out.println("Added to Queue passenger id " + passenger.getID() + "QUEUE SIZE: " + this.passengerQueue.size());
            notifyAll();
            while (this.passengerToCheck != passenger.getID()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Called by a hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * This function blocks until some passenger arrives at transfer gate
     */
    public synchronized void prepareForPassBoarding() {
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.WAIT_FOR_PASSENGER);
        repo.setHostessState(hostessStates.WAIT_FOR_PASSENGER.getState());
        repo.reportStatus();
        while(this.passengerQueue.size() == 0){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called by a hostess.
     * Hostess state is set to CHECK_PASSENGER.
     * This function blocks until the id of the passenger currently showing the documents matches to the id of the passenger called by the hostess for the documentation check
     */
    public synchronized void checkDocuments() {

        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.CHECK_PASSENGER);

        try {
            Passenger passenger = this.passengerQueue.take();
            repo.setPassengerCheckedId(passenger.getID());
            repo.reportPassengerChecked();
            repo.setHostessState(hostessStates.CHECK_PASSENGER.getState());
            repo.setPassengersInQueue(this.passengerQueue.size());
            repo.reportStatus();
            System.out.println("Waking up passenger " + passenger.getID());
            this.passengerToCheck = passenger.getID();
            notifyAll();
            while (this.documentsgiven != this.passengerToCheck) {
                wait();
            }
            this.passengersChecked++;



        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called by a hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * If the number of passengers at transfer gate is empty and the number of passengers that have already boarded is between MIN and MAX, or there are no more passengers to transfer, the hostess advises the pilot that the boarding is complete and that the the flight may start;
     * This function blocks until some passenger arrives at transfer gate.
     */
    public synchronized boolean waitForNextPassenger(){
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.WAIT_FOR_PASSENGER);
        repo.setHostessState(hostessStates.WAIT_FOR_PASSENGER.getState());
        this.boardThePlane = true;
        repo.reportStatus();
        System.out.println("Passenger allowed to Board");
        notifyAll();
        // TODO check this expression for the blocking status
        if ((this.passengerQueue.size() == 0 && this.passengersChecked >= 5) || this.passengersChecked == 10 || this.passengersFlown + this.passengersChecked == 21){
            this.passengersFlown += this.passengersChecked;
            hostess.setPassengersInFlight(this.passengersChecked);
            this.passengersChecked = 0;
            System.out.println(this.passengerQueue.size());
            System.out.println("Ready to Fly");
            return true;
        }
        System.out.println("There are more passengers to check");

        while(this.passengerQueue.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Called by a passenger.
     * This function blocks until the passenger who's showing the documents enters in the plane.
     */
    public synchronized void showDocuments() {
        Passenger passenger = (Passenger) Thread.currentThread();
        System.out.println("Passenger showed documents " + passenger.getID());
        this.documentsgiven = passenger.getID();
        notifyAll();

        while(!this.boardThePlane){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.boardThePlane = false;


    }

    /**
     * All passengers arrived at the destination
     */
    public boolean endOfDay(){
        System.out.println(this.passengersFlown);
        return this.passengersFlown == 21;
    }

}
