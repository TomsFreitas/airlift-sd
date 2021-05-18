package server.servers;

import commInfra.communication.ServerCom;
import commInfra.SimulPar;
import server.interfaces.planeInterface;
import server.proxies.planeProxy;
import server.sharedRegion.Plane;
import client.stubs.genRepoStub;

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

        repoStub = new genRepoStub(SimulPar.genRepoServerHost, SimulPar.genRepoServerPort);

        plane = new Plane(repoStub);
        planeInterface = new planeInterface(plane);

        System.out.println("Plane server Listening");

        waitConnection = true;
        while (waitConnection)
        {
            sconi = scon.accept ();                          // entrada em processo de escuta
            proxy = new planeProxy(sconi, planeInterface);  // lançamento do agente prestador do serviço
            proxy.start ();
        }
        scon.end ();                                         // terminação de operações
        System.out.println("O servidor foi desactivado.");
    }
}

