package adapter;


import java.io.IOException;
import java.util.*;

import exception.AutoException;
import scale.EditOptions;
import scale.EditThread;
import util.*;
import model.*;

/**
 * ProxyAutomobile class is an abstract class,
 * which needs to implement all the methods declared in the interfaces.
 *
 * Also ProxyAutomobile class uses the components inside the model package, util package and exception package.
 * Things in these packages are what we do not want to expose to the outside world(clients).
 *
 * (inheritance, LinkedHashMap<></>)
 */

public abstract class ProxyAutomobile{

    // We save the data inside a static list, LinkedHashMap<String,Automobile>
    // to be able to share it between different instantiation of BuildAuto.
    private static LinkedHashMap<String, Automobile>
            mobileHash = new LinkedHashMap<>();

    public static LinkedHashMap<String, Automobile> getMobileHash() {
        return mobileHash;
    }

    public void buildAuto(String filename, String filetype){

        FileIO fileIO = new FileIO();
        Automobile a1 = null; //Create instance variable a1 to handle all the operations on Automobile

        if (filetype.equals("txt")) {

            a1 = fileIO.buildAutoObject(filename);
            mobileHash.put(a1.createAutoKey(), a1);


        } else if (filetype.equals("Properties")) {

            try {
                a1 = fileIO.buildAutoObjectFromPropertiesFile(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mobileHash.put(a1.createAutoKey(), a1);
        }

    }

    public void printAuto(String Modelname){
        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // Display the specific element
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(Modelname)) {
                auto.print();
            }
        }
    }

    public void updateOptionSetName(String Modelname,
                                    String Optionsetname, String newname) {
        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(Modelname)) {
                auto.updateOptionSetName(Optionsetname, newname);
            }
        }
    }

    public void updateOptionPrice(String modelname, String optionsetname,
                                  String optionname, int newprice) {

        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(modelname)) {
                auto.updateOptionPrice(optionsetname, optionname, newprice);
            }
        }
    }

    public void fix(AutoException e) {
        e.fix(e, e.getErrorNumber());
    }


    // Set the option price and update it
    public void editOptionPrice(String modelName, String optionSetname,
                                String optionName, int newPrice){
        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(modelName)) {
                String[] info = {optionSetname, "newOptionSetname", optionName, Integer.toString(newPrice)};
                EditOptions editOptions = new EditOptions(auto);
                EditThread editThread = new EditThread(0, editOptions, info);
                editThread.start();
            }
        }

    }


    // Set the optionset name and update it
    public void editOptionSetName(String modelName,
                               String optionSetname, String newOptionSetname){
        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(modelName)) {
                String[] info = {optionSetname, newOptionSetname};
                EditOptions editOptions = new EditOptions(auto);
                EditThread editThread = new EditThread(1, editOptions, info);
                editThread.start();
            }
        }
    }



    public void buildAutoFromPropertiesObject(Properties props){

        FileIO fileIO = new FileIO();
        Automobile a1 = null; //Create instance variable a1 to handle all the operations on Automobile

        a1 = fileIO.buildAutoObjectFromPropertiesObject(props);   // call the method in FileIO

        mobileHash.put(a1.createAutoKey(), a1);
    }

    public ArrayList<String> showList() {
        ArrayList<String> modelLists= new ArrayList <>();

        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            Automobile auto = entry.getValue();
            modelLists.add(auto.getModel());
        }

        return modelLists;
    }

    public Automobile findAutomobileInLHM(String modelName) {
        Automobile auto = null;
        // Get a set of the entries
        Set set = mobileHash.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // update the specific model
        while(i.hasNext()) {
            Map.Entry<String,Automobile> entry = (Map.Entry)i.next();
            auto = entry.getValue();
            if (auto.getModel().equalsIgnoreCase(modelName)){
                break;
            }
        }
        return auto;
    }

}
