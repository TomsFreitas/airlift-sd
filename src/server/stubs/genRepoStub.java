package server.stubs;

import commInfra.communication.ClientCom;
import commInfra.messages.message;
import commInfra.messages.messageType;

/**
 * Exposes General Repository server to the client side
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class genRepoStub {

    private String serverHostName;
    private int serverPort;

    /**
     * General Repository Stub Instantiation
     *
     * @param serverHostName Name of the computational system where the server is located
     * @param serverPort Server listening port
     */
    public genRepoStub(String serverHostName, int serverPort){
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
    }

    /**
     *  Set flight number
     *  @param flightNumber number of the actual flight
     * Service Solicitation.
     */
    public void setFlightNumber(int flightNumber){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()); // waiting for the connection to be established


        out = new message(messageType.SET_FLIGHT_NUMBER, -1);
        out.setAnInt(flightNumber);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set pilotState.
     *  Service Solicitation.
     *
     *  @param state state of the pilot
     */
    public void setPilotState(String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open()); // waiting for the connection to be established

        out = new message(messageType.SET_PILOT_STATE, -1);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set hostessState.
     *  Service Solicitation.
     *
     *  @param state state of the hostess
     */
    public void setHostessState(String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_HOSTESS_STATE, -1);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set state of passenger.
     *  Service Solicitation.
     *
     *  @param id id passenger to set state
     *  @param state state of the passenger
     */
    public void setPassengerState(int id, String state){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_STATE, id);
        out.setaString(state);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set number of passengers arrived.
     *  Service Solicitation.
     *
     *  @param numberOfPassengersArrived number of passengers at destination
     */
    public void setNumberOfPassengersArrived(int numberOfPassengersArrived){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_NUMBER_OF_PASSENGERS_ARRIVED, -1);
        out.setAnInt(numberOfPassengersArrived);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set passenger in flight.
     *  Service Solicitation.
     *
     *  @param passengerInFlight Number of passengers in flight
     */
    public void setPassengerInFlight(int passengerInFlight){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_IN_FLIGHT, -1);
        out.setAnInt(passengerInFlight);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set passenger checked.
     *  Service Solicitation.
     *
     *  @param passengerCheckedId ID of passenger being checked
     */
    public void setPassengerCheckedId(int passengerCheckedId){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGER_CHECKED_ID, -1);
        out.setAnInt(passengerCheckedId);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     *  Set passengers in transfer gate (queue).
     *  Service Solicitation.
     *
     *  @param passengersInQueue Number of passengers in Queue
     */
    public void setPassengersInQueue(int passengersInQueue){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.SET_PASSENGERS_IN_QUEUE, -1);
        out.setAnInt(passengersInQueue);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Passenger checked.
     * Service Solicitation.
     */
    public void reportPassengerChecked(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_PASSENGER_CHECKED, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Flight departed.
     * Service Solicitation.
     */
    public void reportFlightDeparted(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_DEPARTED, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Flight arrived.
     * Service Solicitation.
     */
    public void reportFlightArrived(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_ARRIVED, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Flight returning.
     * Service Solicitation.
     */
    public void reportFlightReturning(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_FLIGHT_RETURNING, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Calculate the Airlift sum up (number of passengers transported per flight).
     * Service Solicitation.
     */
    public void finalReport(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.FINAL_REPORT, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Report Status.
     * Service Solicitation.
     */
    public void reportStatus(){
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_STATUS, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();


    }

    /**
     * Boarding started
     * Service Solicitation.
     */
    public void reportBoardingStarted() {
        ClientCom con = new ClientCom(serverHostName, serverPort);
        message in, out;
        while(!con.open());

        out = new message(messageType.REPORT_BOARDING_STARTED, -1);
        con.writeObject(out);

        in = (message) con.readObject();
        con.close();

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
