package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.RuleSystem.MoveLog;
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

    public static final String BACKUP_MSG = "Backup Move Successful";
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

    /**
     * Post a command to back up the a move of a player,
     * assuming its their turn.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   gson object to interpret with AJAX
     */
    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();
        int idVal = Integer.parseInt(request.queryParams(GetGameRoute.ID_ATTR));
        GameBoard gb = sessionManager.retrieveSession(idVal);

        RuleMaster master = gb.getMaster();

        // incorporate the log and chainer objects for sync purposes.
        MoveLog log = master.getLog();
        GameBoard.cells[][] prevBoard = log.getPrevPosition();

        gb.setBoard(prevBoard); // now also modifies the master cell state
        master.getChainer().undoJump();  // undo a jump action from the chainer

        return gson.toJson(Message.info(BACKUP_MSG));
    }
}
