package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.boardview.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * the GET /spectator/game route handler.
 * @author Triston Lincoln
 */
public class GetSpectGameRoute implements Route {
    private final String VIEW_NAME = "game.ftl";
    private final TemplateEngine templateEngine;
    private final SessionManager sessionManager;
    private final PlayerLobby playerLobby;

    /**
     * The constructor for the GET /spectator/game route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    GetSpectGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(sessionManager, "sessionManager must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.sessionManager = sessionManager;
    }

    @Override
    public String handle(Request request, Response response) {
        Session session = request.session();
        HashMap<String, Object> vm = new HashMap<>();

        String username = request.session().attribute("username");
        Player currentUser = this.playerLobby.getPlayer(username);

        int id = Integer.parseInt(request.queryParams("gameID"));

        GameBoard game = sessionManager.retrieveSession(id);
        BoardView gameView = game.toBoardView();

        vm.put("currentUser", currentUser);
        vm.put("title", "Spectating Game");
        vm.put("viewMode","SPECTATOR");
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board", gameView);
        vm.put("game",game);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
