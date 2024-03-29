package client.entity;
import client.stubs.departureAirportStub;
import client.stubs.planeStub;
import commInfra.states.hostessStates;


/**
 * Hostess thread and Lifecycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Hostess extends Thread {
    /**
     * Current Hostess state
     */
    private hostessStates state;
    /**
     * Departure Airport Shared Region
     */
    private departureAirportStub da;
    /**
     * Plane Shared Region
     */
    private planeStub plane;
    /**
     * True if conditions are met for the plane to takeoff
     */
    private boolean readyToFly;
    /**
     * Number of passengers in the plane
     */
    private int passengersInFlight;


    /** Hostess constructor
     * @param da Departure Airport Stub
     * @param plane Plane Stub
     */
    public Hostess(departureAirportStub da, planeStub plane){
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


    /**
     * Hostess Lifecycle
     *<p>
     * The Hostess waits for the next flight.<p>
     * When advised by the pilot that the plane is ready to board, the hostess signals the passenger at the head of the queue, if there is one, to join her and present the flight documentation.<p>
     * Performs documents verification. After checking it, she requests him/her to board the plane and wait for the next passenger.<p>
     * If the queue is empty and the number of passengers that have already boarded is between MIN and MAX, or there are no more passengers to transfer, the hostess advises the pilot that the boarding is complete and that the flight may start. <p>
     * This cycle repeats until all passengers are transferred.
     */
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
                System.out.println(this.readyToFly);

            }
            this.readyToFly = false;
            this.plane.informPlaneReadyForTakeOff();

        }


        System.out.println("Hostess lifecycle ended");






    }
}
