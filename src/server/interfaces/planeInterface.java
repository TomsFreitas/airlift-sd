package server.interfaces;

import commInfra.messages.message;
import commInfra.messages.messageType;
import commInfra.states.hostessStates;
import commInfra.states.passengerStates;
import commInfra.states.pilotStates;
import server.proxies.planeProxy;
import server.sharedRegion.Plane;
import server.servers.planeServer;

/**
 * Plane Interface
 *
 * <p>
 *     It provides message processing and method execution for this shared region.
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class planeInterface {
    private Plane plane;

    private int shutdown;

    /**
     * Instantiation
     * 
     * @param plane Plane
     */
    public planeInterface(Plane plane){
        this.plane = plane;
        this.shutdown = 0;
    }

    /**
     * Processing the messages by performing the respective task.
     * Generates a response message.
     *
     * @param in message with the request
     * @return reply message
     */
    public message processAndReply(message in) {
        message reply = new message();
        planeProxy proxy = (planeProxy) Thread.currentThread();
        System.out.println("------------------");
        System.out.println(in.getMessageType());
        System.out.println("-------------------");
        switch (in.getMessageType()){
            case BOARD_THE_PLANE:
                plane.boardThePlane();
                reply.setMessageType(messageType.ACK);
                reply.setPassengerStates(passengerStates.IN_FLIGHT);
                break;
            case WAIT_FOR_END_OF_FLIGHT:
                plane.waitForEndOfFlight();
                reply.setMessageType(messageType.ACK);
                break;
            case INFORM_PLANE_READY_FOR_TAKEOFF:
                plane.informPlaneReadyForTakeOff(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                reply.setHostessStates(hostessStates.READY_TO_FLY);
                break;
            case WAIT_FOR_ALL_IN_BOARD:
                plane.waitForAllInBoard();
                reply.setMessageType(messageType.ACK);
                reply.setPilotStates(pilotStates.READY_FOR_BOARDING);
                break;
            case ANNOUNCE_ARRIVAL:
                plane.announceArrival();
                reply.setMessageType(messageType.ACK);
                reply.setPilotStates(pilotStates.DEBOARDING);
                break;
            case LEAVE_THE_PLANE:
                plane.leaveThePlane();
                reply.setMessageType(messageType.ACK);
                reply.setPassengerStates(passengerStates.AT_DESTINATION);
                break;
            case SHUTDOWN:
                this.shutdown++;
                reply.setMessageType(messageType.ACK);
                break;
            default:
                System.out.println("Error in plane interface");

        }
        if (this.shutdown == 2){
            planeServer.waitConnection = false;
            proxy.getSconi().setTimeout(1);
            System.out.println("INITIATED SHUTDOWN");
        }

       return reply;
    }
}
