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
import java.util.logging.Logger;

/**
 * The {@code GET /game} route handler.
 *
 * @author Michael Ambrose
 */
public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    public static final String IN_GAME_MSG = "Playing Game";

    public static final String OPP_ATTR = "opponent";
    public static final String GAME_OVER_ATTR = "isGameOver";
    public static final String GAME_OVER_MSG_ATTR = "gameOverMessage";
    public static final String MODE_ATTR = "modeOptionsAsJSON";
    public static final String CUR_USER_ATTR = "currentUser";
    public static final String VIEW_ATTR = "viewMode";
    public static final String PLAY_ATTR = "PLAY";
    public static final String RED_PLAY_ATTR = "redPlayer";
    public static final String WHITE_PLAY_ATTR = "whitePlayer";
    public static final String ACTIVE_ATTR = "activeColor";
    public static final String BOARD_ATTR = "board";
    public static final String GAME_ATTR = "game";
    public static final String ID_ATTR = "gameID";
    public static final String PLAYER_KEY = "PLAYER_KEY";

    private final String VIEW_NAME = "game.ftl";
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final SessionManager sessionManager;
    
    /**
     * The constructor for the {@code GET/game} route handler
     * 
     * @param templateEngine engine used to construct a webpage route
     * @param playerLobby
     *  the manager of all players in the application
     * @param sessionManager
     *  manages all games in the application
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
        Gson gson = new Gson();

        final Session session = request.session();

        // retrieve opponent name and get them from the player lobby,
        // also do this for the client
        final String opponent = request.queryParams(OPP_ATTR); //
        String username = request.session().attribute(GetHomeRoute.USERNAME_ATTR);
        Player currentUser = this.playerLobby.getPlayer(username);

        // if an assignable opponent exists for a player (game started),
        // automatically load a board for them.
        if (currentUser.getOpponent() != null){
            Player opponentUser = currentUser.getOpponent();
            GameBoard thisBoard = opponentUser.getGame();

            if (currentUser.getGame().getRedPlayer() == opponentUser) {
                thisBoard = thisBoard.flipBoard(); // use of return value to not affect original state
            }

            RuleMaster rm = currentUser.getGame().getMaster();
            RuleMaster opprm = opponentUser.getGame().getMaster();
            if (rm.getGameOver()){
                Player winner = rm.getWinner();
                if (currentUser.equals(winner)){
                    if (opponentUser.equals(opponentUser.getGame().getRedPlayer())){
                        opprm.setWin(RuleMaster.WHITE_ATTR);
                    }
                    else{
                        opprm.setWin(RuleMaster.RED_ATTR);
                    }
                    opprm.setGameOver(true);
                    winner.setInGame(false, thisBoard);
                }
                else{
                    currentUser.setInGame(false, thisBoard);
                }
                final Map<String,Object> modeOptions = new HashMap<>(2);

                modeOptions.put(GAME_OVER_ATTR, true);

                if (currentUser.getResigned() || opponentUser.getResigned()){
                    if (currentUser.equals(winner)) {
                        modeOptions.put(GAME_OVER_MSG_ATTR, opponentUser + " has resigned");
                    }
                    else{
                        response.redirect(WebServer.HOME_URL);
                    }
                }
                else {
                    modeOptions.put(GAME_OVER_MSG_ATTR, winner.toString() + " has captured all pieces");
                }
                vm.put(MODE_ATTR, gson.toJson(modeOptions));
            }

            BoardView thisBoardView = thisBoard.toBoardView();
            vm.put(CUR_USER_ATTR, currentUser);
            vm.put(GetHomeRoute.TITLE_ATTR, IN_GAME_MSG);
            vm.put(VIEW_ATTR,PLAY_ATTR);
            vm.put(RED_PLAY_ATTR, thisBoard.getRedPlayer());
            vm.put(WHITE_PLAY_ATTR, thisBoard.getWhitePlayer());
            vm.put(ACTIVE_ATTR, thisBoard.getActiveColor());
            vm.put(BOARD_ATTR, thisBoardView);
            vm.put(GAME_ATTR,thisBoard);

            // Game ID must be stored in session
            vm.put(ID_ATTR, currentUser.getGame().getGameID());

            // store client player id into session
            session.attribute(PLAYER_KEY, currentUser.toString());

        }
        else {
            Player opponentUser = this.playerLobby.getPlayer(opponent);
            if(opponentUser == null)
            {
                session.attribute(GetHomeRoute.ERROR_ATTR, true);
                response.redirect(WebServer.HOME_URL);
            }
            if (opponentUser.isInGame()) { // error case
                session.attribute(GetHomeRoute.ERROR_ATTR, true);
                response.redirect(WebServer.HOME_URL);
            }
            else{

                currentUser.setOpponent(opponentUser);
                opponentUser.setOpponent(currentUser);
                GameBoard thisBoard = new GameBoard(currentUser, opponentUser);
                currentUser.setInGame(true, thisBoard);
                opponentUser.setInGame(true, thisBoard);

                // game must be stored in SessionManager
                sessionManager.addSession(thisBoard.getGameID(), thisBoard);

                BoardView thisBoardView = thisBoard.toBoardView();
                vm.put(CUR_USER_ATTR, currentUser);
                vm.put(GetHomeRoute.TITLE_ATTR, IN_GAME_MSG);
                vm.put(VIEW_ATTR,PLAY_ATTR);
                vm.put(RED_PLAY_ATTR, thisBoard.getRedPlayer());
                vm.put(WHITE_PLAY_ATTR, thisBoard.getWhitePlayer());
                vm.put(ACTIVE_ATTR, thisBoard.getActiveColor());
                vm.put(BOARD_ATTR, thisBoardView);
                vm.put(GAME_ATTR,thisBoard);

                // Game ID must be stored in session
                vm.put(ID_ATTR, currentUser.getGame().getGameID());

                // store client player id into session
                session.attribute(PLAYER_KEY, currentUser.toString());
            }
        }

        vm.put(GetHomeRoute.USERNAME_ATTR, username); // store username in home.ftl

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
