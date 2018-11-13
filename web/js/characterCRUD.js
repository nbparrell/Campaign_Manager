
var characterCRUD = {}; // globally available object


(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    characterCRUD.startInsert = function () {

        ajax('htmlPartial/insertCreateCharacter.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajax("webAPIs/getRacesAPI.jsp", setRacePickList, "ucRaceError");
            ajax("webAPIs/getClassesAPI.jsp", setClassPickList, "ucClassError");

            function setRacePickList(httpRequest) {

                console.log("setPickList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                for (var i = 0; i < jsonObj.webUserList.length; i++) {
                    console.log(jsonObj.webUserList[i].errorMsg);
                    if (undefinedCheck(jsonObj.webUserList[i].errorMsg)) {
                        console.log("Checked for errorMsg");
                        document.getElementById("ucRaceError").innerHTML = jsonObj.webUserList[i].errorMsg + " " + i;
                        return;
                    }
                }

                makePickList(jsonObj.webUserList, "race_id", "race_type", "ucRacePickList");
            }

            function setClassPickList(httpRequest) {

                console.log("setPickList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);
                for (var i = 0; i < jsonObj.webUserList.length; i++) {
                    console.log(jsonObj.webUserList[i].errorMsg);
                    if (undefinedCheck(jsonObj.webUserList[i].errorMsg)) {
                        console.log("Checked for errorMsg");
                        document.getElementById("ucClassError").innerHTML = jsonObj.webUserList[i].errorMsg + " " + i;
                        return;
                    }
                }

                makePickList(jsonObj.webUserList, "class_id", "class_name", "ucClassPickList");
            }

            function undefinedCheck(param) {
                if (typeof param === typeof Undefined) {
                    console.log("runs");
                    return false;
                }
                return true;
            }
        }

    };


    characterCRUD.insertSave = function () {
        ajaxCall("webAPIs/getProfileAPI.jsp", getInsertInfo, displaygetInsertInfoError);
        function getInsertInfo(httpRequest) {
            var obj = JSON.parse(httpRequest.responseText);
            console.log("characterCRUD.insertSave was called");
            console.log(obj);

            var ddClassesList = document.getElementById("ucClassPickList");
            var ddRacesList = document.getElementById("ucRacePickList");
            var ddAlignmentList = document.getElementById("ucAlignmentPickList");

            // create a user object from the values that the user has typed into the page.
            var userInputObj = {
                "Character_Name": document.getElementById("ucName").value,
                "Age": document.getElementById("ucAge").value,
                "character_description": document.getElementById("ucDesc").value,
                "Strength": document.getElementById("ucStrength").value,
                "Dexterity": document.getElementById("ucDexterity").value,
                "Constitution": document.getElementById("ucConstitution").value,
                "Intelligence": document.getElementById("ucWisdom").value,
                "Wisdom": document.getElementById("ucIntelligence").value,
                "Charisma": document.getElementById("ucCharisma").value,
                "Alignment": ddAlignmentList.options[ddAlignmentList.selectedIndex].value,

                // Modification here for role pick list
                "race_id": ddRacesList.options[ddRacesList.selectedIndex].value,
                "class_id": ddClassesList.options[ddClassesList.selectedIndex].value,
                "webUserId": obj.webUserList[0].webUserId,
                "errorMsg": ""
            };
            console.log(userInputObj);

            // build the url for the ajax call. Remember to escape the user input object or else 
            // you'll get a security error from the server. JSON.stringify converts the javaScript
            // object into JSON format (the reverse operation of what gson does on the server side).
            var myData = escape(JSON.stringify(userInputObj));
            var url = "webAPIs/insertCharacterAPI.jsp?jsonData=" + myData;
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
                    if (undefinedCheck(jsonObj.Character_Name)) {
                        document.getElementById("ucNameError").innerHTML = jsonObj.Character_Name;
                    }
                    if (undefinedCheck(jsonObj.Age)) {
                        document.getElementById("ucAgeError").innerHTML = jsonObj.Age;
                    }
                    if (undefinedCheck(jsonObj.race_id)) {
                        document.getElementById("ucRaceError").innerHTML = jsonObj.race_id;
                    }
                    if (undefinedCheck(jsonObj.class_id)) {
                        document.getElementById("ucClassError").innerHTML = jsonObj.class_id;
                    }
                    if (undefinedCheck(jsonObj.Strength)) {
                        document.getElementById("ucStrengthError").innerHTML = jsonObj.Strength;
                    }
                    if (undefinedCheck(jsonObj.Dexterity)) {
                        document.getElementById("ucDexterityError").innerHTML = jsonObj.Dexterity;
                    }
                    if (undefinedCheck(jsonObj.Constitution)) {
                        document.getElementById("ucConstitutionError").innerHTML = jsonObj.Constitution;
                    }
                    if (undefinedCheck(jsonObj.Intelligence)) {
                        document.getElementById("ucWisdomError").innerHTML = jsonObj.Intelligence;
                    }
                    if (undefinedCheck(jsonObj.Wisdom)) {
                        document.getElementById("ucIntelligenceError").innerHTML = jsonObj.Wisdom;
                    }
                    if (undefinedCheck(jsonObj.Charisma)) {
                        document.getElementById("ucCharismaError").innerHTML = jsonObj.Charisma;
                    }
                    if (undefinedCheck(jsonObj.Alignment)) {
                        document.getElementById("ucAlignmentError").innerHTML = jsonObj.Alignment;
                    }
                } else
                {
                    characterCRUD.list();
                }

//            if (jsonObj.errorMsg.length === 0) { // success
//                jsonObj.errorMsg = "Record successfully inserted !!!";
//            }
//            document.getElementById("recordError").innerHTML = jsonObj.errorMsg;
            }

            function undefinedCheck(param) {
                if (typeof param === typeof Undefined) {
                    console.log("runs");
                    return false;
                }
                return true;
            }
        }

        function displaygetInsertInfoError(httpRequest) {

        }
    };


    characterCRUD.list = function () {
        ajaxCall("webAPIs/getProfileAPI.jsp", checkLoginStatus, displayCheckLoginStatusError);
        var target = document.getElementById("content");
        function checkLoginStatus(httpRequest) {
            try {
                var obj = JSON.parse(httpRequest.responseText);
                if (obj.webUserList.length === 0) {
                    target.innerHTML = "There is no user with id '" + id + "' in the database";
                } else if (!obj.webUserList[0].errorMsg === null) {
                    target.innerHTML = "Error extracting the Web User from the database: " + obj.webUserList[0].errorMsg;
                }
                document.getElementById("content").innerHTML = "";
                var navibar = document.createElement("div");
                var dataList = document.createElement("div");
                navibar.id = "navibar";
                dataList.id = "dataList"; // set the id so it matches CSS styling rule.
                navibar.innerHTML = "<h2>Characters <img class='iconBtn' src='icons/insert.png' onclick='characterCRUD.startInsert();'/></h2>";
                document.getElementById("content").appendChild(navibar)
                document.getElementById("navibar").appendChild(dataList);
                ajax('webAPIs/listCharactersAPI.jsp', setListUI, 'dataList');
            } catch (err) {
                target.innerHTML = "<h4>You are not logged in</h4>";
                console.log(httpRequest);
            }


            function setListUI(httpRequest) {

                console.log("starting characterCRUD.list (setListUI) with this httpRequest object (next line)");
                console.log(httpRequest);

                var obj = JSON.parse(httpRequest.responseText);

                if (obj === null) {
                    dataList.innerHTML = "listUsersResponse Error: JSON string evaluated to null.";
                    return;
                }
                var editButton = "<img class='iconBtn' src='icons/view.png' onclick='characterCRUD.view();'/>";

                for (var i = 0; i < obj.webUserList.length; i++) {

                    // add a property to each object in webUserList - a span tag that when clicked 
                    // invokes a JS function call that passes in the web user id that should be deleted
                    // from the database and a reference to itself (the span tag that was clicked)
                    var id = obj.webUserList[i].Character_id;
                    obj.webUserList[i].View = "<img class='iconBtn' src='icons/view.png' onclick='characterCRUD.view(" + id + ",this)';/>";

                    // remove a property from each object in webUserList 
                    delete obj.webUserList[i].userPassword2;
                }
                // buildTable Parameters: 
                // First:  array of objects that are to be built into an HTML table.
                // Second: string that is database error (if any) or empty string if all OK.
                // Third:  reference to DOM object where built table is to be stored. 
                buildTable(obj.webUserList, obj.dbError, dataList);
            }
        }

        function displayCheckLoginStatusError(httpRequest) {
            console.log(httpRequest);
        }
    };

    characterCRUD.view = function (characterID, icon) {

        ajax('htmlPartial/viewCharacter.html', setViewUI, 'content');
        function setViewUI(httpRequest) {
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;
            ajax('webAPIs/getCharacterAPI.jsp?characterID=' + characterID, populateCharacterSheet, 'content');

            function populateCharacterSheet(httpRequest) {
                var obj = JSON.parse(httpRequest.responseText);
                console.log(obj);
                if (obj === null) {
                    dataList.innerHTML = "listUsersResponse Error: JSON string evaluated to null.";
                    return;
                }
                var cName = document.getElementById("Character_Name");
                var cAge = document.getElementById("Character_Age");
                var cRace = document.getElementById("Character_Race");
                var cClass = document.getElementById("Character_Class");
                var cLevel = document.getElementById("Character_Level");
                var cAlign = document.getElementById("Character_Alignment");
                var cPlayer = document.getElementById("Character_Player");
                var cStr = document.getElementById("Character_Strength");
                var cDex = document.getElementById("Character_Dexterity");
                var cCon = document.getElementById("Character_Constitution");
                var cInt = document.getElementById("Character_Intelligence");
                var cWis = document.getElementById("Character_Wisdom");
                var cChr = document.getElementById("Character_Charisma");
                var cDesc = document.getElementById("Character_Desc");
                var cEquip = document.getElementById("Character_Equip");
                var cProf = document.getElementById("Character_Proficiency");
                if (!undefinedCheck(obj.errorMsg)) {
                    console.log("Attempting to populate Character Sheet");
     
                    document.getElementById("Character_Name").value = obj.Character_Name;
                     
                    document.getElementById("Character_Age").value = obj.Age;
                    
                    document.getElementById("Character_Race").value = obj.race_type;
                  
                    document.getElementById("Character_Class").value = obj.class_name;
                 
                    document.getElementById("Character_Level").value = obj.character_level;
                    
                    document.getElementById("Character_Alignment").value = obj.Alignment;
                    
                    document.getElementById("Character_Player").value = obj.Username;
                    
                    document.getElementById("Character_Strength").value = obj.Strength;
                    
                    document.getElementById("Character_Dexterity").value = obj.Dexterity;
                    
                    document.getElementById("Character_Constitution").value = obj.Constitution;
                    
                    document.getElementById("Character_Intelligence").value = obj.Intelligence;
                   
                    document.getElementById("Character_Wisdom").value = obj.Wisdom;
                    
                    document.getElementById("Character_Charisma").value = obj.Intelligence;
                    
                    document.getElementById("Character_Desc").value = obj.character_description;
                    
                    document.getElementById("Character_Equip").value = obj.character_equipment;
                    
                    document.getElementById("Character_Proficiency").value = obj.character_prof;
                } else {
                    console.log("Failed to populate due to error: " + obj.errorMsg);
                    document.getElementById("errorMsg").innerHTML = obj.errorMsg;
                }
            }

            function undefinedCheck(param) {
                console.log("Checking to see if it is undefined.");
                if (typeof param === typeof Undefined) {
                    console.log("runs");
                    return false;
                }
                return true;
            }
        }
    };


}());  // the end of the IIFE