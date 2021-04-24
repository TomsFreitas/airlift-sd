package entity;
import states.pilotStates;
import sharedRegion.*;

public class Pilot extends Thread{
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
        while (!this.endOfDay) {
            parkAtTransferGate();
            this.da.informPlaneReadyForBoarding();
            this.plane.waitForAllInBoard();
            flyToDestinationPoint();
            this.plane.announceArrival();
            flyToDeparturePoint();
            System.out.println("Flying to departure");
            this.endOfDay = this.da.endOfDay();
        }
        parkAtTransferGate();



        System.out.println("Pilot lifecycle ended");
    }

    private void flyToDestinationPoint(){
        state = pilotStates.FLYING_FORWARD;
        repo.setPilotState(pilotStates.FLYING_FORWARD.getState());
        repo.reportStatus();
        long duration = (long) (1 + 100 * Math.random());
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void flyToDeparturePoint(){
        state = pilotStates.FLYING_BACK;
        repo.setPilotState(pilotStates.FLYING_BACK.getState());
        repo.reportStatus();
        long duration = (long) (1 + 100 * Math.random());
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void parkAtTransferGate() {
        state = pilotStates.AT_TRANSFER_GATE;
        //repo.setPilotState(pilotStates.AT_TRANSFER_GATE.getState());
        //repo.reportStatus();
    }
}
