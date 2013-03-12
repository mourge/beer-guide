package controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;

public class BeerStyle {
    private class Range {
        public String upperBound;
        public String lowerBound;
    }

    private int servingPressure;
    private String style;
    private String glass;

    private Range fermtemp;
    private Range servtemp;
    private Range color;
    private Range og;
    private Range fg;
    private Range ibu;
    
    private void setRange(Range range, String upperBound, String lowerBound) {
        range.upperBound = upperBound;
        range.lowerBound = lowerBound;
    }

    private String glass() {
        return glass;
    }

    private String style() {
        return style;
    }

    public String servingPressure() {
        return String.format("%d psi", servingPressure);
    }

    public String fgString() {
        return getRangeString(fg);
    }

    public JSONObject fg() {
        return getRange(fg);
    }

    public String colorString() {
        return getRangeString(color);
    }

    public JSONObject color() {
        return getRange(color);
    }

    public String ogString() {
        return getRangeString(og);
    }

    public JSONObject og() {
        return getRange(og);
    }

    public String fermtempString() {
        return getRangeString(fermtemp);
    }

    public JSONObject fermtemp() {
        return getRange(fermtemp);
    }

    public String servtemperatureString() {
        return getRangeString(servtemp);
    }

    public JSONObject servtemperature() {
        return getRange(servtemp);
    }

    public String ibuString() {
        return getRangeString(ibu);
    }

    public JSONObject ibu() {
        return getRange(ibu);
    }

    private JSONObject getRange(Range range) {
        JSONParser parser = new JSONParser();
        JSONObject returnObject = null;
        try {
            returnObject = (JSONObject) parser.parse(String.format("{\"Upper\": %s, \"Lower\": %s}", range.upperBound, range.lowerBound));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return returnObject;
    }

    private String getRangeString(Range range) {
        return String.format("%s - %s", range.upperBound, range.lowerBound);
    }

    public String fermenterView(String key) {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style));
        returnValue.append(String.format("\"%s\":\"%s\"", BeerStyleStructure.FERMTEMPKEY, fermtempString()));
        returnValue.append("}");
        return returnValue.toString();
    }

    public String kegView(String key) {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.SERVPRESKEY, servingPressure()));
        returnValue.append(String.format("\"%s\":\"%s\"", BeerStyleStructure.SERVTEMPKEY, servtemperature()));
        returnValue.append("}");
        return returnValue.toString();
    }

    public String fullOutput() {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style()));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.GLASSKEY, glass()));

        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.COLORLKEY, colorString()));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.FERMTEMPKEY, fermtempString()));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.SERVPRESKEY, servingPressure()));
        returnValue.append(String.format("\"%s\":\"%s\"", BeerStyleStructure.SERVTEMPKEY, servtemperatureString()));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.OGKEY, ogString()));
        returnValue.append(String.format("\"%s\":\"%s\"", BeerStyleStructure.FGKEY, fgString()));
        returnValue.append("}");

        return returnValue.toString();
    }

    public BeerStyle(JSONObject inputString) {
        Field[] fields = BeerStyleStructure.class.getFields();
        for (Field field : fields) {
            if (!inputString.containsKey(field)) { return; }
        }

        JSONObject range;
        style = (String) inputString.get(BeerStyleStructure.STYLEKEY);

        servingPressure = new Integer((Integer) inputString.get(BeerStyleStructure.SERVPRESKEY));
        glass = (String) inputString.get(BeerStyleStructure.GLASSKEY);

        range = (JSONObject) inputString.get(BeerStyleStructure.COLORLKEY);
        setRange(color, (String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.FERMTEMPKEY);
        setRange(fermtemp, (String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.OGKEY);
        setRange(og, (String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.FGKEY);
        setRange(fg, (String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.IBUKEY);
        setRange(ibu, (String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
    }
}
