package scale;

import adapter.ProxyAutomobile;
import model.Automobile;

/**
 * Edit the options and define the behaviors of different threads(options)
 *
 */
public class EditOptions extends ProxyAutomobile{

    private Automobile automobile;
    private boolean available;
    private String[] info; // values that might change -- get it from Editor

    // constructor
    public EditOptions(Automobile automobile) {
        this.automobile = automobile;
        available = true;
    }

    public synchronized void updateOptionPrice(String[] info) {
        System.out.println("Entering updateOptionPrice method ");

        while (available == false) {   // indicate there is nothing to update.
            try {
                // wait to another thread sends notifyAll()
                System.out.println("updateOptionPrice Waiting ");
                wait();
            } catch (InterruptedException e) {
                System.out.println("updateOptionPrice done waiting ");
            }
        }

        available = false;
        // Now it can update the option price
        boolean result = automobile.updateOptionPrice(info[0], info[2], Integer.parseInt(info[3]));

        if (result) {
            System.out.println("***Option:" + info[2] + " , New Price:" + Integer.parseInt(info[3]) + "***");
        } else {
            System.out.println("****Thread 0 was not able to update the Option price****");
        }

        System.out.println("updateOptionPrice Notifyall ");
        notifyAll();
        System.out.println("updateOptionPrice Done! ");
        available = true;
    }

    public synchronized void updateOptionSetName(String[] info) {
        System.out.println("Entering updateOptionSetName method ");

        while (available == false) {     // indicate there is nothing to update.
            try {
                // wait to another thread sends notifyAll()
                System.out.println("updateOptionSetName Waiting ");
                wait();
            } catch (InterruptedException e) {
                System.out.println("updateOptionSetName done waiting ");
            }
        }


        available = false;
        // Now it can update the optionset name
        // call the method in Automobile class: updateOptionSetName(String oldName, String newName)
        boolean result = automobile.updateOptionSetName(info[0], info[1]);

        if (result) {
            System.out.println("***OptionSet:" + info[0] + " , New Name:" + info[1] + "***");
        } else {
            System.out.println("****Thread 1 was not able to update the OptionSet name****");
        }

        System.out.println("updateOptionSetName Notifyall ");
        notifyAll();
        System.out.println("updateOptionSetName Done! ");
        available = true;

    }
}
