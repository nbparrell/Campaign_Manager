package model.webUser;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    private static StringDataUser validate(StringDataUser inputData) {

        StringDataUser errorMsgs = new StringDataUser();

        /* Useful to copy field names from StringData as a reference
    public String webUserId = "";
    public String userEmail = "";
    public String userPassword = "";
    public String userPassword2 = "";
    public String birthday = "";
    public String membershipFee = "";
    public String userRoleId = "";   // Foreign Key
    public String userRoleType = ""; // getting it from joined user_role table.
         */
        // Validation
        String result;
        result = ValidationUtils.stringValidationMsg(inputData.userEmail, 45, true);
        System.out.print(result.length());
        if(result.length() != 0){
            errorMsgs.userEmail = result;
        }
        result = ValidationUtils.stringValidationMsg(inputData.userPassword, 45, true);
        System.out.print(result.length());
        if(result.length() != 0){
            errorMsgs.userPassword = result;
        }

        if (inputData.userPassword.compareTo(inputData.userPassword2) != 0) { // case sensative comparison
            errorMsgs.userPassword2 = "Both passwords must match";
        }
        
        result = ValidationUtils.dateValidationMsg(inputData.birthday, false);
        System.out.print(result.length());
        if(result.length() != 0){
            errorMsgs.birthday = result;
        }
        result = ValidationUtils.decimalValidationMsg(inputData.membershipFee, false);
        System.out.print(result.length());
        if(result.length() != 0){
            errorMsgs.membershipFee = result;
        }
        result = ValidationUtils.integerValidationMsg(inputData.userRoleId, true);
        System.out.print(result.length());
        if(result.length() != 0){
            errorMsgs.userRoleId = result;
        }

        return errorMsgs;
    } // validate 

    public static StringDataUser insertUser(StringDataUser inputData, DbConn dbc) {

        StringDataUser errorMsgs = new StringDataUser();
        errorMsgs = validate(inputData);
        System.out.print(errorMsgs.getCharacterCount());
        if (errorMsgs.getCharacterCount() > 28) {  // at least one field has an error, don't go any further.
            System.out.print("userEmail message = " + errorMsgs.userEmail);
            System.out.print("userPassword message = " + errorMsgs.userPassword);
            System.out.print("userPassword2 message = " + errorMsgs.userPassword2);
            System.out.print("birthday message = " + errorMsgs.birthday);
            System.out.print("membershipFee message = " + errorMsgs.membershipFee);
            System.out.print("userRoleId message = " + errorMsgs.userRoleId);
            System.out.print("Campaign message = " + errorMsgs.Campaign);
            System.out.print("Description message = " + errorMsgs.Description);
            System.out.print("Name message = " + errorMsgs.Name);
            System.out.print("Notes message = " + errorMsgs.Notes);
            System.out.print("Session_Date message = " + errorMsgs.Session_Date);
            System.out.print("Session_Location message = " + errorMsgs.Session_Location);
            System.out.print("Signed_Up message = " + errorMsgs.Signed_Up);
            System.out.print("campaign_session_id message = " + errorMsgs.campaign_session_id);
            System.out.print("campaign_session_posting_id message = " + errorMsgs.campaign_session_posting_id);
            System.out.print("campaign_session_postin_name message = " + errorMsgs.campaign_session_posting_name);
            System.out.print("successMsg message = " + errorMsgs.successMsg);
            System.out.print("userRoleType message = " + errorMsgs.userRoleType);
            System.out.print("webUserId message = " + errorMsgs.webUserId);
            System.out.print("error message = " + errorMsgs.errorMsg);
            errorMsgs.errorMsg = ("Please try again: " + errorMsgs.userRoleId + errorMsgs.membershipFee + errorMsgs.birthday + errorMsgs.userPassword + errorMsgs.userEmail);
            
            return errorMsgs;

        } else { // all fields passed validation

            /*
                       String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, "+
                    "web_user.user_role_id, user_role_type "+
                    "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id " + 
                    "ORDER BY web_user_id ";
             */
            // Start preparing SQL statement
            String sql = "INSERT INTO web_user (user_username, user_email, user_password, membership_fee, birthday, user_role_id) "
                    + "values (?,?,?,?,?,?)";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.Username);
            pStatement.setString(2, inputData.userEmail); // string type is simple
            pStatement.setString(3, inputData.userPassword);
            pStatement.setBigDecimal(4, ValidationUtils.decimalConversion(inputData.membershipFee));
            pStatement.setDate(5, ValidationUtils.dateConversion(inputData.birthday));
            pStatement.setInt(6, ValidationUtils.integerConversion(inputData.userRoleId));

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            System.out.println(errorMsgs.errorMsg);
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
                }
            } else if (errorMsgs.errorMsg.contains("foreign key")) {
                errorMsgs.errorMsg = "Invalid User Role Id";
            } else if (errorMsgs.errorMsg.contains("Duplicate entry")) {
                errorMsgs.errorMsg = "That email address is already taken";
            }

        } // customerId is not null and not empty string.
        return errorMsgs;
    } // insert
    
    public static StringDataUser insertCampaignSession(StringDataUser inputData, DbConn dbc) {

        StringDataUser errorMsgs = new StringDataUser();
        errorMsgs = validate(inputData);
        System.out.print(errorMsgs.getCharacterCount());
        if (errorMsgs.getCharacterCount() > 28) {  // at least one field has an error, don't go any further.
            System.out.print("userEmail message = " + errorMsgs.userEmail);
            System.out.print("userPassword message = " + errorMsgs.userPassword);
            System.out.print("userPassword2 message = " + errorMsgs.userPassword2);
            System.out.print("birthday message = " + errorMsgs.birthday);
            System.out.print("membershipFee message = " + errorMsgs.membershipFee);
            System.out.print("userRoleId message = " + errorMsgs.userRoleId);
            System.out.print("Campaign message = " + errorMsgs.Campaign);
            System.out.print("Description message = " + errorMsgs.Description);
            System.out.print("Name message = " + errorMsgs.Name);
            System.out.print("Notes message = " + errorMsgs.Notes);
            System.out.print("Session_Date message = " + errorMsgs.Session_Date);
            System.out.print("Session_Location message = " + errorMsgs.Session_Location);
            System.out.print("Signed_Up message = " + errorMsgs.Signed_Up);
            System.out.print("campaign_session_id message = " + errorMsgs.campaign_session_id);
            System.out.print("campaign_session_posting_id message = " + errorMsgs.campaign_session_posting_id);
            System.out.print("campaign_session_postin_name message = " + errorMsgs.campaign_session_posting_name);
            System.out.print("successMsg message = " + errorMsgs.successMsg);
            System.out.print("userRoleType message = " + errorMsgs.userRoleType);
            System.out.print("webUserId message = " + errorMsgs.webUserId);
            System.out.print("error message = " + errorMsgs.errorMsg);
            errorMsgs.errorMsg = ("Please try again: " + errorMsgs.userRoleId + errorMsgs.membershipFee + errorMsgs.birthday + errorMsgs.userPassword + errorMsgs.userEmail);
            
            return errorMsgs;

        } else { // all fields passed validation

            /*
                       String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, "+
                    "web_user.user_role_id, user_role_type "+
                    "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id " + 
                    "ORDER BY web_user_id ";
             */
            // Start preparing SQL statement
            String sql = "INSERT INTO web_user (campaign_session_name, campaign_session_location, campaign_session_date, campaign_session_campaign, campaign_session_desc) "
                    + "values (?,?,?,?,?)";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.userEmail); // string type is simple
            pStatement.setString(2, inputData.userPassword);
            pStatement.setBigDecimal(3, ValidationUtils.decimalConversion(inputData.membershipFee));
            pStatement.setDate(4, ValidationUtils.dateConversion(inputData.birthday));
            pStatement.setInt(5, ValidationUtils.integerConversion(inputData.userRoleId));

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            System.out.println(errorMsgs.errorMsg);
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
                }
            } else if (errorMsgs.errorMsg.contains("foreign key")) {
                errorMsgs.errorMsg = "Invalid User Role Id";
            } else if (errorMsgs.errorMsg.contains("Duplicate entry")) {
                errorMsgs.errorMsg = "That email address is already taken";
            }

        } // customerId is not null and not empty string.
        return errorMsgs;
    } // insert
    
} // class