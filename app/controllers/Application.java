package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
    public static final String INVALID_TAG = "INVALID TAG";
    private static JsonLibrary library = new JsonLibrary();
  
    public static Result index() {
        return ok("Beer Me");
    }

    public static Result fullOutput(String style) {
        return ok(library.getValue(style));
    }

    public static Result keys() {
        return ok(library.gimmeTheKeys());
    }

    public static Result fermenterView(String key) {
        return ok(library.fermenterView(key));
    }

    public static Result kegView(String key) {
        return ok(library.kegView(key));
    }

    public static Result prime() {
        library.primeData();
        return ok("Data entered");
    }

    private static String keyTranslate(String key) {
        switch (key) {
            case "fermtemp" : return "Fermentation Temperatures";
            case "color" : return "Color-Range";
            case "servtemp" : return "Serving Temperatures";
            case "servepressure" : return "Serving Pressure";
            case "og" : return "Original Gravity";
            case "ibu" : return "Bitterness";
            default: return INVALID_TAG;
        }
    }

    public static Result raise(String style, String key, String newValue) {
        key = keyTranslate(key);
        if (key.equals(INVALID_TAG)) {
            return internalServerError("invalid");
        }

        library.adjustUpperRange(style, key, newValue);
         return ok("Data adjusted");
    }

    public static Result lower(String style, String key, String newValue) {
        key = keyTranslate(key);
        if (key.equals(INVALID_TAG)) {
            return internalServerError("invalid\n");
        }

        library.adjustLowerRange(style, key, newValue);
        return ok("Data adjusted\n");
    }
}
