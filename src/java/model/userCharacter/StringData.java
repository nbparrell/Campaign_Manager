package model.userCharacter;


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
       
    public void setCharacterListInfo(ResultSet results) {        //this.errorMsg = "setUserInfo was called";
        try {
            this.Character_id = FormatUtils.formatInteger(results.getObject("user_characters_id"));
            this.Username = FormatUtils.formatString(results.getObject("user_username"));
            this.Character_Name = FormatUtils.formatString(results.getObject("user_characters_name"));
            this.character_level = FormatUtils.formatInteger(results.getObject("user_characters_lvl"));
            this.class_name = FormatUtils.formatString(results.getObject("character_classes_name"));
            this.race_type = FormatUtils.formatString(results.getObject("character_races_type"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setUserInfo (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setRaceClassInfo(ResultSet results) {
        try {
            System.out.print(results.getMetaData());
            System.out.print(results.first());
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        System.out.print("setRaceClassInfo runs");
        try {
            this.race_type = (FormatUtils.formatString(results.getObject("character_races_type")));
            this.strBonus = FormatUtils.formatInteger(results.getObject("character_races_str_bonus"));
            this.dexBonus = FormatUtils.formatInteger(results.getObject("character_races_dex_bonus"));
            this.conBonus = FormatUtils.formatInteger(results.getObject("character_races_con_bonus"));
            this.intBonus = FormatUtils.formatInteger(results.getObject("character_races_int_bonus"));
            this.wisBonus = FormatUtils.formatInteger(results.getObject("character_races_wis_bonus"));
            this.chrBonus = FormatUtils.formatInteger(results.getObject("character_races_chr_bonus"));
            this.character_prof = FormatUtils.formatString(results.getObject("character_races_prof"));
            this.class_name = FormatUtils.formatString(results.getObject("character_classes_name"));
            this.character_prof = this.character_prof + " " + FormatUtils.formatString(results.getObject("character_classes_prof"));
            this.character_equipment = FormatUtils.formatString(results.getObject("character_classes_starting_equipment"));
        } catch (SQLException e) {
            System.out.print("Doesn't assign information to correct locations: " + e.getMessage());
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setRaceClassInfo (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setRacesList(ResultSet results) {
        try {
            this.race_id = FormatUtils.formatInteger(results.getObject("character_races_id"));
            this.race_type = FormatUtils.formatString(results.getObject("character_races_type"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setRacesList (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setClassesList(ResultSet results) {
        try {
            this.class_id = FormatUtils.formatInteger(results.getObject("character_classes_id"));
            this.class_name = FormatUtils.formatString(results.getObject("character_classes_name"));
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setClassesList (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
    
    public void setCharacterInfo(ResultSet results) {        //this.errorMsg = "setUserInfo was called";
        try {
            this.Character_Name = FormatUtils.formatString(results.getObject("user_characters_name"));
            this.race_type = FormatUtils.formatString(results.getObject("character_races_type"));
            this.class_name = FormatUtils.formatString(results.getObject("character_classes_name"));
            this.character_level = FormatUtils.formatInteger(results.getObject("user_characters_lvl"));
            this.Alignment = FormatUtils.formatString(results.getObject("user_characters_alignment"));
            this.character_description = FormatUtils.formatString(results.getObject("user_characters_description"));
            this.character_equipment = FormatUtils.formatString(results.getObject("user_characters_equipment"));
            this.Age = FormatUtils.formatInteger(results.getObject("user_characters_age"));
            this.Strength = FormatUtils.formatInteger(results.getObject("user_characters_str"));
            this.Dexterity = FormatUtils.formatInteger(results.getObject("user_characters_dex"));
            this.Constitution = FormatUtils.formatInteger(results.getObject("user_characters_con"));
            this.Intelligence = FormatUtils.formatInteger(results.getObject("user_characters_int"));
            this.Wisdom = FormatUtils.formatInteger(results.getObject("user_characters_wis"));
            this.Charisma = FormatUtils.formatInteger(results.getObject("user_characters_cha"));
            this.character_prof = FormatUtils.formatString(results.getObject("user_characters_prof"));
            this.Username = FormatUtils.formatString(results.getObject("user_username"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringDataUser.setUserInfo (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }
}
