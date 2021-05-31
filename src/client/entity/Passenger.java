package client.entity;
import commInfra.SimulPar;
import commInfra.states.passengerStates;
import client.stubs.*;

/**
 * Passenger thread and lifecycle implementation
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class Passenger extends Thread {
    /**
     * Current Passenger state
     */
    private passengerStates state;

    /**
     * Passenger ID
     */
    private int id;

    /**
     * Departure Airport Shared Region
     */
    private departureAirportStub da;

    /**
     * Destination Airport Shared Region
     */
    private destinationAirportStub destA;

    /**
     * Plane Shared Region
     */

    private planeStub plane;


    /**
     * Passenger constructor
     * @param id Passenger ID
     * @param da Departure Airport Stub
     * @param destA Destination Airport Stub
     * @param plane Plane Stub
     */
    public Passenger(int id, departureAirportStub da, destinationAirportStub destA, planeStub plane){
        this.id = id;
        this.state = passengerStates.GOING_TO_AIRPORT;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
    }


    /**
     * Passenger Lifecycle
     *<p>
     * A passenger travels to the departure airport and wait in queue.<p>
     * When called by the hostess he proceeds to show his documents.<p>
     * After approval he boards the plane and waits for his flight to end.<p>
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
        this.destA.leave();

        System.out.println("Life cycle of passenger ended ID: " + this.id);
    }

    /**
     * Simulates travelling to Departure Airport
     */
    private void travelToAirport() {
        long duration = (long) (SimulPar.Passenger_MinSleep + SimulPar.Passenger_MaxSleep * Math.random());
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
