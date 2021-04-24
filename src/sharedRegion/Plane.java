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

    public Plane(genRepo repo){
        this.repo = repo;
        this.occupiedSeats = 0;
        this.passengersAtDestination = 0;
        this.timeToLeave = false;
        this.ReadyForBoarding = false;
    }


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
