package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

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
     * @return gson message indicating turn submission success or failure
     */

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();
        int idVal = Integer.parseInt(request.queryParams("gameID"));
        // now to retrieve a game with the queried ID
        GameBoard gb = sessionManager.retrieveSession(idVal);
        gb.switchActiveColor();
        RuleMaster rm = gb.getMaster();

        // in the case where the chainer sees another possible move,
        // prevent the turn from submitting
        if(rm.getChainer().mustJump())
        {
            return gson.toJson(Message.error("To submit this turn, you must complete the jump chain!"));
        }

        // clear chains and log
        rm.getChainer().clearJumps();
        rm.getLog().clearStack();

        //response.redirect(WebServer.GAME_URL);
        return gson.toJson(Message.info("Submitted Turn"));
    }
}
