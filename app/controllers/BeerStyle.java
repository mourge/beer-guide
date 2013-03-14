package controllers;

import org.json.simple.JSONObject;

public class BeerStyle {
    private class Range {
        public String upperBound;
        public String lowerBound;
    }

    private String servingPressure;
    private String style;
    private String glass;

    private Range fermtemp;
    private Range servtemp;
    private Range color;
    private Range og;
    private Range fg;
    private Range ibu;
    
    private Range setRange(String upperBound, String lowerBound) {
        Range range = new Range();
        range.upperBound = upperBound;
        range.lowerBound = lowerBound;
        return range;
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

    public JSONObject servtemperature() {
        return getRange(servtemp);
    }

    public String servtemperatureString() {
        return getRangeString(servtemp);
    }

    public String servPressure() {
        return servingPressure;
    }

    public String ibuString() {
        return getRangeString(ibu);
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

    private String getRangeString(Range range) {
        return String.format("%s - %s", range.upperBound, range.lowerBound);
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
