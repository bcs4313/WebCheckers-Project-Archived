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

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        Gson gson = new Gson();

        String id = request.queryParams("gameID");
        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        Player currentUser = this.playerLobby.getPlayer(username);
        Player opponentUser = currentUser.getOpponent();
        GameBoard game = sessionManager.retrieveSession(Integer.parseInt(id));
        RuleMaster rm = currentUser.getGame().getMaster();
        RuleMaster opprm = opponentUser.getGame().getMaster();

        if (game.getWhitePlayer().equals(currentUser)){
            //Make white the loser/red the winner, set game states via rm and set player states
            rm.setWin("red player");
        }
        else{
            //Make red the loser/white the winner set game states via rm and set player states
            rm.setWin("white player");
        }
        currentUser.setResigned(true);
        opponentUser.setResigned(true);
        rm.setGameOver(true);
        return gson.toJson(Message.info("Successful Resignation"));
    }

}
