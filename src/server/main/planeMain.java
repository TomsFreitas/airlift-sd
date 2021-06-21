package server.main;

import commInfra.SimulPar;
import interfaces.Register;
import interfaces.planeInterface;
import interfaces.genRepoInterface;
import server.sharedRegion.Plane;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class planeMain {

    public static void main(String[] args) {
        Registry registry = null;
        Register reg = null;
        Plane plane;
        planeInterface PlaneInt = null;
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

        plane = new Plane(genRepoInt);

        try {
            PlaneInt = (planeInterface) UnicastRemoteObject.exportObject(plane, SimulPar.planeServerPort);
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
            reg.bind("Plane", PlaneInt);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            plane.waitShutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            genRepoInt.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            reg.unbind("Plane");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            UnicastRemoteObject.unexportObject(plane, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }


    }

}
