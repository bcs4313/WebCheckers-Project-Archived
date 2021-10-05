package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardview.BoardView;
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
    
    /**
     * The constructor for the {@code GET/game} route handler
     * 
     * @param templateEngine
     */

    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
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

        final String opponent = request.queryParams("user");
        String username = request.session().attribute("username");
        Player currentUser = this.playerLobby.getPlayer(username);
        Player opponentUser = this.playerLobby.getPlayer(opponent);
        GameBoard thisBoard = new GameBoard(currentUser, opponentUser);

        BoardView thisBoardView = thisBoard.toBoardView();
        vm.put("currentUser", currentUser);
        vm.put("title", "Playing Game");
        vm.put("viewMode","PLAY");
        vm.put("redPlayer",thisBoard.getRedPlayer());
        vm.put("whitePlayer",thisBoard.getWhitePlayer());
        vm.put("activeColor","RED");
        vm.put("board", thisBoardView);


        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
