package server.servers;
import commInfra.SimulPar;
import commInfra.communication.ServerCom;
import server.sharedRegion.destinationAirport;
import server.interfaces.destinationInterface;
import server.proxies.destinationProxy;
import client.stubs.genRepoStub;

public class destinationServer {
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

            sconi = scon.accept();
            proxy = new destinationProxy(sconi, destinationInterface);
            proxy.start();

        }
    }
}
