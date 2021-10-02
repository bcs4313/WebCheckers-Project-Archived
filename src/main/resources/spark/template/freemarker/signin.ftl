<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <div class="page">

        <h1>Web Checkers | ${title}</h1>

        <div class="body">

            <!-- Provide a message to the user, if supplied. -->
            <#include "message.ftl" />

            <!-- user sign in form, consisting of one button and on text field-->
            <form action="/signin" method="post">
                <label for="username"><b>Username</b></label>
                <input type="text">

                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</body>

</html>