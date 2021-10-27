package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

public class PostSubmitTurn implements Route {

    private final SessionManager sessionManager;
    private final TemplateEngine templateEngine;

    public PostSubmitTurn(final TemplateEngine templateEngine, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "sessionManager must not be null");
        this.templateEngine = templateEngine;
        this.sessionManager = sessionManager;
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
        Gson gson = new Gson();
        int idVal = Integer.parseInt(request.queryParams("gameID"));
        // now to retrieve a game with the queried ID
        GameBoard gb = sessionManager.retrieveSession(idVal);
        gb.switchActiveColor();
        gb.resetTurn();

        //response.redirect(WebServer.GAME_URL);
        return gson.toJson(Message.info("Submitted Turn"));
    }
}
