package server.main;

import commInfra.SimulPar;
import interfaces.Register;
import interfaces.destinationAirportInterface;
import interfaces.genRepoInterface;
import server.sharedRegion.destinationAirport;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class destinationMain {

    public static void main(String[] args) {
        Registry registry = null;
        Register reg = null;
        destinationAirport destA;
        destinationAirportInterface destinationAirportInt = null;
        genRepoInterface genRepoInt = null;

        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security Manager installed");

        try {
            registry = LocateRegistry.getRegistry(SimulPar.RegistryHost, SimulPar.RegistryPort);
            genRepoInt = (genRepoInterface) registry.lookup("genRepo");
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }

        destA = new destinationAirport(genRepoInt);

        try {
            destinationAirportInt = (destinationAirportInterface) UnicastRemoteObject.exportObject(destA, SimulPar.destinationAirportServerPort);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            registry = LocateRegistry.getRegistry(SimulPar.RegistryHost, SimulPar.RegistryPort);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RMI registry was created");

        try {
            reg = (Register) registry.lookup("RegisterHandler");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            reg.bind("destinationAirport", destinationAirportInt);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        destA.waitShutdown();
        genRepoInt.disconnect();

        try {
            reg.unbind("destinationAirport");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            UnicastRemoteObject.unexportObject(destA, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }


    }

}
