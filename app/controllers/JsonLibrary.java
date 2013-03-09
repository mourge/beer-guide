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

}
