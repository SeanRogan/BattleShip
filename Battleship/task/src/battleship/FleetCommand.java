package battleship;


import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

public class FleetCommand {

    public boolean isFleetSunk() {
        return fleetIsSunk;
    }

    public void setFleetIsSunk(boolean fleetIsSunk) {
        this.fleetIsSunk = fleetIsSunk;
    }

    public int getShipsInFleetSunk() {
        return shipsInFleetSunk;
    }

    public void setShipsInFleetSunk(int shipsInFleetSunk) {
        this.shipsInFleetSunk = shipsInFleetSunk;
    }

    private int shipsInFleetSunk;
    private boolean fleetIsSunk;
    private final GameBoard gameBoard;
    private final GameBoard gameBoardFog;

    public FleetCommand(GameBoard gameBoard, GameBoard gameBoardFog) {
        this.gameBoard = gameBoard;
        this.gameBoardFog = gameBoardFog;
        this.fleetIsSunk =false;
    }

    public void positionTheFleet(GameBoard gameBoard, GameBoard gameBoardFog,  InputValidator iV) {

        //iterate through each shiptype
        for(ShipType ship: ShipType.values() ) {
            //place ship prompt message
            //take input, validate it, throw exception if its wrong.

            iV.takeInputForShipPlacement(ship, gameBoard);
            //place ship
            placeShip(gameBoard, gameBoardFog, ship, iV);
            gameBoard.printBoard();
        }
    }

    private void placeShip(GameBoard gameBoard, GameBoard gameBoardFog, ShipType shipType, InputValidator iV) {
        //horizontal
        if(iV.getFirstLetterCoordinate() == iV.getSecondLetterCoordinate()) {
            for(int x = iV.getFirstNumberCoordinate(); x <= iV.getSecondNumberCoordinate(); x++) {
                String address = changeNumToLetter(iV.getFirstLetterCoordinate()) + x;
                String sqAbove = changeNumToLetter(iV.getFirstLetterCoordinate() - 1) + x;
                String sqBelow = changeNumToLetter(iV.getFirstLetterCoordinate() + 1) + x;
                String sqToRight = changeNumToLetter(iV.getFirstLetterCoordinate()) + (iV.getSecondNumberCoordinate() + 1);
                String sqToLeft = changeNumToLetter(iV.getFirstLetterCoordinate()) + (iV.getFirstNumberCoordinate() - 1);
                for(GameSquare g : gameBoard.getGameBoard()) {
                    if((g.getAddress().equals(address)) && !g.isActiveExclusionZone()){
                        g.setShipOnSquare(shipType);
                        g.setState(SquareState.SHIP.c);
                        g.setSquareIsOccupied(true);
                        g.setActiveExclusionZone(true);
                        for(GameSquare gF : gameBoardFog.getGameBoard())  {
                            if((gF.getAddress().equals(address)) && !gF.isActiveExclusionZone()){
                                gF.setShipOnSquare(shipType);
                                gF.setSquareIsOccupied(true);
                                gF.setActiveExclusionZone(true);
                            }
                        }
                    }
                    if(g.getAddress().equals(sqAbove) ||
                            g.getAddress().equals(sqBelow) ||
                            g.getAddress().equals(sqToLeft) ||
                            g.getAddress().equals(sqToRight)) {
                        g.setActiveExclusionZone(true);
                    }
                }
            }
        }
        //vertical
        //if first letter value is smaller than second letter
        if(iV.getFirstLetterCoordinate() < iV.getSecondLetterCoordinate()) {
            //place ship on board bottom to top
            for (int x = iV.getFirstLetterCoordinate(); x <= iV.getSecondLetterCoordinate(); x++) {
                //init address string and exclusion zone address strings
                String address = changeNumToLetter(x) + iV.getFirstNumberCoordinate();
                String sqAbove = changeNumToLetter(iV.getFirstLetterCoordinate() - 1) + iV.getFirstNumberCoordinate();
                String sqBelow = changeNumToLetter(iV.getSecondLetterCoordinate() + 1) + iV.getFirstNumberCoordinate();
                String sqToRight = changeNumToLetter(x) + iV.getFirstNumberCoordinate() + 1;
                String sqToLeft = changeNumToLetter(x) + iV.getFirstNumberCoordinate() + 1;
                //search over gamesquares for ones with matching address,
                for(GameSquare g : gameBoard.getGameBoard()) {
                    if((g.getAddress().equals(address))  && !g.isActiveExclusionZone()) {
                        // when found update ship on square and state character
                        g.setShipOnSquare(shipType);
                        g.setState(SquareState.SHIP.c);
                        g.setSquareIsOccupied(true);
                        g.setActiveExclusionZone(true);
                        for(GameSquare gF : gameBoardFog.getGameBoard()) {
                            if(gF.getAddress().equals(address)) {
                                gF.setShipOnSquare(shipType);
                                gF.setSquareIsOccupied(true);
                                gF.setActiveExclusionZone(true);
                            }
                        }
                    }
                    if(g.getAddress().equals(sqAbove) ||
                            g.getAddress().equals(sqBelow) ||
                            g.getAddress().equals(sqToRight) ||
                            g.getAddress().equals(sqToLeft)
                    ) {
                        g.setActiveExclusionZone(true);
                    }
                }
            }
        }
    }

