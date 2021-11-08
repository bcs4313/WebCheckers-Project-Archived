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

        GameBoard.cells[][] debugBoard = gb.getBoard();
        System.out.println("Prev: prevBoard ->");
        for (GameBoard.cells[] cells : debugBoard) {
            for (GameBoard.cells cell : cells) {
                System.out.print(cell + ", ");
            }
            System.out.println();
        }

        //FIX for undo issues:
        // incorporate the log and chainer objects for sync purposes.
        MoveLog log = master.getLog();
        GameBoard.cells[][] prevBoard = log.getPrevPosition();

        System.out.println("After: prevBoard ->");
        for (GameBoard.cells[] cells : prevBoard) {
            for (GameBoard.cells cell : cells) {
                System.out.print(cell + ", ");
            }
            System.out.println();
        }

        gb.setBoard(prevBoard); // now also modifies the master cell state
        master.getChainer().undoJump();  // undo a jump action from the chainer
        master.lowerCounter(); // reduce the movement counter by 1

        return gson.toJson(Message.info("Backup Move Successful"));
    }
}
