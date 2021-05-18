package client.main;

import client.entity.Hostess;
import client.stubs.departureAirportStub;
import client.stubs.planeStub;
import commInfra.SimulPar;

public class hostessMain {
    public static void main(String[] args) {
        departureAirportStub da = new departureAirportStub(SimulPar.departureAirportServerHost, SimulPar.departureAirportServerPort);
        planeStub plane = new planeStub(SimulPar.planeServerHost, SimulPar.planeServerPort);

        Hostess hostess = new Hostess(da, plane);
        hostess.start();

        try {
            hostess.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
