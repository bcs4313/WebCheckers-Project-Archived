<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
  <div class="page">

    <h1>Web Checkers | ${title}</h1>

    <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />

    <div class="body">

      <!-- Provide a message to the user, if supplied. -->
      <#include "message.ftl" />

      <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
      -->
      <table class="t">
        <#if username??>
          <tr>
            <th>Current Players:</th>
            <th>Current Games:</th>
          </tr>
          <#list allUsernames as user>
            <tr>
              <td>
                <form action="/game" method="get">
                  <input type="hidden" name="opponent" value="${user}" />
                  <button type="submit" class="player">${user}</button>
                </form>
              </td>
              <#list games as game>
                <td>
                  <form action="/spectator/game" method="get">
                    <input type="hidden" name="gameID" value="${game}" />
                    <button type="submit" class="game">${game}</button>
                  </form>
                </td>
              </#list>
            </tr>
          </#list>
        <#else>
          Number of Active Players: ${amtPlayers}
        </#if>
      </table>
    </div>
  </div>
</body>

</html>
