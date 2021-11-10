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
    <#if username??>
      Current Players:
      <#list allUsernames>
        <#items as user>
          <form action="/game" method="get">
            <ul>
              <li>
              <input type="hidden" name="opponent" value="${user}" />
              <button type="submit" class="player">${user}</button>
              </li>
            </ul>
          </form>
        </#items>
      </#list>
      <#if games??>
        Current Games:
        <#list games>
          <#items as game>
            <form action="/spectator/game" method="get">
              <ul>
                <li>
                  <input type="hidden" name="gameID" value="${game}" />
                  <button type="submit" class="game">${game}</button>
                </li>
              </ul>
            </form>
          </#items>
        </#list>
      <#else>
       No Active Games
      </#if>
    <#else>
      Number of Active Players: ${amtPlayers}
    </#if>
    </div>
  </div>
</body>

</html>
