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

public class planeProxy extends Thread implements Passenger, Pilot, Hostess {


    private final ServerCom sconi;

    private planeInterface planeInterface;

    private hostessStates hostessState;

    private pilotStates pilotStates;

    private passengerStates passengerStates;

    private int passengersInFlight;

    private message in, out;

    public planeProxy(ServerCom sconi, planeInterface planeInterface){
        this.sconi = sconi;
        this.planeInterface = planeInterface;
    }

    @Override
    public void run() {

        in = (message) this.sconi.readObject();

        out = this.planeInterface.processAndReply(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    public ServerCom getSconi() {
        return sconi;
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
