package until;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class để parse JSON response từ API
 * Sử dụng Gson để parse JSON
 */
public class JsonParser {
    private static final Gson gson = new Gson();
    
    /**
     * Parse JSON response thành JsonObject
     */
    public static JsonObject parseResponse(String json) {
        return gson.fromJson(json, JsonObject.class);
    }
    
    /**
     * Kiểm tra response có success không
     */
    public static boolean isSuccess(String json) {
        try {
            JsonObject obj = parseResponse(json);
            return obj.has("success") && obj.get("success").getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Lấy message từ response
     */
    public static String getMessage(String json) {
        try {
            JsonObject obj = parseResponse(json);
            if (obj.has("message")) {
                return obj.get("message").getAsString();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
    
    /**
     * Lấy data từ response
     */
    public static JsonElement getData(String json) {
        try {
            JsonObject obj = parseResponse(json);
            if (obj.has("data")) {
                return obj.get("data");
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    /**
     * Parse data thành object
     */
    public static <T> T parseData(String json, Class<T> classOfT) {
        JsonElement data = getData(json);
        if (data != null && !data.isJsonNull()) {
            return gson.fromJson(data, classOfT);
        }
        return null;
    }
    
    /**
     * Parse data thành list
     */
    public static <T> List<T> parseDataList(String json, Class<T> classOfT) {
        JsonElement data = getData(json);
        if (data != null && data.isJsonArray()) {
            JsonArray array = data.getAsJsonArray();
            List<T> list = new ArrayList<>();
            for (JsonElement element : array) {
                list.add(gson.fromJson(element, classOfT));
            }
            return list;
        }
        return new ArrayList<>();
    }
    
    /**
     * Convert object thành JSON string
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
