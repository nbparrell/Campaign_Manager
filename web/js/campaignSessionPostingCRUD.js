
var campaignSessionPostingCRUD = {}; // globally available object


(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    campaignSessionPostingCRUD.startInsert = function () {

        ajax('htmlPartial/insertCampaignSessionPosting.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajax("webAPIs/getUsersAPI.jsp", setUserPickList, "cspUserError");
            ajax("webAPIs/getSessionsAPI.jsp", setSessionPickList, "cspSessionError");

            function setUserPickList(httpRequest) {

                console.log("setPickList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("cspUserError").innerHTML = jsonObj.dbError;
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
                makePickList(jsonObj.webUserList, "webUserId", "Username", "cspUserPickList");
            }
            
            function setSessionPickList(httpRequest) {

                console.log("setPickList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("cspSessionError").innerHTML = jsonObj.dbError;
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
                makePickList(jsonObj.webUserList, "campaign_session_id", "Name", "cspSessionPickList");
            }
        }
    };


    campaignSessionPostingCRUD.insertSave = function () {

        console.log("campaignSessionPostingCRUD.insertSave was called");

        var ddUserList = document.getElementById("cspUserPickList");
        var ddSessionList = document.getElementById("cspSessionPickList");

        // create a user object from the values that the user has typed into the page.
        var userInputObj = {
            "webUserId": ddUserList.options[ddUserList.selectedIndex].value,
            "campaign_session_id": ddSessionList.options[ddSessionList.selectedIndex].value,
            "campaign_session_posting_name": document.getElementById("cspName").value,
            "Notes": document.getElementById("cspDesc").value,
            "errorMsg": "",
            "successMsg": ""
        };
        console.log(userInputObj);

        // build the url for the ajax call. Remember to escape the user input object or else 
        // you'll get a security error from the server. JSON.stringify converts the javaScript
        // object into JSON format (the reverse operation of what gson does on the server side).
        var myData = escape(JSON.stringify(userInputObj));
        var url = "webAPIs/insertPostingAPI.jsp?jsonData=" + myData;
        ajax(url, processInsert, "responseText");

        function processInsert(httpRequest) {
            console.log("processInsert was called here is httpRequest.");
            console.log(httpRequest);

            // the server prints out a JSON string of an object that holds field level error 
            // messages. The error message object (conveniently) has its fiels named exactly 
            // the same as the input data was named. 
            var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
            console.log("here is JSON object (holds error messages.");
            if (undefinedCheck(jsonObj.errorMsg)) {
                document.getElementById("responseText").innerHTML = jsonObj.errorMsg;
                if (undefinedCheck(jsonObj.webUserId)) {
                    document.getElementById("cspUserError").innerHTML = jsonObj.webUserId;
                }
                if (undefinedCheck(jsonObj.campaign_session_id)) {
                    document.getElementById("cspSessionError").innerHTML = jsonObj.campaign_session_id;
                }
                if (undefinedCheck(jsonObj.campaign_session_posting_name)) {
                    document.getElementById("cspNameError").innerHTML = jsonObj.campaign_session_posting_name;
                }
                if (undefinedCheck(jsonObj.Notes)) {
                    document.getElementById("cspDescError").innerHTML = jsonObj.Notes;
                }
            } else
            {
                document.getElementById("cspUserError").innerHTML = "";
                document.getElementById("cspSessionError").innerHTML = "";
                document.getElementById("cspNameError").innerHTML = "";
                document.getElementById("cspDescError").innerHTML = "";
                document.getElementById("responseText").innerHTML = jsonObj.successMsg;
                campaignSessionPostingCRUD.list();
                
            }
        }
        
        function undefinedCheck(param){ 
            if(typeof param === typeof Undefined){
                console.log("runs");
                return false;
            }
            return true;
        }
    };


    campaignSessionPostingCRUD.list = function () {

        document.getElementById("content").innerHTML = "";
        var dataList = document.createElement("div");
        dataList.id = "dataList"; // set the id so it matches CSS styling rule.
        dataList.innerHTML = "<h2>Attendance <img class='iconBtn' src='icons/insert.png' onclick='campaignSessionPostingCRUD.startInsert();'/></h2>";
        document.getElementById("content").appendChild(dataList);

        ajax('webAPIs/listAssocAPI.jsp', setListUI, 'dataList');

        function setListUI(httpRequest) {

            console.log("starting campaignSessionPostingCRUD.list (setListUI) with this httpRequest object (next line)");
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