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
        System.out.println("PassengerRun");
        //travelToAirport();
        this.da.waitInQueue();
        this.da.showDocuments();
        this.plane.boardThePlane();
        System.out.println("ehhe");
    }

    private void travelToAirport() {
        Random random = new Random();
        long duration = (long) random.nextInt(5000 + 1 - 500) - 500;
        System.out.println(duration);
        try {
            sleep(duration);
            System.out.println("slept");
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
