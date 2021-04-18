package sharedRegion;

import entity.Passenger;
import states.passengerStates;

import java.util.ArrayList;

public class Plane {
    private ArrayList<Passenger> seats;
    private int occupiedSeats;
    private boolean timeToLeave;

    public Plane(){
        this.occupiedSeats = 0;
        this.timeToLeave = false;
    }


    public synchronized void boardThePlane() {
        this.occupiedSeats++;
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(passengerStates.IN_FLIGHT);
        System.out.println("Passenger entered the plane ID: " + passenger.getID());

        while (!timeToLeave){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
