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
     *
     */
    private pilotStates state;
    private departureAirport da;
    private destinationAirport destA;
    private Plane plane;
    private boolean endOfDay;

    private genRepo repo;


    public Pilot(departureAirport da, destinationAirport destA, Plane plane, genRepo repo){
        this.state = pilotStates.AT_TRANSFER_GATE;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
        this.repo = repo;
        this.endOfDay = false;
    }


    public void setState(pilotStates state) {
        this.state = state;
    }

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
