package server.interfaces;

import messages.*;
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
    public message process(message in){
        message reply = new message();              // implementar construtor
        departureProxy proxy = (departureProxy) Thread.currentThread();

        switch (in.getMessageType()){
            case PARK_AT_TRANSFER_GATE:
                // serviceProviderProxy implementar
                departureAirport.parkAtTransferGate();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case INFORM_PLANE_READY_FOR_BOARDING:
                // serviceProviderProxy implementar
                departureAirport.informPlaneReadyForBoarding();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case FLY_TO_DESTINATION_POINT:
                // serviceProviderProxy implementar
                departureAirport.flyToDestinationPoint();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                // serviceProviderProxy implementar
                departureAirport.waitForNextFlight();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case PREPARE_FOR_PASS_BOARDING:
                // serviceProviderProxy implementar
                departureAirport.prepareForPassBoarding();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case CHECK_DOCUMENTS:
                // serviceProviderProxy implementar
                departureAirport.checkDocuments();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                // serviceProviderProxy implementar
                departureAirport.waitForNextPassenger();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case WAIT_IN_QUEUE:
                // serviceProviderProxy implementar
                departureAirport.waitInQueue();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case SHOW_DOCUMENTS:
                // serviceProviderProxy implementar
                departureAirport.showDocuments();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            default:
                System.out.println("Error in departure interface");
        }

        return reply;
    }
}