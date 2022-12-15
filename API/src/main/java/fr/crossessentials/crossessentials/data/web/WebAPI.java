package fr.crossessentials.crossessentials.data.web;

import fr.crossessentials.crossessentials.json.JsonObject;

import java.util.function.Consumer;

public interface WebAPI {

    void makeRequest(String endpoint, String method,JsonObject body, Consumer<WebResponse> response);

}
