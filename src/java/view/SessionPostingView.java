package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.sessionPosting.*;

// classes in my project
import dbUtils.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionPostingView {


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
                    + "user_username, "
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.listAssocAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success
    public static StringData insertCampaignSessionPostingAPI(DbConn dbc, StringData insertData) {
        StringData sd = new StringData();
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

    public static StringData deletePostingAPI(DbConn dbc, String id) {
        StringData sd = new StringData();
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
    
    
    public static StringData updateCampaignSessionPostingAPI(DbConn dbc, StringData insertData) {
        StringData sd = new StringData();
        if (ValidationUtils.insertSessionPostingValidation(insertData, sd)) {
            try {
                String sql = "UPDATE campaign_session_posting "
                        + "SET "
                        + "web_user_id = ?, "
                        + "campaign_session_id = ?, "
                        + "campaign_session_posting_name = ?, "
                        + "campaign_session_posting_desc = ? "
                        + "WHERE campaign_session_posting_id = ?";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.webUserId); // string type is simple
                stmt.setInt(2, ValidationUtils.integerConversion(insertData.campaign_session_id));
                stmt.setString(3, insertData.campaign_session_posting_name);
                stmt.setString(4, insertData.Notes);
                stmt.setInt(5, ValidationUtils.integerConversion(insertData.campaign_session_posting_id));
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

}
