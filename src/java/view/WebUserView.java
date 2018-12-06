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
            StringData sd = new StringData();
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.loginAPI(): " + e.getMessage();
            sdl.add(sd);
        }

        return sdl;
    }

    //Sets StringDataUser.errorMsg on error or StringDataUser.successMsg on success.
    public static StringData insertUsersAPI(DbConn dbc, StringData insertData) {
        int type = 0;
        //StringDataList sdl = new StringDataList();
        StringData sd = new StringData();
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

    //Uses StringDataList.addUserInfo
    public static StringDataList listUsersAPI(DbConn dbc) {
        int type = 0;
        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT web_user_id, "
                    + "user_username, "
                    + "user_first_name, "
                    + "user_last_name, "
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
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in WebUserView.allUsersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

    public static StringData deleteUserAPI(DbConn dbc, String id) {
        StringData sd = new StringData();
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
    
    public static StringData updateUsersAPI(DbConn dbc, StringData insertData) {
        int type = 0;
        //StringDataList sdl = new StringDataList();
        StringData sd = new StringData();
        if (ValidationUtils.insertUserValidation(insertData, sd)) {
            try {
                String sql = "UPDATE web_user "
                        + "SET "
                        + "user_username = ?, "
                        + "user_email = ?, "
                        + "user_password = ?, "
                        + "user_first_name = ?, "
                        + "user_last_name = ?, "
                        + "membership_fee = ?, "
                        + "birthday = ?, "
                        + "user_role_id = ? "
                        + "WHERE web_user_id = ?";

                PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
                stmt.setString(1, insertData.Username);
                stmt.setString(2, insertData.userEmail); // string type is simple
                stmt.setString(3, insertData.userPassword);
                stmt.setString(4, insertData.firstName);
                stmt.setString(5, insertData.lastName);
                stmt.setBigDecimal(6, ValidationUtils.decimalConversion(insertData.membershipFee));
                stmt.setDate(7, ValidationUtils.dateConversion(insertData.birthday));
                stmt.setInt(8, ValidationUtils.integerConversion(insertData.userRoleId));
                stmt.setInt(9, ValidationUtils.integerConversion(insertData.webUserId));

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

}
