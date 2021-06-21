package client.main;

import client.entity.Pilot;

import commInfra.SimulPar;
import interfaces.departureAirportInterface;
import interfaces.planeInterface;
import interfaces.destinationAirportInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Pilot Main Class implementation
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class pilotMain {

    public static void main(String[] args){

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

        Pilot pilot = new Pilot(depA, destA, plane);

        pilot.start();

        try {
            pilot.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        try {
            depA.disconnect();
            destA.disconnect();
            plane.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
