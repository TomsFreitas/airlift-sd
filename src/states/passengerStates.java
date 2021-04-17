package states;

public enum passengerStates {


    GOING_TO_AIRPORT("GTA"),
    IN_QUEUE("IQ"),
    IN_FLIGHT("IF"),
    AT_DESTINATION("AD");

    private String state;

    passengerStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
