package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.*;

// classes in my project
import dbUtils.*;

public class WebUserView {

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
    
    public static StringDataList allSessionsAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT campaign_session_id, "
                    + "campaign_session_name "
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
    
    public static StringDataUser updateCampaignSessionAPI(DbConn dbc, StringDataUser insertData) {
        StringDataUser sd = new StringDataUser();
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
        } catch (Exception e) {
            sd.errorMsg = "Exception thrown in WebUserView.updateCampaignSessionAPI(): " + e.getMessage();
        }
        
        return sd;
    }
    
    
    public static StringDataUser updateCampaignSessionPostingAPI(DbConn dbc, StringDataUser insertData) {
        StringDataUser sd = new StringDataUser();
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
        } catch (Exception e) {
            sd.errorMsg = "Exception thrown in WebUserView.updateCampaignSessionPostingAPI(): " + e.getMessage();
        }
        
        return sd;
    }
    
    public static StringDataList updateUsersAPI(DbConn dbc, StringDataUser insertData) {
        int type = 0;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "INSERT INTO web_user "
                    + "(user_email, "
                    + "user_password, "
                    + "membership_fee, "
                    + "birthday, "
                    + "user_role_id) "
                    + "values (?,?,?,?,?)";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            
            stmt.setString(1, insertData.userEmail); // string type is simple
            stmt.setString(2, insertData.userPassword);
            stmt.setBigDecimal(3, ValidationUtils.decimalConversion(insertData.membershipFee));
            stmt.setDate(4, ValidationUtils.dateConversion(insertData.birthday));
            stmt.setInt(5, ValidationUtils.integerConversion(insertData.Description));
        
            int results = stmt.executeUpdate();
            if (results > 1) {
                StringDataUser sd = new StringDataUser();
                System.out.print("Added more then one campaign: " + results);
                sd.errorMsg = ("added more then one campaign: " + results);
                sdl.add(sd);
            } else {
                StringDataUser sd = new StringDataUser();
                System.out.print("Added one campaign");
                sd.successMsg = ("Successfully added one campaign");
                sdl.add(sd);
            }
            stmt.close();
        } catch (Exception e) {
            StringDataUser sd = new StringDataUser();
            sd.errorMsg = "Exception thrown in WebUserView.loginAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        
        return sdl;
    }
    
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

}
