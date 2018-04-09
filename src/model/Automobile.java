package model;

import java.io.Serializable;
import java.util.ArrayList;


/*
 * Build the model and print all information out
 */

public class Automobile implements Serializable{


    /**
     * serialVersionUID is used in the serialization process;
     *
     * put the serialVersionUID in the class to guarantee:
     *
     * when doing the deserialization, the object must be compatible with the class,
     * even though the class evolves.
     *
     * checkout this set up instruction:
     * <https://netlicensing.io/blog/2012/12/25/intellij-idea-how-to-generate-serialversionuid/>
     *
     */

    private static final long serialVersionUID = 7481360255043486669L;

    private String carType;
    private int year;
    private String make;
    private String model;
    private int baseprice;
    private ArrayList<OptionSet> opset;
    private ArrayList<Option> choices;
    private ArrayList<String> opsetname = null;
    private ArrayList<String> optname = null;

    // Constructors
    public Automobile(){
        this.opset = new ArrayList<>();
        this.choices = new ArrayList <>();
    }

    public Automobile(String carType, int year, String make, String model, int baseprice){
        this.carType = carType;
        this.year = year;
        this.make = make;
        this.model = model;
        this.baseprice = baseprice;
        this.opset = new ArrayList<>();
        this.choices = new ArrayList <>();
    }

    // generate the unique key for an Automobile object, which will be used in LHM
    public String createAutoKey() {
        return carType+year+make+model+baseprice;
    }

    // Get the carType/year/make/model/baseprice
    public String getCarType(){
        return carType;
    }

    public int getYear(){
        return year;
    }

    public String getMake(){                    // Get the Make of the automobile
        return make;
    }

    public String getModel() {                  // Get the model of the automobile
        return model;
    }

    public int getBasePrice() {                 // Get the value of the baseprice
        return baseprice;
    }

    // Get the optionsets
    public ArrayList<OptionSet> getOpset(){
        return opset;
    }

    // Get the optionset with a given index
    public OptionSet getOpset(int index){
        if (index < opset.size())
            return opset.get(index);
        return null;
    }

    // Get the name of the optionset
    public String getOptionSetName(OptionSet optionSet){
        return optionSet.getName();
    }


    public ArrayList<String> getOptionsetsName(){
        for (OptionSet optionset: opset){
            opsetname.add(getOptionSetName(optionset));
        }
        return opsetname;
    }

    public ArrayList<String> getOptionsName(String optionsetname){
        optname = findOptionSet(optionsetname).getOptname();

        return optname;
    }

    // Get the options
    public void listOptions() {
        int number = 1;
        for(OptionSet optionSet : opset) {
            System.out.println("OptionSet:" + optionSet.getName());
            ArrayList<Option> options = optionSet.getOpt();
            for(Option option : options) {
                System.out.println(number +":" + option.getName());
                number++;
            }
            number = 1;
        }
    }

    // Get the userchoice list
    public ArrayList<Option> getUserChoices() {
        return choices;
    }

    // Get the names of the chosen option
    public String getOptionChoice(String setname){
        if (findOptionSet(setname).getOptionChoice() == null)
            return "None";
        return findOptionSet(setname).getOptionChoice().getName();  // call getOptionChoice() method in OptionSet class
    }

    // Get the prices of the chosen option
    public int getOptionChoicePrice(String setname){
        if (findOptionSet(setname) == null || findOptionSet(setname).getOptionChoice() == null)
            return 0;
        return findOptionSet(setname).getOptionChoice().getPrice();
    }



    // Get the total price of the automobile
    public int getTotalPrice(){
        int totalPrice = baseprice;
        for(Option option:choices){
            totalPrice += option.getPrice();
        }
        return totalPrice;
    }


    // look for the optionset with the optionsetname
    public OptionSet findOptionSet(String name) {
        for (int i = 0; i < opset.size(); i++) {          // repeat with the index of the arraylist
            if (opset.get(i).getName().equals(name)) {    // "name" has private access in model.OptionSet
                return opset.get(i);                      // return the expected optionset
            }
        }
        return null;                                      // return null if finding nothing
    }

    // look for the optionset with the optionname
    public OptionSet findOption(String name){
        for (int i = 0; i < opset.size(); i++){
            if (opset.get(i).findOption(name) != null){
                return opset.get(i);
            }
        }
        return null;
    }


    // Delete an optionset with given optionSet name,
    // at the same time, delete the optionchoice
    public void deleteOptionSet(String optionSetName){
        if (opset == null) {
            return;
        }
        // delete its chosen option from userChoice list
        deleteOptionChoice(optionSetName);
        // delete the OptionSet from optionSets list
        for(OptionSet opSet : opset) {
            if(opSet.getName().equalsIgnoreCase(optionSetName)) {
                opset.remove(opSet);
                return;
            }
        }
    }