    public void fireCannon(GameBoard gameBoard, GameBoard gameBoardFog) {

        Scanner scan = new Scanner(System.in);
        Messages m = new Messages();
        String letterRegex = "[a-zA-Z]";
        int targetLetterCoord = 0;
        int targetNumberCoord;
        try {
            while (targetLetterCoord == 0) {
                //take input from console
                String target = scan.nextLine().toLowerCase();
                //change input from letter/number to number/number coordinates

                targetLetterCoord = changeLetterToNumber(target.charAt(0));
                //this line below uses a regex pattern to delete the letter from the input and save the remaining number
                targetNumberCoord = Integer.parseInt(target.replaceAll(letterRegex, ""));
                int index = getIndex(targetLetterCoord, targetNumberCoord);

                //if the changeLetterToNumber returns a 0, it was an invalid shot so throw an exception.
                if (targetLetterCoord == 0 || targetNumberCoord > 10) {
                    System.out.println("Error! You entered the wrong coordinates!");
                    break;
                    //if targetNumberCoord >0 and <=10 the shot is valid
                } else {

                    gameBoardFog: for (GameSquare gfog : gameBoardFog.getGameBoard()) {
                        //find square address matching target
                        if (gfog.getAddress().equals(target)) {
                            //if there is a ship
                            if (gfog.isSquareIsOccupied()) {
                                //if square is set to fog,
                                if (gfog.getState().equals(SquareState.FOG.c)) {
                                    //set to hit state,
                                    gfog.setState(SquareState.HIT.c);
                                    //this.gameBoard.getGameBoardElement(index-1).setState(SquareState.HIT.c);
                                    //if ship is not sunk
                                    if ((getShipsInFleetSunk() <= 4) && !isShipSunk(gameBoardFog, gfog.getShipOnSquare())) {
                                        //trigger hit message
                                        System.out.println();
                                        m.hit();

                                        break gameBoardFog;
                                        //else , while not on last ship, trigger ship is sunk message
                                    } else {
                                        if (getShipsInFleetSunk() <= 4 && isShipSunk(gameBoardFog, gfog.getShipOnSquare())) {
                                            if (getShipsInFleetSunk() < 4) {
                                                System.out.println();
                                                setShipsInFleetSunk(++shipsInFleetSunk);
                                                m.sunk();
                                                break gameBoardFog;
                                            } else {
                                                setFleetIsSunk(true);
                                                m.declareWinner();
                                            }
                                        }
                                    }

                                }
                            }
                            //if there is no ship
                            if (!gfog.isSquareIsOccupied()) {

                                //set squareState to miss
                                gfog.setState(SquareState.MISS.c);
                                gameBoard.getGameBoardElement(index).setState(SquareState.MISS.c);

                                //trigger miss message
                                System.out.println();

                                m.miss();
                            }

                        }


                    }
                     gameboard: for (GameSquare g : gameBoard.getGameBoard()) {
                        //find square address matching target
                        if (g.getAddress().equals(target)) {
                            //if there is a ship
                            if (g.isSquareIsOccupied()) {
                                //if square is set to ocean, then
                                    //set to hit state,
                                    g.setState(SquareState.HIT.c);
                                    break gameboard;
                            }
                            //if there is no ship
                            if (!g.isSquareIsOccupied()) {
                                //set squareState to miss
                                g.setState(SquareState.MISS.c);
                                break gameboard;
                            }
                        }
                    }
                }
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println("Error! You entered the wrong coordinates!");
        }
    }

    private int getIndex(int letterCoord , int numberCoord) {
        if(numberCoord < 10 && letterCoord <10) {
            return Integer.parseInt(String.valueOf(letterCoord) +
                    numberCoord) - 10;
        }

        if(numberCoord == 10 && letterCoord < 10) {
            return Integer.parseInt(String.valueOf(letterCoord+1) + 0);
        }

        if(letterCoord == 10 && numberCoord < 10) {
            return Integer.parseInt(String.valueOf(letterCoord-1) + numberCoord);
        }

        if(letterCoord == 10 && numberCoord == 10) {
            return 100;
        }
        return -1;
    }

    private boolean isShipSunk(GameBoard gameBoard, ShipType shipType) {
        int hits = 0;

        boolean sunk = false;

        // loop through fog of war board
       gameBoard: for (GameSquare gF : gameBoard.getGameBoard()) {
            //check ship on square isnt null or the .equals below throws a null pointer exception
            if (gF.getShipOnSquare() != null) {
                //if Square is occupied by shiptype given, and the square has been hit,
                if ((gF.getShipOnSquare().equals(shipType)) && (gF.getState().equals(SquareState.HIT.c))) {
                    //increment the "hits" count.
                    hits++;

                    if(hits == shipType.size) {
                        sunk = true;
                        break gameBoard;

                    }
                }
            }
        }
        return sunk;
    }

    private static int changeLetterToNumber(char letter) {

        switch(letter) {

            case 'a' : {return 1;}

            case 'b' : {return 2;}

            case 'c' : {return 3;}

            case 'd' : {return 4;}

            case 'e' : {return 5;}

            case 'f' : {return 6;}

            case 'g' : {return 7;}

            case 'h' : {return 8;}

            case 'i' : {return 9;}

            case 'j' : {return 10;}

        }
        return 0;
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

}
