package client.stubs;

import client.entity.Passenger;
import client.entity.Pilot;
import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

public class destinationAirportStub {

    private String serverHostName;
    private int serverPort;

    public destinationAirportStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

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
