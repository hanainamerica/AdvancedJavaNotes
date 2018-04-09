package client;

/**
 * Controls the actions for socket client
 *
 * These method will be implemented in DefaultSocketClient
 */

public interface SocketClientInterface {
    boolean openConnection();
    void handleSession();
    void closeSession();
}
