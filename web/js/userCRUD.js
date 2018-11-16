
var userCRUD = {}; // globally available object


(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    userCRUD.startInsert = function () {

        ajax('htmlPartial/insertUpdateUser.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajax("webAPIs/getRolesAPI.jsp", setRolePickList, "userRoleIdError");

            function setRolePickList(httpRequest) {

                console.log("setRolePickList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("userRoleIdError").innerHTML = jsonObj.dbError;
                    return;
                }

                /*  copy/pasting the first entry from the output of my get role API
                 * 
                 {
                 "dbError": "",
                 "roleList": [
                 {
                 "userRoleId": "1",
                 "userRoleType": "Admin",
                 "errorMsg": ""
                 },
                 */

                // function makePickList(list, keyProp, valueProp, selectListId) {
                makePickList(jsonObj.roleList, "userRoleId", "userRoleType", "rolePickList");
            }
        }
    };


    userCRUD.insertSave = function () {

        console.log("userCRUD.insertSave was called");

        var ddList = document.getElementById("rolePickList");

        // create a user object from the values that the user has typed into the page.
        var userInputObj = {
            "webUserId": "",
            "Username": document.getElementById("userUsername").value,
            "userEmail": document.getElementById("userEmail").value,
            "firstName": document.getElementById("userFName").value,
            "lastName": document.getElementById("userLName").value,
            "userPassword": document.getElementById("userPassword").value,
            "userPassword2": document.getElementById("userPassword2").value,
            "birthday": document.getElementById("birthday").value,
            "membershipFee": document.getElementById("membershipFee").value,

            // Modification here for role pick list
            "userRoleId": ddList.options[ddList.selectedIndex].value,

            "userRoleType": "",
            "errorMsg": ""
        };
        console.log(userInputObj);

        // build the url for the ajax call. Remember to escape the user input object or else 
        // you'll get a security error from the server. JSON.stringify converts the javaScript
        // object into JSON format (the reverse operation of what gson does on the server side).
        var myData = escape(JSON.stringify(userInputObj));
        var url = "webAPIs/insertUserAPI.jsp?jsonData=" + myData;
        ajax(url, processInsert, "recordError");

        function processInsert(httpRequest) {
            console.log("processInsert was called here is httpRequest.");
            console.log(httpRequest);

            // the server prints out a JSON string of an object that holds field level error 
            // messages. The error message object (conveniently) has its fiels named exactly 
            // the same as the input data was named. 
            var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
            console.log("here is JSON object (holds error messages.");
            if (undefinedCheck(jsonObj.errorMsg)) {
                if (undefinedCheck(jsonObj.Username)) {
                    document.getElementById("userUsernameError").innerHTML = jsonObj.Username;
                }else{
                    document.getElementById("userUsernameError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.userEmail)) {
                    document.getElementById("userEmailError").innerHTML = jsonObj.userEmail;
                }else{
                    document.getElementById("userEmailError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.firstName)) {
                    document.getElementById("userFNameError").innerHTML = jsonObj.firstName;
                }else{
                    document.getElementById("userFNameError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.lastName)) {
                    document.getElementById("userLNameError").innerHTML = jsonObj.lastName;
                }else{
                    document.getElementById("userLNameError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.userPassword)) {
                    document.getElementById("userPasswordError").innerHTML = jsonObj.userPassword;
                }else{
                    document.getElementById("userPasswordError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.userPassword2)) {
                    document.getElementById("userPassword2Error").innerHTML = jsonObj.userPassword2;
                }else{
                    document.getElementById("userPassword2Error").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.birthday)) {
                    document.getElementById("birthdayError").innerHTML = jsonObj.birthday;
                }else{
                    document.getElementById("birthdayError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.membershipFee)) {
                    document.getElementById("membershipFeeError").innerHTML = jsonObj.membershipFee;
                }else{
                    document.getElementById("membershipFeeError").innerHTML = "";
                }
                if (undefinedCheck(jsonObj.userRoleId)) {
                    document.getElementById("userRoleIdError").innerHTML = jsonObj.userRoleId;
                }else{
                    document.getElementById("userRoleIdError").innerHTML = "";
                }
            } else
            {
                userCRUD.list();
            }

            if (jsonObj.errorMsg.length === 0) { // success
                jsonObj.errorMsg = "Record successfully inserted !!!";
            }
            document.getElementById("recordError").innerHTML = jsonObj.errorMsg;
        }
        
        function undefinedCheck(param){ 
            if(typeof param === typeof Undefined){
                console.log("runs");
                return false;
            }
            return true;
        }
    };


    userCRUD.list = function () {

        document.getElementById("content").innerHTML = "";
        var dataList = document.createElement("div");
        dataList.id = "dataList"; // set the id so it matches CSS styling rule.
        dataList.innerHTML = "<h2>Web Users <img class='iconBtn' src='icons/insert.png' onclick='userCRUD.startInsert();'/></h2>";
        document.getElementById("content").appendChild(dataList);

        ajax('webAPIs/listUsersAPI.jsp', setListUI, 'dataList');

        function setListUI(httpRequest) {

            console.log("starting userCRUD.list (setListUI) with this httpRequest object (next line)");
            console.log(httpRequest);

            var obj = JSON.parse(httpRequest.responseText);

            if (obj === null) {
                dataList.innerHTML = "listUsersResponse Error: JSON string evaluated to null.";
                return;
            }

            for (var i = 0; i < obj.webUserList.length; i++) {

                // remove a property from each object in webUserList 
                delete obj.webUserList[i].userPassword2;
            }

            // buildTable Parameters: 
            // First:  array of objects that are to be built into an HTML table.
            // Second: string that is database error (if any) or empty string if all OK.
            // Third:  reference to DOM object where built table is to be stored. 
            buildTable(obj.webUserList, obj.dbError, dataList);
        }
    };

}());  // the end of the IIFE