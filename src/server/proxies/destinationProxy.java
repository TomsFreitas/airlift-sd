package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.interfaces.Passenger;
import server.interfaces.Pilot;
import server.interfaces.destinationInterface;

/**
 * Destination Airport Proxy. Provides services to client requests.
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class destinationProxy extends Thread implements Pilot, Passenger{
    private static int proxyNo;

    private final ServerCom sconi;

    private destinationInterface destinationInterface;
    private passengerStates passengerState;
    private pilotStates pilotState;
    message in, out;

    /**
     * Interface instantiation
     *
     * @param sconi Communication channel (package commInfra.communication)
     * @param destinationInterface Destination Airport Interface
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
                                           // mensagem de entrada/saida

        in = (message) this.sconi.readObject();

        out = this.destinationInterface.processAndReply(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    /**
     * Get Communication channel
     * 
     * @return ServerCom communication channel
     */
    public ServerCom getSconi() {
        return sconi;
    }

    /**
     * Set Passenger state
     * 
     * @param state New state
     */
    @Override
    public void setState(passengerStates state) {
        this.passengerState = state;
    }

    /**
     * Get passenger ID
     * 
     * @return Passenger ID
     */
    @Override
    public int getID() {
        return in.getID() ;
    }

    /**
     * Set Pilot state
     * 
     * @param state New state
     */
    @Override
    public void setState(pilotStates state) {
        this.pilotState = state;
    }
}
