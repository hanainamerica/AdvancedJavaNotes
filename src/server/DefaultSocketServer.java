package server;

/**
 * Default socket server can open/handle the file and message communication with
 * the socket client.
 */


import java.net.*;
import java.io.*;
import model.Automobile;

import java.util.ArrayList;
import java.util.Properties;

public class DefaultSocketServer extends Thread
        implements SocketClientInterface,
        SocketClientConstants{

    private String strHost;
    private int iPort;
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private static final int WAITING = 0;
    private static final int UPLOAD_PROPERTY_FILE = 1;
    private static final int LIST_OF_CAR_MODELS = 2;
    private static final int GET_AUTO = 3;


    private int state = WAITING;

    private BuildCarModelOptions buildCarModelOptions;

    // constructor
    public DefaultSocketServer(String strHost, int iPort){
        setPort(iPort);
        setHost(strHost);
    }

    public DefaultSocketServer(Socket socket, BuildCarModelOptions buildCarModelOptions) {
        setSocket(socket);
        this.buildCarModelOptions = buildCarModelOptions;
    }

    public void setPort(int iPort){
        this.iPort = iPort;
    }

    public void setHost(String strHost){
        this.strHost = strHost;
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    // run
    public void run(){
        if (openConnection()){
            handleSession();
            //closeSession();
        }
    }

    public boolean openConnection(){
        try {

            // outputStream is chained to the client socket's output
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            // inputStream is chained to the server socket's input
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
            if (DEBUG) System.err.println("Unable to obtain stream to/from " + strHost);
            return false;
        }
        return true;
    }

    // read the client's request
    public void handleSession(){
        Object userMessage = null;
        try {
            // inputStream is chained to server socket's input
            // whenever we do readObject(), it goes over the network to the client

            while ((userMessage = inputStream.readObject()) != null) {
                if (userMessage != null) {
                    processClientRequest(userMessage);
                }
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    private void processClientRequest(Object userMessage) {
        Properties props = null;

        System.out.println("Server state: " + state);


        switch(state) {
            case WAITING:
                String selectedOption = (String) userMessage;

                System.out.println("Server received user selection: " + selectedOption);

                if (selectedOption.equals("1")) {

                    state = UPLOAD_PROPERTY_FILE;

                } else if (selectedOption.equals("2")) {

                    // the list of mobiles
                    ArrayList<String> autosList = buildCarModelOptions.showList();

                    try {

                        // outputStream is chained to client socket's output
                        // whenever we do writeObject(), it goes over the network to the server
                        outputStream.writeObject(autosList);
                        state = GET_AUTO;

                    } catch (IOException e) {
                        e.printStackTrace();
                        state = WAITING;
                    }
                }

                break;

            case UPLOAD_PROPERTY_FILE: // received properties file
                try {
                    props = (Properties) userMessage;

                    // builds auto from Properties object and add it to LHM
                    buildCarModelOptions.buildAutoFromPropertiesObject(props);

                    // send response to client
                    outputStream.writeObject("Done!");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                state = WAITING;
                break;


            case GET_AUTO:
                try {

                    String selectedModel = (String) userMessage;

                    // find the auto which is selected by client
                    Automobile selectedAuto = buildCarModelOptions.findAutomobileInLHM(selectedModel);

                    // send response to client
                    outputStream.writeObject(selectedAuto);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                state = WAITING;

        }
    }

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