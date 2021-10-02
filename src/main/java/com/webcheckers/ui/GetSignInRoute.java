package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.util.Message;

/**
 * The {@code GET /signin} route handler.
 *
 * @author Michael Ambrose
 */

public class GetSignInRoute implements Route {

    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code GET/signin} route handler
     * 
     * @param templateEngine
     */

    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {

        return templateEngine.render();
    }
}

