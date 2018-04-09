package servlet;

import client.DefaultSocketClient;
import model.Automobile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.ArrayList;

public class ServletClient extends DefaultSocketClient{

    private static ServletClient newClient;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public ServletClient(String strHost, int iPort){
        super(strHost, iPort);
    }

    public static ServletClient getNewClient(){


        if (newClient == null){
            newClient = new ServletClient("localhost", 7777);
            newClient.start();
            try {
                newClient.join(); //Wait for the socket connection to server to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("CarConfigClient running and communicating to port: 7777");

        return newClient;
    }

    @Override
    public void run() {
        openConnection();
    }

    public ArrayList<String> getAvailableModels() {
        ArrayList<String> autoModels = null;
        try {
            outputStream.writeObject("2");
            try {
                autoModels = (ArrayList<String>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SocketException se) {
            se.printStackTrace();
            // System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return autoModels;

    }

    public Automobile getModel(String modelname) {
        Automobile automobile = null;
        try {
            outputStream.writeObject(modelname);
            try {
                automobile = (Automobile) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SocketException se) {
            se.printStackTrace();
            // System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return automobile;
    }
}
