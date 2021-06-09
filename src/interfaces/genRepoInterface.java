package interfaces;

import java.rmi.Remote;

public interface genRepoInterface extends Remote {

    void setFlightNumber(int flightNumber);

    void setPilotState(String state);

    void setHostessState(String state);

    void setPassengerState(int id, String state);

    void setNumberOfPassengersArrived(Integer numberOfPassengersArrived);

    void setPassengerInFlight(int passengerInFlight);

    void setPassengerCheckedId(int passengerCheckedId);

    void setPassengersInQueue(int passengersInQueue);

    void reportBoardingStarted();

    void reportPassengerChecked();

    void reportFlightDeparted();

    void reportFlightArrived();

    void reportFlightReturning();

    void finalReport();

    void reportStatus();
}
