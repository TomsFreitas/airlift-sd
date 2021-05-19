package client.stubs;

import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

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

        out = new message(messageType.SET_HOSTESS_STATE, -1);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setPassengerState(int id, String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_STATE, id);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setNumberOfPassengersArrived(int numberOfPassengersArrived){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_NUMBER_OF_PASSENGERS_ARRIVED, -1);
        out.setAnInt(numberOfPassengersArrived);
        con.writeObject(out);

        in = (message) con.readObject();

    }
    public void setPassengerInFlight(int passengerInFlight){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_IN_FLIGHT, -1);
        out.setAnInt(passengerInFlight);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setPassengerCheckedId(int passengerCheckedId){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_CHECKED_ID, -1);
        out.setAnInt(passengerCheckedId);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void setPassengersInQueue(int passengersInQueue){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGERS_IN_QUEUE, -1);
        out.setAnInt(passengersInQueue);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void reportPassengerChecked(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_PASSENGER_CHECKED, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void reportFlightDeparted(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_DEPARTED, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void reportFlightArrived(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_ARRIVED, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void reportFlightReturning(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_RETURNING, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void finalReport(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.FINAL_REPORT, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }

    public void reportStatus(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_STATUS, -1);
        con.writeObject(out);

        in = (message) con.readObject();

    }


    public void reportBoardingStarted() {
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_BOARDING_STARTED, -1);
        con.writeObject(out);

        in = (message) con.readObject();
    }
}
