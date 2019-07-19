import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        int numberToGuess = (int) (Math.random()*100);
        Scanner input = new Scanner(System.in);
        int inputNumber = 0;
        int countGuesses = 1;

        System.out.println("Guess the number between 0 and 100");

        do {
            System.out.print("My guess: ");
            inputNumber = input.nextInt();
            if (inputNumber == numberToGuess) {
                System.out.println("Correct!");
                System.out.println("Number of guesses: "+countGuesses);
                break;
            }
            else if (inputNumber < numberToGuess) {
                System.out.println("Too low...");
            }
            else if (inputNumber > numberToGuess) {
                System.out.println("Too high...");
            }
            countGuesses++;
        }
        while (true);
    }
}
