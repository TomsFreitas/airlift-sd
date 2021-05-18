package server.proxies;

import communication.ServerCom;
import messages.message;
import server.interfaces.departureInterface;

/**
 * departureProxy
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class departureProxy extends Thread {
    private static int proxyNo;

    private ServerCom sconi;

    private server.interfaces.departureInterface departureInterface;

    /**
     * Interface instantiation
     *
     * @param sconi
     * @param departureInterface
     */
    public departureProxy(ServerCom sconi, departureInterface departureInterface){
        this.sconi = sconi;
        this.departureInterface = departureInterface;
    }

    /**
     * Life cycle of SPA Thread
     */
    @Override
    public void run() {
        message in, out;                                    // mensagem de entrada/saida

        in = (message) this.sconi.readObject();

        out = this.departureInterface.process(in);

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
            cl = Class.forName ("server.proxies.departureProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("departureProxy not found!");
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