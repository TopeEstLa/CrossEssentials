package fr.crossessentials.crossessentials.data.web;

import java.util.Optional;

/**
 *
 * @param responseCode response code of the request (-1 if the server timed out)
 * @param message optional response message of the request, can be empty if the server didn't return a response, the response was not valid json or because of a timeout
 */
public record WebResponse(int responseCode, Optional<String> message) {

    public boolean isError() {
        return responseCode < 200 || responseCode >= 300;
    }

    public boolean isTimeout() {
        return responseCode == -1;
    }
}
