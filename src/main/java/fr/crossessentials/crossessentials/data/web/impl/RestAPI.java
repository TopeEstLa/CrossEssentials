package fr.crossessentials.crossessentials.data.web.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.crossessentials.crossessentials.data.web.WebResponse;
import fr.crossessentials.crossessentials.data.web.WebAPI;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;

public class RestAPI implements WebAPI {

    @Override
    public void makeRequest(String endpoint, String method, @Nullable JsonObject body, Consumer<WebResponse> response) {
        try {
            // TODO add config settings
            URL url = new URL("localhost" + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            if (body != null) {
                connection.setDoOutput(true);

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
                    writer.write(body.getAsString());
                }
            }


            try {
                connection.connect();
            } catch (SocketTimeoutException e) {
                response.accept(new WebResponse(-1, Optional.empty()));
                return;
            }

            try {
                JsonObject jsonElement = JsonParser.parseString(connection.getResponseMessage()).getAsJsonObject();

                response.accept(new WebResponse(connection.getResponseCode(),Optional.ofNullable(jsonElement)));
            } catch (Exception e) {
                response.accept(new WebResponse(connection.getResponseCode(), Optional.empty()));
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
