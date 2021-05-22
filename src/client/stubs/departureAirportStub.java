package client.stubs;

import client.entity.Hostess;
import client.entity.Passenger;
import client.entity.Pilot;
import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

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
        pilot.setState(in.getPilotStates());
        con.close();


    }

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
