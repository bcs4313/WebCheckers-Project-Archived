package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the implementation of the handle
 * for the HOME route handler
 *
 * @author Triston Lincoln (trl6895@rit.edu)
 */
@Tag("UI-Tier")
class GetHomeRouteTest {

    // Component Under Test
    private GetHomeRoute CuT;

    // Friendly Objects
    private PlayerLobby playerLobby;

    // Mock Objects
    private Request request;
    private Response response;
    private Session session;
    private SessionManager sessionManager;
    private TemplateEngine engine;

    // Strings for testing sessions with players
    private static final String SESSION_USER = "hi";
    private static final String OTHER_USER = "my name is";

    /**
     * before each test, set up friendlies, mocks, and CuT
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        sessionManager = mock(SessionManager.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        playerLobby = new PlayerLobby();

        CuT = new GetHomeRoute(engine, playerLobby, sessionManager);
    }

    /**
     * Test attributes of a new session
     */
    @Test
    public void newSession() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);

        HashMap<String, Player> usernameMap = playerLobby.getUsernameMap();
        Set<String> allUsernames = usernameMap.keySet();
        ArrayList<String> copyNames = new ArrayList<>(allUsernames);

        // check if view model exists
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // check that all necessary attributes are present and correct
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERNAME_ATTR, null);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERLIST_ATTR, copyNames);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERAMT_ATTR, allUsernames.size());

        // check if view name is correct
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test session that has a signed-in user associated with the current session.
     * (Simulates the redirect from a successful attempted login from PostSignInRoute)
     */
    @Test
    public void sessionWithPlayer() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        playerLobby.login(new Player(SESSION_USER));
        playerLobby.login(new Player(OTHER_USER));

        when(session.attribute(GetHomeRoute.USERNAME_ATTR)).thenReturn(SESSION_USER);

        CuT.handle(request,response);

        HashMap<String, Player> usernameMap = playerLobby.getUsernameMap();
        Set<String> allUsernames = usernameMap.keySet();
        ArrayList<String> copyNames = new ArrayList<>(allUsernames);

        // check if view model exists
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // check that all necessary attributes are present and correct
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERNAME_ATTR, SESSION_USER);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
        copyNames.remove(SESSION_USER);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERLIST_ATTR, copyNames);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERAMT_ATTR, allUsernames.size());

        // check if view name is correct
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test session where the error attribute is present
     */
    @Test
    public void errorPresent(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        playerLobby.login(new Player(SESSION_USER));
        playerLobby.login(new Player(OTHER_USER));

        when(session.attribute(GetHomeRoute.USERNAME_ATTR)).thenReturn(SESSION_USER);
        when(request.session().attribute(GetHomeRoute.ERROR_ATTR)).thenReturn(true);

        CuT.handle(request,response);

        HashMap<String, Player> usernameMap = playerLobby.getUsernameMap();
        Set<String> allUsernames = usernameMap.keySet();
        ArrayList<String> copyNames = new ArrayList<>(allUsernames);

        // check if view model exists
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // check that all necessary attributes are present and correct
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERNAME_ATTR, SESSION_USER);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.ERROR_OPP_IN_GAME_MSG);
        copyNames.remove(SESSION_USER);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERLIST_ATTR, copyNames);
        testHelper.assertViewModelAttribute(GetHomeRoute.USERAMT_ATTR, allUsernames.size());

        // check if view name is correct
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }
}