package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

public class Plane {
    private int occupiedSeats;
    private boolean timeToLeave;
    private boolean ReadyForBoarding;
    private boolean planeReadyForTakeOff;
    private int lastCheckedId;

    public Plane(){
        this.occupiedSeats = 0;
        this.timeToLeave = false;
        this.ReadyForBoarding = false;
        this.lastCheckedId = 0;
    }






    public synchronized void boardThePlane() {
        this.occupiedSeats++;
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_FLIGHT);
        System.out.println("Passenger entered the plane ID: " + passenger.getID());
        this.lastCheckedId = passenger.getID();
        notifyAll();


    }

    public synchronized void waitForEndOfFlight(){
        while (!timeToLeave){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void informPlaneReadyForTakeOff() {
        Hostess hostess = (Hostess) Thread.currentThread();
        hostess.setState(hostessStates.READY_TO_FLY);
        while (this.occupiedSeats != hostess.getPassengersInFlight()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.planeReadyForTakeOff = true;
        notifyAll();

    }

    public synchronized void waitForAllInBoard() {
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.WAIT_FOR_BOARDING);
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

    public synchronized void announceArrival(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.DEBOARDING);
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

    public synchronized void leaveThePlane() {
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.AT_DESTINATION);
        this.occupiedSeats--;
        notifyAll();
        System.out.println("Passenger left the plane ID: " + passenger.getID() );
    }


}
