package sharedRegion;

import entity.Passenger;
import entity.Pilot;
import states.passengerStates;
import states.pilotStates;

public class destinationAirport {
    private genRepo repo;
    private int passengersAtDestination;

    public destinationAirport(genRepo repo) {
        this.repo = repo;
        this.passengersAtDestination = 0;

    }

    public synchronized void flyToDeparturePoint(){
        repo.reportFlightReturning();
        Pilot pilot = (Pilot) Thread.currentThread();
        pilot.setState(pilotStates.FLYING_BACK);
        repo.setPilotState(pilotStates.FLYING_BACK.getState());
        repo.reportStatus();
        long duration = (long) (1 + 200 * Math.random());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
