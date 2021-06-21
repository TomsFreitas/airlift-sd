package server.main;

import commInfra.SimulPar;
import interfaces.Register;
import interfaces.genRepoInterface;
import server.sharedRegion.genRepo;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class genRepoMain {

    public static void main(String[] args) {
        Registry registry = null;
        Register reg = null;
        genRepo repo;
        genRepoInterface genRepoInt = null;

        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security Manager installed");

        repo = new genRepo("log.txt");

        try {
            genRepoInt = (genRepoInterface) UnicastRemoteObject.exportObject(repo, SimulPar.genRepoServerPort);
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
            reg.bind("genRepo", genRepoInt);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            repo.waitShutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            reg.unbind("genRepo");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            UnicastRemoteObject.unexportObject(repo, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }


    }

}
