package battleship;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Messages m = new Messages();
//***player1 setup***
        InputValidator iV1 = new InputValidator();
        GameBoard gameBoard1 = new GameBoard(SquareState.OCEAN);
        GameBoard gameBoardFog1 = new GameBoard(SquareState.FOG);
        FleetCommand fC1 = new FleetCommand(gameBoard1,gameBoardFog1);
        m.placeShips(1);
        gameBoard1.printBoard();
        fC1.positionTheFleet(gameBoard1, gameBoardFog1, iV1);
        m.pressEnter();
//***player 2 setup***
        InputValidator iV2 = new InputValidator();
        GameBoard gameBoard2 = new GameBoard(SquareState.OCEAN);
        GameBoard gameBoardFog2 = new GameBoard(SquareState.FOG);
        FleetCommand fC2 = new FleetCommand(gameBoard2,gameBoardFog2);
        m.placeShips(2);
        gameBoard2.printBoard();
        fC2.positionTheFleet(gameBoard2, gameBoardFog2, iV2);
        m.pressEnter();

        //while the game isnt over, keep taking turns one after the other.
        while(!gameIsOver(fC1,fC2)) {
            //player one board displayed
            gameBoardFog2.printBoard();
            System.out.println("---------------------");
            gameBoard1.printBoard();
            //player one takes their turn
            m.takeTurn(1);
            //cannon fire and changes square state on opposing players
            // clear gameboard, and current players fog of war gameboard
            fC1.fireCannon(gameBoard2,gameBoardFog2);
            //press enter message to switch players
            m.pressEnter();

            //player two takes turn
            gameBoardFog1.printBoard();
            System.out.println("---------------------");
            gameBoard2.printBoard();
            m.takeTurn(2);
            fC2.fireCannon(gameBoard1,gameBoardFog1);
            m.pressEnter();
        }
        m.declareWinner();
    }

    private static boolean gameIsOver(FleetCommand fC1, FleetCommand fC2) {
        //check if either fleet is sunk, if it is return true. else return false.
        if(fC1.isFleetSunk() || fC2.isFleetSunk()) return true;
        return false;
    }


}
