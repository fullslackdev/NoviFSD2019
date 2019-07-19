import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Functions {
    protected int[] swapNumbers(int x, int y) {
        int[] value = new int[2];
        x = x + y;
        y = x - y;
        x = x - y;
        value[0] = x;
        value[1] = y;
        return value;
    }

    protected float circleCircumference(float diameter) {
        return (float)Math.PI * diameter;
    }

    protected float circleSurfaceArea(float diameter) {
        float radius = diameter / 2;
        return (float)Math.PI * (radius * radius);
    }

    protected void calculateSquareOfSequence(int first, int last) {
        for (int i = first; i <= last; i++) {
            int square = i * i;
            System.out.println(i + " * " + i + " = " + square);
        }
    }

    protected void printMultiplicationTable(int number) {
        int value = 0;
        for (int i = 1; i <= 10; i++) {
            value = i * number;
            System.out.println(i + " x " + number + " = " + value);
        }

        /*int i = 1;
        while (i <= 10) {
            value = i * number;
            System.out.println(i + " x " + number + " = " + value);
            i++;
        }*/
    }

    protected void translateMonth(int number) {
        String text = "";
        switch (number) {
            case 1:
                text = "januari";
                break;
            case 2:
                text = "februari";
                break;
            case 3:
                text = "maart";
                break;
            case 4:
                text = "april";
                break;
            case 5:
                text = "mei";
                break;
            case 6:
                text = "juni";
                break;
            case 7:
                text = "juli";
                break;
            case 8:
                text = "augustus";
                break;
            case 9:
                text = "september";
                break;
            case 10:
                text = "oktober";
                break;
            case 11:
                text = "november";
                break;
            case 12:
                text = "december";
                break;
            default:
                text = "Geen maand ingevoerd!";
        }
        if (number > 0 && number < 13) {
            System.out.println("Maand " + number + " is " + text);
        } else {
            System.out.println("Fout: " + text);
        }
    }

    protected void calculateFaculty(int number) {
        //n = 5 -> 5! = 5 x 4 x 3 x 2 x 1 -> 5! = 120
        int value = number;
        System.out.print("n = " + number + " -> " + number + "! = " + number);//n = 5 -> 5! = 5
        for (int i = number - 1; i >= 1; i--) {
            value = value * i;
            System.out.print(" x " + i);// x 4 x 3 x 2 x 1
        }
        System.out.print(" -> " + number + "! = " + value);// -> 5! = 120
        System.out.println();
    }

    protected int sumOfTable(int[] array) {
        int sum = 0;
        for (int e: array) {
            sum += e;
        }
        return sum;
    }

    protected void fillArray(int[] array) {
        if (array.length == 10) {
            for (int i = 3; i < array.length; i++) {
                array[i] = i + 1;
            }
            System.out.println(Arrays.toString(array));
        } else {
            System.out.println("Array has wrong size!");
        }
    }

    protected void findValueInArray (int[] array, int search) {
        boolean found = false;
        int i = 0;
        for (i = 0; i < array.length; i++) {
            if (array[i] == search) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Found value in array on location: "+ (i+1));
        } else {
            System.out.println("Value not found");
        }
    }

    protected void copyArrayValue(int[] inputArray) {
        int[] outputArray = new int[inputArray.length];
        System.out.println("Inputarray: "+Arrays.toString(inputArray));
        for (int i = 0; i < inputArray.length; i++) {
            outputArray[i] = inputArray[i];
        }
        System.out.println("Outputarray:" +Arrays.toString(outputArray));
    }

    protected void reverseArray(int[] inputArray) {
        int[] outputArray = new int[inputArray.length];
        System.out.println("Inputarray: "+Arrays.toString(inputArray));
        for (int i = inputArray.length - 1, j = 0; i >= 0; i--, j++) {
            outputArray[i] = inputArray[j];
        }
        System.out.println("Outputarray:" +Arrays.toString(outputArray));
    }

    protected void bubbleSort(int[] array) {
        System.out.println("Unsorted array: "+ Arrays.toString(array));
        int length = array.length;
        int counter = 0;
        boolean sorted = false;
        for (int i = 0; i < length - 1; i++) {
            sorted = false;
            for (int j = 0; j < length - 1; j++) {
                if (array[j] > array[j+1]) {
                    /*int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;*/
                    array[j] = array[j] + array[j+1];
                    array[j+1] = array[j] - array[j+1];
                    array[j] = array[j] - array[j+1];
                    sorted = true;
                }
                counter++;
            }
            if (!sorted) {
                break;
            }
        }
        System.out.println("Sorted array: "+Arrays.toString(array)+" (took "+counter+" iterations to sort)");
    }

    protected String hashingMethod(String input) {
        String output = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            output = bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error: "+ex);
        }

        return output;
    }

    protected String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
