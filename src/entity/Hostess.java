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
    private boolean endOfDay;


    public Hostess(destinationAirport destA, departureAirport da, Plane plane){
        this.state = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.destA = destA;
        this.da = da;
        this.plane = plane;
        this.readyToFly = false;
        this.lastCheckedPassenger = 0;
        this.endOfDay = false;

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

        while (!this.da.endOfDay()) {
            this.plane.waitForNextFlight();
            this.da.prepareForPassBoarding();
            while (!this.readyToFly) {
                this.da.checkDocuments();
                this.readyToFly = this.da.waitForNextPassenger();

            }
            this.readyToFly = false;
            this.plane.informPlaneReadyForTakeOff();
        }


        System.out.println("Hostess lifecycle ended");






    }
}
