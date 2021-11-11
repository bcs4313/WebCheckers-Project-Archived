package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.model.boardview.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.GetGameRoute.GAME_OVER_ATTR;

/**
 * the GET /spectator/game route handler.
 * @author Triston Lincoln
 */
public class GetSpectGameRoute implements Route {
    private final String VIEW_NAME = "game.ftl";
    private final TemplateEngine templateEngine;
    private final SessionManager sessionManager;
    private final PlayerLobby playerLobby;

    private static final String SPECT_MSG = "Spectating Game";
    private static final String SPECT_MODE = "SPECTATOR";
    /**
     * The constructor for the GET /spectator/game route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     * @param playerLobby
     *  the manager of all players in the application
     * @param sessionManager
     *  manages all games in the application
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

    /**
     * Render the WebCheckers Game page
     * as a spectator
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
    public String handle(Request request, Response response) {
        Session session = request.session();
        HashMap<String, Object> vm = new HashMap<>();
        Gson gson = new Gson();

        String username = request.session().attribute(GetHomeRoute.USERNAME_ATTR);
        Player currentUser = this.playerLobby.getPlayer(username);
        Player opponentUser = currentUser.getOpponent();

        int id = Integer.parseInt(request.queryParams(GetGameRoute.ID_ATTR));

        GameBoard game = sessionManager.retrieveSession(id);
        BoardView gameView = game.toBoardView();

        RuleMaster rm = game.getMaster();
        if (rm.getGameOver()){
            Player winner = rm.getWinner();
            final Map<String,Object> modeOptions = new HashMap<>(2);
            modeOptions.put(GetGameRoute.GAME_OVER_ATTR, true);
            if (game.getWhitePlayer().getResigned() || game.getRedPlayer().getResigned()){
                if (game.getWhitePlayer().equals(winner)) {
                    modeOptions.put(GetGameRoute.GAME_OVER_MSG_ATTR, game.getRedPlayer().toString() + " has resigned");
                }
                else{
                    modeOptions.put(GetGameRoute.GAME_OVER_MSG_ATTR, game.getWhitePlayer().toString() + " has resigned");
                }
            }
            else {
                if (game.getWhitePlayer().equals(winner)) {
                    modeOptions.put(GetGameRoute.GAME_OVER_MSG_ATTR, game.getWhitePlayer().toString() + " has captured all pieces");
                }
                else{
                    modeOptions.put(GetGameRoute.GAME_OVER_MSG_ATTR, game.getRedPlayer().toString() + " has captured all pieces");
                }
            }
            vm.put(GetGameRoute.MODE_ATTR, gson.toJson(modeOptions));
        }

        vm.put(GetGameRoute.CUR_USER_ATTR, currentUser);
        vm.put(GetHomeRoute.TITLE_ATTR, SPECT_MSG);
        vm.put(GetGameRoute.VIEW_ATTR,SPECT_MODE);
        vm.put(GetGameRoute.RED_PLAY_ATTR, game.getRedPlayer());
        vm.put(GetGameRoute.WHITE_PLAY_ATTR, game.getWhitePlayer());
        vm.put(GetGameRoute.ACTIVE_ATTR, game.getActiveColor());
        vm.put(GetGameRoute.BOARD_ATTR, gameView);
        vm.put(GetGameRoute.GAME_ATTR,game);
        vm.put(GetHomeRoute.USERNAME_ATTR, username);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
