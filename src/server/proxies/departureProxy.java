package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.interfaces.departureInterface;
import server.interfaces.Pilot;
import server.interfaces.Hostess;
import server.interfaces.Passenger

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

public class departureProxy extends Thread implements Passenger, Pilot, Hostess {
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

    @Override
    public void setState(hostessStates state) {

    }

    @Override
    public void setPassengersInFlight(int passengersInFlight) {

    }

    @Override
    public int getPassengersInFlight() {
        return 0;
    }

    @Override
    public void setState(passengerStates state) {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setState(pilotStates state) {

    }
}