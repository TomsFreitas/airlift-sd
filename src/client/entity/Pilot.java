package client.entity;
import commInfra.ReturnObject;
import commInfra.states.pilotStates;
import interfaces.*;
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
    private departureAirportInterface da;

    /**
     * DestinationAirport Shared Region
     */
    private destinationAirportInterface destA;
    /**
     * Plane Shared Region
     */
    private planeInterface plane;

    /**
     * True if end of work day
     */
    private boolean endOfDay;


    /**
     * Pilot constructor
     *
     * @param da Departure Airport Stub
     * @param destA Destination Airport Stub
     * @param plane Plane Stub
     */
    public Pilot(departureAirportInterface da, destinationAirportInterface destA, planeInterface plane){
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
        ReturnObject ret;
        while (true) {
            ret = this.da.parkAtTransferGate(this.endOfDay);
            this.state = ret.getPilotState();
            if (this.endOfDay){
                break;
            }
            ret = this.da.informPlaneReadyForBoarding();
            this.state = ret.getPilotState();
            ret = this.plane.waitForAllInBoard();
            this.state = ret.getPilotState();
            ret = this.da.flyToDestinationPoint();
            this.state = ret.getPilotState();
            ret = this.plane.announceArrival();
            this.state = ret.getPilotState();
            ret = this.destA.flyToDeparturePoint();
            this.state = ret.getPilotState();
            System.out.println("Flying to departure");
            this.endOfDay = this.destA.endOfDay();
        }

        System.out.println("Pilot lifecycle ended");
    }




}
