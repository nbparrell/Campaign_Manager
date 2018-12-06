package model.sessionPosting;

import model.webUser.*;
import java.sql.SQLException;
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
public class StringData {

    //Indentifying information
    public String campaign_session_posting_name;
    public String Name;
    public String Username;
    public String firstName;
    public String lastName;
    public String userEmail;
    public String Character_Name;

    //Session/Campaign Information
    public String Signed_Up;
    public String Session_Date;
    public String Notes;
    public String Description;
    public String Session_Location;
    public String Campaign;

    //User Information
    public String userPassword;
    public String userPassword2;
    public String birthday;
    public String membershipFee;
    public String userRoleType; // getting it from joined user_role table.

    //Stat Block for Character Screen
    public String Strength;
    public String Dexterity;
    public String Constitution;
    public String Intelligence;
    public String Wisdom;
    public String Charisma;

    //Character Details
    public String Age;
    public String Alignment;
    public String character_description;
    public String character_equipment;
    public String character_prof;
    public String character_level;
    public String race_type;
    public String class_name;
    public String starting_equipment;

    //Character Creation information
    public String strBonus;
    public String dexBonus;
    public String conBonus;
    public String intBonus;
    public String wisBonus;
    public String chrBonus;
    public String race_prof;
    public String class_prof;

    //Primary/Foriegn Keys
    public String campaign_session_posting_id;
    public String race_id;
    public String class_id;
    public String userRoleId;
    public String campaign_session_id;
    public String webUserId;
    public String Character_id;

    //Message Passing Objects
    public String successMsg;
    public String errorMsg;

    // default constructor leaves all data members with empty string (Nothing null).
    public StringData() {
    }
    
    public void setCampaignSignUp(ResultSet results) {        //this.errorMsg = "setCampaignSignUp was called";
        try {
            this.campaign_session_posting_id = FormatUtils.formatInteger(results.getObject("campaign_session_posting_id"));
            this.Username = FormatUtils.formatString(results.getObject("user_username"));
            this.userEmail = FormatUtils.formatString(results.getObject("user_email"));
            this.campaign_session_posting_name = FormatUtils.formatString(results.getObject("campaign_session_posting_name"));
            this.Signed_Up = FormatUtils.formatDate(results.getObject("campaign_session_posting_created_date"));
            this.Notes = FormatUtils.formatString(results.getObject("campaign_session_posting_desc"));
            this.Session_Location = FormatUtils.formatString(results.getObject("campaign_session_location"));
            this.Session_Date = FormatUtils.formatDate(results.getObject("campaign_session_date"));
            this.Campaign = FormatUtils.formatString(results.getObject("campaign_session_campaign"));
            this.Description = FormatUtils.formatString(results.getObject("campaign_session_desc"));
            this.Name = FormatUtils.formatString(results.getObject("campaign_session_name"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setCampaignSignUp (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
}
