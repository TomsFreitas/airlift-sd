package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.interfaces.Hostess;
import server.interfaces.Passenger;
import server.interfaces.Pilot;
import server.interfaces.planeInterface;

/**
 * Plane Proxy. Provides services to client requests.
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class planeProxy extends Thread implements Passenger, Pilot, Hostess {


    private final ServerCom sconi;

    private planeInterface planeInterface;

    private hostessStates hostessState;

    private pilotStates pilotStates;

    private passengerStates passengerStates;

    private int passengersInFlight;

    private message in, out;

    /**
     * Interface instantiation
     *
     * @param sconi Communication channel (package commInfra.communication)
     * @param planeInterface Plane Interface
     */
    public planeProxy(ServerCom sconi, planeInterface planeInterface){
        this.sconi = sconi;
        this.planeInterface = planeInterface;
    }

    /**
     * Life cycle of SPA Thread
     */
    @Override
    public void run() {

        in = (message) this.sconi.readObject();

        out = this.planeInterface.processAndReply(in);

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

    /** Set Hostess State
     * 
     * @param state New state
     */
    @Override
    public void setState(hostessStates state) {
        this.hostessState = state;
    }

    /** Set number of passengers in flight
     * 
     * @param passengersInFlight Number of passengers
     */
    @Override
    public void setPassengersInFlight(int passengersInFlight) {
        this.passengersInFlight = passengersInFlight;
    }

    /** Get number of passengers in current flight
     * 
     * @return Number of passengers in current flight
     */
    @Override
    public int getPassengersInFlight() {
        return this.passengersInFlight;
    }

    /**
     * Set Passenger state
     * 
     * @param state New state
     */
    @Override
    public void setState(passengerStates state) {
        this.passengerStates = state;
    }

    /**
     * Get passenger ID
     * 
     * @return Passenger ID
     */
    @Override
    public int getID() {
        return in.getID();
    }

    /**
     * Set Pilot state
     * 
     * @param state New state
     */
    @Override
    public void setState(pilotStates state) {
        this.pilotStates = state;
    }
}
