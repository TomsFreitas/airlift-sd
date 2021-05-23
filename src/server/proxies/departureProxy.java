package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.interfaces.departureInterface;
import server.interfaces.Pilot;
import server.interfaces.Hostess;
import server.interfaces.Passenger;

/**
 * Departure Airport Proxy
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class departureProxy extends Thread implements Passenger, Pilot, Hostess {
    private static int proxyNo;

    private ServerCom sconi;

    private departureInterface departureInterface;

    private hostessStates hostessState;

    private pilotStates pilotStates;

    private passengerStates passengerStates;

    private int passengersInFlight;

    private message in, out;

    /**
     * Instantiation
     *
     * @param sconi Communication channel (package commInfra.communication)
     * @param departureInterface Departure Airport Interface
     */
    public departureProxy(ServerCom sconi, departureInterface departureInterface){
        this.sconi = sconi;
        this.departureInterface = departureInterface;
        this.hostessState = hostessStates.WAIT_FOR_NEXT_FLIGHT;
        this.pilotStates = pilotStates.AT_TRANSFER_GATE;
        this.passengersInFlight = 0;
        this.passengerStates = passengerStates.GOING_TO_AIRPORT;
    }

    /**
     * Life cycle of SPA Thread
     */
    @Override
    public void run() {


        in = (message) this.sconi.readObject();

        out = this.departureInterface.processAndReply(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    /**
     * Get Communication channel
     * 
     * @return
     */
    public ServerCom getSconi() {
        return this.sconi;
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