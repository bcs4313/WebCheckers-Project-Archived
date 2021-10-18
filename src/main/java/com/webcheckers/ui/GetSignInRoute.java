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
    public static final String TITLE_ATTR = "title"; //Carlos added this, same as the message below this.

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

        Boolean error = request.session().attribute("error");
        System.out.println(error);                            //Carlos used this to test
        if (error != null) {
            if (error) {

                //throw new                                     //Carlos added this
                vm.put("message", INVALID_MSG);
                //halt();                                     //Carlos added this
                //response.redirect(VIEW_NAME);      //Carlos Added this
            }
        }

        vm.put(TITLE_ATTR, "Checkers Sign In"); // add title to signin.ftl  //Carlos changed this to Title_ATTR

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

