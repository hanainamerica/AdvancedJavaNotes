package server;

/**
 * Created by rr on 11/14/17.
 */
public interface SocketClientInterface {
    boolean openConnection();
    void handleSession();
    void closeSession();
}
