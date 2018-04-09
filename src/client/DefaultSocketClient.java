package client;


        /*
         * Default socket client can open/handle the file and message communication with
         *
         * the socket server.
         */


import java.net.*;
import java.io.*;
import model.Automobile;

import java.util.ArrayList;
import java.util.Properties;

public class DefaultSocketClient extends Thread
        implements SocketClientInterface,
        SocketClientConstants{

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Socket socket;
    private String strHost;
    private int iPort;

    /**
     * constructors
     *
     */

    public DefaultSocketClient(String strHost, int iPort){
        setPort(iPort);
        setHost(strHost);
    }

    public DefaultSocketClient(Socket socket) {
        this.socket = socket;
    }

    public void setHost(String strHost){
        this.strHost = strHost;
    }

    public void setPort(int iPort){
        this.iPort = iPort;
    }

    /**
     * default socket client should implement the method in thread class
     *
     */

    public void run(){

        if (openConnection()){
            handleSession();
        }

    }

    /**
     * build the connection with outputstream and inputstream
     * @return
     */

    public boolean openConnection(){
        try {

            // sends data to server
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            // receives from server
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
            if (DEBUG) System.err.println("Unable to obtain stream to/from " + strHost);
            return false;
        }
        return true;
    }

    /**
     * when connection get started handle the session - being called once
     *
     */

    public void handleSession(){
        // get a choice from the user
        String choice = null;

        // based on user choice send data to server
        boolean keepAlive = true;
        // listen to server response  & if the connection should alive
        while (keepAlive) {
            // get a choice from the user
            choice = CarModelOptionsIO.printMenu();

            // based on user choice send data to server
            keepAlive = createServerRequst(choice);
        }
        System.out.println("Closing Session");
        closeSession();

    }

    /**
     *
     * @param choice
     * @return
     */

    public boolean createServerRequst(String choice) {
        try {
            switch(choice){
                case "1":
                    outputStream.writeObject(choice);
                    Properties props = CarModelOptionsIO.createPropertiesObject();
                    outputStream.writeObject(props);
                    String status = (String) inputStream.readObject();
                    System.out.println("Status for choice 1: " + status);
                    break;

                case "2":
                    outputStream.writeObject(choice);
                    ArrayList<String> autoModels = null;
                    try {
                        autoModels = (ArrayList<String>) inputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    SelectCarOption selectCarOption = new SelectCarOption(autoModels);
                    String userChoice = selectCarOption.showModelsAndSelect();
                    outputStream.writeObject(userChoice);

                    // receive the selected automobile from server
                    Automobile automobile = null;
                    try {
                        automobile = (Automobile) inputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    // configure and display the automobile
                    selectCarOption.configureCar(automobile);
                    selectCarOption.displayconfiguredCar(automobile);

                    break;
                case "3":
                    return false;

                // The default section handles all values that are not explicitly handled by one of the case sections.
                default:
                    System.out.println("Enter \"1\", \"2\" or \"3\"");
                    break;
            }
        } catch (SocketException se) {
            se.printStackTrace();
            // System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return true;
    }

    /**
     * close the stream and socket
     */

    public void closeSession(){
        try {
            inputStream = null;
            outputStream = null;
            socket.close();
        }
        catch (IOException e){
            if (DEBUG) System.err.println("Error closing socket to " + strHost);
        }
    }
}