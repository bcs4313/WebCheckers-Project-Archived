package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * the GET /spectator/checkTurn route handler.
 * @author Triston Lincoln
 */
public class PostSpectCheckRoute implements Route {
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the POST /spectator/checkTurn route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostSpectCheckRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        return null;
    }
}
