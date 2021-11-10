package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

public class PostResignGame implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final SessionManager sessionManager;

    public final static String RESIGN_MSG = "Successful Resignation";
    /**
     * The constructor for the POST /resignGame route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostResignGame(TemplateEngine templateEngine, PlayerLobby playerLobby, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.sessionManager = sessionManager;
    }

    /**
     * Post a command to quit a game permanently,
     * giving the other player the win.
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
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        Gson gson = new Gson();

        String id = request.queryParams(GetGameRoute.ID_ATTR);
        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        Player currentUser = this.playerLobby.getPlayer(username);
        Player opponentUser = currentUser.getOpponent();
        GameBoard game = sessionManager.retrieveSession(Integer.parseInt(id));
        RuleMaster rm = currentUser.getGame().getMaster();

        if (game.getWhitePlayer().equals(currentUser)){
            //Make white the loser/red the winner, set game states via rm and set player states
            rm.setWin(RuleMaster.RED_ATTR);
        }
        else{
            //Make red the loser/white the winner set game states via rm and set player states
            rm.setWin(RuleMaster.WHITE_ATTR);
        }
        currentUser.setResigned(true);
        opponentUser.setResigned(true);
        rm.setGameOver(true);
        return gson.toJson(Message.info(RESIGN_MSG));
    }

}
