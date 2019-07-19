import java.util.Scanner;

public class Pyramid {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Pyramid height: ");
        int pyramidHeight = input.nextInt();

        for (int i = 0; i < pyramidHeight; i++) {
            for (int j = 0; j < pyramidHeight - i; j++) {
                System.out.print(" ");
            }
            for (int k = 0; k <= i; k++) {
                System.out.print("* ");
            }
            //System.out.print("* ");
            System.out.println();
        }

        for (int i = 0; i <= pyramidHeight; i++) {
            for (int j = 0; j < pyramidHeight - i; j++) {
                System.out.print(" ");
            }
            for (int k = 0; k < i; k++) {
                if ((pyramidHeight >= 10) && (i < 10)) {
                    System.out.print(" " + i + " ");
                } else {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }

    }
}
