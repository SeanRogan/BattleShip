package com.seanrogandev.battleship;

import java.util.ArrayList;
import java.util.stream.Stream;

    public class GameBoard {

        private final ArrayList<GameSquare> gameBoard;
        //gameboard constructor to initialize a new board filled with ocean and all the address already there
        public GameBoard(SquareState state) {
            //init new gameboard
            this.gameBoard = new ArrayList<>(0);
            for(int i = 0; i < 100; i++) {
                String[] keyList = {
                        "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
                        "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "b10",
                        "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10",
                        "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10",
                        "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10",
                        "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10",
                        "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10",
                        "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "h10",
                        "i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8", "i9", "i10",
                        "j1", "j2", "j3", "j4", "j5", "j6", "j7", "j8", "j9", "j10",
                };
                gameBoard.add(i, new GameSquare(keyList[i],state.c));
            }
        }
        public Stream<GameSquare> getGameBoardStream () {
            return gameBoard.stream();
        }
        public ArrayList<GameSquare> getGameBoard() {
            return gameBoard;
        }

        public GameSquare getGameBoardElement(int index) {
            return gameBoard.get(index);
        }

        public void printBoard() {
            char start = 'A';
            String gridNumbers = " 1 2 3 4 5 6 7 8 9 10";
            System.out.println(gridNumbers);
            //this should loop only ten times but have the index be 0, 10, 20,etc.
            //to accommodate the "n+1 n+2 etc." indexing below
            for(int n = 0;n < 100; n = n + 10) {

                System.out.print(start + " ");
                start++;
                System.out.print(getGameBoardElement(n).getState() + " ");

                System.out.print(getGameBoardElement(n+1).getState() + " ");

                System.out.print(getGameBoardElement(n+2).getState() + " ");

                System.out.print(getGameBoardElement(n+3).getState() + " ");

                System.out.print(getGameBoardElement(n+4).getState() + " ");

                System.out.print(getGameBoardElement(n+5).getState() + " ");

                System.out.print(getGameBoardElement(n+6).getState() + " ");

                System.out.print(getGameBoardElement(n+7).getState() + " ");

                System.out.print(getGameBoardElement(n+8).getState() + " ");

                System.out.print(getGameBoardElement(n+9).getState() + " \n");
            }
        }
    }

