package dbUtils;

import model.webUser.*;
import java.util.StringTokenizer;
import java.util.Date;

public class ValidationUtils {

    /* Check string "val" to see if it has a valid java.sql.Date in it.
     * Return "" if the input is OK. Otherwise, return error message. */
    public static String dateValidationMsg(String val, boolean required) {
        // System.out.println("*************trying to convert ["+val+"] to date");

        if (val == null) {
            return "ValidationUtils.dateValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if ((val.length() == 0) && !required) {
            return null;  // Since this field is not required, empty string is valid user entry.
        }
        try {
            java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("MM/dd/yyyy"); //please notice the capital M
            dateformat.setLenient(false);
            java.util.Date myDate = dateformat.parse(val);
            java.sql.Date convertedDate = new java.sql.Date(myDate.getTime()); // // not using (on purpose).
            return null; // means date is good
        } catch (Exception e) {
            return "Please enter a valid date (format: MM/DD/YYYY)";  // can also add (to debug) + e.getMessage();
        }
    } // dateValidationMsg

    /* Convert "val" (String) to java.sql.Date and return the converted date. */
    public static java.sql.Date dateConversion(String val) {

        if ((val == null) || (val.length() == 0)) {
            return null;
        }
        try {
            StringTokenizer tkn = new StringTokenizer(val, "-");
            String year = tkn.nextToken();
            String month = tkn.nextToken();
            String day = tkn.nextToken();
            String date = month + "/" + day + "/" + year;
            java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("MM/dd/yyyy"); //please notice the capital M
            dateformat.setLenient(false);
            Date myDate = dateformat.parse(date);
            System.out.print("Formatted Date: ");
            System.out.print(new java.sql.Date(myDate.getTime()));
            return new java.sql.Date(myDate.getTime());
            //return d.toString(); // debugging...
        } catch (Exception e) {
            System.out.println("ValidationUtils.dateConversion(): cannot convert " + val + " to date.");
            return null;
        }
    } // dateConversion()


    /* Check string "val" to see if it has a valid BigDecimal in it.
     * Return "" if the input is OK. Otherwise, return error message. */
    public static String decimalValidationMsg(String val, boolean required) {

        if (val == null) {
            return "ValidationUtils.decimalValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if ((val.length() == 0) && !required) {
            return "";  // Since this field is not required, empty string is valid user entry.
        }
        try {
            java.math.BigDecimal convertedDecimal = new java.math.BigDecimal(val); // not using (on purpose).
            return "";
        } catch (Exception e) {
            return "Please enter an dollar amount";
        }
    } // decimalValidationMsg()

    /* Convert "val" (String) to java.math.BigDecimal and return the converted BigDecimal. */
    public static java.math.BigDecimal decimalConversion(String val) {

        if ((val == null) || (val.length() == 0)) {
            return null;  // Since this field is not required, empty string is valid user entry.
        }
        try {
            return new java.math.BigDecimal(val);
        } catch (Exception e) {
            System.out.println("ValidationUtils.decimalConversion(): cannot convert " + val + " to java.math.BigDecimal.");
            return null;
        }
    } // decimalValidationMsg()

    /* Check string "val" to see if it has a valid integer in it.
     * Return "" if the input is OK. Otherwise, return error message. */
    public static String integerValidationMsg(String val, boolean required) {
        if (val == null) {
            return "ValidationUtils.integerValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if ((val.length() == 0) && !required) {
            return null;  // Since this field is not required, empty string is a valid user entry.
        }
        try {
            Integer convertedInteger = new Integer(val); // not using (on purpose).
            return null;
        } catch (Exception e) {
            return "Please enter a valid number";
        }
    } // integerValidationMsg()

