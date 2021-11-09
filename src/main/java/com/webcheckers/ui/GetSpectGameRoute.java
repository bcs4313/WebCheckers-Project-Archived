package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * the GET /spectator/game route handler.
 * @author Triston Lincoln
 */
public class GetSpectGameRoute implements Route {
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the GET /spectator/game route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    GetSpectGameRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        return null;
    }
}
