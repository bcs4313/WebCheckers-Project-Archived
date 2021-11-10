package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import static spark.Spark.halt;

/**
 * The {@code GET /signin} route handler.
 *
 * @author Michael Ambrose
 */

public class GetSignInRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    public static final String TITLE_ATTR = "title";
    public static final String SIGN_IN_MSG = "Checkers Sign In";

    private final String VIEW_NAME = "signin.ftl";
    private final TemplateEngine templateEngine;
    public static final Message INVALID_MSG = Message.error("Username invalid. Try another username.");     //Public for
                                                                                            //now but I think we have to
    /**                                                                                  //move our test folder location
     * The constructor for the {@code GET/signin} route handler                    //so it doesn't have to be.
     * 
     * @param templateEngine engine used to construct a page with a ftl file.
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
   *   the rendered HTML for the SignIn page
   */

   @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        Boolean error = request.session().attribute(GetHomeRoute.ERROR_ATTR);
        if (error != null) {
            if (error) {
                // Tell user that inputted name was invalid
                vm.put(GetHomeRoute.MESSAGE_ATTR, INVALID_MSG);

            }
        }

        vm.put(TITLE_ATTR, SIGN_IN_MSG); // add title to signin.ftl

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

