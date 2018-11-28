package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.*;

// classes in my project
import dbUtils.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WebUserView {

    //Uses StringDataList.addUserListInfo
    public static StringDataList allUsersAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT web_user_id, "
                    + "user_username "
                    + "FROM web_user "
                    + "ORDER BY web_user_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addUserListInfo(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addClassesList
    public static StringDataList allClassesAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT character_classes_id, "
                    + "character_classes_name "
                    + "FROM character_classes "
                    + "ORDER BY character_classes_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addClassesList(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.allClassesAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addRacesList
    public static StringDataList allRacesAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT character_races_id, "
                    + "character_races_type "
                    + "FROM character_races "
                    + "ORDER BY character_races_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addRacesList(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.allRacesAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addCampaignListingList
    public static StringDataList allSessionsAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT campaign_session_id, "
                    + "CONCAT(campaign_session_name,' ',campaign_session_date) AS campaign_session_list "
                    + "FROM campaign_sessions "
                    + "ORDER BY campaign_session_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addCampaignListingList(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            if (e.getMessage().contains("Duplicate")) {
                sd.errorMsg = "Session already exist please use a Different Name for sessoin.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            }
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addCampaignSignUp
    public static StringDataList listAssocAPI(DbConn dbc) {
        int type = 1;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT campaign_session_posting_id, "
                    + "campaign_session_posting_name, "
                    + "campaign_session_posting_created_date, "
                    + "campaign_session_posting_desc, "
                    + "campaign_session_posting_id, "
                    + "campaign_session_location, "
                    + "campaign_session_campaign, "
                    + "campaign_session_date, "
                    + "campaign_sessions.campaign_session_id, "
                    + "web_user.web_user_id, "
                    + "user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "user_role_id, "
                    + "campaign_session_name,"
                    + "campaign_session_desc "
                    + "FROM web_user, campaign_sessions, campaign_session_posting "
                    + "where campaign_session_posting.web_user_id = web_user.web_user_id "
                    + "AND campaign_session_posting.campaign_session_id = campaign_sessions.campaign_session_id "
                    + "ORDER BY campaign_session_posting_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addCampaignSignUp(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.listAssocAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addCampaignListing
    public static StringDataList listOtherAPI(DbConn dbc) {
        int type = 2;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT campaign_session_id, "
                    + "campaign_session_location, "
                    + "campaign_session_campaign, "
                    + "campaign_session_date, "
                    + "campaign_sessions.campaign_session_id,  "
                    + "campaign_session_name,"
                    + "campaign_session_desc "
                    + "FROM campaign_sessions "
                    + "ORDER BY campaign_session_date ASC";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addCampaignListing(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addUserInfoWithId
    public static StringDataList loginAPI(DbConn dbc, String username, String password) {
        int type = 0;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT "
                    + "web_user_id, "
                    + "user_username, "
                    + "user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "web_user.user_role_id, "
                    + "user_role_type "
                    + "FROM web_user, user_role "
                    + "WHERE web_user.user_role_id = user_role.user_role_id "
                    + "AND user_username = ? AND user_password = ?";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addUserInfoWithId(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.loginAPI(): " + e.getMessage();
            sdl.add(sd);
        }

        return sdl;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringDataUser insertCampaignSessionAPI(DbConn dbc, StringDataUser insertData) {
        StringDataUser sd = new StringDataUser();
        if (ValidationUtils.insertSessionValidation(insertData, sd)) {
            try {
                String sql = "INSERT INTO campaign_sessions"
                        + " (campaign_session_name, "
                        + "campaign_session_location, "
                        + "campaign_session_date, "
                        + "campaign_session_campaign, "
                        + "campaign_session_desc) "
                        + "values (?,?,?,?,?)";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.Name); // string type is simple
                stmt.setString(2, insertData.Session_Location);
                stmt.setDate(3, ValidationUtils.dateConversion(insertData.Session_Date));
                stmt.setString(4, insertData.Campaign);
                stmt.setString(5, insertData.Description);
                int results = stmt.executeUpdate();
                if (results > 1) {
                    System.out.print("Added more then one campaign: " + results);
                    sd.errorMsg = ("added more then one campaign: " + results);
                } else {
                    System.out.print("Added one campaign");
                    sd.successMsg = ("Successfully added one campaign");
                }
                stmt.close();
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate")) {
                    sd.errorMsg = "Session already exist with that name for the date selected please use a Different Name for session.";
                } else if (e.getMessage().contains("null")) {
                    sd.errorMsg = "Please enter a valid date.";
                } else {
                    sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
                }
                //sd.errorMsg = "Exception thrown in WebUserView.updateCampaignSessionAPI(): " + e.getMessage();
            }
        }

        return sd;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringDataUser insertCampaignSessionPostingAPI(DbConn dbc, StringDataUser insertData) {
        StringDataUser sd = new StringDataUser();
        if (ValidationUtils.insertSessionPostingValidation(insertData, sd)) {
            try {
                String sql = "INSERT INTO campaign_session_posting"
                        + " (web_user_id, "
                        + "campaign_session_id, "
                        + "campaign_session_posting_name, "
                        + "campaign_session_posting_desc) "
                        + "values (?,?,?,?)";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.webUserId); // string type is simple
                stmt.setString(2, insertData.campaign_session_id);
                stmt.setString(3, insertData.campaign_session_posting_name);
                stmt.setString(4, insertData.Notes);
                int results = stmt.executeUpdate();
                if (results > 1) {
                    System.out.print("Added more then one campaign: " + results);
                    sd.errorMsg = ("signed up for more then one campaign: " + results);
                } else {
                    System.out.print("Added one campaign");
                    sd.successMsg = ("Successfully added signed up for campaign");
                }
                stmt.close();
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate")) {
                    sd.webUserId = "This user has already signed up for this campaign session<br>"
                            + "please select a different session to sign up for.";
                    sd.errorMsg = "Try again";
                } else {
                    sd.errorMsg = "Exception thrown in WebUserView.updateCampaignSessionPostingAPI(): " + e.getMessage();
                }
            }
        }

        return sd;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringDataUser getCharacterAPI(DbConn dbc, String Character_ID) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataUser sd = new StringDataUser();
        try {
            String sql = "SELECT "
                    + "user_characters_name, "
                    + "character_races_type, "
                    + "character_classes_name, "
                    + "user_characters_lvl, "
                    + "user_characters_alignment, "
                    + "user_characters_description, "
                    + "user_characters_equipment, "
                    + "user_characters_age, "
                    + "user_characters_str, "
                    + "user_characters_dex, "
                    + "user_characters_con, "
                    + "user_characters_int, "
                    + "user_characters_wis, "
                    + "user_characters_cha, "
                    + "user_characters_prof,"
                    + "user_username "
                    + "FROM user_characters, character_races, character_classes, web_user "
                    + "WHERE user_characters_id = ? "
                    + "AND user_characters.character_races_id = character_races.character_races_id "
                    + "AND user_characters.character_classes_id = character_classes.character_classes_id "
                    + "AND user_characters.web_user_id = web_user.web_user_id";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setInt(1, ValidationUtils.integerConversion(Character_ID));
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sd.setCharacterInfo(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            sd.errorMsg = "Exception thrown in WebUserView.listCharactersAPI(): " + e.getMessage();
        }
        return sd;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static void populateRaceClassInfo(StringDataUser insertData, StringDataUser raceClassInfo) {
        System.out.print("Attempting to query database for character race info");
        DbConn dbc = new DbConn();
        try {
            String sql = "SELECT character_races_type, "
                    + "character_races_str_bonus, "
                    + "character_races_dex_bonus, "
                    + "character_races_con_bonus, "
                    + "character_races_int_bonus, "
                    + "character_races_wis_bonus, "
                    + "character_races_chr_bonus, "
                    + "character_races_prof, "
                    + "character_classes_name, "
                    + "character_classes_prof, "
                    + "character_classes_starting_equipment "
                    + "FROM character_races, character_classes "
                    + "WHERE character_races_id = ? "
                    + "AND character_classes_id = ? ";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, insertData.race_id);
            stmt.setString(2, insertData.class_id);
            ResultSet results = stmt.executeQuery();
            raceClassInfo.setRaceClassInfo(results);
            results.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.print("Failed to Query database " + e.getMessage());
            insertData.errorMsg = "Exception thrown in WebUserView.populateRaceClassInfo: " + e.getMessage();
        }
        dbc.close();
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success.
    public static StringDataUser insertUsersAPI(DbConn dbc, StringDataUser insertData) {
        int type = 0;
        //StringDataList sdl = new StringDataList();
        StringDataUser sd = new StringDataUser();
        if (ValidationUtils.insertUserValidation(insertData, sd)) {
            try {
                String sql = "INSERT INTO web_user "
                        + "(user_username, "
                        + "user_email, "
                        + "user_password, "
                        + "user_first_name, "
                        + "user_last_name, "
                        + "membership_fee, "
                        + "birthday, "
                        + "user_role_id) "
                        + "values (?,?,?,?,?,?,?,?)";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.Username);
                stmt.setString(2, insertData.userEmail); // string type is simple
                stmt.setString(3, insertData.userPassword);
                stmt.setString(4, insertData.firstName);
                stmt.setString(5, insertData.lastName);
                stmt.setBigDecimal(6, ValidationUtils.decimalConversion(insertData.membershipFee));
                stmt.setDate(7, ValidationUtils.dateConversion(insertData.birthday));
                stmt.setInt(8, ValidationUtils.integerConversion(insertData.userRoleId));

                int results = stmt.executeUpdate();
                if (results > 1) {
                    System.out.print("Added more then one campaign: " + results);
                    sd.errorMsg = ("added more then one campaign: " + results);
                    //sdl.add(sd);
                } else {
                    System.out.print("Added one campaign");
                    sd.successMsg = ("Successfully added one campaign");
                    //sdl.add(sd);
                }
                stmt.close();
            } catch (SQLException e) {
                //System.out.print(e.toString());
                int friendlyMessageExists = 1;
                System.out.print("Error message: " + e.getMessage());
                String eMessage = e.getMessage();
                while (eMessage != null) {
                    if (eMessage.contains("username")) {
                        sd.Username = "This username already exists please choose another one";
                    } else if (eMessage.contains("email")) {
                        sd.userEmail = "This email already exists please choose another one";
                    } else {
                        friendlyMessageExists = 0;
                    }
                    try {
                        eMessage = e.getNextException().getMessage();
                    } catch (Exception ex) {
                        eMessage = null;
                        System.out.print(ex.getMessage());
                    }
                }
                if (friendlyMessageExists == 1) {
                    sd.errorMsg = "Try again";
                } else {
                    sd.errorMsg = "Exception thrown in WebUserView.insertUsersAPI(): " + e.getMessage();
                }
            }
            //sdl.add(sd);
        }

        return sd;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringDataUser insertCharactersAPI(DbConn dbc, StringDataUser insertData) {
        int type = 0;
        //StringDataList sdl = new StringDataList();
        StringDataUser raceClassInfo = new StringDataUser();
        populateRaceClassInfo(insertData, raceClassInfo);
        StringDataUser sd = new StringDataUser();
        int stat;
        int statBonus;
        int totalStat;
        if (ValidationUtils.insertCharacterValidation(insertData, sd)) {
            try {
                String sql = "INSERT INTO user_characters "
                        + "(user_characters_name, "
                        + "character_races_id, "
                        + "character_classes_id, "
                        + "user_characters_lvl, "
                        + "user_characters_alignment, "
                        + "user_characters_description, "
                        + "user_characters_equipment, "
                        + "user_characters_age, "
                        + "user_characters_str, "
                        + "user_characters_dex, "
                        + "user_characters_con, "
                        + "user_characters_int, "
                        + "user_characters_wis, "
                        + "user_characters_cha, "
                        + "user_characters_prof, "
                        + "web_user_id) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.Character_Name);
                stmt.setInt(2, ValidationUtils.integerConversion(insertData.race_id)); // string type is simple
                stmt.setInt(3, ValidationUtils.integerConversion(insertData.class_id));
                stmt.setInt(4, 1);
                stmt.setString(5, insertData.Alignment);
                stmt.setString(6, insertData.character_description);
                stmt.setString(7, raceClassInfo.character_equipment);
                stmt.setInt(8, ValidationUtils.integerConversion(insertData.Age));
                //Calc Str
                stat = ValidationUtils.integerConversion(insertData.Strength);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.strBonus);
                totalStat = stat + statBonus;
                stmt.setInt(9, totalStat);
                //Calc Dex
                stat = ValidationUtils.integerConversion(insertData.Dexterity);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.dexBonus);
                totalStat = stat + statBonus;
                stmt.setInt(10, totalStat);
                //Calc Con
                stat = ValidationUtils.integerConversion(insertData.Constitution);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.conBonus);
                totalStat = stat + statBonus;
                stmt.setInt(11, totalStat);
                //Calc Int
                stat = ValidationUtils.integerConversion(insertData.Intelligence);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.intBonus);
                totalStat = stat + statBonus;
                stmt.setInt(12, totalStat);
                //Calc Wis
                stat = ValidationUtils.integerConversion(insertData.Wisdom);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.wisBonus);
                totalStat = stat + statBonus;
                stmt.setInt(13, totalStat);
                //Calc Chr
                stat = ValidationUtils.integerConversion(insertData.Charisma);
                statBonus = ValidationUtils.integerConversion(raceClassInfo.chrBonus);
                totalStat = stat + statBonus;
                stmt.setInt(14, totalStat);

                stmt.setString(15, raceClassInfo.character_prof);
                stmt.setInt(16, ValidationUtils.integerConversion(insertData.webUserId));

                int results = stmt.executeUpdate();
                if (results > 1) {
                    System.out.print("Added more then one campaign: " + results);
                    sd.errorMsg = ("added more then one campaign: " + results);
                    //sdl.add(sd);
                } else {
                    System.out.print("Added one campaign");
                    sd.successMsg = ("Successfully added one campaign");
                    //sdl.add(sd);
                }
                stmt.close();
            } catch (SQLException e) {
                //System.out.print(e.toString());
                System.out.print("Error message: " + e.getMessage());
                if (e.getMessage().contains("username")) {
                    sd.Username = "This username already exists please choose another one";
                    sd.errorMsg = "Try again";
                } else if (e.getMessage().contains("email")) {
                    sd.userEmail = "This email already exists please choose another one";
                    sd.errorMsg = "Try again";
                } else {
                    sd.errorMsg = "Exception thrown in WebUserView.insertCharactersAPI(): " + e.getMessage();
                }
                //sdl.add(sd);
            }
        }
        return sd;
    }

    //Uses StringDataList.addUserInfo
    public static StringDataList listUsersAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT web_user_id, "
                    + "user_username, "
                    + "user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "web_user.user_role_id, "
                    + "user_role_type "
                    + "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id "
                    + "ORDER BY web_user_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addUserInfoWithId(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Uses StringDataList.addCharacterInfo
    public static StringDataList listCharactersAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT user_username, "
                    + "user_characters_id, "
                    + "user_characters_name, "
                    + "user_characters_lvl, "
                    + "character_classes_name, "
                    + "character_races_type "
                    + "FROM user_characters, character_classes, character_races, web_user "
                    + "WHERE user_characters.character_races_id = character_races.character_races_id "
                    + "AND user_characters.character_classes_id = character_classes.character_classes_id "
                    + "AND user_characters.web_user_id = web_user.web_user_id "
                    + "ORDER BY user_characters_id";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addCharacterListInfo(results, type);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.listCharactersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    public static StringDataUser deleteCharactersAPI(DbConn dbc, String id) {
        StringDataUser sd = new StringDataUser();
        try {
            String sql = "DELETE "
                    + "FROM user_characters "
                    + "WHERE user_characters_id = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, id);
            int results = stmt.executeUpdate();
            if (results > 1) {
                System.out.print("Deleted more then one character: " + results);
                sd.errorMsg = ("Deleted more then one character: " + results);
                //sdl.add(sd);
            } else {
                System.out.print("Deleted one character");
                sd.successMsg = ("Successfully deleted one character");
                //sdl.add(sd);
            }
            stmt.close();
        } catch (Exception e) {
            if (e.getMessage().contains("key")) {
                 sd.errorMsg = "Unable to delete character.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.deleteSessionAPI(): " + e.getMessage();
            }
        }

        return sd;
    }

    public static StringDataUser deleteUserAPI(DbConn dbc, String id) {
        StringDataUser sd = new StringDataUser();
        try {
            String sql = "DELETE "
                    + "FROM web_user "
                    + "WHERE web_user_id = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, id);
            int results = stmt.executeUpdate();
            if (results > 1) {
                System.out.print("Deleted more then one User: " + results);
                sd.errorMsg = ("Deleted more then one User: " + results);
                //sdl.add(sd);
            } else {
                System.out.print("Deleted one User");
                sd.successMsg = ("Successfully deleted one User");
                //sdl.add(sd);
            }
            stmt.close();
        } catch (Exception e) {
            if (e.getMessage().contains("key")) {
                 sd.errorMsg = "Unable to delete user because they are going to a session.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.deleteUserAPI(): " + e.getMessage();
            }
        }

        return sd;
    }

    public static StringDataUser deleteSessionAPI(DbConn dbc, String id) {
        StringDataUser sd = new StringDataUser();
        try {
            String sql = "DELETE "
                    + "FROM campaign_sessions "
                    + "WHERE campaign_session_id = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, id);
            int results = stmt.executeUpdate();
            if (results > 1) {
                System.out.print("Deleted more then one Session: " + results);
                sd.errorMsg = ("Deleted more then one Session: " + results);
                //sdl.add(sd);
            } else {
                System.out.print("Deleted one Session");
                sd.successMsg = ("Successfully deleted one Session");
                //sdl.add(sd);
            }
            stmt.close();
        } catch (Exception e) {
            if (e.getMessage().contains("key")) {
                 sd.errorMsg = "Unable to delete session because people have signed up for it.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.deleteSessionAPI(): " + e.getMessage();
            }
        }

        return sd;
    }

    public static StringDataUser deletePostingAPI(DbConn dbc, String id) {
        StringDataUser sd = new StringDataUser();
        try {
            String sql = "DELETE "
                    + "FROM campaign_session_posting "
                    + "WHERE campaign_session_posting_id = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, id);
            int results = stmt.executeUpdate();
            if (results > 1) {
                System.out.print("Deleted more then one Posting: " + results);
                sd.errorMsg = ("Deleted more then one Posting: " + results);
                //sdl.add(sd);
            } else {
                System.out.print("Deleted one Posting");
                sd.successMsg = ("Successfully deleted one Posting");
                //sdl.add(sd);
            }
            stmt.close();
        } catch (Exception e) {
            if (e.getMessage().contains("key")) {
                 sd.errorMsg = "Unable to delete Posting.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.deletePostingAPI(): " + e.getMessage();
            }
        }

        return sd;
    }

}
