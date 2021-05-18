package client.entity;
import states.pilotStates;
import client.stubs.*;

/**
 * Pilot thread and life cycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Pilot extends Thread{
    /**
     * Current Pilot state
     */
    private pilotStates state;

    /**
     * DepartureAirport Shared Region
     */
    private departureAirportStub da;

    /**
     * DestinationAirport Shared Region
     */
    private destinationAirportStub destA;
    /**
     * Plane Shared Region
     */
    private planeStub plane;

    /**
     * True if end of work day
     */
    private boolean endOfDay;


    /**
     * Pilot constructor
     *
     * @param da Departure Airport Shared Region
     * @param destA Destination Airport Shared Region
     * @param plane Plane Shared Region
     */
    public Pilot(departureAirportStub da, destinationAirportStub destA, planeStub plane){
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
     *<p>
     *
     * Pilot begins parking at the Transfer Gate.<p>
     * Informs the hostess the plane is ready for boarding.<p>
     * Waits for all the checked in passengers to board the plane.<p>
     * When all passengers are on board the pilot flies to the destination airport and announces the arrival.<p>
     * After all the passengers leave the plane, he flies back to the departure airport.<p>
     * If all the passengers in the simulation have not been transferred, the cycle repeats.<p>
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
            this.endOfDay = this.destA.endOfDay();
        }

        System.out.println("Pilot lifecycle ended");
    }




}
