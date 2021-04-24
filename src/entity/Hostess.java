package entity;
import states.hostessStates;
import sharedRegion.*;

/**
 * Hostess thread and Lifecycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Hostess extends Thread {
    /**
     * Current Hostess state
     * @serialField state
     */
    private hostessStates state;
    /**
     * Departure Airport Shared Region
     * @serialField da
     */
    private departureAirport da;
    /**
     * Plane Shared Region
     * @serialField plane
     */
    private Plane plane;
    /**
     * True if conditions are met for the plane to takeoff
     * @serialField readyToFly
     */
    private boolean readyToFly;
    /**
     * Number of passengers in the plane
     * @serialField passengersInFlight
     */
    private int passengersInFlight;


    /** Hostess constructor
     * @param da Departure Airport Shared Region
     * @param plane Plane Shared Region
     */
    public Hostess(departureAirport da, Plane plane){
        this.state = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.da = da;
        this.plane = plane;
        this.readyToFly = false;
        this.passengersInFlight = 0;

    }

    /** Set Hostess State
     * @param state New state
     */
    public void setState(hostessStates state) {
        this.state = state;
    }

    /** Set number of passengers in flight
     * @param passengersInFlight Number of passengers
     */
    public void setPassengersInFlight(int passengersInFlight) {
        this.passengersInFlight = passengersInFlight;
    }

    /** Get number of passengers in current flight
     * @return Number of passengers in current flight
     */
    public int getPassengersInFlight() {
        return passengersInFlight;
    }

    @Override
    public void run() {

        while (true) {
            this.da.waitForNextFlight();
            if(this.da.endOfDay()){
                break;
            }
            this.da.prepareForPassBoarding();
            while (!this.readyToFly) {
                this.da.checkDocuments();
                this.readyToFly = this.da.waitForNextPassenger();

            }
            this.readyToFly = false;
            this.plane.informPlaneReadyForTakeOff();

        }


        System.out.println("Hostess lifecycle ended");






    }
}