    /* Convert "val" (String) to Integer and return the converted Integer. */
    public static Integer integerConversion(String val) {

        if ((val == null) || (val.length() == 0)) {
            return null;
        }
        try {
            return new Integer(val);
        } catch (Exception e) {
            System.out.println("ValidationUtils.integerConversion(): cannot convert " + val + " to Integer.");
            return null;
        }
    } // integerConversion()   

    /* Check string "val" to see if it meets the db constraints (e.g., not emtpy string 
     * if it is a required field, not longer than db allows). If OK, return "". 
     * Otherwise, return error message. */
    public static String stringValidationMsg(String val, int maxlen, boolean required) {

        if (val == null) {
            return "ValidationUtils.stringValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if (val.length() == 0) {
            if (required) {
                return "Input is required";
            } else {
                return null; // Empty string OK if fld not req'd.
            }
        }

        if (val.length() > maxlen) {
            return "Please shorten to [" + val.substring(0, maxlen) + "]";
        } else {
            return null; // input is good
        }
    }

    public static String stringEmailValidationMsg(String val, int maxlen, boolean required) {
        String pattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b"; //Regex to confirm that email is in the correct format.
        if (val == null) {
            return "ValidationUtils.stringValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if (val.length() == 0) {
            if (required) {
                return "Input is required";
            }
        }

        if (val.length() > maxlen) {
            return "Please shorten to [" + val.substring(0, maxlen) + "]";
        }

        if (val.matches(pattern)) {
            return null;
        } else {
            return "Please enter the email in the acceptable format of xxx@xxx.xxx";
        }
    }

    public static String stringValidationDateMsg(String val, boolean birthday, boolean meeting, boolean required) {

        String today = java.time.LocalDate.now().toString();
        java.sql.Date sqlToday = dateConversion(today);
        java.sql.Date proposedDate = dateConversion(val);
        if (val == null) {
            return "ValidationUtils.stringValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if (val.length() == 0) {
            if (required) {
                return "Input is required";
            } else {
                return null; // Empty string OK if fld not req'd.
            }
        }

        if (sqlToday.compareTo(proposedDate) < 0 && birthday) {
            return "Enter in a date before " + sqlToday;
        } else if (sqlToday.compareTo(proposedDate) > 0 && meeting) {
            return "Enter a date after " + sqlToday;
        } else {
            return null; // input is good
        }
    }
    
    public static String stringStatValidationMsg(String val, int maxVal, boolean required) {

        if (val == null) {
            return "ValidationUtils.stringValidationMsg(): Programmer error - should not be trying to validate null.";
        }
        if (val.length() == 0) {
            if (required) {
                return "Enter Valid number";
            } else {
                return null; // Empty string OK if fld not req'd.
            }
        }
        String canConvertInt = integerValidationMsg(val, true);
        if(canConvertInt != null){
            return canConvertInt;
        }
        int realVal = integerConversion(val);
        if (realVal > maxVal) {
            return "Value must be less then " + maxVal;
        } else {
            return null; // input is good
        }
    }

    public static boolean passwordMatch(StringDataUser inputData, StringDataUser responseData) {
        if (inputData.userPassword.equals(inputData.userPassword2)) {
            return true;
        } else {
            responseData.userPassword2 = "Passwords need to Match";
            return false;
        }
    }

    public static String membershipFeeCheck(String fee) {
        if (fee.equals("")) {
            fee = "0";
        }
        return fee;
    }

    public static boolean insertUserValidation(StringDataUser inputData, StringDataUser responseData) {
        boolean validationPassed = true;
        responseData.userPassword = stringValidationMsg(inputData.userPassword, 45, true);
        responseData.Username = stringValidationMsg(inputData.Username, 45, true);
        responseData.userEmail = stringEmailValidationMsg(inputData.userEmail, 45, true);
        responseData.firstName = stringValidationMsg(inputData.firstName, 45, true);
        responseData.lastName = stringValidationMsg(inputData.lastName, 45, true);
        responseData.birthday = stringValidationDateMsg(inputData.birthday, true, false, false);
        inputData.membershipFee = membershipFeeCheck(inputData.membershipFee);

        if (responseData.Username != null) {
            validationPassed = false;
        } else if (responseData.userPassword != null) {
            validationPassed = false;
        } else if (responseData.userEmail != null) {
            validationPassed = false;
        } else if (responseData.firstName != null) {
            validationPassed = false;
        } else if (responseData.lastName != null) {
            validationPassed = false;
        } else if (responseData.birthday != null) {
            validationPassed = false;
        }

        if (passwordMatch(inputData, responseData) && validationPassed) {
            return true;
        }
        responseData.errorMsg = "Try again";
        return false;
    }

