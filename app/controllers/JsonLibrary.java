package controllers;


import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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

    public void addKeyValuePair(String key, BeerStyle value) {
        redisConnection.set(key, value.fullOutput());
    }

    public String getValue(String key) {
        String returnValue = fetchFromRedis(key).toString();;
        return returnValue;
    }

    public JSONObject fetchFromRedis(String key) {
        JSONObject redisReturn = null;
            if (redisConnection.exists(key)) {
                String redisValue = redisConnection.get(key);
                Object obj = null;
                try {
                    obj = new JSONParser().parse((redisValue));
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                redisReturn = (JSONObject) obj;;
            }
            else {
                redisReturn = DefaultBeerStyle.beer();
            }
        return redisReturn;
    }

    public String gimmeTheKeys() {
        return redisConnection.keys("*").toString();
    }

    public BeerStyle createStyleEntry(String style, String glass) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(BeerStyleStructure.STYLEKEY, style);
        jsonObject.put(BeerStyleStructure.GLASSKEY, glass);
        jsonObject.put(BeerStyleStructure.SERVPRESKEY, "10psi");

        jsonObject.put(BeerStyleStructure.COLORLKEY, getRangeJsonObject("lower", "upper"));
        jsonObject.put(BeerStyleStructure.FERMTEMPKEY, getRangeJsonObject("lower", "upper"));
        jsonObject.put(BeerStyleStructure.SERVTEMPKEY, getRangeJsonObject("lower", "upper"));
        jsonObject.put(BeerStyleStructure.OGKEY, getRangeJsonObject("lower", "upper"));
        jsonObject.put(BeerStyleStructure.FGKEY, getRangeJsonObject("lower", "upper"));
        jsonObject.put(BeerStyleStructure.IBUKEY, getRangeJsonObject("lower", "upper"));

        return new BeerStyle(jsonObject);
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
        JSONObject rangeObject = (JSONObject) jsonObject.get(key);
        rangeObject.put(upperLower, value);

        jsonObject.put(key, rangeObject);
        return jsonObject;
    }
}
