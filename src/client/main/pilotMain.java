package client.main;

import client.entity.Pilot;
import client.stubs.departureAirportStub;
import client.stubs.destinationAirportStub;
import client.stubs.planeStub;
import commInfra.SimulPar;

public class pilotMain {

    public static void main(String[] args){

        departureAirportStub da = new departureAirportStub(SimulPar.departureAirportServerHost, SimulPar.departureAirportServerPort);
        destinationAirportStub destA = new destinationAirportStub(SimulPar.destinationAirportServerHost, SimulPar.destinationAirportServerPort);
        planeStub plane = new planeStub(SimulPar.planeServerHost, SimulPar.planeServerPort);

        Pilot pilot = new Pilot(da, destA, plane);
        pilot.start();

        try {
            pilot.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        plane.shutdown();
        destA.shutdown();
        da.shutdown();

    }

}
