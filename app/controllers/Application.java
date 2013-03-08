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

}
