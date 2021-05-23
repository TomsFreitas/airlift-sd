package server.interfaces;

import commInfra.messages.*;
import commInfra.states.pilotStates;
import server.proxies.destinationProxy;
import server.sharedRegion.destinationAirport;
import server.servers.destinationServer;

/**
 * Destination Airport Interface
 *
 * <p>
 *     It provides the necessary states in destination airport.
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */


public class destinationInterface {
    private destinationAirport destinationAirport;

    /**
     * Instantiation
     * 
     * @param destinationAirport Destination Airport
     */
    public destinationInterface(destinationAirport destinationAirport){
        this.destinationAirport = destinationAirport;
    }

    /**
     * Processing the messages by performing the respective task.
     * Generates a response message.
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
                reply.setPilotStates(pilotStates.FLYING_BACK);
                break;
            case LEAVE:
                destinationAirport.leave();
                reply.setMessageType(messageType.ACK);
                break;
            case END_OF_DAY:
               boolean aBoolean = destinationAirport.endOfDay();
               reply.setaBoolean(aBoolean);
               reply.setMessageType(messageType.ACK);
               break;
            case SHUTDOWN:
                destinationServer.waitConnection = false;
                proxy.getSconi().setTimeout(1);
                reply.setMessageType(messageType.ACK);
                break;
            default:
                System.out.println("Error in destination interface");
        }

        return reply;
    }
}
