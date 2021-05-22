package server.servers;
import commInfra.SimulPar;
import commInfra.communication.ServerCom;
import server.sharedRegion.departureAirport;
import server.interfaces.departureInterface;
import server.proxies.departureProxy;
import server.stubs.genRepoStub;

import java.net.SocketTimeoutException;

public class departureServer {
    public static boolean waitConnection;

    public static void main(String[] args) {
        departureAirport da;
        departureInterface departureInterface;
        ServerCom scon, sconi;
        departureProxy proxy;
        genRepoStub repoStub;

        scon = new ServerCom(SimulPar.departureAirportServerPort);
        scon.start();

        repoStub = new genRepoStub(SimulPar.genRepoServerHost, SimulPar.genRepoServerPort);

        da = new departureAirport(repoStub);

        departureInterface = new departureInterface(da);

        System.out.println("Departure Airport Server Listening");

        waitConnection = true;

        while (waitConnection){
            try {
                sconi = scon.accept();
                proxy = new departureProxy(sconi, departureInterface);
                proxy.start();
            }catch (SocketTimeoutException e){

            }

        }

        repoStub.shutdown();
        scon.end();
        System.out.println("Departure shutdown");
    }
}
