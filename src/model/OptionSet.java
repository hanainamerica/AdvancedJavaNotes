package model;

import java.io.Serializable;
import java.util.ArrayList;


/*
 * Each OptionSet has a set of Options.
 *
 */


public class OptionSet implements Serializable {

    private static final long serialVersionUID = 8837812409318340985L;
    private String name;
    private Option choice;
    private ArrayList<Option> opt;
    private ArrayList<String> optname = null;

    // Constructors
    protected OptionSet(){
        this.opt = new ArrayList<>();
    }

    // getters
    protected ArrayList<Option> getOpt(){
        return opt;
    }

    protected Option getOpt(int index){
        if (index < opt.size()){
            return opt.get(index);
        }
        return null;
    }

    protected String getName(){                            // Getter of the field name
        return name;
    }

    protected ArrayList<String> getOptname(){
        for (Option option: opt){
            optname.add(option.getName());
        }

        return optname;
    }

    protected Option getOptionChoice(){
        return choice;
    }

    // find
    protected Option findOption(String name){
        for (int i = 0; i < opt.size(); i++) {                   // repeat with the index of the arraylist
            if (opt.get(i).getName().equalsIgnoreCase(name)) {
                return opt.get(i);                               // return the expected option
            }
        }
        return null;                                    // return null if finding nothing
    }


    // Delete an option from the opt array with a given index
    protected void deleteOptionp(int indexopt){
        this.opt.set(indexopt, null);
    }

    protected void deleteOption(String optionName){
        for (Option option : opt){
            if (option != null && option.getName().equalsIgnoreCase(optionName)){
                // remove the option from ArrayList of Options
                opt.remove(option);
                // clear the choice variable, if it was chosen
                deleteOptionChoice(optionName);
                return;
            }
        }
    }

    // clear the choice by optionName
    protected void deleteOptionChoice(String optionName) {
        if (choice.getName().equalsIgnoreCase(optionName)) {
            choice = null;
        }
    }



    protected void updateOption() {                                // Update the array of option
        ArrayList<Option> newopt = new ArrayList<Option>();        // create a new arraylist
        int index = 0;                                             // initiate the index of the new arraylist
        for (int i = 0; i < opt.size(); i++) {                     // repeat with the index of the old arraylist
            if (opt.get(i) != null) {                              // if the option exists, add it to the new arraylist
                newopt.set(index, opt.get(i));
                index++;
            }
        }
        opt = newopt;                                              // reset the opt
    }

    protected void updateOptionPrice(String name, int price) { // find by name
        for (Option option : opt) {
            if (option != null && option.getName().equalsIgnoreCase(name)) {
                option.setPrice(price);
            }
        }
    }

    protected void updateOptionName(String oldName, String newName) {	// find by price - might be more than one
        for (Option option : opt) {
            if (option != null && option.getName() == oldName) {
                option.setName(newName);
            }
        }
    }


    // Setters
    protected void setName(String name){
        this.name = name;
    }

    protected void setOption(int optionsize) {
        for (int i = 0; i < optionsize; i++) {
            Option option = new Option();
            this.opt.add(option);
        }
    }

    protected void setOptionValue(int index, String value) {
        if (index < opt.size() ) {
            this.opt.get(index).setName(value);
        }
    }

    protected void setOptionPrice(int index, int value) {
        if (index < opt.size() ) {
            this.opt.get(index).setPrice(value);
        }
    }

    protected void setOptionChoice(String optionname){
        this.choice = this.findOption(optionname);
    }


    /* Print all the information of an option set */
    protected void print() {
        StringBuffer sb = new StringBuffer();
        sb.append("The optionset name is: ");
        sb.append(name);
        System.out.println(sb);
        for (int i = 0; i < opt.size(); i++) {
            opt.get(i).print();
        }
    }
}
