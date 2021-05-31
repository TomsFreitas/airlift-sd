package server.servers;

import commInfra.communication.ServerCom;
import commInfra.SimulPar;
import server.interfaces.planeInterface;
import server.proxies.planeProxy;
import server.sharedRegion.Plane;
import server.stubs.genRepoStub;

import java.net.SocketTimeoutException;

/**
 * Plane Server. Provides communication between the client and the service provider.
 *
 * @author Tomás Freitas
 * @author Tiago Almeioda
 */


public class planeServer {

    /**
     * Activity signalization
     *
     * @serialField waitConnection
     */
    public static boolean waitConnection;

    public static void main(String[] args){
        Plane plane;
        planeInterface planeInterface;
        ServerCom scon, sconi;
        planeProxy proxy;
        genRepoStub repoStub;

        scon = new ServerCom(SimulPar.planeServerPort);
        scon.start();

        repoStub = new genRepoStub(SimulPar.genRepoServerHost, SimulPar.genRepoServerPort);

        plane = new Plane(repoStub);
        planeInterface = new planeInterface(plane);

        System.out.println("Plane server Listening");

        waitConnection = true;
        while (waitConnection)
        {
            try {
                sconi = scon.accept ();                          // entrada em processo de escuta
                proxy = new planeProxy(sconi, planeInterface);  // lançamento do agente prestador do serviço
                proxy.start ();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        scon.end ();                                         // terminação de operações
        System.out.println("O servidor foi desactivado.");
    }
}

