package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

public class PostBackupMove implements Route {
    private final TemplateEngine templateEngine;
    private final SessionManager sessionManager;
    /**
     * The constructor for the POST /backupMove route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostBackupMove(TemplateEngine templateEngine, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "sessionManager must not be null");
        this.templateEngine = templateEngine;
        this.sessionManager = sessionManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();
        int idVal = Integer.parseInt(request.queryParams("gameID"));
        GameBoard gb = sessionManager.retrieveSession(idVal);

        RuleMaster master = gb.getMaster();

        gb.setboard(master.getB_before());

        return gson.toJson(Message.info("Backup Move Successful"));
    }
}
