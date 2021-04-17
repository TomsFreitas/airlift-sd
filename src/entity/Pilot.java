package entity;
import states.pilotStates;
import sharedRegion.*;

public class Pilot extends Thread{
    private pilotStates state;
    private departureAirport da;
    private destinationAirport destA;
    private Plane plane;


    public Pilot(departureAirport da, destinationAirport destA, Plane plane){
        this.state = pilotStates.AT_TRANSFER_GATE;
        this.da = da;
        this.destA = destA;
        this.plane = plane;
    }

    public void setState(pilotStates state) {
        this.state = state;
    }

    @Override
    public void run() {
        this.da.informPlaneReadyForBoarding();
        //System.out.println("informed");

    }
}
