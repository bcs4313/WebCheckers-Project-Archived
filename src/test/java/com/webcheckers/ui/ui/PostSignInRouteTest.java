package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.PostSignInRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tester of the PostSignInRoute class (junit)
 * @author Cody Smith
 */
public class PostSignInRouteTest {
    // test object
    private PostSignInRoute testComponent;

    // mocks
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

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

    /**
     * Attempt to login with invalid chars
     * should be denied.
     */
    @Test
    public void invalidCharacterTest()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // now to set up a playerLobby to process
        // initialize required object/s
        playerLobby = new PlayerLobby();
        // generate a route
        testComponent = new PostSignInRoute(playerLobby, engine);

        // add request parameters
        when(request.queryParams("username")).thenReturn("**?:>||");

        // now handle the response with the prepared variables
        String result = testComponent.handle(request, response);

        // assert that this login failed
        assertEquals(result, "fail");
    }

    /**
     * Login with valid credentials
     */
    @Test
    public void basicLoginTest()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // now to set up a playerLobby to process
        // initialize required object/s
        playerLobby = new PlayerLobby();
        // generate a route
        testComponent = new PostSignInRoute(playerLobby, engine);

        // add request parameters
        when(request.queryParams("username")).thenReturn("james");

        // now handle the response with the prepared variables
        String result = testComponent.handle(request, response);

        // assert that this login succeeded
        assertEquals(result, "success");
    }
}
