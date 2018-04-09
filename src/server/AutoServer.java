package server;

import java.util.ArrayList;
import java.util.Properties;
import model.Automobile;

/**
 * This interface is built for server to provide automobile service
 */


public interface AutoServer {

    // server will use this method to build/add auto to LHM for client
    public abstract void buildAutoFromPropertiesObject(Properties props);

    // server will use this method to provide auto lists for client
    public abstract ArrayList<String> showList();

    // server will use this method to find the auto which is selected by client
    public abstract Automobile findAutomobileInLHM(String modelName);


}
