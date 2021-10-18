package com.webcheckers.ui.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.ui.GetSignInRoute;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * The unit test suite for the GetSignInRoute component.
 *
 * @author Carlos Hargrove
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

    private GetSignInRoute beta;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        beta = new GetSignInRoute(engine);
    }

    /**
     * Test that the Game view will create a new sign in page if none exists.
     */
    @Test
    public void new_game() {

        when(session.attribute("error")).thenReturn(null);


        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());


        beta.handle(request, response);


        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, "Checkers Sign In");

        testHelper.assertViewName("signin.ftl");
    }


    /**
     * Test that CuT redirects to the Home view when a @Linkplain(PlayerServices) object does
     * not exist, i.e. the session timed out or an illegal request on this URL was received.
     */

    @Test
    public void faulty_session() {

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute("error")).thenReturn(true);

        beta.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("message", GetSignInRoute.INVALID_MSG);
        testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, "Checkers Sign In");

        testHelper.assertViewName("signin.ftl");

    }

}