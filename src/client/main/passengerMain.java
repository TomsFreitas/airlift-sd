package client.main;

import client.entity.Passenger;

import client.stubs.departureAirportStub;
import client.stubs.destinationAirportStub;
import client.stubs.genRepoStub;
import client.stubs.planeStub;
import commInfra.SimulPar;

public class passengerMain {

    public static void main(String[] args) {
        Passenger[] passengers = new Passenger[21];
        genRepoStub genRepo = new genRepoStub(SimulPar.genRepoServerHost, SimulPar.genRepoServerPort);
        departureAirportStub da = new departureAirportStub(SimulPar.departureAirportServerHost, SimulPar.departureAirportServerPort);
        destinationAirportStub destA = new destinationAirportStub(SimulPar.destinationAirportServerHost, SimulPar.destinationAirportServerPort);
        planeStub plane = new planeStub(SimulPar.planeServerHost, SimulPar.planeServerPort);

        for (int i = 0; i < SimulPar.N_Passengers; i++){
            passengers[i] = new Passenger(i+1, da, destA, plane);
        }

        for (Passenger passenger : passengers) {
            passenger.start();
        }

        for (Passenger passenger : passengers) {
            try {
                passenger.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }



    }
}
