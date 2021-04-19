package entity;
import states.hostessStates;
import sharedRegion.*;

public class Hostess extends Thread {
    private hostessStates state;
    private Plane plane;
    private destinationAirport destA;
    private departureAirport da;
    private boolean readyToFly;
    private int lastCheckedPassenger;


    public Hostess(destinationAirport destA, departureAirport da, Plane plane){
        this.state = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.destA = destA;
        this.da = da;
        this.plane = plane;
        this.readyToFly = false;
        this.lastCheckedPassenger = 0;

    }

    public void setState(hostessStates state) {
        this.state = state;
    }

    public void setLastCheckedPassenger(int lastCheckedPassenger) {
        this.lastCheckedPassenger = lastCheckedPassenger;
    }

    public int getLastCheckedPassenger() {
        return lastCheckedPassenger;
    }

    @Override
    public void run() {

        this.plane.waitForNextFlight();
        this.da.prepareForPassBoarding();
        while (!this.readyToFly) {
            this.da.checkDocuments();
            this.readyToFly = this.da.waitForNextPassenger();

        }
        this.plane.informPlaneReadyForTakeOff();


        System.out.println("Hostess lifecycle ended");






    }
}
