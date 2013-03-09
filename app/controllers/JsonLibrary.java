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
    private static HashMap<String, String> internalMap = null;

    public JsonLibrary() {
        internalMap = new HashMap();
        primeData();
    }

    private void primeData() {
        addKeyValuePair("porter", "{style: \"porter\"}");
        addKeyValuePair("stout", "{style: \"stout\"}");
        addKeyValuePair("kolsh", "{style: \"kolsh\"}");
        addKeyValuePair("eipa", "{style: \"english ipa\"}");
        addKeyValuePair("aipa", "{style: \"american ipa\"}");
        addKeyValuePair("epaleale", "{style: \"english pale ale\"}");
        addKeyValuePair("apaleale", "{style: \"american pale ale\"}");
        addKeyValuePair("ebitter", "{style: \"english bitter\"}");
        addKeyValuePair("esb", "{style: \"extra special bitter\"}");

        addKeyValuePair("default", "{style: \"bud-lite\"}");
    }

    public void addKeyValuePair(String key, String value) {
        internalMap.put(key, value);
    }

    public String getValue(String key) {
        String returnValue = internalMap.get(key);
        if (returnValue != null) {
            return returnValue;
        }
        return internalMap.get("default");

    }

    public String gimmeTheKeys() {
        return internalMap.keySet().toString();

    }

    public void createStyleEntry() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Style", "style");

        JSONObject colorRange = new JSONObject();
        colorRange.put("Lower", "lower");
        colorRange.put("Upper", "upper");

        jsonObject.put("Color-Range", colorRange);

        JSONObject fermentationTemperatureRange = new JSONObject();
        fermentationTemperatureRange.put("Lower", "lower");
        fermentationTemperatureRange.put("Upper", "upper");
        jsonObject.put("Fermentation Temperatures", fermentationTemperatureRange);

        JSONObject servingTemperatureRange = new JSONObject();
        servingTemperatureRange.put("Lower", "lower");
        servingTemperatureRange.put("Upper", "upper");
        jsonObject.put("Serving Temperatures", servingTemperatureRange);

        jsonObject.put("Glass", "pint");

        JSONObject originalGravity = new JSONObject();
        originalGravity.put("Lower", "lower");
        originalGravity.put("Upper", "upper");
        jsonObject.put("Original Gravity", originalGravity);

        JSONObject ibu = new JSONObject();
        ibu.put("Lower", "lower");
        ibu.put("Upper", "upper");
        jsonObject.put("Bitterness", ibu);

    }
}
