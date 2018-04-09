package server;

import adapter.BuildAuto;
import model.Automobile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class implements the methods in server interface
 *
 * And set up the connection with the client.
 */

public class BuildCarModelOptions implements AutoServer{

    private BuildAuto buildAuto;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private DefaultSocketServer dss = null;

    // constructor
    public BuildCarModelOptions(){
        buildAuto = new BuildAuto();
        try{
            // ServerSocket makes this server application "listen" for
            // client requests on port 7777 on the machine this code is running on
            serverSocket = new ServerSocket(7777);
            System.out.println("Server is setup and waiting for connections...");
        }catch (IOException e){
            System.err.println("Could not listen on port: 7777.");
            System.exit(1);
        }

        try{
            // the accept method block until a request comes in,
            // and then the method returns a Socket(on some anonymous port)
            // for communicating with the client
            socket = serverSocket.accept();

            // the thread's job is to open/handle corresponding session
            // from the client's socket stream
            dss = new DefaultSocketServer(socket, this);
            new Thread(dss).start();
            System.out.println("Server accepted the client");
        }catch (IOException e){
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }

    public BuildAuto getBuildAuto(){
        return buildAuto;
    }

    public void setBuildAuto(BuildAuto buildAuto){
        this.buildAuto = buildAuto;
    }

    @Override
    public void buildAutoFromPropertiesObject(Properties props) {
        buildAuto.buildAutoFromPropertiesObject(props);
    }

    @Override
    public ArrayList<String> showList() {
        return buildAuto.showList();
    }

    @Override
    public Automobile findAutomobileInLHM(String modelName) {
        return buildAuto.findAutomobileInLHM(modelName);
    }

    public static void main(String[] args) {
        BuildCarModelOptions server = new BuildCarModelOptions();
    }
}