package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok("Beer Me");
    }

    public static Result fullOutput(String style) {
        String response = "";
        if (style.equals("porter")) {
            response = "{style: \"porter\"}";
        }
        else if (style.equals("stout")) {
            response = "{style: \"stout\"}";
        }
        else {
            response = "{style: \"bud-lite\"}";
        }
        return ok(response);
    }
}
