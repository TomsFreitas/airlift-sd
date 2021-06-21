package client.entity;
import commInfra.ReturnObject;
import commInfra.SimulPar;
import commInfra.states.passengerStates;
import interfaces.*;

import java.rmi.RemoteException;

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
    private departureAirportInterface da;

    /**
     * Destination Airport Shared Region
     */
    private destinationAirportInterface destA;

    /**
     * Plane Shared Region
     */

    private planeInterface plane;


    /**
     * Passenger constructor
     * @param id Passenger ID
     * @param da Departure Airport Interface
     * @param destA Destination Airport Interface
     * @param plane Plane Interface
     */
    public Passenger(int id, departureAirportInterface da, destinationAirportInterface destA, planeInterface plane){
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
        ReturnObject ret = null;
        travelToAirport();
        System.out.println("Passenger arrived at airport ID: " + this.id);
        try {
            ret = this.da.waitInQueue(this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.state = ret.getPassengerState();
        try {
            this.da.showDocuments(this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            ret = this.plane.boardThePlane(this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.state = ret.getPassengerState();
        try {
            this.plane.waitForEndOfFlight();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            ret = this.plane.leaveThePlane(this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.state = ret.getPassengerState();
        try {
            this.destA.leave();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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
