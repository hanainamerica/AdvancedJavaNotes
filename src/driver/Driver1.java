package driver;

import exception.AutoException;
import model.Automobile;
import util.FileIO;

import java.io.IOException;

/**
 * Test the functionality of FileIO
 *
 * Create object from property file
 */

public class Driver1 {
    public static void main(String args[]){

        try{
            FileIO fileIO = new FileIO();
            //Build Automobile Object from a file.
            Automobile FordZTW = fileIO.buildAutoObject("car1.properties");
            //Print attributes before serialization
            FordZTW.print();
            //Serialize the object
            fileIO.SerializeAuto(FordZTW);
            //Deserialize the object and read it into memory.
            Automobile newFordZTW = fileIO.DeserializeAuto("TestAuto.ser");
            //Print new attributes.
            if (newFordZTW != null) {
                System.out.println(newFordZTW);
            }
        }catch(AutoException e){                  // SerializeAuto() throws AutoException
            e.fix(e,e.getErrorNumber());
        }
    }
}
