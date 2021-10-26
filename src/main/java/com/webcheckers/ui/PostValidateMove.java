package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.model.RuleSystem.RuleMaster;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * This route is called first when a player makes a move,
 * it must return a message of either ERROR or INFO type
 * (enum). This tells the server if the move is acceptable.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class PostValidateMove implements Route {
    private final TemplateEngine templateEngine;
    private final SessionManager sessionManager;

    /**
     * The constructor for the POST /validateMove route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     * @param sessionManager - allows easy identification of the game in question.
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostValidateMove(TemplateEngine templateEngine, SessionManager sessionManager) {
        System.out.println("construct");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.sessionManager = sessionManager;
    }

    @Override
    public String handle(Request request, Response response) {
        Gson gson = new Gson();
        Move movement = gson.fromJson(request.queryParams("actionData"), Move.class);

        System.out.println("PostValidateTrigger");
        System.out.println("obj = " + movement.toString());
        // retrieve positions regarding the move
        Position beforePos = movement.getStart();
        Position afterPos = movement.getEnd();

        System.out.println("beforePos = " + beforePos.toString());
        System.out.println("afterPos = " + afterPos.toString());

        System.out.println("BEFOREPOS:: y:" + beforePos.getRow() + " x:" + beforePos.getCell());
        System.out.println("AFTERPOS:: y:" + afterPos.getRow() + " x:" + afterPos.getCell());

        System.out.println("GAMEID: " + request.queryParams("gameID"));

        // retrieve gameID from session manager
        int idVal = Integer.parseInt(request.queryParams("gameID"));

        // now to retrieve a game with the queried ID
        GameBoard gb = sessionManager.retrieveSession(idVal);

        // get the rulemaster of the board to evaluate the validity of a move
        RuleMaster master = gb.getMaster();

        // now create a board transition and trigger the master ruleset
        master.createBoardTransition(beforePos, afterPos);
        master.triggerRuleSet(); // trigger the ruleset of master


        return null;
    }
}
