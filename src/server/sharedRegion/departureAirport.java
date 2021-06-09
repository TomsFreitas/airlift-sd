package server.sharedRegion;

import interfaces.genRepoInterface;
import interfaces.departureAirportInterface;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import commInfra.SimulPar;
import commInfra.states.*;
import commInfra.ReturnObject;

/**
 * Implementation of the Departure Airport Shared Memory
 * The passengers arrive at random times to check in for the transfer flight
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public class departureAirport implements departureAirportInterface {

    /**
     * General Repository Shared Region
     */
    private genRepoInterface repo;

    /**
     * List of passengers in the transfer gate
     */
    private BlockingQueue<Integer> passengerQueue;


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

    private boolean first;
    private int passengersCheckedtmp;

    /**
     * Departure Airport constructor
     * @param repo General Repository of information
     */
    public departureAirport(genRepoInterface repo){
        this.repo = repo;
        this.passengerQueue = new LinkedBlockingQueue<>();
        this.ReadyForBoarding = false;
        this.passengerToCheck = 0;
        this.boardThePlane = false;
        this.documentsgiven = 0;
        this.passengersChecked = 0;
        this.passengersFlown = 0;
        this.flightNumber = 0;
        this.first = true;

    }

    /**
     * Called by the pilot.
     * Pilot state is set to READY_FOR_BOARDING and warns the hostess to start calling passengers for the documentation check.
     * @return
     */
    @Override
    public synchronized ReturnObject informPlaneReadyForBoarding(){

        repo.setPilotState(pilotStates.READY_FOR_BOARDING.getState());
        this.ReadyForBoarding = true;
        this.flightNumber++;
        repo.setFlightNumber(this.flightNumber);
        repo.reportBoardingStarted();
        repo.reportStatus();
        notifyAll();
        return new ReturnObject(pilotStates.READY_FOR_BOARDING);

    }

    /**
     * Called by the pilot.
     * Pilot state is set to AT_TRANSFER_GATE.
     * @return
     */
    @Override
    public synchronized ReturnObject parkAtTransferGate(boolean endOfDay) {

        repo.setPilotState(pilotStates.AT_TRANSFER_GATE.getState());
        if (!this.first){
            repo.reportStatus();

        }else{
            this.first = false;
        }
        if(endOfDay) repo.finalReport();

        return new ReturnObject(pilotStates.AT_TRANSFER_GATE);
    }

    /**
     * Called by the pilot.
     * Pilot state is set to FLYING_FORWARD and this function blocks the current thread execution for a random duration.
     * @return
     */
    @Override
    public synchronized ReturnObject flyToDestinationPoint(){

        repo.setPilotState(pilotStates.FLYING_FORWARD.getState());
        repo.reportStatus();
        long duration = (long) (SimulPar.Pilot_MinSleep + SimulPar.Pilot_MaxSleep * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ReturnObject(pilotStates.FLYING_FORWARD);
    }

    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_NEXT_FLIGHT.
     * This function blocks until the pilot announces that the plane is ready to board.
     * @return
     */
    @Override
    public synchronized ReturnObject waitForNextFlight() {

        repo.setHostessState(hostessStates.WAIT_FOR_NEXT_FLIGHT.getState());
        repo.reportStatus();
        if(this.endOfDay()){
            return null;
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

        return new ReturnObject(hostessStates.WAIT_FOR_NEXT_FLIGHT);

    }

    /**
     * Called by a passenger.
     * Passenger state is set to IN_QUEUE.
     * This function blocks until the id of the passenger called by the hostess for the documentation check matches to the current passenger
     * @return
     */
    @Override
    public synchronized ReturnObject waitInQueue(int id){

        repo.setPassengerState(id, passengerStates.IN_QUEUE.getState());
        try {
            this.passengerQueue.put(id);
            repo.setPassengersInQueue(this.passengerQueue.size());
            repo.reportStatus();
            System.out.println("Added to Queue passenger id " +id + "QUEUE SIZE: " + this.passengerQueue.size());
            notifyAll();
            while (this.passengerToCheck != id) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ReturnObject(passengerStates.IN_QUEUE);
    }


    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * This function blocks until some passenger arrives at transfer gate
     * @return
     */
    @Override
    public synchronized ReturnObject prepareForPassBoarding() {

        repo.setHostessState(hostessStates.WAIT_FOR_PASSENGER.getState());
        repo.reportStatus();
        while(this.passengerQueue.size() == 0){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnObject(hostessStates.WAIT_FOR_PASSENGER);
    }

    /**
     * Called by the hostess.
     * Hostess state is set to CHECK_PASSENGER.
     * This function blocks until the id of the passenger currently showing the documents matches to the id of the passenger called by the hostess for the documentation check
     * @return
     */
    @Override
    public synchronized ReturnObject checkDocuments() {

        try {
            int passenger = this.passengerQueue.take();
            repo.setPassengerCheckedId(passenger);
            repo.reportPassengerChecked();
            repo.setHostessState(hostessStates.CHECK_PASSENGER.getState());
            repo.setPassengersInQueue(this.passengerQueue.size());
            repo.reportStatus();
            System.out.println("Waking up passenger " + passenger);
            this.passengerToCheck = passenger;
            notifyAll();
            while (this.documentsgiven != this.passengerToCheck) {
                wait();
            }
            this.passengersChecked++;



        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ReturnObject(hostessStates.CHECK_PASSENGER);

    }

    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * If there are no passengers at transfer gate and the number of passengers that have already boarded is between MIN and MAX, or there are no more passengers to transfer, the hostess advises the pilot that the boarding is complete and that the the flight may start;
     * This function blocks until some passenger arrives at transfer gate.
     *
     * @return <code>true</code> if ready to fly.
     */
    @Override
    public synchronized ReturnObject waitForNextPassenger(){

        repo.setHostessState(hostessStates.WAIT_FOR_PASSENGER.getState());
        this.boardThePlane = true;
        repo.reportStatus();
        System.out.println("Passenger allowed to Board");
        notifyAll();
        if ((this.passengerQueue.size() == 0 && this.passengersChecked >= SimulPar.F_MinCapacity) || this.passengersChecked == SimulPar.F_MaxCapacity || this.passengersFlown + this.passengersChecked == SimulPar.N_Passengers){
            this.passengersFlown += this.passengersChecked;
            this.passengersCheckedtmp = this.passengersChecked;
            this.passengersChecked = 0;
            System.out.println(this.passengerQueue.size());
            System.out.println("Ready to Fly");
            return new ReturnObject(true, this.passengersCheckedtmp, hostessStates.WAIT_FOR_PASSENGER);
        }
        System.out.println("There are more passengers to check");

        while(this.passengerQueue.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnObject(false, hostessStates.WAIT_FOR_PASSENGER);
    }

    /**
     * Called by a passenger.
     * This function blocks until the hostess gives this passenger permission to board the plane.
     */
    @Override
    public synchronized void showDocuments(int id) {
        System.out.println("Passenger showed documents " + id);
        this.documentsgiven = id;
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
    @Override
    public boolean endOfDay(){
        System.out.println(this.passengersFlown);
        return this.passengersFlown == SimulPar.N_Passengers;
    }

}
