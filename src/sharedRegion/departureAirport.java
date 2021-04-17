package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class departureAirport {

    private BlockingQueue<Passenger> passengerQueue;
    private int passengersInAirport;
    private boolean ReadyForBoarding;
    private int passengerToCheck;
    private boolean boardThePlane;
    private boolean documentsgiven;


    public departureAirport(){
        int maxPeopleInAirport = 20;
        this.passengerQueue = new ArrayBlockingQueue<Passenger>(maxPeopleInAirport);
        this.passengersInAirport = 0;
        this.ReadyForBoarding = false;
        this.passengerToCheck = 0;
        this.boardThePlane = false;
        this.documentsgiven = false;

    }


    public synchronized void informPlaneReadyForBoarding(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.READY_FOR_BOARDING);
        this.ReadyForBoarding = true;
        notifyAll();
    }

    public synchronized void waitInQueue(){

        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_QUEUE);
        try {
            this.passengerQueue.put(passenger);
            System.out.println("Added to Queue passenger id " + passenger.getID());
            notifyAll();
            while (this.passengerToCheck != passenger.getID()) {
                wait();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    public synchronized void waitForFlight() {
        Hostess hostess = (Hostess) Thread.currentThread();
        while(!this.ReadyForBoarding){
            System.out.println(this.ReadyForBoarding);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void prepareForPassBoarding() {
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.WAIT_FOR_PASSENGER);
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
            this.passengerToCheck = passenger.getID();
            notifyAll();
            while (!this.documentsgiven) {
                wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean waitForNextPassenger(){
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.WAIT_FOR_PASSENGER);
        this.boardThePlane = true;
        notifyAll();
        // TODO check this expression for the blocking status
        if (this.passengerQueue.size() == 0){
            return true;
        }
        return false;
    }

    public synchronized void showDocuments() {
        this.documentsgiven = true;
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



}
