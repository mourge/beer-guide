package controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: pjohnson
 * Date: 3/9/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeerStyle {
    private class Range {
        public String upperBound;
        public String lowerBound;
    }

    private int servingPressure;
    private String style;
    private String glass;

    private Range fermtemp;
    private Range color;
    private Range og;
    private Range fg;
    private Range ibu;

    private final String STYLEKEY = "Style";
    private final String GLASSKEY = "Glass";

    private final String COLORLKEY = "Color-Range";
    private final String FERMTEMPKEY ="Fermentation Temperatures";
    private final String SERVTEMPKEY ="Serving Temperatures";
    private final String SERVPRESKEY="Serving Pressure";
    private final String OGKEY ="Original Gravity";
    private final String FGKEY ="Final Gravity";
    private final String IBUKEY ="Bitterness";
    
    private void setRange(Range range, String upperBound, String lowerBound) {
        range.upperBound = upperBound;
        range.lowerBound = lowerBound;
    }

    public String servingPressure() {
        return String.format("%d psi", servingPressure);
    }

    public String ibuString() {
        return String.format("%s - %s", ibu.upperBound, ibu.lowerBound);
    }

    public JSONObject ibu() {
        JSONParser parser = new JSONParser();
        JSONObject returnObject = null;
        try {
            returnObject = (JSONObject) parser.parse(String.format("{\"Upper\": %s, \"Lower\": %s}", ibu.upperBound, ibu.lowerBound));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return returnObject;
    }

    public BeerStyle(JSONObject inputString) {
        style = (String) inputString.get(STYLEKEY);

        servingPressure =0 ;
         glass = null;

//        setRange(fermtemp, inputString.get
//         color = null;
//         og = null;
//         fg = null;
//         ibu = null;
    }
}