    public static boolean insertSessionValidation(StringDataUser inputData, StringDataUser responseData) {
        boolean validationPassed = true;
        responseData.Name = stringValidationMsg(inputData.Name, 45, true);
        responseData.Session_Location = stringValidationMsg(inputData.Session_Location, 45, true);
        responseData.Session_Date = stringValidationDateMsg(inputData.Session_Date, false, true, true);
        responseData.Campaign = stringValidationMsg(inputData.Campaign, 45, true);
        responseData.Description = stringValidationMsg(inputData.Description, 967295, true);

        if (responseData.Name != null) {
            validationPassed = false;
        } else if (responseData.Session_Location != null) {
            validationPassed = false;
        } else if (responseData.Session_Date != null) {
            validationPassed = false;
        } else if (responseData.Campaign != null) {
            validationPassed = false;
        } else if (responseData.Description != null) {
            validationPassed = false;
        }

        if (validationPassed) {
            return true;
        }
        responseData.errorMsg = "Try again";
        return false;
    }

    public static boolean insertSessionPostingValidation(StringDataUser inputData, StringDataUser responseData) {
        boolean validationPassed = true;
        responseData.campaign_session_posting_name = stringValidationMsg(inputData.campaign_session_posting_name, 45, true);
        responseData.Notes = stringValidationMsg(inputData.Notes, 967295, true);

        if (responseData.campaign_session_posting_name != null) {
            validationPassed = false;
        } else if (responseData.Notes != null) {
            validationPassed = false;
        }

        if (validationPassed) {
            return true;
        }
        responseData.errorMsg = "Try again";
        return false;
    }

    public static boolean insertCharacterValidation(StringDataUser inputData, StringDataUser responseData) {
        boolean validationPassed = true;
        responseData.Character_Name = stringValidationMsg(inputData.Character_Name, 45, true);
//        responseData.character_description = stringValidationMsg(inputData.character_description, 967295, true); May Implement later
        responseData.Age = stringStatValidationMsg(inputData.Age, 4000, true);
        responseData.Strength = stringStatValidationMsg(inputData.Strength, 18, true);
        responseData.Dexterity = stringStatValidationMsg(inputData.Dexterity, 18, true);
        responseData.Constitution = stringStatValidationMsg(inputData.Constitution, 18, true);
        responseData.Intelligence = stringStatValidationMsg(inputData.Intelligence, 18, true);
        responseData.Wisdom = stringStatValidationMsg(inputData.Wisdom, 18, true);
        responseData.Charisma = stringStatValidationMsg(inputData.Charisma, 18, true);

        if (responseData.Character_Name != null) {
            validationPassed = false;
        } else if (responseData.Age != null) {
            validationPassed = false;
        } else if (responseData.Strength != null) {
            validationPassed = false;
        } else if (responseData.Dexterity != null) {
            validationPassed = false;
        } else if (responseData.Constitution != null) {
            validationPassed = false;
        } else if (responseData.Intelligence != null) {
            validationPassed = false;
        } else if (responseData.Wisdom != null) {
            validationPassed = false;
        } else if (responseData.Charisma != null) {
            validationPassed = false;
        }

        if (validationPassed) {
            return true;
        }
        responseData.errorMsg = "Try again";
        return false;
    }
}
