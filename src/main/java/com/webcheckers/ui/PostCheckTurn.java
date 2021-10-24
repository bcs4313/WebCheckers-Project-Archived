package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.GameBoard;
import com.webcheckers.ui.boardview.BoardView;
import spark.*;

import java.util.Objects;

public class PostCheckTurn implements Route {
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the POST /validateMove route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostCheckTurn(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        BoardView boardView = session.attribute("board");

        return "success";
    }
}
