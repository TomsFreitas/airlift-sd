package server.interfaces;

import messages.message;
import server.proxies.planeProxy;
import server.sharedRegion.Plane;

public class planeInterface {
    private Plane plane;

    public planeInterface(Plane plane){
        this.plane = plane;
    }

    public message process(message in) {
        planeProxy proxy = (planeProxy) Thread.currentThread();
        switch (in.getMessageType()){
            case BOARD_THE_PLANE:
                plane.boardThePlane();
                break;
            case WAIT_FOR_END_OF_FLIGHT:
                plane.waitForEndOfFlight();
                break;
            case INFORM_PLANE_READY_FOR_TAKEOFF:
                plane.informPlaneReadyForTakeOff();
                break;
            case WAIT_FOR_ALL_IN_BOARD:
                plane.waitForAllInBoard();
                break;
            case ANNOUNCE_ARRIVAL:
                plane.announceArrival();
                break;
            case LEAVE_THE_PLANE:
                plane.leaveThePlane();
                break;
            default:
                System.out.println("Error in plane interface");

        }
    }
}
