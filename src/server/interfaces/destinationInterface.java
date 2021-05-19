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
    public message processAndReply(message in){
        message reply = new message();              // implementar construtor
        destinationProxy proxy = (destinationProxy) Thread.currentThread();
        System.out.println("------------------");
        System.out.println(in.getMessageType());
        System.out.println("-------------------");

        switch (in.getMessageType()){
            case FLY_TO_DEPARTURE_POINT:
                destinationAirport.flyToDeparturePoint();
                reply.setMessageType(messageType.ACK);
                //reply.setMessageType(messageType.FLY_TO_DEPARTURE_POINT);     // implementar
                break;
            case LEAVE:
                destinationAirport.leave();
                reply.setMessageType(messageType.ACK);
                break;
            case END_OF_DAY:
               boolean aBoolean = destinationAirport.endOfDay();
               reply.setaBoolean(aBoolean);
               reply.setMessageType(messageType.ACK);
            default:
                System.out.println("Error in destination interface");
        }

        return reply;
    }
}
