package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.userCharacter.*;

// classes in my project
import dbUtils.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharacterView {

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
            StringData sd = new StringData();
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.allRacesAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringData getCharacterAPI(DbConn dbc, String Character_ID) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringData sd = new StringData();
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
    public static void populateRaceClassInfo(StringData insertData, StringData raceClassInfo) {
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

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringData insertCharactersAPI(DbConn dbc, StringData insertData) {
        int type = 0;
        //StringDataList sdl = new StringDataList();
        StringData raceClassInfo = new StringData();
        populateRaceClassInfo(insertData, raceClassInfo);
        StringData sd = new StringData();
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.listCharactersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    public static StringData deleteCharactersAPI(DbConn dbc, String id) {
        StringData sd = new StringData();
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
}
