package entity;
import states.hostessStates;
import sharedRegion.*;

public class Hostess extends Thread {
    private hostessStates state;
    private Plane plane;
    private destinationAirport destA;
    private departureAirport da;
    private boolean readyToFly;
    private int passengersInFlight;
    private boolean endOfDay;


    public Hostess(destinationAirport destA, departureAirport da, Plane plane){
        this.state = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.destA = destA;
        this.da = da;
        this.plane = plane;
        this.readyToFly = false;
        this.passengersInFlight = 0;
        this.endOfDay = false;

    }

    public void setState(hostessStates state) {
        this.state = state;
    }

    public void setPassengersInFlight(int passengersInFlight) {
        this.passengersInFlight = passengersInFlight;
    }

    public int getPassengersInFlight() {
        return passengersInFlight;
    }

    @Override
    public void run() {

        while (!this.da.endOfDay()) {
            this.da.waitForNextFlight();
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
