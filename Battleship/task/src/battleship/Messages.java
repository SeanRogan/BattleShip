package battleship;

import java.io.IOException;

//*****This class contains methods
// to print status messages to the command line******
public class Messages {
    //winner message
    public void declareWinner() {
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
    //take turn message
    public void takeTurn(int player) {
        System.out.printf("Player %d, its your turn:\n" , player);
    }
    //place your ships on the field message
    public void placeShips(int player) {
        System.out.printf("Player %d, place your ships on the game field\n\n" , player);

    }
    //pass the move message
    public void pressEnter() {
            System.out.println("Press Enter and pass the move to another player");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    //sunk ship message
    public void sunk() {
        System.out.println("You sank a ship!");
    }
    //place ship prompt message - takes the shiptype and changes the prompt message based on its name and size
    public void shipPrompt(ShipType shipType){
        String shipPrompt = "Enter the coordinates of the %s (%d cells):\n";
        System.out.printf(shipPrompt, shipType.name, shipType.size);
    }
    //invalid position warning message
    public void invalidPositionWarning() {
        System.out.print( "Error! Invalid ship location! Try again:\n");
    }
    //cannon hit ship message
    public void hit() {
        System.out.println("You hit a ship!");
    }
    //cannon missed message
    public void miss() {
        System.out.println("You missed!");
    }
}
