package client;

        /*
         * SelectCarOption can be used for user to configure a car
         */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Automobile;
import model.OptionSet;

public class SelectCarOption {
    ArrayList<String> autoModels;
    BufferedReader in = null;

    public SelectCarOption(ArrayList<String> autoModels) {
        this.autoModels = autoModels;
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Prompts the user for available models and save their choice
     * @return String
     */
    public String showModelsAndSelect() {
        System.out.println("Pick one of these models to customize: " + autoModels.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String userChoice = null;
        try {
            userChoice = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userChoice;
    }

    /**
     * Allow user to configure the car
     * @param auto
     */
    public void configureCar(Automobile auto) {
        auto.listOptions();  // call the listOptions() method in the Automobile class
        System.out.println("Please select an option in this format");
        System.out.println("OptionSet Name:Option");
        System.out.println("Type \"exit\" to exit of finish configuration");

        String line = null;
        try {
            while((line = in.readLine()) != null) {
                if (line.equals("exit")) {
                    break;
                }

                String[] lineArray = line.split(":");
                auto.setOptionChoice(lineArray[0], lineArray[1]);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * display the configured car on the screen of client
     * @param auto
     */

    public void displayconfiguredCar(Automobile auto) {
        // Calculating total price of the car
        System.out.println("The total price of this car options is " + auto.getTotalPrice() + " dollars.");
        ArrayList<OptionSet> optionSets = auto.getOpset();
        for (OptionSet optionSet : optionSets) {

            String choice = auto.getOptionChoice(auto.getOptionSetName(optionSet));
            if (choice == null) {
                continue;
            }

            int choicePrice = auto.getOptionChoicePrice(auto.getOptionSetName(optionSet));
            // printing option choice and its price for this OptionSet
            System.out.println(choice + ": " + Integer.toString(choicePrice));
        }

    }

}