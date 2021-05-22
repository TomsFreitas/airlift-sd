package server.servers;
import commInfra.SimulPar;
import commInfra.communication.ServerCom;
import server.sharedRegion.destinationAirport;
import server.interfaces.destinationInterface;
import server.proxies.destinationProxy;
import server.stubs.genRepoStub;

import java.net.SocketTimeoutException;

/**
 * Destination Airport Server
 *
 * @author Tomás Freitas
 * @author Tiago Almeioda
 */
public class destinationServer {
    
    /**
     * Activity signalization
     *
     * @serialField waitConnection
     */
    public static boolean waitConnection;

    public static void main(String[] args) {
        destinationAirport destA;
        destinationInterface destinationInterface;
        ServerCom scon, sconi;
        destinationProxy proxy;
        genRepoStub repoStub;

        scon = new ServerCom(SimulPar.destinationAirportServerPort);
        scon.start();

        repoStub = new genRepoStub(SimulPar.genRepoServerHost, SimulPar.genRepoServerPort);

        destA = new destinationAirport(repoStub);

        destinationInterface = new destinationInterface(destA);

        System.out.println("destination Airport Server Listening");

        waitConnection = true;

        while (waitConnection){

            try {
                sconi = scon.accept();
                proxy = new destinationProxy(sconi, destinationInterface);
                proxy.start();
            } catch (SocketTimeoutException e) {

            }

        }
        scon.end();
        System.out.println("Destination shutdown");
    }
}
