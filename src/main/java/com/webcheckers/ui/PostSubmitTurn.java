package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;
import com.webcheckers.model.RuleSystem.Chainer;
import com.webcheckers.model.RuleSystem.InitJumpRule;
import com.webcheckers.model.RuleSystem.MoveLog;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * the POST /submitTurn route handler.
 * @author Triston Lincoln
 */
public class PostSubmitTurn implements Route {

    private final SessionManager sessionManager;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the POST /submitTurn route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     * @param sessionManager - the manager for the games being played
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
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
        RuleMaster rm = gb.getMaster();

        // in the case where the chainer sees another possible move,
        // prevent the turn from submitting
        Chainer chainer = rm.getChainer();
        MoveLog log = rm.getLog();
        Position latestJump = chainer.head();

        if(latestJump != null) {
            int[] positions = {7 - latestJump.getCell(), 7 - latestJump.getRow()};
            // use the initJump rule with a specific restriction on where to check
            InitJumpRule ijr = new InitJumpRule(rm, gb.getActiveColor(), positions);

            if (ijr.isTriggered(rm.getB_After(), null)) {
                if (log.getLength() - chainer.getLength() == 0) {
                    return gson.toJson(Message.error("To submit this turn, you must complete the jump chain!"));
                }
            }
        }

        // we passed the turn based guards, now we can enact switching to
        // the opponent.
        gb.switchActiveColor();

        // clear chains and log
        rm.getChainer().clearJumps();
        rm.getLog().clearStack();
        rm.resetCounter(); // create a new board init state

        //response.redirect(WebServer.GAME_URL);
        return gson.toJson(Message.info("Submitted Turn"));
    }
}
