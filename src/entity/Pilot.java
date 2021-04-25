package entity;
import states.pilotStates;
import sharedRegion.*;

/**
 * Pilot thread and life cycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Pilot extends Thread{
    /**
     * Current Pilot state
     * @serialField state
     */
    private pilotStates state;

    /**
     * DepartureAirport Shared Region
     * @serialField da
     */
    private departureAirport da;

    /**
     * DestinationAirport Shared Region
     * @serialField destA
     */
    private destinationAirport destA;
    /**
     * Plane Shared Region
     * @serialField plane
     */
    private Plane plane;

    /**
     * True if end of work day
     * @serialField endOfDay
     */
    private boolean endOfDay;


    /**
     * Pilot constructor
     *
     * @param da Departure Airport Shared Region
     * @param destA Destination Airport Shared Region
     * @param plane Plane Shared Region
     */
    public Pilot(departureAirport da, destinationAirport destA, Plane plane){
        this.state = pilotStates.AT_TRANSFER_GATE;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
        this.endOfDay = false;
    }


    /**
     * Set Pilot state
     * @param state New state
     */
    public void setState(pilotStates state) {
        this.state = state;
    }


    /**
     * Pilot Lifecycle
     *
     *
     * Pilot begins parking at the Transfer Gate.
     * Informs the hostess the plane is ready for boarding.
     * Waits for all the checked in passengers to board the plane.
     * When all passengers are on board the pilot flies to the destination airport and announces the arrival.
     * After all the passengers leave the plane, he flies back to the departure airport.
     * If all the passengers in the simulation have not been transferred, the cycle repeats.
     *
     */
    @Override
    public void run() {
        while (true) {
            this.da.parkAtTransferGate();
            if (this.endOfDay){
                break;
            }
            this.da.informPlaneReadyForBoarding();
            this.plane.waitForAllInBoard();
            this.da.flyToDestinationPoint();
            this.plane.announceArrival();
            this.destA.flyToDeparturePoint();
            System.out.println("Flying to departure");
            this.endOfDay = this.da.endOfDay();
        }

        System.out.println("Pilot lifecycle ended");
    }




}
