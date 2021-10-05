package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;
import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;

import java.util.*;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private static final Message ERROR_OPP_IN_GAME_MSG = Message.error("Requested opponent is already in a game.");

  private final TemplateEngine templateEngine;

  private final PlayerLobby playerLobby;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    // get a map of players in the lobby, using the keyset
    // to obtain purely a set of player names.
    HashMap<String, Player> usernameMap = this.playerLobby.getUsernameMap();
    Set<String> allUsernames = usernameMap.keySet();
    ArrayList<String> copyNames = new ArrayList<>(allUsernames);

    vm.put("title", "Welcome!"); // store title in home.ftl

    // attempt to retrieve the username for the session
    String username = request.session().attribute("username");
    Player currentUser = this.playerLobby.getPlayer(username);

    if (currentUser != null) {
      if (currentUser.isInGame()) {
        response.redirect(WebServer.GAME_URL);
      }
    }

    vm.put("username", username); // store username in home.ftl

    copyNames.remove(username);
    //store list of all signed-in usernames
    vm.put("allUsernames", copyNames);

    //store the amount of active players
    vm.put("amtPlayers", allUsernames.size());


    // get a boolean that evaluates if a previous signin was
    // invalid. If so, identify the error and display it on the home.ftl
    // page.
    Boolean error = request.session().attribute("error");
    if (error != null){
      if (error) {
        // if user selected an opponent who is in a game, display this message
        vm.put("message", ERROR_OPP_IN_GAME_MSG);
      }
      else {
        // display a user message in the Home page
        vm.put("message", WELCOME_MSG);
      }
    }
    else {
      // display a user message in the Home page
      vm.put("message", WELCOME_MSG);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
