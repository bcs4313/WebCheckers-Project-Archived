package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.text.View;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.util.Message;

/**
 * The {@code GET /signin} route handler.
 *
 * @author Michael Ambrose
 */

public class GetSignInRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final String VIEW_NAME = "signin.ftl";
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code GET/signin} route handler
     * 
     * @param templateEngine
     */

    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        LOG.config("GetSignInRoute is initialized");
    }

   /**
   * Render the WebCheckers SignIn page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */

   @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Checkers Sign In");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

