package entity;
import states.passengerStates;
import sharedRegion.*;

import java.util.Random;

public class Passenger extends Thread {
    private passengerStates state;
    private int id;
    private departureAirport da;
    private destinationAirport destA;
    private Plane plane;

    public Passenger(int id, departureAirport da, destinationAirport destA, Plane plane){
        this.id = id;
        this.state = passengerStates.GOING_TO_AIRPORT;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
    }



    @Override
    public void run(){
        travelToAirport();
        System.out.println("Passenger arrived at airport ID: " + this.id);
        this.da.waitInQueue();
        this.da.showDocuments();
        this.plane.boardThePlane();
        this.plane.leaveThePlane();
        System.out.println("Life cycle of passenger ended ID: " + this.id);
    }

    private void travelToAirport() {
        long duration = (long) (1 + 500 * Math.random());
        //long duration = (long) 1000 * this.id;
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setState(passengerStates state) {
        this.state = state;
    }


    public passengerStates getStates() {
        return state;
    }

    public int getID() {
        return id;
    }
}
