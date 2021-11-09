package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.*;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  //
  // Constants
  //

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";

  /**
   * The URL pattern to request the SignIn page
   */
  public static final String SIGNIN_URL = "/signin";

  /**
   * The URL pattern to request the SignOut page
   */
  public static final String SIGNOUT_URL = "/signout";

  /**
   * The URL pattern to request the Game page
   */
  public static final String GAME_URL = "/game";

  public static final String SPECT_GAME_URL = "/spectator/game";

  public static final String SPECT_CHECK_URL = "/spectator/checkTurn";

  public static final String SPECT_EXIT_URL = "/spectator/stopWatching";

  public static final String CHECKTURN_URL = "/checkTurn";

  public static final String VALIDATEMOVE_URL = "/validateMove";

  public static final String BACKUPMOVE_URL = "/backupMove";

  public static final String RESIGNGAME_URL = "/resignGame";

  public static final String SUBMITTURN_URL = "/submitTurn";
  //
  // Attributes
  //

  private final TemplateEngine templateEngine;
  private final Gson gson;

  private final PlayerLobby playerLobby; // player waiting room manager
  private final SessionManager sessionManager; // game ID management system

  //
  // Constructor
  //

  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   * @param playerLobby
   *    Application Level object that manages user sign in
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final TemplateEngine templateEngine, final Gson gson, final PlayerLobby playerLobby, final SessionManager sessionManager) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    Objects.requireNonNull(playerLobby, "PlayerLobby must not be null");
    //
    this.templateEngine = templateEngine;
    this.gson = gson;
    this.playerLobby = playerLobby;
    this.sessionManager = sessionManager;
  }

  //
  // Public methods
  //

  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFileLocation("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    //// A route is set for a request verb by specifying the path for the
    //// request, and the function callback (request, response) -> {} to
    //// process the request. The order that the routes are defined is
    //// important. The first route (request-path combination) that matches
    //// is the one which is invoked. Additional documentation is at
    //// http://sparkjava.com/documentation.html and in Spark tutorials.

    //// Each route (processing function) will check if the request is valid
    //// from the client that made the request. If it is valid, the route
    //// will extract the relevant data from the request and pass it to the
    //// application object delegated with executing the request. When the
    //// delegate completes execution of the request, the route will create
    //// the parameter map that the response template needs. The data will
    //// either be in the value the delegate returns to the route after
    //// executing the request, or the route will query other application
    //// objects for the data needed.

    //// FreeMarker defines the HTML response using templates. Additional
    //// documentation is at
    //// http://freemarker.org/docs/dgui_quickstart_template.html.
    //// The Spark FreeMarkerEngine lets you pass variable values to the
    //// template via a map. Additional information is in online
    //// tutorials such as
    //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

    //// These route definitions are examples. You will define the routes
    //// that are appropriate for the HTTP client interface that you define.
    //// Create separate Route classes to handle each route; this keeps your
    //// code clean; using small classes.

    // Shows the Checkers game Home page.
    get(HOME_URL, new GetHomeRoute(templateEngine, this.playerLobby, this.sessionManager));

    // Shows the Checkers game SignIn page.
    get(SIGNIN_URL, new GetSignInRoute(templateEngine));

    // Shows the Checkers game Game page
    get(GAME_URL, new GetGameRoute(templateEngine, this.playerLobby, this.sessionManager));

    get(SPECT_GAME_URL, new GetSpectGameRoute(templateEngine, this.playerLobby, this.sessionManager));

    get(SPECT_EXIT_URL, new GetSpectExitRoute(templateEngine));

    // Posts login request to Signin Page
    post(SIGNIN_URL, new PostSignInRoute(playerLobby, templateEngine));

    post(SIGNOUT_URL, new PostSignOutRoute(playerLobby, templateEngine));

    post(CHECKTURN_URL, new PostCheckTurn(templateEngine, this.playerLobby, this.sessionManager));

    post(VALIDATEMOVE_URL, new PostValidateMove(templateEngine, this.sessionManager));

    post(BACKUPMOVE_URL, new PostBackupMove(templateEngine, this.sessionManager));

    post(RESIGNGAME_URL, new PostResignGame(templateEngine, this.playerLobby, this.sessionManager));

    post(SUBMITTURN_URL, new PostSubmitTurn(templateEngine, this.sessionManager));

    post(SPECT_CHECK_URL, new PostSpectCheckRoute(templateEngine));
    //
    LOG.config("WebServer is initialized.");
  }

}