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

    private BlockingQueue<Passenger> passengerQueue;
    private int passengersInAirport;
    private boolean ReadyForBoarding;
    private int passengerToCheck;
    private boolean boardThePlane;
    private int documentsgiven;
    private boolean planeReadyForTakeOff;
    private int passengersChecked;


    public departureAirport(){
        int maxPeopleInAirport = 30;
        this.passengerQueue = new LinkedBlockingQueue<>();
        this.passengersInAirport = 0;
        this.ReadyForBoarding = false;
        this.passengerToCheck = 0;
        this.boardThePlane = false;
        this.documentsgiven = 0;
        this.planeReadyForTakeOff = false;
        this.passengersChecked = 0;

    }


    public synchronized void waitInQueue(){
        System.out.println("Trying to get in queue");
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

            System.out.println("Getting a passenger from the queue");
            Passenger passenger = this.passengerQueue.take();
            System.out.println("Waking up passenger " + passenger.getID());
            this.passengerToCheck = passenger.getID();
            hostess.setLastCheckedPassenger(passenger.getID());
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
        this.boardThePlane = true;
        System.out.println("Passenger allowed to Board");
        notifyAll();
        // TODO check this expression for the blocking status
        if (this.passengerQueue.size() == 0 && (this.passengersChecked >= 5 && this.passengersChecked <= 20)){
            this.passengersChecked = 0;
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

}
