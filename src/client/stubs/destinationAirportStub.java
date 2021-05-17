package client.stubs;

import client.entity.Passenger;
import client.entity.Pilot;
import communication.ClientCom;
import messages.message;
import messages.messageType;

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

        return in.getaBoolean();

    }

}
