package server.interfaces;

import commInfra.messages.*;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.proxies.departureProxy;
import server.sharedRegion.departureAirport;

/**
 * departureInterface
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */


public class departureInterface {
    private departureAirport departureAirport;

    public departureInterface(departureAirport departureAirport){
        this.departureAirport = departureAirport;
    }

    /**
     *
     * @param in message with the request
     * @return reply message
     */
    public message processAndReply(message in){
        message reply = new message();              // implementar construtor
        departureProxy proxy = (departureProxy) Thread.currentThread();

        switch (in.getMessageType()){
            case PARK_AT_TRANSFER_GATE:
                departureAirport.parkAtTransferGate();
                reply.setMessageType(messageType.ACK);
                reply.setPilotStates(pilotStates.AT_TRANSFER_GATE);
                break;
            case INFORM_PLANE_READY_FOR_BOARDING:
                // serviceProviderProxy implementar
                departureAirport.informPlaneReadyForBoarding();
                reply.setMessageType(messageType.ACK);
                reply.setPilotStates(pilotStates.READY_FOR_BOARDING);
                break;
            case FLY_TO_DESTINATION_POINT:
                // serviceProviderProxy implementar
                departureAirport.flyToDestinationPoint();
                reply.setMessageType(messageType.ACK);
                reply.setPilotStates(pilotStates.FLYING_FORWARD);
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                // serviceProviderProxy implementar
                departureAirport.waitForNextFlight();
                reply.setMessageType(messageType.ACK);
                reply.setHostessStates(hostessStates.WAIT_FOR_NEXT_FLIGHT);
                break;
            case PREPARE_FOR_PASS_BOARDING:
                // serviceProviderProxy implementar
                departureAirport.prepareForPassBoarding();
                reply.setMessageType(messageType.ACK);
                reply.setHostessStates(hostessStates.WAIT_FOR_PASSENGER);
                break;
            case CHECK_DOCUMENTS:
                // serviceProviderProxy implementar
                departureAirport.checkDocuments();
                reply.setMessageType(messageType.ACK);
                reply.setHostessStates(hostessStates.CHECK_PASSENGER);
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                // serviceProviderProxy implementar
                departureAirport.waitForNextPassenger();
                reply.setMessageType(messageType.ACK);
                reply.setHostessStates(hostessStates.WAIT_FOR_PASSENGER);
                break;
            case WAIT_IN_QUEUE:
                // serviceProviderProxy implementar
                departureAirport.waitInQueue();
                reply.setMessageType(messageType.ACK);
                reply.setID(in.getID());
                reply.setPassengerStates(passengerStates.IN_QUEUE);
                break;
            case SHOW_DOCUMENTS:
                // serviceProviderProxy implementar
                departureAirport.showDocuments();
                reply.setID(in.getID());
                reply.setMessageType(messageType.ACK);
                break;
            default:
                System.out.println("Error in departure interface");
        }

        return reply;
    }
}