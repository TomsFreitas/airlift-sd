package client.main;

import client.entity.Passenger;

import commInfra.SimulPar;
import interfaces.departureAirportInterface;
import interfaces.destinationAirportInterface;
import interfaces.planeInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Passenger Main Class implementation
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class passengerMain {

    public static void main(String[] args) {
        Passenger[] passengers = new Passenger[21];
        departureAirportInterface depA = null;
        planeInterface plane = null;
        destinationAirportInterface destA = null;

        try{
            Registry registry = LocateRegistry.getRegistry(SimulPar.RegistryHost, SimulPar.RegistryPort);
            depA = (departureAirportInterface) registry.lookup("departureAirport");
            plane = (planeInterface) registry.lookup("Plane");
            destA = (destinationAirportInterface) registry.lookup("destinationAirport");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < SimulPar.N_Passengers; i++){
            passengers[i] = new Passenger(i+1, depA, destA, plane);
        }

        for (Passenger passenger : passengers) {
            passenger.start();
        }

        for (Passenger passenger : passengers) {
            try {
                passenger.join();
                depA.disconnect();
                plane.disconnect();
                destA.disconnect();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }



    }
}
