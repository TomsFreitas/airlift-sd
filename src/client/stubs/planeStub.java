package client.stubs;

import client.entity.Hostess;
import client.entity.Passenger;
import client.entity.Pilot;
import commInfra.communication.ClientCom;
import commInfra.messages.messageType;
import commInfra.messages.message;

public class planeStub {
    private String serverHostName;
    private int serverPort;

    public planeStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

    /**
     * Called by a passenger this function sets the passenger state to IN_FLIGHT and increments the plane's seat counter.
     */
    public  void boardThePlane() {

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        Passenger passenger = (Passenger) Thread.currentThread();
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.BOARD_THE_PLANE, passenger.getID());
        con.writeObject(out);

        in = (message) con.readObject();
        passenger.setState(in.getPassengerStates());
        con.close();




    }

    /**
     * Called by a passenger thread this function blocks the passenger until the pilot announces the flight has landed.
     */
    public  void waitForEndOfFlight(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        Passenger passenger = (Passenger) Thread.currentThread();
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.WAIT_FOR_END_OF_FLIGHT, passenger.getID());
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Called by the hostess, this function blocks until all checked in passengers are effectively on board.
     * Hostess state is set to READY_TO_FLY and warns the pilot to take off.
     */
    public  void informPlaneReadyForTakeOff() {
        Hostess hostess = (Hostess) Thread.currentThread();
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                hostess.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.INFORM_PLANE_READY_FOR_TAKEOFF, -1);
        out.setAnInt(hostess.getPassengersInFlight());
        con.writeObject(out);

        in = (message) con.readObject();
        hostess.setState(in.getHostessStates());
        con.close();

    }

    /**
     * Called by the pilot, this function sets the state to WAIT_FOR_BOARDING and blocks until the hostess lets the pilot know it's time to take off.
     */
    public  void waitForAllInBoard() {
        Pilot pilot = (Pilot) Thread.currentThread();
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.WAIT_FOR_ALL_IN_BOARD, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();


    }

    /**
     * Called by the pilot, this function set the state to DEBOARDING and warns all the passengers that the plane has landed.
     * It blocks until all the passengers have left the plane
     */
    public  void announceArrival(){
        Pilot pilot = (Pilot) Thread.currentThread();
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.ANNOUNCE_ARRIVAL, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        pilot.setState(in.getPilotStates());
        con.close();



    }

    /**
     * Called by a passenger, this function sets the state to AT_DESTINATION and decrements the occupied seats counter.
     * When called by the last passenger inside the plane, it warns the pilot that the plane is empty.
     */
    public  void leaveThePlane() {
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        Passenger passenger = (Passenger) Thread.currentThread();
        while(!con.open()){  // waiting for the connection to be established
            try {
                passenger.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.LEAVE_THE_PLANE, passenger.getID());
        con.writeObject(out);

        in = (message) con.readObject();
        passenger.setState(in.getPassengerStates());
        con.close();


    }

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

