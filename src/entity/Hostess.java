package entity;
import states.hostessStates;
import sharedRegion.*;

public class Hostess extends Thread {
    private hostessStates state;
    private Plane plane;
    private destinationAirport destA;
    private departureAirport da;
    private boolean readyToFly;

    public Hostess(destinationAirport destA, departureAirport da, Plane plane){
        this.state = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.destA = destA;
        this.da = da;
        this.plane = plane;
        this.readyToFly = false;
    }

    public void setState(hostessStates state) {
        this.state = state;
    }

    @Override
    public void run() {

        this.da.waitForFlight();
        this.da.prepareForPassBoarding();
        while (!this.readyToFly) {
            this.da.checkDocuments();
            readyToFly = this.da.waitForNextPassenger();
        }






    }
}
