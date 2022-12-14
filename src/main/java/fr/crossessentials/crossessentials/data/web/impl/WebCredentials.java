package fr.crossessentials.crossessentials.data.web.impl;

import fr.crossessentials.crossessentials.data.ConnectorCredentials;

/**
 * @author TopeEstLa
 */
public record WebCredentials(String url, String secret) implements ConnectorCredentials {

}
