package fr.crossessentials.crossessentials.data.web;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface WebAPI {

    void makeRequest(String endpoint, String method,  JsonObject body, Consumer<WebResponse> response);


}
