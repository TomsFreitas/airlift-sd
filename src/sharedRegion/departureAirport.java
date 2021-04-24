package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class departureAirport {

    private genRepo repo;
    private BlockingQueue<Passenger> passengerQueue;
    private int passengersInAirport;
    private boolean ReadyForBoarding;
    private int passengerToCheck;
    private boolean boardThePlane;
    private int documentsgiven;
    private boolean planeReadyForTakeOff;
    private int passengersChecked;
    private int passengersFlown;
    private int flightNumber;


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

    public synchronized void parkAtTransferGate() {
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.AT_TRANSFER_GATE);
        repo.setPilotState(pilotStates.AT_TRANSFER_GATE.getState());
        repo.reportStatus();
    }

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
            //repo.reportStatus();
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

    public boolean endOfDay(){
        System.out.println(this.passengersFlown);
        return this.passengersFlown == 21;
    }

}
