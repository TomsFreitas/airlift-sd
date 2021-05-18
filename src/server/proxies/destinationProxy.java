package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import server.interfaces.destinationInterface;

/**
 * destinationProxy
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class destinationProxy extends Thread {
    private static int proxyNo;

    private ServerCom sconi;

    private server.interfaces.destinationInterface destinationInterface;

    /**
     * Interface instantiation
     *
     * @param sconi commInfra.communication channel
     * @param destinationInterface
     */
    public destinationProxy(ServerCom sconi, destinationInterface destinationInterface){
        this.sconi = sconi;
        this.destinationInterface = destinationInterface;
    }

    /**
     * Life cycle of SPA Thread
     */
    @Override
    public void run() {
        message in, out;                                    // mensagem de entrada/saida

        in = (message) this.sconi.readObject();

        out = this.destinationInterface.process(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    /**
     * Instantiation id
     *
     * @return id
     */
    public static int getProxyNo() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName ("server.proxies.destinationProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("destinationProxy not found!");
            e.printStackTrace ();
            System.exit (1);
        }

        synchronized (cl) {
            proxyId = proxyNo;
            proxyNo += 1;
        }

        return proxyId;
    }

}
