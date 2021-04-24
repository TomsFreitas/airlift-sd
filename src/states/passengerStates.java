package states;

public enum passengerStates {


    GOING_TO_AIRPORT("GTAP"),
    IN_QUEUE("INQE"),
    IN_FLIGHT("INFL"),
    AT_DESTINATION("ATDS");

    private String state;

    passengerStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
