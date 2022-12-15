package fr.crossessentials.crossessentials.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author TopeEstLa
 */
public class JsonObject {

    private final Map<String, Object> map = new HashMap<>();

    public JsonObject write(String key, Object object) {
        this.map.put(key, object);
        return this;
    }

    public String readString(String key) {
        return (String)this.map.get(key);
    }

    public List<String> readStringList(String key) {
        return (List<String>)this.map.get(key);
    }

    public int readInt(String key) {
        return ((Integer)this.map.get(key)).intValue();
    }

    public double readDouble(String key) {
        return ((Double)this.map.get(key)).doubleValue();
    }

    public float readFloat(String key) {
        return ((Float)this.map.get(key)).floatValue();
    }

    public short readShort(String key) {
        return ((Short)this.map.get(key)).shortValue();
    }

    public byte readByte(String key) {
        return ((Byte)this.map.get(key)).byteValue();
    }

    public Object readObject(String key) {
        return this.map.get(key);
    }

    public boolean readBoolean(String key) {
        return ((Boolean)this.map.get(key)).booleanValue();
    }

    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    public <T> T read(String key, Class<T> aClass) {
        if (!contains(key))
            return null;
        return (T)this.map.get(key);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void ifNotEmpty(Consumer<JsonObject> consumer) {
        if (!isEmpty())
            consumer.accept(this);
    }

    public static JsonObject of(String key, Object value) {
        return (new JsonObject()).write(key, value);
    }

    public static JsonObject of(Map<String, Object> map){
        JsonObject object = new JsonObject();
        map.forEach(object::write);
        return object;
    }

    public String toString() {
        return "JsonObject{" + this.map + '}';
    }

    public static JsonObject empty(){
        return new JsonObject();
    }

    public static JsonObject fromString(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        return of(objectMapper.convertValue(str, Map.class));
    }

    public static String toString(JsonObject object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object.map, String.class);
    }
}
