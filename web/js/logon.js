function loginFn(username, password) {
    var target = document.getElementById("loginStatus");
    ajaxCall("webAPIs/logonAPI.jsp?username=" + username + "&pass=" + password, displayLoginSuccess, displayLoginError);

    function displayLoginSuccess(httpRequest) {
        var obj = JSON.parse(httpRequest.responseText);
        console.log(obj);
        if (obj.webUserList.length === 0) {
            target.innerHTML = "There is no user with that username or password";
        } else if (!obj.webUserList[0].errorMsg === null) {
            target.innerHTML = "Error extracting the Web User from the database: "+obj.webUserList[0].errorMsg;
        } else {
            target.innerHTML = "<h4>Logged in</h4>";
        }
    }

    function displayLoginError(httpRequest) {
        target.innerHTML = "Error trying to make the API call: " + httpRequest.errorMsg;
    }
}
