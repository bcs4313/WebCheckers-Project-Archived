<html>
<!--
    This is the freemarker templete for the /signin page.
    Included in the page is the form to submit a request to sign in
    (handled by the POST /signin route)
    Data needed: title
    @author: Triston Lincoln
-->
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
                <input name="username" type="text">

                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</body>

</html>