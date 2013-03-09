package controllers;


import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import redis.clients.jedis.Jedis;


/**
 * Created with IntelliJ IDEA.
 * User: mourge
 * Date: 3/7/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */


public class JsonLibrary {
    private static HashMap<String, JSONObject> internalMap = null;
    private static Jedis redisConnection;

    public JsonLibrary() {
        redisConnection = new Jedis("localhost");
        redisConnection.connect();
    }

    public void primeData() {
        addKeyValuePair("porter", createStyleEntry("porter", "pint"));
        addKeyValuePair("stout", createStyleEntry("stout", "pint"));
        addKeyValuePair("kolsh", createStyleEntry("kolsh", "pint"));
        addKeyValuePair("eipa", createStyleEntry("english ipa", "pint"));
        addKeyValuePair("aipa", createStyleEntry("american ipa", "pint"));
        addKeyValuePair("epaleale", createStyleEntry("english pale ale", "pint"));
        addKeyValuePair("apaleale", createStyleEntry("american pale ale", "pint"));
        addKeyValuePair("ebitter", createStyleEntry("english bitter", "pint"));
        addKeyValuePair("esb", createStyleEntry("extra special bitter", "pint"));

        addKeyValuePair("default", createStyleEntry("bud-lite", "pint"));
    }

    public void addKeyValuePair(String key, JSONObject value) {
        redisConnection.set(key, value.toJSONString());
    }

    public String getValue(String key) {
        return fetchFromRedis(key).toJSONString();
    }

    private JSONObject fetchFromRedis(String key) {
        JSONObject redisReturn;
        JSONParser parser = new JSONParser();
        try {
            redisReturn = (JSONObject) parser.parse(redisConnection.get(key));
        } catch (ParseException e) {
            redisReturn = createStyleEntry("Bud Lite", "Frosted Pint Glass");
        }
        return redisReturn;
    }

    public String gimmeTheKeys() {
        return redisConnection.keys("*").toString();

    }

    public JSONObject createStyleEntry(String style, String glass, JSONObject colorRange,
                JSONObject fermentationTemperature, JSONObject serveTemperature, JSONObject originalGrav,
                JSONObject ibu, String servePressure) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Style", style);
        jsonObject.put("Glass", glass);

        jsonObject.put("Color-Range", colorRange);
        jsonObject.put("Fermentation Temperatures", fermentationTemperature);
        jsonObject.put("Serving Temperatures", serveTemperature);
        jsonObject.put("Serving Pressure", servePressure);
        jsonObject.put("Original Gravity", originalGrav);
        jsonObject.put("Bitterness", ibu);

        return jsonObject;
    }

    public JSONObject createStyleEntry(String style, String glass) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Style", style);
        jsonObject.put("Glass", glass);
        jsonObject.put("Serving Pressure", "10psi");

        jsonObject.put("Color-Range", getRangeJsonObject("lower", "upper"));
        jsonObject.put("Fermentation Temperatures", getRangeJsonObject("lower", "upper"));
        jsonObject.put("Serving Temperatures", getRangeJsonObject("lower", "upper"));
        jsonObject.put("Original Gravity", getRangeJsonObject("lower", "upper"));
        jsonObject.put("Bitterness", getRangeJsonObject("lower", "upper"));

        return jsonObject;
    }

    private JSONObject getRangeJsonObject(String lower, String upper) {
        JSONObject range = new JSONObject();
        range.put("Lower", lower);
        range.put("Upper", upper);
        return range;
    }

    public void adjustUpperRange(String style, String key, String value) {
        JSONObject jsonObject = adjustRangeValue(style, key, value, "Upper");
        redisConnection.set(style, jsonObject.toJSONString());
    }

    public void adjustLowerRange(String style, String key, String value) {
        JSONObject jsonObject = adjustRangeValue(style, key, value, "Lower");
        redisConnection.set(style, jsonObject.toJSONString());
    }

    private JSONObject adjustRangeValue(String style, String key, String value, String upperLower) {
        JSONObject jsonObject = fetchFromRedis(style);
        System.out.println(String.format("   Fetching value from Redis to adjust %s %s %s %s ", style, key, value, upperLower));
        System.out.println(jsonObject);
        JSONObject rangeObject = (JSONObject) jsonObject.get(key);
        rangeObject.put(upperLower, value);

        jsonObject.put(key, rangeObject);
        return jsonObject;
    }

    public String fermenterView(String key) {
        JSONObject entry = fetchFromRedis(key);
        entry.remove("Glass");
        entry.remove("Color-Range");
        entry.remove("Serving Temperatures");
        entry.remove("Serving Pressure");
        entry.remove("Original Gravity");
        entry.remove("Bitterness");

        return entry.toJSONString();
    }

    public String kegView(String key) {
        JSONObject entry = fetchFromRedis(key);

        entry.remove("Glass");
        entry.remove("Fermentation Temperatures");
        entry.remove("Color-Range");
        entry.remove("Original Gravity");
        entry.remove("Bitterness");

        return entry.toJSONString();
    }
}
