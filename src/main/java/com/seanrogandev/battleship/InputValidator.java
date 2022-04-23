package com.seanrogandev.battleship;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputValidator {

    final private Scanner scan = new Scanner(System.in);
    final private Messages m = new Messages();

    InputValidator() {

    }

    private String userInput;
    private int firstLetterCoordinate,
            secondLetterCoordinate,
            firstNumberCoordinate,
            secondNumberCoordinate;

    //*** public logic method group***
    public void takeInputForShipPlacement(ShipType shipType, GameBoard gameBoard) {
        boolean inputIsValid = false;

        while(!inputIsValid) {
            //ship prompt message
            m.shipPrompt(shipType);
            //take input from keyboard
            this.userInput = scan.nextLine().strip().toLowerCase();
            try {
                //change letter/number pair into number/number coords,
                // and if theyre backwards, swap the position
                changeInputStringToCoordinatePair(userInput);
            } catch (Exception e) {

            }
            //validate ship size and location
            if (validateInput(shipType, gameBoard)) {
                inputIsValid = true;
            } else {
                m.invalidPositionWarning();
            }
        }
    }


    //*** Field accessors method group***
    public int getFirstLetterCoordinate() {
        return firstLetterCoordinate;
    }
    public void setFirstLetterCoordinate(int firstLetterCoordinate) {
        this.firstLetterCoordinate = firstLetterCoordinate;
    }

    public int getSecondLetterCoordinate() {
        return secondLetterCoordinate;
    }
    public void setSecondLetterCoordinate(int secondLetterCoordinate) {
        this.secondLetterCoordinate = secondLetterCoordinate;
    }

    public int getFirstNumberCoordinate() {
        return firstNumberCoordinate;
    }
    public void setFirstNumberCoordinate(int firstNumberCoordinate) {
        this.firstNumberCoordinate = firstNumberCoordinate;
    }

    public int getSecondNumberCoordinate() {
        return secondNumberCoordinate;
    }
    public void setSecondNumberCoordinate(int secondNumberCoordinate) {
        this.secondNumberCoordinate = secondNumberCoordinate;
    }

    //*** private Input Processing method group***
    private void changeInputStringToCoordinatePair(String userInput) {
        String[] splitInput = userInput.split("[ ]");
        //change letters to number and save as coordinate elements
        setFirstLetterCoordinate(changeLetterToNumber(splitInput[0].charAt(0)));
        setSecondLetterCoordinate(changeLetterToNumber(splitInput[1].charAt(0)));
        //if first letter is larger than second, reverse them.
        if(firstLetterCoordinate > secondLetterCoordinate) {
            setFirstLetterCoordinate(changeLetterToNumber(splitInput[1].charAt(0)));
            setSecondLetterCoordinate(changeLetterToNumber(splitInput[0].charAt(0)));
        }
        splitInput = reverseInputIfBackwards(splitInput);
        //save numbers as coordinate elements
        setFirstNumberCoordinate(Integer.parseInt(splitInput[0]));
        setSecondNumberCoordinate(Integer.parseInt(splitInput[1]));

    }
    private static String[] reverseInputIfBackwards(String[] input) {
        String[] processedInput = new String[2];
        String regex = "[a-zA-Z]";
        //remove letters from input
        input[0] = input[0].replaceAll(regex,"");
        input[1] = input[1].replaceAll(regex,"");
        //if first num is larger than second num.
        if(Integer.parseInt(input[0]) > Integer.parseInt(input[1])) {
            //swap the two
            processedInput[1] = input[0];
            processedInput[0] = input[1];
        }
        if(!(Integer.parseInt(input[0]) > Integer.parseInt(input[1]))) {
            processedInput = input;
        }


        return processedInput;
    }

    private int changeLetterToNumber(char input) {
        int number = 0;
        switch(input) {
            case 'a' : number = 1;
                break;
            case 'b' : number = 2;
                break;
            case 'c' : number = 3;
                break;
            case 'd' : number = 4;
                break;
            case 'e': number = 5;
                break;
            case 'f' : number = 6;
                break;
            case 'g' : number = 7;
                break;
            case 'h' : number = 8;
                break;
            case 'i' : number = 9;
                break;
            case 'j' : number = 10;
            default:
                break;
        }
        return number;
    }
    private String changeNumToLetter(int number) {
        switch (number) {
            case 1 : {
                return "a";
            }
            case 2 : {
                return "b";
            }
            case 3 : {
                return "c";
            }
            case 4: {
                return "d";
            }
            case 5 : {
                return "e";
            }
            case 6 : {
                return "f";
            }
            case 7 : {
                return "g";
            }
            case 8 : {
                return "h";
            }
            case 9 : {
                return "i";
            }
            case 10 : {
                return "j";
            }

        }
        return "x";
    }


    //*** private Input Validation method group***
    private boolean validateInput(ShipType shipType, GameBoard gameBoard) {
        boolean inputIsValid = false;
        //check if ship is being placed vertically or horizontally
        int orientation = checkOrientation();
        //if orientation is valid
        if(orientation != 2) {
            //validate size of ship,
            // and check perimeter
            // of selection for existing ships
            return validateSize(shipType, orientation) && checkPerimeter(gameBoard, orientation);
        }
        return false;
    }
    private int checkOrientation() {
        int orientation = 2; //0 for horizontal, 1 for vertical. 2 for invalid
        //if both letter inputs are the same, ship is horizontal
        if(firstLetterCoordinate == secondLetterCoordinate) orientation = 0;
        //if letter input is not the same but numbers are, ship is vertical
        if((firstLetterCoordinate != secondLetterCoordinate) && (firstNumberCoordinate == secondNumberCoordinate)) {
            orientation = 1;
        }
        //if invalid throw a warning
        if(orientation == 2) {
            m.invalidPositionWarning();
        }
        return orientation;
    }
    private boolean validateSize(ShipType ship , int orientation) {
        //based on orientation, measures size of selection with expected size of ship being placed
        switch (orientation) {
            //horizontal
            case 0: {
                if ((secondNumberCoordinate - firstNumberCoordinate) == (ship.size - 1) || (firstNumberCoordinate - secondNumberCoordinate) == (ship.size - 1) ) return true;
            }
            break;
            //vertical
            case 1: {
                if(((secondLetterCoordinate - firstLetterCoordinate)) == (ship.size -1) || (firstLetterCoordinate - secondLetterCoordinate) ==(ship.size -1)) return true;
            }
            break;
        }
        return false;
    }

    private char whatSide() {
        char side = 'I'; //I = invalid, N = top, S = bottom, W = left side, E = right side, C = not at border
        //if both coord nums are 10, right side, if 1, left side
        boolean onBorder = false;
        if(firstNumberCoordinate == 10
                || secondNumberCoordinate == 10) {
            side = 'E';
            onBorder = true;
        }
        if(firstNumberCoordinate == 1
                || secondNumberCoordinate == 1) {
            side = 'W';
            onBorder = true;
        }
        //if both coord letters are 10, bottom side, if 1, top side
        if(firstLetterCoordinate == 10
                || secondLetterCoordinate == 10) {
            side = 'S';
            onBorder = true;
        }
        if(firstLetterCoordinate == 1
                || secondLetterCoordinate == 1) {
            side = 'N';
            onBorder = true;
        }
        if(!onBorder) {
            side = 'C';
            return side;
        }
        return side;
    }
    private boolean checkPerimeter(GameBoard gameBoard , int orientation) {
        //0 horizontal 1 vertical
        if(orientation == 0) {
            for(int i = getFirstNumberCoordinate(); i<getSecondNumberCoordinate(); i++) {
                String address = changeNumToLetter(getFirstLetterCoordinate()) + i;
                for(GameSquare g : gameBoard.getGameBoard()) {
                    if(g.getAddress().equals(address) && g.isActiveExclusionZone()) {
                        return false;
                    }
                }
            }
        } else {
            if(getFirstLetterCoordinate() < getSecondLetterCoordinate()) {
                for(int i = getFirstLetterCoordinate(); i<=getSecondLetterCoordinate(); i++) {
                    String address = changeNumToLetter(i) + getFirstNumberCoordinate();
                    for(GameSquare g : gameBoard.getGameBoard()) {
                        if(g.getAddress().equals(address) && g.isActiveExclusionZone()) {
                            return false;
                        }
                    }
                }
            }
            else {
                for(int i = getFirstLetterCoordinate(); i > getSecondLetterCoordinate(); i--) {
                    String address = changeNumToLetter(i) + getFirstNumberCoordinate();
                    for(GameSquare g : gameBoard.getGameBoard()) {
                        if(g.getAddress().equals(address) && g.isActiveExclusionZone()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
