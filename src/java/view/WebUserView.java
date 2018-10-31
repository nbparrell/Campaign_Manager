package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.*;

// classes in my project
import dbUtils.*;

public class WebUserView {

    public static StringDataList listUsersAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT web_user_id, "
                    + "user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "web_user.user_role_id, "
                    + "user_role_type "
                    + "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id "
                    + "ORDER BY web_user_id ";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addUserInfo(results, type);
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

    public static StringDataList listAssocAPI(DbConn dbc) {
        int type = 1;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT campaign_session_posting_name, "
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
                    + "ORDER BY campaign_session_posting_name";  // you always want to order by something, not just random order.
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

    public static StringDataList listOtherAPI(DbConn dbc) {
        int type = 2;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT "
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

    public static StringDataList loginAPI(DbConn dbc, String username, String password) {
        int type = 0;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT "
                    + "web_user_id, "
                    + "user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "web_user.user_role_id, "
                    + "user_role_type "
                    + "FROM web_user, user_role "
                    + "WHERE web_user.user_role_id = user_role.user_role_id "
                    + "AND user_email = ? AND user_password = ?";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.addUserInfo(results, type);
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

}
