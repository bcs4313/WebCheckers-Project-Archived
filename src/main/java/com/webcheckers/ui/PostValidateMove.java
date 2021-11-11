package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.util.Message;
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

    private static final String ACTION_DATA = "actionData";
    private static final String GOOD_MOVE = "Nice Move!";
    private static final String BAD_MOVE = "Move is not allowed bruh";

    /**
     * The constructor for the POST /validateMove route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     * @param sessionManager - allows easy identification of the game in question.
     * @throws NullPointerException
     *    when the sessionManager or templateEngine parameter is null
     */
    PostValidateMove(TemplateEngine templateEngine, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.sessionManager = sessionManager;
    }

    /**
     * Post a command containing the information regarding a move,
     * If the move is valid (RuleMaster checks this), Change the
     * GameBoard matrix according to the info.
     * if not, revert the move and print an error message.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   gson object to interpret with AJAX
     */
    @Override
    public String handle(Request request, Response response) {
        Gson gson = new Gson();
        Move movement = gson.fromJson(request.queryParams(ACTION_DATA), Move.class);

        // retrieve positions regarding the move
        Position beforePos = movement.getStart();
        Position afterPos = movement.getEnd();

        // retrieve gameID from session manager
        int idVal = Integer.parseInt(request.queryParams(GetGameRoute.ID_ATTR));

        // now to retrieve a game with the queried ID
        GameBoard gb = sessionManager.retrieveSession(idVal);

        // get the rulemaster of the board to evaluate the validity of a move
        RuleMaster master = gb.getMaster();

        // clone the previous board position to use if move is invalid
        GameBoard.cells[][] beforeBoard = copyBoard(gb.getBoard());

        // now create a board transition and trigger the master ruleset
        master.createBoardTransition(beforePos, afterPos, sessionManager.retrieveSession(gb.getGameID()).getActiveColor());

        // are we allowed to make this move?
        boolean result = master.triggerRuleSet(); // trigger the ruleset of master

        if(result)
        {
            // since the move is valid, we must send one of two messages
            // true: Nice Move!
            // false: opponent is still taking their turn

            return gson.toJson(Message.info(GOOD_MOVE));
        }
        else
        {
            gb.setBoard(beforeBoard); // undo board on an invalid move
            master.getLog().getPrevPosition(); // undo log storage
            return gson.toJson(Message.error(BAD_MOVE));
        }
    }

    /**
     * Helper method to copy a board state
     * @return copied matrix state
     */
    public GameBoard.cells[][] copyBoard(GameBoard.cells[][] reference)
    {
        GameBoard.cells[][] board = new GameBoard.cells[8][8];
        for(int y = 0; y < reference.length; y++)
        {
            System.arraycopy(reference[y], 0, board[y], 0, reference.length);
        }
        return board;
    }
}