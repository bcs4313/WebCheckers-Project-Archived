package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.PostCheckTurn;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostCheckTurnTest {
    private static final GameBoard.cells X = GameBoard.cells.X;
    private static final GameBoard.cells E = GameBoard.cells.E;
    private static final GameBoard.cells W = GameBoard.cells.W;
    private static final GameBoard.cells R = GameBoard.cells.R;
    private static final GameBoard.cells WK = GameBoard.cells.WK;
    private static final GameBoard.cells RK = GameBoard.cells.RK;

    private TemplateEngine engine;
    private PlayerLobby lobby;
    private SessionManager manager;
    private Request request;
    private Response response;
    private Session session;

    /**
     * set up mock objects to use for this test session
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        manager= new SessionManager();
    }

    @Test
    public void checkTurnTrue(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(GetGameRoute.ID_ATTR)).thenReturn("0");
        when(request.queryParams(GetHomeRoute.USERNAME_ATTR)).thenReturn("self");
        lobby = new PlayerLobby();
        lobby.login(new Player("self"));

        Player self = new Player("self");
        Player opp = new Player("opp");
        GameBoard.cells[][] board = new GameBoard.cells[][]{
                {X, W, X, E, X, E, X, E},
                {E, X, E, X, E, X, E, X},
                {X, E, X, E, X, E, X, E},
                {E, X, E, X, E, X, E, X},
                {X, E, X, E, X, E, X, E},
                {E, X, E, X, E, X, E, X},
                {X, E, X, E, X, E, X, E},
                {R, X, E, X, E, X, E, X}};
        GameBoard gameBoard = new GameBoard(self,opp,board,GameBoard.activeColors.RED);
        manager.addSession(0,gameBoard);

        PostCheckTurn checkTurn = new PostCheckTurn(engine,lobby,manager);


        assertEquals(gameBoard.getRedPlayer(), self);
        assertEquals(gameBoard.getWhitePlayer(), opp);

        gameBoard = new GameBoard(opp,self,board,GameBoard.activeColors.RED);
        manager.addSession(0,gameBoard);

        checkTurn = new PostCheckTurn(engine,lobby,manager);


        assertEquals(gameBoard.getWhitePlayer(), self);
        assertEquals(gameBoard.getRedPlayer(), opp);

    }
}
