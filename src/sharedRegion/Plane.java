package sharedRegion;

import entity.Hostess;
import entity.Passenger;
import entity.Pilot;
import states.hostessStates;
import states.passengerStates;
import states.pilotStates;

public class Plane {
    private genRepo repo;
    private int occupiedSeats;
    private int passengersAtDestination;
    private boolean timeToLeave;
    private boolean ReadyForBoarding;
    private boolean planeReadyForTakeOff;
    private int lastCheckedId;

    public Plane(genRepo repo){
        this.repo = repo;
        this.occupiedSeats = 0;
        this.passengersAtDestination = 0;
        this.timeToLeave = false;
        this.ReadyForBoarding = false;
        this.lastCheckedId = 0;
    }


    public synchronized void boardThePlane() {
        this.occupiedSeats++;
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_FLIGHT);
        repo.setPassengerState(passenger.getID(), passengerStates.IN_FLIGHT.getState());
        repo.getPassengersInFlight(occupiedSeats);
        repo.reportStatus();
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
        repo.setHostessState(hostessStates.READY_TO_FLY.getState());
        repo.reportStatus();
        while (this.occupiedSeats != hostess.getPassengersInFlight()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.planeReadyForTakeOff = true;
        repo.readyToFly(this.planeReadyForTakeOff);
        notifyAll();

    }

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
        repo.readyToFly(this.planeReadyForTakeOff);

    }

    public synchronized void announceArrival(){
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.DEBOARDING);
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

    public synchronized void leaveThePlane() {
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.AT_DESTINATION);
        repo.setPassengerState(passenger.getID(), passengerStates.AT_DESTINATION.getState());
        repo.reportStatus();
        this.passengersAtDestination++;
        this.occupiedSeats--;
        repo.leaveThePlane(this.passengersAtDestination, this.occupiedSeats);
        notifyAll();
        System.out.println("Passenger left the plane ID: " + passenger.getID() );


    }

}
