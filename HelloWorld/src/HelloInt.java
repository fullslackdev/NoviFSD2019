import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;

public class HelloInt {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Functions functions = new Functions();
        //String bla = "2019-07-16 15:14:44.827";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = formatter.format(LocalDateTime.now());
        System.out.println(formattedDate);

        System.out.println("Test data");

        /*int firstInt = 0;
        System.out.print("Input 1st integer: ");
        try {
            firstInt = input.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Vul een getal in!");
            exit(1);
        }*/

        int firstInt = -1;
        do {
            System.out.println("Input 1st integer: ");
            while(!input.hasNextInt()) {
                System.out.println("Vul een getal in!");
                input.next();
            }
            firstInt = input.nextInt();
        } while (firstInt < 0);

        //System.out.print("Input 2nd integer: ");
        //int secondInt = input.nextInt();

        //System.out.print("Diameter of circle: ");
        //float diameter = input.nextFloat();

        /*int sum = firstInt + secondInt;
        int difference = firstInt - secondInt;
        int product = firstInt * secondInt;
        float average = (firstInt + secondInt) / 2;
        int distance = Math.abs(firstInt - secondInt);
        int max = Math.max(firstInt, secondInt);
        int min = Math.min(firstInt, secondInt);

        System.out.println("Expected output");
        System.out.println("Sum of two integers: "+sum);
        System.out.println("Difference of two integers: "+difference);
        System.out.println("Product of two integers: "+product);
        System.out.println("Average of two integers: "+average);
        System.out.println("Distance of two integers: "+distance);
        System.out.println("Max integer: "+max);
        System.out.println("Min integer: "+min);*/

        /*int[] value = functions.swapNumbers(firstInt, secondInt);
        firstInt = value[0];
        secondInt = value[1];

        System.out.println("After swapping:");
        System.out.println("Input 1 is: "+firstInt);
        System.out.println("Input 2 is: "+secondInt);*/

        //System.out.println("Circumference is: "+functions.circleCircumference(diameter));
        //System.out.println("Surface area is: "+functions.circleSurfaceArea(diameter));

        //functions.calculateSquareOfSequence(firstInt,secondInt);
        //functions.printMultiplicationTable(firstInt);
        //functions.translateMonth(firstInt);
        //functions.calculateFaculty(firstInt);

        int[] array = {1,6,2,7,3,8,4,9,5,10};
        //System.out.println("Sum of array is "+functions.sumOfTable(array));

        //int[] partialArray = {1,2,3,0,0,0,0,0,0,0};
        /*int[] partialArray = new int[10];
        partialArray[0] = 1;
        partialArray[1] = 2;
        partialArray[2] = 3;
        functions.fillArray(partialArray);*/

        //functions.findValueInArray(array,firstInt);

        //functions.copyArrayValue(array);

        functions.reverseArray(array);

        functions.bubbleSort(array);

        /*Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t 0");
        System.exit(0);*/
    }
}
