package server.servers;

import communication.ServerCom;
import main.SimulPar;
import server.interfaces.planeInterface;
import server.proxies.planeProxy;
import server.sharedRegion.Plane;
import stubs.genRepoStub;

import java.net.SocketTimeoutException;

public class planeServer {

    private static final int port = SimulPar.planeServerPort;
    public static boolean waitConnection;

    public static void main(String[] args){
        Plane plane;
        planeInterface planeInterface;
        ServerCom scon, sconi;
        planeProxy proxy;
        genRepoStub repoStub;

        scon = new ServerCom(port);
        scon.start();

        repoStub = new genRepoStub();

        plane = new Plane(repoStub);
        planeInterface = new planeInterface(plane);

        System.out.println("Plane server Listening");

        waitConnection = true;
        while (waitConnection)
            try {
                sconi = scon.accept ();                          // entrada em processo de escuta
                proxy = new planeProxy(sconi, planeInterface);  // lançamento do agente prestador do serviço
                proxy.start ();
            } catch (SocketTimeoutException e) {}
        scon.end ();                                         // terminação de operações
        System.out.println("O servidor foi desactivado.");
    }
}

