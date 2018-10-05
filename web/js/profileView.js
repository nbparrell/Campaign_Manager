function profileFn() {
    var target = document.getElementsByClassName("infoSection")[0];
    ajaxCall("webAPIs/getProfileAPI.jsp", displayProfile, displayProfileError);

    function displayProfile(httpRequest) {
        try{
            var obj = JSON.parse(httpRequest.responseText);
            if (obj.webUserList.length === 0) {
                target.innerHTML = "There is no user with id '" + id + "' in the database";
            } else if (!obj.webUserList[0].errorMsg === null) {
                target.innerHTML = "Error extracting the Web User from the database: " + obj.webUserList[0].errorMsg;
            } else {
                target.innerHTML = "<h4>Profile Information</h4>";
                target.innerHTML += "User Email: " + obj.webUserList[0].userEmail;
                target.innerHTML += "<br/>Password: " + obj.webUserList[0].userPassword;
                target.innerHTML += "<br/>Birthday: " + obj.webUserList[0].birthday;
                target.innerHTML += "<br/>Fees : " + obj.webUserList[0].membershipFee;
            }
        } catch(err){
            target.innerHTML = "<h4>You are not logged in</h4>";
        }
    }

    function displayProfileError(httpRequest) {
        target.innerHTML = "Error trying to make the API call: " + httpRequest.errorMsg;
    }
}
