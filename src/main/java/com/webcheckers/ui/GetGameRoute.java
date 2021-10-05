package com.webcheckers.ui;

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

    
    /**
     * The constructor for the {@code GET/game} route handler
     * 
     * @param templateEngine
     */

    public GetGameRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
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

        vm.put("title", "Playing Game");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
