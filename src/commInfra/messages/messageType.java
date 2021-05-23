package commInfra.messages;

import java.io.Serializable;

/**
 * Enum implementing the typical message types:
 *
 * <ul>
 *     <li>PARK_AT_TRANSFER_GATE - The pilot parks the plane at transfer gate.</li>
 *     <li>INFORM_PLANE_READY_FOR_BOARDING - The pilot informs the hostess that the plane is ready for boarding.</li>
 *     <li>WAIT_FOR_ALL_IN_BOARD - The pilot waits for the hostess notifying him that the boarding is complete.</li>
 *     <li>FLY_TO_DESTINATION_POINT - The pilot flies to the destination town.</li>
 *     <li>ANNOUNCE_ARRIVAL - After landing and parking the plane at the arrival gate, the pilot announces the passengers that they may leave.</li>
 *     <li>FLY_TO_DEPARTURE_POINT - When the last passenger has exited, the pilot flies back to the origin town.</li>
 *     <li>WAIT_FOR_NEXT_FLIGHT - The hostess waits for the next flight (pilot parks the plane at the departure gate).</li>
 *     <li>WAIT_FOR_NEXT_PASSENGER - The hostess waits for passengers arrives at the transfer gate.</li>
 *     <li>CHECK_DOCUMENTS - The hostess calls a passenger to check the documents and allow him to board the plane.</li>
 *     <li>INFORM_PLANE_READY_FOR_TAKEOFF - The hostess notifies the pilot that the flight is ready to take off.</li>
 *     <li>WAIT_IN_QUEUE - The passenger queue in at transfer gate waiting to be called by the hostess.</li>
 *     <li>SHOW_DOCUMENTS - The passenger shows the documents.</li>
 *     <li>PREPARE_FOR_PASS_BOARDING - </li>
 *     <li>END_OF_DAY - </li>
 *     <li>BOARD_THE_PLANE - The passenger boards the plane.</li>
 *     <li>WAIT_FOR_END_OF_FLIGHT - </li>
 *     <li>LEAVE_THE_PLANE - The passenger leaves the plane.</li>
 *     <li>SET_FLIGHT_NUMBER - Set the flight number.</li>
 *     <li>SET_PILOT_STATE - Set the Pilot state.</li>
 *     <li>SET_HOSTESS_STATE - Set the Hostess state.</li>
 *     <li>SET_PASSENGER_STATE - Set the Passenger state.</li>
 *     <li>SET_NUMBER_OF_PASSENGERS_ARRIVED - Set the number of passengers arrived.</li>
 *     <li>SET_PASSENGER_IN_FLIGHT - Set the passenger in flight.</li>
 *     <li>SET_PASSENGER_CHECKED_ID - </li>
 *     <li>SET_PASSENGERS_IN_QUEUE - </li>
 *     <li>REPORT_BOARDING_STARTED - Report the boarding has started. </li>
 *     <li>REPORT_PASSENGER_CHECKED - Report the passenger has checked. </li>
 *     <li>REPORT_FLIGHT_DEPARTED - Report the flight has departed.</li>
 *     <li>REPORT_FLIGHT_ARRIVED - Report the flight has arrived</li>
 *     <li>REPORT_FLIGHT_RETURNING - Report the flight is returning </li>
 *     <li>REPORT_STATUS - Report the current status information.</li>
 *     <li>LEAVE - The passenger leaves de destination airport.</li>
 *     <li>FINAL_REPORT - </li>
 *     <li>ACK - </li>
 *     <li>SHUTDOWN - Shut down the server</li>
 *
 * </ul>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public enum messageType implements Serializable {

    INFORM_PLANE_READY_FOR_BOARDING,

    PARK_AT_TRANSFER_GATE,

    FLY_TO_DESTINATION_POINT,

    WAIT_FOR_NEXT_FLIGHT,

    WAIT_IN_QUEUE,

    PREPARE_FOR_PASS_BOARDING,

    CHECK_DOCUMENTS,

    WAIT_FOR_NEXT_PASSENGER,

    SHOW_DOCUMENTS,

    END_OF_DAY,

    FLY_TO_DEPARTURE_POINT,

    BOARD_THE_PLANE,

    WAIT_FOR_END_OF_FLIGHT,

    INFORM_PLANE_READY_FOR_TAKEOFF,

    WAIT_FOR_ALL_IN_BOARD,

    ANNOUNCE_ARRIVAL,

    LEAVE_THE_PLANE,

    SET_FLIGHT_NUMBER,

    SET_PILOT_STATE,

    SET_HOSTESS_STATE,

    SET_PASSENGER_STATE,

    SET_NUMBER_OF_PASSENGERS_ARRIVED,

    SET_PASSENGER_IN_FLIGHT,

    SET_PASSENGER_CHECKED_ID,

    SET_PASSENGERS_IN_QUEUE,

    REPORT_BOARDING_STARTED,

    REPORT_PASSENGER_CHECKED,

    REPORT_FLIGHT_DEPARTED,

    REPORT_FLIGHT_ARRIVED,

    REPORT_FLIGHT_RETURNING,

    REPORT_STATUS,

    LEAVE,

    FINAL_REPORT,

    ACK,

    SHUTDOWN;

}
