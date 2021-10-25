package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

/**
 * The POST /game route handler.
 * @author Carlos Hargrove
 */
public class PostGameRoute implements Route {
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the POST /game route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostGameRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();
        System.out.print("POST GAME ROUTE");

        return null;
    }

}

