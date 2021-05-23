package client.stubs;

import client.entity.Hostess;
import client.entity.Passenger;
import client.entity.Pilot;
import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

/**
 * Exposes Departure Airport server to the client side
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class departureAirportStub {
    private String serverHostName;
    private int serverPort;

    /**
     * Departure Airport Stub Instantiation
     *
     * @param serverHostName Name of the computational system where the server is located
     * @param serverPort Server listening port
     */
    public departureAirportStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

    /**
     * Called by the pilot.
     * Pilot state is set to READY_FOR_BOARDING and warns the hostess to start calling passengers for the documentation check.
     * Service solicitation.
     */
    public  void informPlaneReadyForBoarding() {
        Pilot pilot = (Pilot) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.INFORM_PLANE_READY_FOR_BOARDING, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();


    }

    /**
     * Called by the pilot.
     * Pilot state is set to AT_TRANSFER_GATE.
     * Service solicitation.
     */
    public  void parkAtTransferGate(boolean endOfDay) {
        Pilot pilot = (Pilot) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.PARK_AT_TRANSFER_GATE, -1);
        out.setaBoolean(endOfDay);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();


    }

    /**
     * Called by the pilot.
     * Pilot state is set to FLYING_FORWARD and this function blocks the current thread execution for a random duration.
     * Service solicitation.
     */
    public  void flyToDestinationPoint() {
        Pilot pilot = (Pilot) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.FLY_TO_DESTINATION_POINT, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();


    }

    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_NEXT_FLIGHT.
     * This function blocks until the pilot announces that the plane is ready to board.
     * Service solicitation.
     */
    public void waitForNextFlight() {
        Hostess hostess = (Hostess) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.WAIT_FOR_NEXT_FLIGHT, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        hostess.setState(in.getHostessStates());
        con.close();

    }

    /**
     * Called by a passenger.
     * Passenger state is set to IN_QUEUE.
     * This function blocks until the id of the passenger called by the hostess for the documentation check matches to the current passenger
     * Service solicitation.
     */
    public void waitInQueue() {
        Passenger passenger = (Passenger) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.WAIT_IN_QUEUE, passenger.getID());
        con.writeObject(out);

        in = (message) con.readObject();
        passenger.setState(in.getPassengerStates());
        con.close();


    }

    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * This function blocks until some passenger arrives at transfer gate.
     * Service solicitation.
     */
    public  void prepareForPassBoarding() {
        Hostess hostess = (Hostess) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.PREPARE_FOR_PASS_BOARDING, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        hostess.setState(in.getHostessStates());
        con.close();

    }

    /**
     * Called by the hostess.
     * Hostess state is set to CHECK_PASSENGER.
     * This function blocks until the id of the passenger currently showing the documents matches to the id of the passenger called by the hostess for the documentation check.
     * Service solicitation.
     */
    public  void checkDocuments() {
        Hostess hostess = (Hostess) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.CHECK_DOCUMENTS, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        hostess.setState(in.getHostessStates());
        con.close();

    }

    /**
     * Called by the hostess.
     * Hostess state is set to WAIT_FOR_PASSENGER.
     * If there are no passengers at transfer gate and the number of passengers that have already boarded is between MIN and MAX, or there are no more passengers to transfer, the hostess advises the pilot that the boarding is complete and that the the flight may start;
     * This function blocks until some passenger arrives at transfer gate.
     * Service solicitation.
     *
     * @return True if ready to fly
     */
    public boolean waitForNextPassenger() {
        Hostess hostess = (Hostess) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.WAIT_FOR_NEXT_PASSENGER, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        hostess.setPassengersInFlight(in.getAnInt());
        hostess.setState(in.getHostessStates());
        con.close();

        return in.getaBoolean();
    }

    /**
     * Called by a passenger.
     * This function blocks until the hostess gives this passenger permission to board the plane.
     * Service solicitation.
     */
    public  void showDocuments() {
        Passenger passenger = (Passenger) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.SHOW_DOCUMENTS, passenger.getID());
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
    public boolean endOfDay() {
        Hostess hostess = (Hostess) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
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
