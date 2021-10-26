package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.boardview.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The {@code GET /game} route handler.
 *
 * @author Michael Ambrose
 */

public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final String VIEW_NAME = "game.ftl";
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final SessionManager sessionManager;
    
    /**
     * The constructor for the {@code GET/game} route handler
     * 
     * @param templateEngine engine used to construct a webpage route
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.sessionManager = Objects.requireNonNull(sessionManager, "playerLobby is required");
        LOG.config("GetGameRoute is initialized");
    }

    /**
     * Render the WebCheckers Game page
     * 
     * @param request
     *  the HTTP request
     * @param response
     *  the HTTP response
     * 
     * @return
     *  the rendered HTML for the Game page
     */

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        HashMap<String, Player> usernameMap = this.playerLobby.getUsernameMap();

        System.out.println("GetGameRoute trigger");

        final Session session = request.session();

        // retrieve opponent name and get them from the player lobby,
        // also do this for the client
        final String opponent = request.queryParams("opponent"); //
        String username = request.session().attribute("username");
        Player currentUser = this.playerLobby.getPlayer(username);

        // if an assignable opponent exists for a player (game started),
        // automatically load a board for them.
        if (currentUser.getOpponent() != null){
            Player opponentUser = currentUser.getOpponent();
            GameBoard thisBoard = opponentUser.getGame();

            System.out.println("ID:: " + thisBoard.getGameID());

            if (currentUser.getGame().getRedPlayer() == opponentUser) {
                thisBoard = thisBoard.flipBoard(); // use of return value to not affect original state
            }

            BoardView thisBoardView = thisBoard.toBoardView();
            vm.put("currentUser", currentUser);
            vm.put("title", "Playing Game");
            vm.put("viewMode","PLAY");
            vm.put("redPlayer", thisBoard.getRedPlayer());
            vm.put("whitePlayer", thisBoard.getWhitePlayer());
            vm.put("activeColor", thisBoard.getActiveColor());
            vm.put("board", thisBoardView);
            vm.put("game",thisBoard);

            System.out.println("activeColor = " + thisBoard.getActiveColor().toString());

            // Game ID must be stored in session
            vm.put("gameID", currentUser.getGame().getGameID());

            // store client player id into session
            session.attribute("PLAYER_KEY", currentUser.toString());

        }
        else {
            Player opponentUser = this.playerLobby.getPlayer(opponent);
            if(opponentUser == null)
            {
                session.attribute("error", true);
                response.redirect(WebServer.HOME_URL);
            }
            if (opponentUser.isInGame()) { // error case
                session.attribute("error", true);
                response.redirect(WebServer.HOME_URL);
            }
            else{

                currentUser.setOpponent(opponentUser);
                opponentUser.setOpponent(currentUser);
                GameBoard thisBoard = new GameBoard(currentUser, opponentUser);
                currentUser.setInGame(true, thisBoard);
                opponentUser.setInGame(true, thisBoard);

                System.out.println("ID:: " + thisBoard.getGameID());

                // game must be stored in SessionManager
                sessionManager.addSession(thisBoard.getGameID(), thisBoard);

                // Game ID must be stored in session
                vm.put("gameID", currentUser.getGame().getGameID());

                BoardView thisBoardView = thisBoard.toBoardView();
                vm.put("currentUser", currentUser);
                vm.put("title", "Playing Game");
                vm.put("viewMode","PLAY");

                System.out.println("activeColor == " + thisBoard.getActiveColor().toString());

                vm.put("redPlayer", thisBoard.getRedPlayer());
                vm.put("whitePlayer", thisBoard.getWhitePlayer());
                vm.put("activeColor",thisBoard.getActiveColor());
                vm.put("board", thisBoardView);
                vm.put("game",thisBoard);

                // store client player id into session
                session.attribute("PLAYER_KEY", currentUser.toString());
            }
        }

        vm.put("username", username); // store username in home.ftl

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
