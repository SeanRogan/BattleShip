package com.seanrogandev.battleship;


public class GameSquare {
    public GameSquare(String address, Character state) {
        this.address = address;
        this.state = state;
    }

    private boolean squareIsOccupied;
    private ShipType shipOnSquare;
    private final String address;
    private Character state;
    private boolean activeExclusionZone;

    public boolean isActiveExclusionZone() {
        return activeExclusionZone;
    }
    public void setActiveExclusionZone(boolean activeExclusionZone) {
        this.activeExclusionZone = activeExclusionZone;
    }

    public boolean isSquareIsOccupied() {
        return squareIsOccupied;
    }

    public void setSquareIsOccupied(boolean squareIsOccupied) {
        this.squareIsOccupied = squareIsOccupied;
    }

    public ShipType getShipOnSquare() {
        return shipOnSquare;
    }

    public void setShipOnSquare(ShipType shipOnSquare) {
        this.shipOnSquare = shipOnSquare;
    }

    public String getAddress() {
        return address;
    }

    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }


}