package server.interfaces;
import commInfra.messages.message;
import commInfra.messages.messageType;
import server.sharedRegion.genRepo;

/**
 * genRepoInterface
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class genRepoInterface {

    private genRepo genRepo;

    public genRepoInterface(genRepo genRepo){
        this.genRepo = genRepo;
    }

    public message processAndReply(message in){
        message reply = new message();
        System.out.println(in.getMessageType());

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
            default:
                System.out.println("Error in genRepo interface");
                break;

        }
        return reply;
    }

}
