package controllers;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pjohnson
 * Date: 3/11/13
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultBeerStyle {
    public static JSONObject beer() {
        JSONObject defaultBeer = new JSONObject();
        defaultBeer.put(BeerStyleStructure.STYLEKEY, "");
        defaultBeer.put(BeerStyleStructure.GLASSKEY, "");
        defaultBeer.put(BeerStyleStructure.FERMTEMPKEY, "");
        defaultBeer.put(BeerStyleStructure.SERVTEMPKEY, "");
        defaultBeer.put(BeerStyleStructure.SERVPRESKEY, "");
        defaultBeer.put(BeerStyleStructure.OGKEY, "");
        defaultBeer.put(BeerStyleStructure.FGKEY, "");
        defaultBeer.put(BeerStyleStructure.IBUKEY, "");

        return new JSONObject(defaultBeer);
    }
}
