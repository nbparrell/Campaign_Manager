
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
            } else {
                campaignSessionPostingCRUD.list();

            }
        }
        //This is used to check to see if an object in the JSON exists or not.
        function undefinedCheck(param) {
            if (typeof param === typeof Undefined) {
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
                var id = obj.webUserList[i].campaign_session_posting_id;
                obj.webUserList[i].delete = "<img class='iconBtn' src='icons/delete.png'  onclick='campaignSessionPostingCRUD.Delete(" + id + ",this)'  />";
                obj.webUserList[i].Update = "<img class='iconBtn' src='icons/update.png'  onclick='campaignSessionPostingCRUD.startUpdate(" + id + ",this)'  />";
                delete obj.webUserList[i].campaign_session_posting_id;
            }

            // buildTable Parameters: 
            // First:  array of objects that are to be built into an HTML table.
            // Second: string that is database error (if any) or empty string if all OK.
            // Third:  reference to DOM object where built table is to be stored. 
            buildTable(obj.webUserList, obj.dbError, dataList);
        }
    };

    campaignSessionPostingCRUD.Delete = function (id, icon) {
        if (confirm("Do you really want to delete campaign posting " + id + "? ")) {
            console.log("icon that was passed into JS function is printed on next line");
            console.log(icon);
            // HERE YOU HAVE TO CALL THE DELETE API and the success function should run the 
            // following (delete the row that was clicked from the User Interface).
            alert('webAPIs/deletePostingAPI.jsp?deleteId=' + id);
            ajaxCall('webAPIs/deletePostingAPI.jsp?deleteId=' + id, updateTable, setDeleteError);
            function updateTable(httpRequest) {
                var msg = JSON.parse(httpRequest.responseText);
                console.log(msg.errorMsg)
                if (undefinedCheck(msg.successMsg)) {
                    // icon's parent is cell whose parent is row 
                    var dataRow = icon.parentNode.parentNode;
                    var rowIndex = dataRow.rowIndex - 1; // adjust for oolumn header row?
                    var dataTable = dataRow.parentNode;
                    dataTable.deleteRow(rowIndex);
                    alert(msg.successMsg);
                } else {
                    alert(msg.errorMsg);
                }
            }

            function setDeleteError(httpRequest) {
                var msg = JSON.parse(httpRequest.responseText);
                alert(msg.errorMsg);
            }
            //This is used to check to see if an object in the JSON exists or not.
            function undefinedCheck(param) {
                if (typeof param === typeof Undefined) {
                    console.log("runs");
                    return false;
                }
                return true;
            }
        }
    };

    campaignSessionPostingCRUD.startUpdate = function (id, icon) {

        ajax('htmlPartial/insertCampaignSessionPosting.html', setUpdateUI, 'content');

        function setUpdateUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajax("webAPIs/getUsersAPI.jsp", setUserPickList, "cspUserError");
            ajax("webAPIs/getSessionsAPI.jsp", setSessionPickList, "cspSessionError");

            function setUserPickList(httpRequest) {

                console.log("setUserPickList was called, see next line for object holding list of roles");
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
                var ddUserList = document.getElementById("cspUserPickList");
                var data = icon.parentNode.parentNode;
                var dataRow = data.childNodes;
                for(var i = 0; i<ddUserList.length; i++){
                    if((ddUserList.options[i].innerHTML).includes(dataRow[2].innerHTML)){
                        ddUserList.selectedIndex = i;
                        break;
                    }
                }
            }

            function setSessionPickList(httpRequest) {

                console.log("setSessionPickList was called, see next line for object holding list of roles");
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
                var ddSessionList = document.getElementById("cspSessionPickList");
                var data = icon.parentNode.parentNode;
                var dataRow = data.childNodes;
                for(var i = 0; i<ddSessionList.length; i++){
                    if((ddSessionList.options[i].innerHTML).includes(dataRow[1].innerHTML)){
                        ddSessionList.selectedIndex = i;
                        break;
                    }
                }
            }
            
            var data = icon.parentNode.parentNode;
            var dataRow = data.childNodes;
            document.getElementById("cspName").value = dataRow[0].innerHTML;
            document.getElementById("cspDesc").value = dataRow[6].innerHTML;
            document.getElementById("SaveButton").onclick = function(){campaignSessionPostingCRUD.updateSave(id)};
        }
    };


    campaignSessionPostingCRUD.updateSave = function (id) {

        console.log("campaignSessionPostingCRUD.updateSave was called");

        var ddUserList = document.getElementById("cspUserPickList");
        var ddSessionList = document.getElementById("cspSessionPickList");

        // create a user object from the values that the user has typed into the page.
        var userInputObj = {
            "campaign_session_posting_id": id,
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
        var url = "webAPIs/updatePostingAPI.jsp?jsonData=" + myData;
        ajax(url, processUpdate, "responseText");

        function processUpdate(httpRequest) {
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
            } else {
                campaignSessionPostingCRUD.list();

            }
        }
        //This is used to check to see if an object in the JSON exists or not.
        function undefinedCheck(param) {
            if (typeof param === typeof Undefined) {
                console.log("runs");
                return false;
            }
            return true;
        }
    };

}());  // the end of the IIFE