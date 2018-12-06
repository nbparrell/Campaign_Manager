package dbUtils;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.StringData;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    private static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

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
} // class