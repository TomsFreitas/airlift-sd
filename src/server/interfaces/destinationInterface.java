package server.interfaces;

import commInfra.messages.*;
import server.proxies.destinationProxy;
import server.sharedRegion.destinationAirport;

/**
 * destinationInterface
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */


public class destinationInterface {
    private destinationAirport destinationAirport;

    public destinationInterface(destinationAirport destinationAirport){
        this.destinationAirport = destinationAirport;
    }

    /**
     *
     * @param in message with the request
     * @return reply message
     */
    public message process(message in){
        message reply = new message();              // implementar construtor
        destinationProxy proxy = (destinationProxy) Thread.currentThread();

        switch (in.getMessageType()){
            case FLY_TO_DEPARTURE_POINT:
                // serviceProviderProxy implementar
                destinationAirport.flyToDeparturePoint();
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            default:
                System.out.println("Error in destination interface");
        }

        return reply;
    }
}
