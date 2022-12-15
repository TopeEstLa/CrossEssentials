package fr.crossessentials.crossessentials.data;

import com.google.gson.*;
import fr.crossessentials.crossessentials.data.exceptions.BundleDeserializeException;
import fr.crossessentials.crossessentials.json.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Bundle {

    String channel;
    JsonObject data;

    public Bundle() {
        this.data = new JsonObject();
    }
    public Bundle(String channel) {
        this.channel = channel;
        this.data = new JsonObject();
    }

    public Bundle(String channel, JsonObject data) {
        this.channel = channel;
        this.data = data;
    }

    public void setRecipients(String... recipients){
        data.write("$recipients", Arrays.asList(recipients));
    }

    public Bundle addRecipients(String... recipients){
        List<String> recipientsArray = data.readStringList("$recipients");
        if(recipientsArray == null){
            recipientsArray = new ArrayList<>();
            data.write("$recipients",recipientsArray);
        }
        for (String s : recipients) {
            recipientsArray.add(s);
        }
        return this;
    }

    public Bundle removeRecipients(String... recipients){
        JsonArray recipientsArray = data.getAsJsonArray("$recipients");
        if(recipientsArray == null)return this;
        JsonArray newArray = new JsonArray();
        for (JsonElement jsonElement : recipientsArray) {
            if (Arrays.stream(recipients).anyMatch(a -> a.equals(jsonElement.getAsJsonPrimitive().getAsString()))) {
                continue;
            }
            newArray.add(jsonElement);
        }
        if(newArray.size() == 0){
            data.remove("$recipients");
            return this;
        }

        data.add("$recipients",newArray);
        return this;
    }

    public void setType(String s){
        data.add("$type", new JsonPrimitive(s));
    }

    public void setSender(String s){
        data.add("$sender", new JsonPrimitive(s));
    }

    public String getSender(){
        if(!data.has("$sender"))return null;
        return data.getAsJsonPrimitive("$sender").getAsString();
    }



    public String[] getRecipients(){
        if(!data.has("$recipients"))return new String[]{};
        JsonArray recipients = data.getAsJsonArray("$recipients");
        String[] strings = new String[recipients.size()];
        int index = -1;
        for (JsonElement $recipients : recipients) {
            index++;
            strings[index] = $recipients.getAsString();
        }

        return strings;
    }

    public String getType(){
        if(!data.has("$type"))return null;
        return data.getAsJsonPrimitive("$type").getAsString();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public Optional<UUID> getUUID(String key){
        if(!data.has(key))return Optional.empty();
        try{
            return Optional.of(UUID.fromString(data.getAsJsonPrimitive(key).getAsString()));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void setUUID(String key, UUID value){
        data.add(key, new JsonPrimitive(value.toString()));
    }

    public Optional<String> getString(String key){
        if(!data.has(key))return Optional.empty();
        try{
            return Optional.of(data.getAsJsonPrimitive(key).getAsString());
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void setString(String key, String value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<Boolean> getBoolean(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonPrimitive(key).getAsBoolean());
    }

    public void setBoolean(String key, boolean value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<Integer> getInteger(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonPrimitive(key).getAsInt());
    }

    public void setInteger(String key, int value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<Long> getLong(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonPrimitive(key).getAsLong());
    }

    public void setLong(String key, long value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<BigInteger> getBigInteger(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonPrimitive(key).getAsBigInteger());
    }

    public void setBigInteger(String key, BigInteger value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<BigDecimal> getBigDecimal(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonPrimitive(key).getAsBigDecimal());
    }

    public void setBigDecimal(String key, BigDecimal value){
        data.add(key, new JsonPrimitive(value));
    }

    public Optional<JsonArray> getList(String key){
        if(!data.has(key))return Optional.empty();
        return Optional.of(data.getAsJsonArray(key));
    }

    public void setList(String key, JsonArray array){
        data.add(key,array);
    }

    public String serialize(){
        JsonObject root = new JsonObject();
        root.add("channel",new JsonPrimitive(channel));
        root.add("data",data);
        return root.toString();
    }

    public static Bundle deserialize(String s) throws BundleDeserializeException {
        if(!(JsonParser.parseString(s) instanceof JsonObject object))
            throw new BundleDeserializeException("The received string is not/invalid json \n"+s+"\n"+ JsonParser.parseString(s));

        if(!object.has("channel") || !object.has("data"))
            throw new BundleDeserializeException("Missing data or channel field");

        return new Bundle(object.get("channel").getAsString(), object.get("data").getAsJsonObject());
    }
}
