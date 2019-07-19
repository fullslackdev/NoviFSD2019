package dev.fullslack.tictactoe;

import java.util.Scanner;

public class App {
    protected static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Player 1, what is your name?");
        String name = input.nextLine();
        Player player1 = new Player(name, 'X');

        System.out.println("Player 2, what is your name?");
        name = input.nextLine();
        Player player2 = new Player(name, 'O');

        //Board board = new Board(player1, player2);

        //board.play();

        FourInARow fourInARow = new FourInARow(player1, player2);
        fourInARow.play();

        //TicTacToe ticTacToe = new TicTacToe(player1, player2);
        //ticTacToe.play();
    }
}
