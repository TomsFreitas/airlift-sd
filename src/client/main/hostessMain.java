package client.main;

import client.entity.Hostess;
import commInfra.SimulPar;
import interfaces.departureAirportInterface;
import interfaces.planeInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hostess Main Class implementation
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */


public class hostessMain {
    public static void main(String[] args) {
        departureAirportInterface depA = null;
        planeInterface plane = null;

        try{
            Registry registry = LocateRegistry.getRegistry(SimulPar.RegistryHost, SimulPar.RegistryPort);
            depA = (departureAirportInterface) registry.lookup("departureAirport");
            plane = (planeInterface) registry.lookup("Plane");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Hostess hostess = new Hostess(depA, plane);

        hostess.start();

        try {
            hostess.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        depA.disconnect();
        plane.disconnect();

    }
}
