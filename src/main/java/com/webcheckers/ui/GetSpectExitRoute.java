package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * the GET /spectator/stopWatching route handler.
 * @author Triston Lincoln
 */
public class GetSpectExitRoute implements Route {
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the GET /spectator/stopWatching route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    GetSpectExitRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Post a command to stop spectating
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   null
     */
    @Override
    public String handle(Request request, Response response) {
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
