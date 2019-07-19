package dev.fullslack.tictactoe;

import static java.lang.System.exit;

public class Board {/*
    private final static int BOARD_SIZE = 9;
    private final static int ROW_SIZE = (int)Math.sqrt((double)BOARD_SIZE);
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Field[] fields;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        currentPlayer = player1;

        fields = new Field[BOARD_SIZE];
        createFields();
    }

    private void createFields() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            fields[i] = new Field(i+1);
        }
    }

    public void play() {
        boolean continueGame = true;
        while (continueGame) { //is game not finished?
            //print board
            printBoard();

            //player selects field
            System.out.println("Current player: " + currentPlayer.getName());
            //int value = validateInput();
            boolean result = setField(getValidInput());
            //check if player has won
            if (checkForWin()) {
                System.out.println("Winner found! Congratulations " + currentPlayer.getName());
                System.out.println("Current score: " + player1.getName() + " " + player1.getScore() + " vs "
                                    + player2.getName() + " " + player2.getScore());
                System.out.println("Play new game? Y/N");
                String newGameString = TicTacToe.input.next();
                if (newGameString.equalsIgnoreCase("y")) {
                    continueGame = true;
                    createFields();
                    switchCurrentPlayer();
                } else {
                    continueGame = false;
                }
            } else {
                if (result) {
                    //switch current player
                    switchCurrentPlayer();
                } else {
                    System.out.println("ERROR: This field has already been set!");
                }
            }
        }
    }

    private int getValidInput() {
        int value = 0;
        do {
            System.out.println("Please select a field");
            while (!TicTacToe.input.hasNextInt()) {
                String temp = TicTacToe.input.next();
                if (temp.equalsIgnoreCase("q") ||
                        temp.equalsIgnoreCase("quit")) {
                    System.out.println("Have a nice day!");
                    exit(0);
                } else {
                    System.out.println("Enter a number between 1 and " + BOARD_SIZE);
                    //TicTacToe.input.next();
                }
            }
            value = TicTacToe.input.nextInt();
        } while ((value <= 0) || (value > BOARD_SIZE));
        return value;
    }

    private void printBoard() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                int arrayPointer = i * ROW_SIZE + j;
                //is field set?
                if (fields[arrayPointer].isSet()) {
                    //yes: print player symbol
                    char symbol = fields[arrayPointer].getPlayer().getSymbol();
                    System.out.print(symbol + " ");
                } else {
                    //no: print field number
                    int number = fields[arrayPointer].getNumber();
                    System.out.print(number + " ");
                }
            }
            System.out.println();
        }
    }

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;

        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    private boolean setField(int fieldNumber) {
        //check for valid input
        if (fieldNumber > 0 && fieldNumber < 10) {
            //check if field is set
            if (!fields[fieldNumber - 1].isSet()) {
                fields[fieldNumber - 1].setPlayer(currentPlayer);
                return true;
            }
        }
        return false;
    }

    private boolean checkForWin() {
        boolean gameIsWon = false;
        if (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()) {
            gameIsWon = true;
            currentPlayer.addScore();
        }
        return gameIsWon;
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < BOARD_SIZE; i += ROW_SIZE) {
            if (fields[i].isSet() && fields[i+1].isSet() && fields[i+2].isSet()) {
                if (checkRowCol(fields[i].getPlayer().getSymbol(), fields[i+1].getPlayer().getSymbol(), fields[i+2].getPlayer().getSymbol())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < ROW_SIZE; i++) {
            if (fields[i].isSet() && fields[i+ROW_SIZE].isSet() && fields[i+(ROW_SIZE*2)].isSet()) {
                if (checkRowCol(fields[i].getPlayer().getSymbol(), fields[i+ROW_SIZE].getPlayer().getSymbol(), fields[i+(ROW_SIZE*2)].getPlayer().getSymbol())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        if (fields[0].isSet() && fields[ROW_SIZE+1].isSet() && fields[(ROW_SIZE*2)+2].isSet()) {
            if (checkRowCol(fields[0].getPlayer().getSymbol(), fields[ROW_SIZE+1].getPlayer().getSymbol(), fields[(ROW_SIZE*2)+2].getPlayer().getSymbol())) {
                return true;
            }
        }
        if (fields[2].isSet() && fields[ROW_SIZE+1].isSet() && fields[(ROW_SIZE*2)].isSet()) {
            if (checkRowCol(fields[2].getPlayer().getSymbol(), fields[ROW_SIZE+1].getPlayer().getSymbol(), fields[(ROW_SIZE*2)].getPlayer().getSymbol())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        return (c1 == c2) && (c2 == c3);
    }*/
}
