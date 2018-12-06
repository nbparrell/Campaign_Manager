package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.sessionListing.*;

// classes in my project
import dbUtils.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionListView {

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
            StringData sd = new StringData();
            if (e.getMessage().contains("Duplicate")) {
                sd.errorMsg = "Session already exist please use a Different Name for sessoin.";
            } else {
                sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            }
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringData insertCampaignSessionAPI(DbConn dbc, StringData insertData) {
        StringData sd = new StringData();
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

    public static StringData deleteSessionAPI(DbConn dbc, String id) {
        StringData sd = new StringData();
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
    
    public static StringData updateCampaignSessionAPI(DbConn dbc, StringData insertData) {
        StringData sd = new StringData();
        if (ValidationUtils.insertSessionValidation(insertData, sd)) {
            try {
                String sql = "UPDATE campaign_sessions "
                        + "SET "
                        + "campaign_session_name = ?, "
                        + "campaign_session_location = ?, "
                        + "campaign_session_date = ?, "
                        + "campaign_session_campaign = ?, "
                        + "campaign_session_desc = ? "
                        + "WHERE campaign_session_id = ?";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.Name); // string type is simple
                stmt.setString(2, insertData.Session_Location);
                stmt.setDate(3, ValidationUtils.dateConversion(insertData.Session_Date));
                stmt.setString(4, insertData.Campaign);
                stmt.setString(5, insertData.Description);
                stmt.setInt(6, ValidationUtils.integerConversion(insertData.campaign_session_id));
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
}
