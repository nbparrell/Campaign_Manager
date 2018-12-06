package model.webUser;

import java.util.ArrayList;
import java.sql.ResultSet;

// The purpose of this class is to have a nice java object that can be converted to JSON 
// to communicate everything necessary to the web page (the array of users plus a possible 
// list level database error message). 
public class StringDataList {

    public String dbError = "";
    public ArrayList<StringData> webUserList = new ArrayList();

    // Default constructor leaves StringDataList objects nicely set with properties 
    // indicating no database error and 0 elements in the list.
    public StringDataList() {
    }

    // Adds one StringData element to the array list of StringData elements
    public void add(StringData stringData) {
        this.webUserList.add(stringData);
    }

    // Adds creates a StringData element from a ResultSet (from SQL select statement), 
    // then adds that new element to the array list of StringData elements.
    public void addUserInfo(ResultSet results, int type) {
        //StringDataUser sd = new StringDataUser(results, type); Depricated
        StringData sd = new StringData();
        sd.setUserInfo(results);
        this.webUserList.add(sd);
    }
    
    public void addCharacterListInfo(ResultSet results, int type) {
        //StringDataUser sd = new StringDataUser(results, type); Depricated
        StringData sd = new StringData();
        sd.setCharacterListInfo(results);
        this.webUserList.add(sd);
    }
    
    public void addUserInfoWithId(ResultSet results, int type) {
        //StringDataUser sd = new StringDataUser(results, type); Depricated
        StringData sd = new StringData();
        sd.setUserInfoWithID(results);
        this.webUserList.add(sd);
    }

    public void addCampaignListing(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setCampaignListing(results);
        this.webUserList.add(sd);
    }

    public void addCampaignSignUp(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setCampaignSignUp(results);
        this.webUserList.add(sd);
    }

    public void addUserListInfo(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setUserListInfo(results);
        this.webUserList.add(sd);
    }

    public void addCampaignListingList(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setCampaignSessionList(results);
        this.webUserList.add(sd);
    }

    public void addRacesList(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setRacesList(results);
        this.webUserList.add(sd);
    }
    
    public void addClassesList(ResultSet results, int type) {
        StringData sd = new StringData();
        sd.setClassesList(results);
        this.webUserList.add(sd);
    }
}
