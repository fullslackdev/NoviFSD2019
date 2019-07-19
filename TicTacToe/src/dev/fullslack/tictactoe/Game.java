package dev.fullslack.tictactoe;

import static java.lang.System.exit;

public abstract class Game {
    private Player player1;
    private Player player2;
    protected Player emptyPlayer = new Player("empty", 'E');
    protected Player currentPlayer;
    protected Field[] fields;
    protected static int BOARD_SIZE;
    protected static int ROW_SIZE;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        currentPlayer = player1;
    }

    public void play() {
        /*while(true) { //is game not finished?
            printBoard();

            System.out.println("Current player " + currentPlayer.getName());
            System.out.println("Please select a field");
            int value = App.input.nextInt();

            //player selects a field
            boolean result = setField(value);
            if (result) {
                //switch current player
                switchCurrentPlayer();
            }

            //check if player has won
        }*/
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
                String newGameString = App.input.next();
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

    private void createFields() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            fields[i] = new Field(i+1);
            fields[i].setPlayer(emptyPlayer);
        }
    }

    protected abstract void printBoard();

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;

        /*if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }*/
    }

    protected abstract boolean setField(int fieldNumber);

    //protected abstract boolean checkForWin();
    protected boolean checkForWin() {
        boolean gameIsWon = false;
        if (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()) {
            gameIsWon = true;
            currentPlayer.addScore();
        }
        return gameIsWon;
    }

    protected abstract boolean checkRowsForWin();

    protected abstract boolean checkColumnsForWin();

    protected abstract boolean checkDiagonalsForWin();

    protected abstract boolean gameHasEnded();

    private int getValidInput() {
        int value = 0;
        do {
            System.out.println("Please select a field");
            while (!App.input.hasNextInt()) {
                String temp = App.input.next();
                if (temp.equalsIgnoreCase("q") ||
                        temp.equalsIgnoreCase("quit")) {
                    System.out.println("Have a nice day!");
                    exit(0);
                } else {
                    System.out.println("Enter a number between 1 and 9");
                    //TicTacToe.input.next();
                }
            }
            value = App.input.nextInt();
        } while ((value <= 0) || (value > 9));
        return value;
    }
}