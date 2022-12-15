package fr.crossessentials.crossessentials.data.broker.impl;

import fr.crossessentials.crossessentials.data.ConnectorCredentials;

/**
 * @author TopeEstLa
 */
public record RedisCredentials(String host, int port, String username,
                               String password) implements ConnectorCredentials {

}
