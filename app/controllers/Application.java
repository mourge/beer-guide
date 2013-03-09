package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok("Beer Me");
    }

    public static Result fullOutput(String style) {
        JsonLibrary library = new JsonLibrary();

        return ok(library.getValue(style));
    }

    public static Result keys() {
        JsonLibrary library = new JsonLibrary();
        return ok(library.gimmeTheKeys());
    }

    public static Result fermenterView(String key) {
        JsonLibrary library = new JsonLibrary();
        return ok(library.fermenterView(key));
    }

    public static Result kegView(String key) {
        JsonLibrary library = new JsonLibrary();
        return ok(library.kegView(key));
    }

    public static Result prime() {
        JsonLibrary library = new JsonLibrary();
        library.primeData();
        return ok("Data entered");
    }
}
