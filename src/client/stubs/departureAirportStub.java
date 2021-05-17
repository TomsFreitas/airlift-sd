package client.stubs;

import client.entity.Hostess;
import client.entity.Passenger;
import client.entity.Pilot;
import communication.ClientCom;
import messages.message;
import messages.messageType;

public class departureAirportStub {
    private String serverHostName;
    private int serverPort;

    public departureAirportStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

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

    }

    public  void parkAtTransferGate() {
        Pilot pilot = (Pilot) Thread.currentThread();

        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()){  // waiting for the connection to be established
            try {
                pilot.sleep((long) 10);
            } catch (InterruptedException ignored){}
        }

        out = new message(messageType.PARK_AT_TRANSFER_GATE, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

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

    }

    public  void waitForNextFlight() {
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
    }

    public  void waitInQueue() {
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

    }

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
    }

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
    }

    public  void waitForNextPassenger() {
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
    }

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

    }

    public  void endOfDay() {
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
    }
    

    
    
    
}
