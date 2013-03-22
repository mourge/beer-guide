package controllers;

import org.json.simple.JSONObject;

public class BeerStyle {
    private class Range {
        public String upperBound, lowerBound;
    }

    private String servingPressure, style, glass;
    private Range fermtemp, servtemp, color, og, fg, ibu;

    private Range setRange(String upperBound, String lowerBound) {
        Range range = new Range();
        range.upperBound = upperBound;
        range.lowerBound = lowerBound;
        return range;
    }

    public String glass() {
        return glass;
    }

    public String style() {
        return style;
    }

    public String servingPressure() {
        return String.format("%s", servingPressure);
    }

    public String fgString() {
        return fg().toString();
    }

    public String ogString() {
        return og().toString();
    }

    public String colorString() {
        return color().toString();
    }

    public String fermtempString() {
        return fermtemp().toString();
    }

    public String servtemperatureString() {
        return servtemperature().toString();
    }

    public String ibuString() {
        return ibu().toString();
    }

    public JSONObject fg() {
        return getRange(fg);
    }

    public JSONObject color() {
        return getRange(color);
    }

    public JSONObject og() {
        return getRange(og);
    }

    public JSONObject fermtemp() {
        return getRange(fermtemp);
    }

    public JSONObject servtemperature() {
        return getRange(servtemp);
    }

    public JSONObject ibu() {
        return getRange(ibu);
    }

    private JSONObject getRange(Range range) {
        JSONObject returnObject = new JSONObject();
        returnObject.put("Upper", range.upperBound);
        returnObject.put("Lower", range.lowerBound);
        return returnObject;
    }

    public String fermenterView() {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style));
        returnValue.append(String.format("\"%s\":%s", BeerStyleStructure.FERMTEMPKEY, fermtemp()));
        returnValue.append("}");
        return returnValue.toString();
    }

    public String kegView() {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.SERVPRESKEY, servingPressure));
        returnValue.append(String.format("\"%s\":%s", BeerStyleStructure.SERVTEMPKEY, servtemperature()));
        returnValue.append("}");
        return returnValue.toString();
    }

    public String fullOutput() {
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(String.format("{\"%s\":\"%s\",", BeerStyleStructure.STYLEKEY, style()));
        returnValue.append(String.format("\"%s\":\"%s\",", BeerStyleStructure.GLASSKEY, glass()));
        returnValue.append(String.format("\"%s\":%s,", BeerStyleStructure.COLORLKEY, color().toJSONString()));
        returnValue.append(String.format("\"%s\":%s,", BeerStyleStructure.FERMTEMPKEY, fermtemp().toJSONString()));
        returnValue.append(String.format("\"%s\":%s", BeerStyleStructure.SERVTEMPKEY, servtemperature().toJSONString()));
        returnValue.append(String.format("\"%s\":%s,", BeerStyleStructure.OGKEY, og().toJSONString()));
        returnValue.append(String.format("\"%s\":%s,", BeerStyleStructure.FGKEY, fg().toJSONString()));
        returnValue.append(String.format("\"%s\":%s,", BeerStyleStructure.IBUKEY, ibu().toJSONString()));
        returnValue.append(String.format("\"%s\":\"%s\"", BeerStyleStructure.SERVPRESKEY, servingPressure));
        returnValue.append("}");

        return returnValue.toString();
    }

    public BeerStyle(JSONObject inputString) {
        JSONObject range;
        style = (String) inputString.get(BeerStyleStructure.STYLEKEY);
        servingPressure = (String) inputString.get(BeerStyleStructure.SERVPRESKEY);
        glass = (String) inputString.get(BeerStyleStructure.GLASSKEY);
        range = (JSONObject) inputString.get(BeerStyleStructure.SERVTEMPKEY);
        servtemp = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.COLORLKEY);
        color = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.FERMTEMPKEY);
        fermtemp = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.OGKEY);
        og = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.FGKEY);
        fg = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
        range = (JSONObject) inputString.get(BeerStyleStructure.IBUKEY);
        ibu = setRange((String) range.get(BeerStyleStructure.UPPER),(String) range.get(BeerStyleStructure.LOWER));
    }
}
