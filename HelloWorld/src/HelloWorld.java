import javax.lang.model.element.Element;
import javax.swing.*;
import java.util.Scanner;

public class HelloWorld {

    public static void main(String[] args) {
        String inputFirstNameText = new String("Vul hier je voornaam in: ");
        String inputLastNameText = new String("Vul hier je achternaam in: ");
        String inputPasswordText = new String("Vul hier je wachtwoord in: ");
        Scanner input = new Scanner(System.in);

        System.out.print(inputFirstNameText);
        String inputFirstName = input.next();

        System.out.print(inputLastNameText);
        String inputLastName = input.next();

        System.out.print(inputPasswordText);
        String inputPassword = input.next();

        //String inputName = JOptionPane.showInputDialog("Wat is je naam?");

        //System.out.println("Je voornaam is: "+inputFirstName);
        //System.out.println("Je achternaam is: "+inputLastName);
        System.out.println("Je hele naam is: "+inputFirstName+" "+inputLastName);

        char[] nameCharArray = (inputFirstName+inputLastName).toCharArray();
        char[] vowelArray = {'a','e','u','i','o','A','E','U','I','O'};
        //char[] vowelArray = {'a','e','u','i','o'};

        int counter = 0;

        /*for (int i = 0; i < nameCharArray.length; i++) {
            for (int j = 0; j < vowelArray.length; j++) {
                if (vowelArray[j] == nameCharArray[i]) {
                    counter++;
                    break;
                }
            }
        }*/

        for (char e: nameCharArray) {
            for (char v: vowelArray) {
                if (v == e) {
                    counter++;
                    break;
                }
            }
        }

        System.out.println("Aantal klinkers in je hele naam: "+counter);

        Functions functions = new Functions();

        System.out.println("Hashcode van naam: "+functions.hashingMethod(inputFirstName+inputLastName));

        System.out.println("Hashcode van password: "+functions.hashingMethod(inputPassword));
    }
}
