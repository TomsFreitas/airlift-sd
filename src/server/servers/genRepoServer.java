package server.servers;
import commInfra.SimulPar;
import commInfra.communication.ServerCom;
import server.sharedRegion.genRepo;
import server.interfaces.genRepoInterface;
import server.proxies.genRepoProxy;

import java.net.SocketTimeoutException;

public class genRepoServer {

    public static boolean waitConnection;

    public static void main(String[] args) {
        genRepo genRepo;
        genRepoInterface genRepoInterface;
        ServerCom scon, sconi;
        genRepoProxy proxy;

        scon = new ServerCom(SimulPar.genRepoServerPort);
        scon.start();

        genRepo = new genRepo("logger.txt");

        genRepoInterface = new genRepoInterface(genRepo);

        waitConnection = true;
        while (waitConnection)
        {
            try {
                sconi = scon.accept ();                          // entrada em processo de escuta
                proxy = new genRepoProxy(sconi, genRepoInterface);  // lançamento do agente prestador do serviço
                proxy.start ();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        scon.end ();                                         // terminação de operações
        System.out.println("O servidor foi desactivado.");
    }


}
