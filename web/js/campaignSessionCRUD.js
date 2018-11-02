
var campaignSessionCRUD = {}; // globally available object


(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    campaignSessionCRUD.startInsert = function () {

        ajax('htmlPartial/insertCampaignSession.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;
        }
    };


    campaignSessionCRUD.insertSave = function () {

        console.log("campaignSessionCRUD.insertSave was called");


        // create a session object from the values that the user has typed into the page.
        var sessionInputObj = {
            "campaign_session_id": "",
            "Name": document.getElementById("sessionName").value,
            "Session_Location": document.getElementById("sessionLocation").value,
            "Campaign": document.getElementById("sessionCampaign").value,
            "Session_Date": document.getElementById("sessionDate").value,
            "Description": document.getElementById("sessionDesc").value,
            "sucessMsg": "",
            "errorMsg": ""
        };
        console.log(sessionInputObj);

        // build the url for the ajax call. Remember to escape the user input object or else 
        // you'll get a security error from the server. JSON.stringify converts the javaScript
        // object into JSON format (the reverse operation of what gson does on the server side).
        var myData = escape(JSON.stringify(sessionInputObj));
        var url = "webAPIs/insertSessionAPI.jsp?jsonData=" + myData;
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
                if (undefinedCheck(jsonObj.userEmail)) {
                    document.getElementById("sessionNameError").innerHTML = jsonObj.sessionName;
                }
                if (undefinedCheck(jsonObj.userPassword)) {
                    document.getElementById("sessionLocationError").innerHTML = jsonObj.sessionLocation;
                }
                if (undefinedCheck(jsonObj.userPassword2)) {
                    document.getElementById("sessionCampaignError").innerHTML = jsonObj.sessionCampaign;
                }
                if (undefinedCheck(jsonObj.birthday)) {
                    document.getElementById("sessionDateError").innerHTML = jsonObj.sessionDate;
                }
                if (undefinedCheck(jsonObj.membershipFee)) {
                    document.getElementById("sessionDescError").innerHTML = jsonObj.sessionDesc;
                }
                document.getElementById("responseText").innerHTML = jsonObj.errorMsg;
            } else
            {
                document.getElementById("sessionNameError").innerHTML = "";
                document.getElementById("sessionLocationError").innerHTML = "";
                document.getElementById("sessionCampaignError").innerHTML = "";
                document.getElementById("sessionDateError").innerHTML = "";
                document.getElementById("sessionDescError").innerHTML = "";
                document.getElementById("responseText").innerHTML = jsonObj.successMsg;
                document.getElementById("sessionName").value = "";
                document.getElementById("sessionLocation").value = "";
                document.getElementById("sessionCampaign").value = "";
                document.getElementById("sessionDate").value = "";
                document.getElementById("sessionDesc").value = "";
                campaignSessionCRUD.list();
            }

            /*            
                if (jsonObj.errorMsg.length === 0) { // success
                    jsonObj.errorMsg = "Record successfully inserted !!!";
                }
                document.getElementById("responseText").innerHTML = jsonObj.errorMsg;
            */
        }
        
        function undefinedCheck(param){ 
            if(typeof param === typeof Undefined){
                console.log("runs");
                return false;
            }
            return true;
        }
    };


    campaignSessionCRUD.list = function () {

        document.getElementById("content").innerHTML = "";
        var dataList = document.createElement("div");
        dataList.id = "dataList"; // set the id so it matches CSS styling rule.
        dataList.innerHTML = "<h2>Campaign Sessions <img class='iconBtn' src='icons/insert.png' onclick='campaignSessionCRUD.startInsert();'/></h2>";
        document.getElementById("content").appendChild(dataList);

        ajax('webAPIs/listOtherAPI.jsp', setListUI, 'dataList');

        function setListUI(httpRequest) {

            console.log("starting campaignSessionCRUD.list (setListUI) with this httpRequest object (next line)");
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