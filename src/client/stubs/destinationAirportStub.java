package client.stubs;

import client.entity.Passenger;
import client.entity.Pilot;
import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

/**
 * Exposes Destination Airport server to the client side
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class destinationAirportStub {

    private String serverHostName;
    private int serverPort;

    /**
     * Destination Airport Stub Instantiation
     *
     * @param serverHostName Name of the computational system where the server is located
     * @param serverPort Server listening port
     */
    public destinationAirportStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

    /**
     * Called by the pilot.
     *Pilot state is set to FLYING_BACK and this function pauses(suspend) the current thread execution for a random duration.
     * Service solicitation.
     */
    public void flyToDeparturePoint(){
        Pilot pilot = (Pilot) Thread.currentThread();
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.FLY_TO_DEPARTURE_POINT, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();

    }

    /**
     * Called by a passenger.
     * Passengers leave the destination airport.
     * Service solicitation.
     */
    public void leave(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        Passenger passenger = (Passenger) Thread.currentThread();
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.LEAVE, passenger.getID());
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();

    }

    /**
     * All passengers arrived at the destination
     * Service solicitation.
     *
     * @return True if all passengers have flown to the destination
     */
    public boolean endOfDay(){
        Pilot pilot = (Pilot) Thread.currentThread();
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.END_OF_DAY, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


        return in.getaBoolean();

    }

    /**
     * Shut down the server
     * Service solicitation.
     */
    public void shutdown(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SHUTDOWN, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

}
