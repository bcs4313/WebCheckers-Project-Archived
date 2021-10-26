package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

public class PostSubmitTurn implements Route {

    private final TemplateEngine templateEngine;

    public PostSubmitTurn(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Submit a request to end turn.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     */

    @Override
    public Object handle(Request request, Response response) {
    return null;
    }
}
