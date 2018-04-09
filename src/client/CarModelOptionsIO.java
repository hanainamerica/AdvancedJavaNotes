package client;

/*
 * through class carmodeloptionsio, client can:
 *
 * 1. be asked to make choice to upload a properties object or configure a car
 *
 * 2. be asked for provide a path to properties file and create properties object
 *
 * 3. serialize the properties object and send it to the server
 *
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;


public class CarModelOptionsIO {

    private Socket socket = null;
    private DefaultSocketClient dsc = null;

    /**
     * constructor
     *
     */

    public CarModelOptionsIO() {
        try {
            // make a Socket, and setup the network
            socket = new Socket("localhost", 7777);

            // the thread's job is to open/handle the file and message communication with
            // the server's socket stream
            dsc = new DefaultSocketClient(socket);
            new Thread(dsc).start();
            System.out.println("Client is connecting");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * ask the user to make a choice
     * @return
     */
    public static String printMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter one of the following commands:");
        System.out.println("1 - Upload Properties File");
        System.out.println("2 - Configure a Car");
        System.out.println("3 - Exit");
        System.out.println();

        String choice = null;
        while(choice == null) {
            System.out.println("Enter \"1\", \"2\" or \"3\"");
            try {

                // read choice from the user, the choice will be used for handleSession() in socket client
                choice = reader.readLine();
                if (choice.equals("3")  && choice.equals("2") && choice.equals("1")) {
                    choice = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return choice;
    }

    /**
     * user is asked to provide the path of the properties file
     * after uploading the file, properties object is created, serialized and sent to the server
     *
     * @return
     */

    public static Properties createPropertiesObject() {
        System.out.println("Enter a properties file path:");
        String filePath = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            // read url from the user
            filePath = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream(filePath);
            // loads the entire file in the properties object
            properties.load(input);
        } catch(FileNotFoundException ex) {
            System.out.println("Could not open the properties file");
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        return properties;
    }

    public static void main(String args[]) {
        CarModelOptionsIO client = new CarModelOptionsIO();
    }

}
