package controllers;


import java.util.HashMap;

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
        addKeyValuePair("eipa", "{style: \"eipa\"}");
        addKeyValuePair("aipa", "{style: \"aipa\"}");
        addKeyValuePair("epaleale", "{style: \"epaleale\"}");
        addKeyValuePair("apaleale", "{style: \"apaleale\"}");
        addKeyValuePair("ebitter", "{style: \"ebitter\"}");

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
}
