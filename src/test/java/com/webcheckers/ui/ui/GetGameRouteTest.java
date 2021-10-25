package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tester of the GetGameRoute class (junit)
 * @author Cody Smith
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    // test object
    private GetGameRoute testComponent;

    // mocks
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    // friendly values/objects
    private PlayerLobby playerLobby;
    private final String VIEW_NAME = "game.ftl";
    private final String REDIRECT_NAME = "home.ftl";

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



    // test to see if a view is generated with the appropriate player data,
    // and that the stored values in the html are non-null.
    @Test
    public void fieldsTest()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // now to set up a playerLobby to process
        // initialize required object/s
        playerLobby = new PlayerLobby();
        // generate a route
        testComponent = new GetGameRoute(engine, playerLobby);
        Player opp = new Player("opponent");
        Player self = new Player("self");
        playerLobby.login(opp);
        playerLobby.login(self);

        // add request parameters
        when(session.attribute("currentUser")).thenReturn(self);
        when(session.attribute("username")).thenReturn(self.getName());
        when(request.queryParams("opponent")).thenReturn(opp.getName());

        // now handle the response with the prepared variables
        testComponent.handle(request, response);

        // make sure that a view was generated at all
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // is this view under the right identity?
        testHelper.assertViewName(VIEW_NAME);

        // variable tests
        testHelper.assertViewModelAttribute("redPlayer", self);
        testHelper.assertViewModelAttribute("whitePlayer", opp);
        testHelper.assertViewModelAttribute("currentUser", self);
        testHelper.assertViewModelAttribute("activeColor", "RED");
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
    }

    /**
     * Test if this Route redirects to the homepage in the circumstance that
     * there is an error.
     */
    @Test
    void redirectTest()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // the main difference in this test is the highlight of
        // an opponent being in-game
        // now to set up a playerLobby to process
        // initialize required object/s
        playerLobby = new PlayerLobby();
        // generate a route
        testComponent = new GetGameRoute(engine, playerLobby);
        Player opp = new Player("opponent");
        Player self = new Player("self");
        playerLobby.login(opp);
        playerLobby.login(self);
        //opp.setInGame(true);

        // add request parameters
        when(session.attribute("currentUser")).thenReturn(self);
        when(session.attribute("username")).thenReturn(self.getName());
        when(request.queryParams("opponent")).thenReturn(opp.getName());

        // now handle the response with the prepared variables
        testComponent.handle(request, response);

        // make sure that a view was generated at all
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // uninitialized parameters imply a home.ftl redirect
        // No game variables should be pushed in such circumstances
        testHelper.assertViewModelAttributeIsAbsent("currentUser");
        testHelper.assertViewModelAttributeIsAbsent("title");
        testHelper.assertViewModelAttributeIsAbsent("viewMode");
        testHelper.assertViewModelAttributeIsAbsent("redPlayer");
        testHelper.assertViewModelAttributeIsAbsent("whitePlayer");
        testHelper.assertViewModelAttributeIsAbsent("activeColor");
        testHelper.assertViewModelAttributeIsAbsent("board");
    }

    /**
     * Tests if a page loads automatically for a player on the receiving end of a request
     */
    @Test
    void autoLoad()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // now to set up a playerLobby to process
        // initialize required object/s, with an implied opponent
        playerLobby = new PlayerLobby();
        // generate a route
        testComponent = new GetGameRoute(engine, playerLobby);
        Player opp = new Player("opponent");
        Player self = new Player("self");
        self.setOpponent(opp); // this here signifies that an opponent was assigned
        playerLobby.login(opp);
        playerLobby.login(self);

        // add request parameters
        when(session.attribute("currentUser")).thenReturn(self);
        when(session.attribute("username")).thenReturn(self.getName());
        when(request.queryParams("opponent")).thenReturn(null); // this should not be required

        // now handle the response with the prepared variables
        testComponent.handle(request, response);

        // make sure that a view was generated at all
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
    }
}
