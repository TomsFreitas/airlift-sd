package sharedRegion;

import entity.Passenger;

import java.util.ArrayList;

public class Plane {
    private ArrayList<Passenger> seats;
    private int occupiedSeats;

    public Plane(){
        this.occupiedSeats = 0;
    }


    public void boardThePlane() {
        this.occupiedSeats++;
    }
}
