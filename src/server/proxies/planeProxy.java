package server.proxies;

import communication.ServerCom;
import messages.message;
import server.interfaces.planeInterface;

public class planeProxy extends Thread {

    private static int proxyNo;

    private ServerCom sconi;

    private planeInterface planeInterface;

    public planeProxy(ServerCom sconi, planeInterface planeInterface){
        this.sconi = sconi;
        this.planeInterface = planeInterface;
    }

    @Override
    public void run() {
        message in, out;

        in = (message) this.sconi.readObject();

        out = this.planeInterface.process(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    public static int getProxyNo() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName ("server.proxies.planeProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("planeProxy not found!");
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
