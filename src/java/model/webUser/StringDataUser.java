package model.webUser;

import dbUtils.FormatUtils;
import java.sql.ResultSet;


/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringDataUser {

    public String Username;
    public String campaign_session_posting_name;
    public String Signed_Up;
    public String Notes;
    public String campaign_session_posting_id;
    public String Name;
    public String Session_Location;
    public String Campaign;
    public String Session_Date;
    public String campaign_session_id;
    public String Description;
    public String webUserId;
    public String userEmail;
    public String userPassword;
    public String userPassword2;
    public String birthday;
    public String membershipFee;
    public String userRoleId;   // Foreign Key
    public String userRoleType; // getting it from joined user_role table.

    public String successMsg;
    public String errorMsg;

    // default constructor leaves all data members with empty string (Nothing null).
    public StringDataUser() {
    }

    /*
    DEPRICATED
    // overloaded constructor sets all data members by extracting from resultSet.
    public StringDataUser(ResultSet results, int type) {
        if (type == 0) {
            try {
                this.userEmail = FormatUtils.formatString(results.getObject("user_email"));
                this.userPassword = FormatUtils.formatString(results.getObject("user_password"));
                this.birthday = FormatUtils.formatDate(results.getObject("birthday"));
                this.membershipFee = FormatUtils.formatDollar(results.getObject("membership_fee"));
                this.userRoleType = FormatUtils.formatString(results.getObject("user_role_type"));
            } catch (Exception e) {
                this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
            }
        } else if (type == 1) {
            try {
                this.userEmail = FormatUtils.formatString(results.getObject("user_email"));
                this.userPassword = FormatUtils.formatString(results.getObject("user_password"));
                this.birthday = FormatUtils.formatDate(results.getObject("birthday"));
                this.membershipFee = FormatUtils.formatDollar(results.getObject("membership_fee"));
                this.campaign_session_posting_name = FormatUtils.formatString(results.getObject("campaign_session_posting_name"));
                this.Signed_Up = FormatUtils.formatDate(results.getObject("campaign_session_posting_created_date"));
                this.Notes = FormatUtils.formatString(results.getObject("campaign_session_posting_desc"));
                this.Session_Location = FormatUtils.formatString(results.getObject("campaign_session_location"));
                this.Session_Date = FormatUtils.formatDate(results.getObject("campaign_session_date"));
                this.Campaign = FormatUtils.formatString(results.getObject("campaign_session_campaign"));
                this.Description = FormatUtils.formatString(results.getObject("campaign_session_desc"));
                this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
            } catch (Exception e) {
                this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
            }
        } else if (type == 2) {
            try {

                this.Session_Location = FormatUtils.formatString(results.getObject("campaign_session_location"));
                this.Session_Date = FormatUtils.formatDate(results.getObject("campaign_session_date"));
                this.Campaign = FormatUtils.formatString(results.getObject("campaign_session_campaign"));
                this.Description = FormatUtils.formatString(results.getObject("campaign_session_desc"));
                this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
                //this.campaign_session_posting_name = FormatUtils.formatString(results.getObject("campaign_session_posting_name"));
                //this.campaign_session_posting_created_date = FormatUtils.formatDate(results.getObject("campaign_session_posting_created_date"));
                //this.campaign_session_posting_desc = FormatUtils.formatString(results.getObject("campaign_session_posting_desc"));
                //this.campaign_session_posting_id = FormatUtils.formatInteger(results.getObject("campaign_session_posting_id"));
            } catch (Exception e) {
                this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
            }
        }
    }
     */
    public int getCharacterCount() {
        String s = this.webUserId + this.userEmail + this.userPassword + this.birthday
                + this.membershipFee + this.userRoleId + this.userRoleType;
        return s.length();
    }

    public String toString() {
        return "Web User Id:" + this.webUserId
                + ", User Email: " + this.userEmail
                + ", User Password: " + this.userPassword
                + ", Birthday: " + this.birthday
                + ", Membership Fee: " + this.membershipFee
                + ", User Role Id: " + this.userRoleId
                + ", User Role Type: " + this.userRoleType;
    }

    public void setUserInfo(ResultSet results) {        //this.errorMsg = "setUserInfo was called";
        try {
            this.Username = FormatUtils.formatString(results.getObject("user_username"));
            this.userEmail = FormatUtils.formatString(results.getObject("user_email"));
            this.userPassword = FormatUtils.formatString(results.getObject("user_password"));
            this.birthday = FormatUtils.formatDate(results.getObject("birthday"));
            this.membershipFee = FormatUtils.formatDollar(results.getObject("membership_fee"));
            this.userRoleType = FormatUtils.formatString(results.getObject("user_role_type"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public void setCampaignSignUp(ResultSet results) {        //this.errorMsg = "setCampaignSignUp was called";
        try {
            this.userEmail = FormatUtils.formatString(results.getObject("user_email"));
            //this.userPassword = FormatUtils.formatString(results.getObject("user_password"));
            //this.birthday = FormatUtils.formatDate(results.getObject("birthday"));
            //this.membershipFee = FormatUtils.formatDollar(results.getObject("membership_fee"));
            this.campaign_session_posting_name = FormatUtils.formatString(results.getObject("campaign_session_posting_name"));
            this.Signed_Up = FormatUtils.formatDate(results.getObject("campaign_session_posting_created_date"));
            this.Notes = FormatUtils.formatString(results.getObject("campaign_session_posting_desc"));
            this.Session_Location = FormatUtils.formatString(results.getObject("campaign_session_location"));
            this.Session_Date = FormatUtils.formatDate(results.getObject("campaign_session_date"));
            this.Campaign = FormatUtils.formatString(results.getObject("campaign_session_campaign"));
            this.Description = FormatUtils.formatString(results.getObject("campaign_session_desc"));
            this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public void setCampaignListing(ResultSet results) {
        try {
            this.Session_Location = FormatUtils.formatString(results.getObject("campaign_session_location"));
            this.Session_Date = FormatUtils.formatDate(results.getObject("campaign_session_date"));
            this.Campaign = FormatUtils.formatString(results.getObject("campaign_session_campaign"));
            this.Description = FormatUtils.formatString(results.getObject("campaign_session_desc"));
            this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setUserListInfo(ResultSet results) {        //this.errorMsg = "setUserInfo was called";
        try {
            this.webUserId = FormatUtils.formatInteger(results.getObject("web_user_id"));
            System.out.print(this.webUserId);
            this.Username = FormatUtils.formatString(results.getObject("user_username"));
            System.out.print(this.Username);
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataList (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setCampaignSessionList(ResultSet results) {
        try {
            this.campaign_session_id = FormatUtils.formatInteger(results.getObject("campaign_session_id"));
            this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
}