    public void deleteOptionChoice(String optionSetName) {
        OptionSet willBeDeleted = new OptionSet();

        // find the OptionSet that is gonna be deleted
        for (OptionSet optionSet : opset) {
            if (optionSet != null && optionSet.getName().equalsIgnoreCase(optionSetName)) {
                willBeDeleted = optionSet;
            }
        }
        for (Option option : choices) {
            if (option.equals(willBeDeleted.getOptionChoice())) {
                choices.remove(option);
                return;
            }
        }
    }

    // Delete an option with given option name,
    public void deleteOption(String optionName){
        OptionSet willBeDeleted = findOption(optionName);  //

        if (willBeDeleted != null) {
            willBeDeleted.deleteOption(optionName);

        }
        return;
    }


    /**
     * if we want to do some change to the model, update the auto model
     */

    public void updateOpset() {                                          // Update the optionsets
        ArrayList<OptionSet> newopset = new ArrayList<>();               // create a new arraylist
        int index=0;                                                     // initiate the index of the new arraylist
        for(int i=0; i< opset.size(); i++) {                             // repeat with the index of the old arraylist
            if(opset.get(i)!=null) {                                     // if the optionset exists, add it to the new arraylist
                newopset.set(index, opset.get(i));
                index++;
            }
        }
        opset = newopset;
    }

    public void updateOption() {                                         // Update the options
        ArrayList<OptionSet> newopset = new ArrayList<OptionSet>();              // create a new arraylist
        for(int i=0; i< opset.size(); i++) {                             // call the updateOption() method
            opset.get(i).updateOption();
        }
        opset = newopset;
    }

    public boolean updateOptionSetName(String oldName, String newName) {
        OptionSet optionSet = findOptionSet(oldName);
        if (optionSet != null){
            optionSet.setName(newName);
            return true;
        }
        return false;
    }

    public boolean updateOptionPrice(String optionSetName, String optionName, int newPrice) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet != null){
            optionSet.updateOptionPrice(optionName, newPrice);
            return true;
        }
        return false;
    }

    public boolean updateOptionValue(String optionSetName, String optionName, String newOptionName){
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet != null){
            optionSet.updateOptionName(optionName, newOptionName);
            return true;
        }
        return false;
    }


    /**
     * set database for the auto and choices, including all information
     *
     */

    // Set the carType/make/model/baseprice
    public void setCarType(String carType){
        this.carType = carType;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setMake(String make) {          // Set the make of the automobile with a given string
        this.make = make;
    }

    public void setModel(String model) {        // Set the model of the automobile with a given string
        this.model = model;
    }

    public void setBasePrice(int baseprice) {   // Set the baseprice of the automobile
        this.baseprice = baseprice;
    }

    // Initialize an optionset arraylist with a given size
    public void setOptionSet(int optionSetSize) {
        for (int i = 0; i < optionSetSize; i++) {
            OptionSet optionset = new OptionSet();
            opset.add(optionset);
        }
    }

    // Set the names of the optionset
    public void setOptionSetName(int index, String string){
        opset.get(index).setName(string);

    }

    // Initialize an option arraylist with a given size
    public void setOption(OptionSet opset, int optionsize) {
        opset.setOption(optionsize);

    }

    // Set the prices of options
    public void setOptionPrice(int index, int index2, int price){
        opset.get(index2).setOptionPrice(index, price);

    }

    // Set the names of options
    public void setOptionValue(int index, int index2, String value) {
        opset.get(index2).setOptionValue(index, value);

    }

    // Choose a particular option in an option set
    public void setOptionChoice(String setname, String optionname){
        // set the Optionchoice named optionName
        findOptionSet(setname).setOptionChoice(optionname);
        // link the userChoice arraylist to choice
        this.choices.add(findOptionSet(setname).getOptionChoice());
    }




    /**
     * Print all the information of the automobile
     */

    public void print() {
        StringBuffer sb = new StringBuffer();
        sb.append("The type of the vehicle is:");
        sb.append(carType);
        sb.append("\nThe year of the vehicle is:");
        sb.append(year);
        sb.append("\nThe make of the vehicle is:");
        sb.append(make);
        sb.append("\nThe model of the vehicle is: ");
        sb.append(model);
        sb.append("\nThe base price of the vehicle is: ");
        sb.append(baseprice);
        System.out.println(sb);
        for (int i = 0; i < opset.size(); i++) {
            opset.get(i).print();
        }
    }

}

