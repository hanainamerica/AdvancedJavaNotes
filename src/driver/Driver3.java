package driver;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;
import exception.AutoException;


/**
 * Test the interface
 *
 */

public class Driver3 {

    public static void main(String args[]) throws AutoException {


        //Build Automobile Object.
        CreateAuto a1 = new BuildAuto();
        a1.buildAuto("Focus.txt", "txt");
        a1.printAuto("Focus Wagon ZTW");
        //Update one of the OptionSet's name
        UpdateAuto a3 = new BuildAuto();
        a3.updateOptionSetName("Focus Wagon ZTW", "Power Moonroof", "Power");
        a1.printAuto("Focus Wagon ZTW");




    }

}
