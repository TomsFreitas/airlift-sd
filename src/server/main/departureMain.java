package server.main;

import commInfra.SimulPar;
import interfaces.Register;
import interfaces.departureAirportInterface;
import interfaces.genRepoInterface;
import server.sharedRegion.departureAirport;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class departureMain {

    public static void main(String[] args) {
        Registry registry = null;
        Register reg = null;
        departureAirport depA;
        departureAirportInterface departureAirportInt = null;
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

        depA = new departureAirport(genRepoInt);

        try {
            departureAirportInt = (departureAirportInterface) UnicastRemoteObject.exportObject(depA, SimulPar.departureAirportServerPort);
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
            reg.bind("departureAirport", departureAirportInt);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            depA.waitShutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            reg.unbind("departureAirport");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            UnicastRemoteObject.unexportObject(depA, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
        try {
            genRepoInt.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

}
