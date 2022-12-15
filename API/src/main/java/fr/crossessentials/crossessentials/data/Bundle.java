package fr.crossessentials.crossessentials.data;

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
        recipientsArray.addAll(Arrays.asList(recipients));
        return this;
    }

    public Bundle removeRecipients(String... recipients){
        List<String> recipientsArray = data.readStringList("$recipients");
        if(recipientsArray == null)return this;
        recipientsArray.removeAll(List.of(recipients));
        if(recipientsArray.size() == 0){
            data.write("$recipients", null);
            return this;
        }

        return this;
    }

    public void setType(String s){
        data.write("$type", s);
    }

    public void setSender(String s){
        data.write("$sender", s);
    }

    public String getSender(){
        if(!data.contains("$sender"))return null;
        return data.readString("§sender");
    }



    public List<String> getRecipients(){
        return data.readStringList("$recipients");
    }

    public String getType(){
        if(!data.contains("$type"))return null;
        return data.read("$type", String.class);
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
        if(!data.contains(key))return Optional.empty();
        try{
            return Optional.of(UUID.fromString(data.readString(key)));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void setUUID(String key, UUID value){
        data.write(key, value.toString());
    }

    public Optional<String> getString(String key){
        if(!data.contains(key))return Optional.empty();
        try{
            return Optional.of(data.readString(key));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void setString(String key, String value){
        data.write(key, value);
    }

    public Optional<Boolean> getBoolean(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.readBoolean(key));
    }

    public void setBoolean(String key, boolean value){
        data.write(key, value);
    }

    public Optional<Integer> getInteger(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.readInt(key));
    }

    public void setInteger(String key, int value){
        data.write(key, value);
    }

    public Optional<Long> getLong(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.read(key, Long.class));
    }

    public void setLong(String key, long value){
        data.write(key, value);
    }

    public Optional<BigInteger> getBigInteger(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.read(key, BigInteger.class));
    }

    public void setBigInteger(String key, BigInteger value){
        data.write(key, value);
    }

    public Optional<BigDecimal> getBigDecimal(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.read(key, BigDecimal.class));
    }

    public void setBigDecimal(String key, BigDecimal value){
        data.write(key, value);
    }

    public Optional<List<String>> getList(String key){
        if(!data.contains(key))return Optional.empty();
        return Optional.of(data.readStringList(key));
    }

    public void setList(String key, List<String> list){
        data.write(key, list);
    }

    public String serialize(){
        JsonObject root = new JsonObject();
        root.write("channel", channel);
        root.write("data",data);
        return root.toString();
    }

    public static Bundle deserialize(String s) throws BundleDeserializeException {
        JsonObject object = JsonObject.fromString(s);

        if(!object.contains("channel") || !object.contains("data"))
            throw new BundleDeserializeException("Missing data or channel field");

        return new Bundle(object.readString("channel"), object.read("data", JsonObject.class));
    }
}
