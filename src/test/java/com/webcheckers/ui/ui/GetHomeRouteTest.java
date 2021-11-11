package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.GetSignInRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetHomeRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GetHomeRoute beta;
    private PlayerLobby lobby;
    private TemplateEngineTester testHelper;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        lobby = new PlayerLobby();
        Player self = new Player("self");
        lobby.login(self);

        beta = new GetHomeRoute(engine,lobby);
        testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    }

    @Test
    public void returnHome(){
        beta.handle(request,response);


        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);

        testHelper.assertViewName("home.ftl");
    }

    @Test
    public void faultyHome(){
        when(session.attribute("error")).thenReturn(true);
        beta.handle(request,response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.ERROR_OPP_IN_GAME_MSG);

        testHelper.assertViewName("home.ftl");

    }

}
