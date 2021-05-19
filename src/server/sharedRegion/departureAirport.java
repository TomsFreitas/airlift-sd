package server.sharedRegion;
import commInfra.SimulPar;
import server.interfaces.Hostess;
import server.interfaces.Passenger;
import server.interfaces.Pilot;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import client.stubs.genRepoStub;

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
     */
    private genRepoStub repo;

    /**
     * List of passengers in the transfer gate
     */
    private BlockingQueue<Passenger> passengerQueue;


    /**
     * Condition variable that allows the hostess start with the documentation check
     */
    private boolean ReadyForBoarding;

    /**
     * Id of the passenger called by the hostess for the documentation check
     */
    private int passengerToCheck;

    /**
     * Condition variable that allows the passenger board the flight
     */
    private boolean boardThePlane;

    /**
     * ID of the passenger currently showing the documents
     */
    private int documentsgiven;


    /**
     * Number of passengers already checked and already seated in the plane
     */
    private int passengersChecked;

    /**
     * Total number of passengers who have already flown
     */
    private int passengersFlown;

    /**
     * Number of the current flight
     */
    private int flightNumber;

    /**
     * Departure Airport constructor
     * @param repo General Repository of information
     */
    public departureAirport(genRepoStub repo){
        this.repo = repo;
        this.passengerQueue = new LinkedBlockingQueue<>();
        this.ReadyForBoarding = false;
        this.passengerToCheck = 0;
        this.boardThePlane = false;
        this.documentsgiven = 0;
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
     * Pilot state is set to FLYING_FORWARD and this function blocks the current thread execution for a random duration.
     */
    public synchronized void flyToDestinationPoint(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.FLYING_FORWARD);
        repo.setPilotState(pilotStates.FLYING_FORWARD.getState());
        repo.reportStatus();
        long duration = (long) (SimulPar.Pilot_MinSleep + SimulPar.Pilot_MaxSleep * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by the hostess.
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
     * Called by a passenger.
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
     * Called by the hostess.
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
     * Called by the hostess.
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
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * If there are no passengers at transfer gate and the number of passengers that have already boarded is between MIN and MAX, or there are no more passengers to transfer, the hostess advises the pilot that the boarding is complete and that the the flight may start;
     * This function blocks until some passenger arrives at transfer gate.
     *
     * @return <code>true</code> if ready to fly.
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
        if ((this.passengerQueue.size() == 0 && this.passengersChecked >= SimulPar.F_MinCapacity) || this.passengersChecked == SimulPar.F_MaxCapacity || this.passengersFlown + this.passengersChecked == SimulPar.N_Passengers){
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
     * This function blocks until the hostess gives this passenger permission to board the plane.
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
     * @return True if all passengers have flown to the destination
     */
    public boolean endOfDay(){
        System.out.println(this.passengersFlown);
        return this.passengersFlown == SimulPar.N_Passengers;
    }

}
