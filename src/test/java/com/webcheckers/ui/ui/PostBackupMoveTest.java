package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.PostBackupMove;
import com.webcheckers.ui.PostSignInRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostBackupMoveTest {
    private static final GameBoard.cells X = GameBoard.cells.X;
    private static final GameBoard.cells E = GameBoard.cells.E;
    private static final GameBoard.cells W = GameBoard.cells.W;
    private static final GameBoard.cells R = GameBoard.cells.R;
    private static final GameBoard.cells WK = GameBoard.cells.WK;
    private static final GameBoard.cells RK = GameBoard.cells.RK;


    // test object
    private PostBackupMove testComponent;

    // mocks
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private SessionManager manager;

    // friendly values/objects
    private PlayerLobby playerLobby;

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
    }

    @Test
    public void basicBackupTest(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        System.out.println(testHelper.makeAnswer());
        System.out.println(testHelper.makeAnswer());
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(GetGameRoute.ID_ATTR)).thenReturn("0");
        manager = new SessionManager();

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
        GameBoard gameBoard = new GameBoard(self,opp,board,GameBoard.activeColors.WHITE);
        gameBoard.getMaster().createBoardTransition(new Position(0,1),new Position(1,2), GameBoard.activeColors.WHITE);

        manager.addSession(0,gameBoard);

        PostBackupMove beta = new PostBackupMove(engine,manager);

        assertEquals(gameBoard.getBoard().toString(), board.toString());
    }
}
