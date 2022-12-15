package fr.crossessentials.crossessentials.data;

import fr.crossessentials.crossessentials.data.exceptions.ConnectionException;

public interface IConnector {

    void connect(ConnectorCredentials connectorCredentials) throws ConnectionException;

    void shutdown();

    /**
     * will pause the thread until the connection is ready
     */
    void waitForConnection();

}
