package exception;

import java.util.Scanner;

/**
 * This class contains the fix methods, and will be used for raising the exceptions
 */

public class Fix1to100 {

    public String fixMissingAutoPrice(){

        Scanner scanner = new Scanner(System.in);
        String priceInString;
        boolean priceFound;

        do {
            System.out.print("Please enter the automobile price: ");
            priceInString = scanner.nextLine();
            try {
                Integer.parseInt(priceInString);
                priceFound = true;
            } catch (NumberFormatException nfe) {
                priceFound = false;
            }

        } while (!priceFound);

        scanner.close();
        return priceInString;
    }

    public String fixMissingMakeName(){
        return "The make of the model";
    }

    public String fixMissingModelName(){
        return "The name of the model";
    }

    public String fixMissingOptionSetSize(){
        return "The length of the OptionSet";
    }

    public String fixMissingOption(){
        return "The option of the model";
    }

    public void fixSerialization(){
        System.out.println("For the serialization, try again.");
    }

    public String fixMissingAutoType(){
        return "The type of the model";
    }

    public String fixMissingAutoYear(){
        return "The year of the model";
    }

}
