package client.stubs;

import client.entity.Hostess;
import client.entity.Pilot;
import communication.ClientCom;
import messages.message;
import messages.messageType;

public class genRepoStub {

    private String serverHostName;
    private int serverPort;

    public genRepoStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

    public void setFlightNumber(int flightNumber){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()); // waiting for the connection to be established


        out = new message(messageType.SET_FLIGHT_NUMBER, -1);
        out.setAnInt(flightNumber);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setPilotState(String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()); // waiting for the connection to be established

        out = new message(messageType.SET_PILOT_STATE, -1);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setHostessState(String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PILOT_STATE, -1);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setPassengerState(int id, String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PILOT_STATE, id);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();

    }




}
