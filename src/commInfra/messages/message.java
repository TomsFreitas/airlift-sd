package commInfra.messages;

import commInfra.states.*;
import java.io.Serializable;

/**
 * This class defines the messages that are exchanged between servers and clients.
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class message implements Serializable {
    /**
     * Type of message
     */
    private messageType messageType;


    /**
     * ID of the entity that sent the message
     */
    private int ID;

    /**
     * Placeholder for a boolean value to be sent
     */
    private boolean aBoolean;

    /**
     * Placeholder for a String value to be sent
     */
    private String aString;

    /**
     * Placeholder for an int value to be sent
     */
    private int anInt;

    /**
     * To send / receive a hostess state
     */
    private hostessStates hostessStates;
    /**
     * To send / receive a pilot state
     */
    private pilotStates pilotStates;
    /**
     * To send / receive a passenger state
     */
    private passengerStates passengerStates;


    /**
     * Message constructor
     */
    public message(){}

    /**
     * Message constructor
     * @param type The type of message
     * @param id ID of the message creator
     */
    public message(messageType type, int id){
        this.messageType = type;
        this.ID = id;

    }

    /**
     * Sets the ID
     *
     * @param ID Id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns the ID
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the type of the message
     *
     * @param messageType type of the message
     */
    public void setMessageType(commInfra.messages.messageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Returns type of this message.
     *
     * @return messageType
     */
    public messageType getMessageType() {
        return this.messageType;
    }

    /**
     * Sets the boolean value transported
     *
     * @param aBoolean
     */
    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    /**
     * Returns the boolean value transported
     *
     * @return aBoolean
     */
    public boolean getaBoolean(){
        return this.aBoolean;
    }

    /**
     * Sets the integer value transported
     *
     * @param anInt integer value
     */
    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    /**
     * Returns the integer value transported
     *
     * @return anInt
     */
    public int getAnInt() {
        return anInt;
    }

    /**
     * Sets the string value transported
     *
     * @param aString string value
     */
    public void setaString(String aString) {
        this.aString = aString;
    }

    /**
     * Returns the string value transported
     *
     * @return aString
     */
    public String getaString() {
        return aString;
    }

    /**
     * Get Hostess state
     *
     * @return hostess States
     */
    public hostessStates getHostessStates() {
        return hostessStates;
    }

    /**
     * Set Hostess state
     *
     * @param hostessStates New state
     */
    public void setHostessStates(hostessStates hostessStates) {
        this.hostessStates = hostessStates;
    }

    /**
     * Get Pilot state
     *
     * @return pilot States
     */
    public pilotStates getPilotStates() {
        return pilotStates;
    }

    /**
     * Set Pilot state
     *
     * @param pilotStates New state
     */
    public void setPilotStates(pilotStates pilotStates) {
        this.pilotStates = pilotStates;
    }

    /**
     * Get Passenger state
     *
     * @return passenger States
     */
    public passengerStates getPassengerStates() {
        return passengerStates;
    }

    /**
     * Set Passenger state
     *
     * @param passengerStates New state
     */
    public void setPassengerStates(passengerStates passengerStates) {
        this.passengerStates = passengerStates;
    }
    
    
}
