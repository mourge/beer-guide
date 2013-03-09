package controllers;


import java.util.HashMap;
import org.json.simple.JSONObject;


/**
 * Created with IntelliJ IDEA.
 * User: mourge
 * Date: 3/7/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */


public class JsonLibrary {
    private static HashMap<String, JSONObject> internalMap = null;

    public JsonLibrary() {
        internalMap = new HashMap();
        primeData();
    }

    private void primeData() {
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
        internalMap.put(key, value);
    }

    public String getValue(String key) {
        JSONObject returnValue = internalMap.get(key);
        if (returnValue != null) {
            return returnValue.toJSONString();
        }
        return internalMap.get("default").toJSONString();

    }

    public String gimmeTheKeys() {
        return internalMap.keySet().toString();

    }

    public JSONObject createStyleEntry(String style, String glass, JSONObject colorRange,
                JSONObject fermentationTemperature, JSONObject serveTemperature, JSONObject originalGrav,
                JSONObject ibu) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Style", style);
        jsonObject.put("Glass", glass);

        jsonObject.put("Color-Range", colorRange);
        jsonObject.put("Fermentation Temperatures", fermentationTemperature);
        jsonObject.put("Serving Temperatures", serveTemperature);
        jsonObject.put("Original Gravity", originalGrav);
        jsonObject.put("Bitterness", ibu);

        return jsonObject;
    }

    public JSONObject createStyleEntry(String style, String glass) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Style", style);
        jsonObject.put("Glass", glass);

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

    public String fermenterView(String key) {
        JSONObject entry = internalMap.get(key);
        entry.remove("Glass");
        entry.remove("Color-Range");
        entry.remove("Serving Temperatures");
        entry.remove("Original Gravity");
        entry.remove("Bitterness");

        return entry.toJSONString();
    }
}
