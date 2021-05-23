package server.interfaces;
import commInfra.messages.message;
import commInfra.messages.messageType;
import server.proxies.genRepoProxy;
import server.servers.genRepoServer;
import server.sharedRegion.genRepo;

/**
 * General Repository Interface
 *
 * <p>
 *     It provides the necessary states in general repository.
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class genRepoInterface {

    private genRepo genRepo;

    /**
     * Instantiation
     * 
     * @param genRepo General Repository Information
     */
    public genRepoInterface(genRepo genRepo){
        this.genRepo = genRepo;
    }

    /**
     * Processing the messages by performing the respective task.
     * Generates a response message.
     *
     * @param in message with the request
     * @return reply message
     */
    public message processAndReply(message in){
        message reply = new message();
        System.out.println(in.getMessageType());
        genRepoProxy proxy = (genRepoProxy) Thread.currentThread();

        switch (in.getMessageType()){

            case SET_FLIGHT_NUMBER:
                genRepo.setFlightNumber(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_PILOT_STATE:
                genRepo.setPilotState(in.getaString());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_HOSTESS_STATE:
                genRepo.setHostessState(in.getaString());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_PASSENGER_STATE:
                genRepo.setPassengerState(in.getID(),in.getaString());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_NUMBER_OF_PASSENGERS_ARRIVED:
                genRepo.setNumberOfPassengersArrived(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_PASSENGER_IN_FLIGHT:
                genRepo.setPassengerInFlight(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_PASSENGERS_IN_QUEUE:
                genRepo.setPassengersInQueue(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                break;
            case SET_PASSENGER_CHECKED_ID:
                genRepo.setPassengerCheckedId(in.getAnInt());
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_BOARDING_STARTED:
                genRepo.reportBoardingStarted();
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_FLIGHT_ARRIVED:
                genRepo.reportFlightArrived();
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_FLIGHT_DEPARTED:
                genRepo.reportFlightDeparted();
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_FLIGHT_RETURNING:
                genRepo.reportFlightReturning();
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_PASSENGER_CHECKED:
                genRepo.reportPassengerChecked();
                reply.setMessageType(messageType.ACK);
                break;
            case REPORT_STATUS:
                genRepo.reportStatus();
                reply.setMessageType(messageType.ACK);
                break;
            case FINAL_REPORT:
                genRepo.finalReport();
                reply.setMessageType(messageType.ACK);
                break;
            case SHUTDOWN:
                genRepoServer.waitConnection = false;
                proxy.getSconi().setTimeout(1);
                reply.setMessageType(messageType.ACK);
                break;
            default:
                System.out.println("Error in genRepo interface");
                break;

        }

        return reply;
    }

}
