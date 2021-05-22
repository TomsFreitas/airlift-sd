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

    private departureInterface departureInterface;

    private hostessStates hostessState;

    private pilotStates pilotStates;

    private passengerStates passengerStates;

    private int passengersInFlight;

    private message in, out;

    /**
     * Interface instantiation
     *
     * @param sconi
     * @param departureInterface
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

    public ServerCom getSconi() {
        return this.sconi;
    }

    @Override
    public void setState(hostessStates state) {
        this.hostessState = state;
    }

    @Override
    public void setPassengersInFlight(int passengersInFlight) {
        this.passengersInFlight = passengersInFlight;
    }

    @Override
    public int getPassengersInFlight() {
        return this.passengersInFlight;
    }

    @Override
    public void setState(passengerStates state) {
        this.passengerStates = state;
    }

    @Override
    public int getID() {
        return in.getID();
    }

    @Override
    public void setState(pilotStates state) {
        this.pilotStates = state;
    }
}