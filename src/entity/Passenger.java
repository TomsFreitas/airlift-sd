package entity;
import states.passengerStates;
import sharedRegion.*;

import java.util.Random;

/**
 * Passenger thread and lifecycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Passenger extends Thread {
    /**
     * Current Passenger state
     * @serialField state
     */
    private passengerStates state;

    /**
     * Passenger ID
     * @serialField id
     */
    private int id;

    /**
     * Departure Airport Shared Region
     * @serialField da
     */
    private departureAirport da;

    /**
     * Destination Airport Shared Region
     * @serialField destA
     */
    private destinationAirport destA;

    /**
     * Plane Shared Region
     * @serialField plane
     */

    private Plane plane;


    /**
     * Passenger constructor
     * @param id Passenger ID
     * @param da Departure Airport Shared Region
     * @param destA Destination Airport Shared Region
     * @param plane Plane Shared Region
     */
    public Passenger(int id, departureAirport da, destinationAirport destA, Plane plane){
        this.id = id;
        this.state = passengerStates.GOING_TO_AIRPORT;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
    }


    /**
     * Passenger Lifecycle
     *
     * A passenger travels to the departure airport and wait in queue.
     * When called by the hostess he proceeds to show his documents.
     * After approval he boards the plane and waits for his flight to end.
     * He leaves the plane and destination airport.
     */
    @Override
    public void run(){
        travelToAirport();
        System.out.println("Passenger arrived at airport ID: " + this.id);
        this.da.waitInQueue();
        this.da.showDocuments();
        this.plane.boardThePlane();
        this.plane.waitForEndOfFlight();
        this.plane.leaveThePlane();

        System.out.println("Life cycle of passenger ended ID: " + this.id);
    }

    /**
     * Simulates travelling to Departure Airport
     */
    private void travelToAirport() {
        long duration = (long) (200 + 1000 * Math.random());
        //long duration = (long) 1000 * this.id;
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Passenger state
     * @param state New state
     */
    public void setState(passengerStates state) {
        this.state = state;
    }


    /**
     * Get passenger ID
     * @return Passenger ID
     */
    public int getID() {
        return id;
    }
}
