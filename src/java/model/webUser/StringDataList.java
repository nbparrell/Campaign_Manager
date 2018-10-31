package model.webUser;

import java.util.ArrayList;
import java.sql.ResultSet;


// The purpose of this class is to have a nice java object that can be converted to JSON 
// to communicate everything necessary to the web page (the array of users plus a possible 
// list level database error message). 
public class StringDataList {

    public String dbError = "";
    public ArrayList<StringDataUser> webUserList = new ArrayList();

    // Default constructor leaves StringDataList objects nicely set with properties 
    // indicating no database error and 0 elements in the list.
    public StringDataList() {
    }

    // Adds one StringData element to the array list of StringData elements
    public void add(StringDataUser stringData) {
        this.webUserList.add(stringData);
    }

    // Adds creates a StringData element from a ResultSet (from SQL select statement), 
    // then adds that new element to the array list of StringData elements.
    public void addUserInfo(ResultSet results, int type) {
        //StringDataUser sd = new StringDataUser(results, type); Depricated
        StringDataUser sd = new StringDataUser();
        sd.setUserInfo(results);
        this.webUserList.add(sd);
    }
    
    public void addCampaignListing(ResultSet results, int type) {
        StringDataUser sd = new StringDataUser();
        sd.setCampaignListing(results);
        this.webUserList.add(sd);
    }
    
    public void addCampaignSignUp(ResultSet results, int type) {
        StringDataUser sd = new StringDataUser();
        sd.setCampaignSignUp(results);
        this.webUserList.add(sd);
    }
}
