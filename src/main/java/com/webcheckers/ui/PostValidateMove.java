package com.webcheckers.ui;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.model.Position;
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


    /**
     * The constructor for the POST /validateMove route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostValidateMove(TemplateEngine templateEngine) {
        System.out.println("construct");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        Move move = request.attribute("actionData"); // retrieve attribute regarding movement data

        System.out.println("PostValidateTrigger");

        // retrieve positions regarding the move
        Position beforePos = move.getStart();
        Position afterPos = move.getEnd();
        System.out.println("BEFOREPOS:: y:" + beforePos.getRow() + " x:" + beforePos.getCell());
        System.out.println("AFTERPOS:: y:" + afterPos.getRow() + " x:" + afterPos.getCell());

        // now to retreive a game via the user connection
        final String username = request.queryParams("username");
        Player ply = new Player(username);
        GameBoard gb = ply.getGame();
        System.out.println("PostValidateTrigger--> " + gb.getGameID());

        // get the rulemaster of the board to evaluate the validity of a move
        RuleMaster master = gb.getMaster();
        master.createBoardTransition(beforePos, afterPos);
        master.triggerRuleSet(); // trigger the ruleset of master

        //master.

        //Message m = new Message();
        return null;
    }
}
