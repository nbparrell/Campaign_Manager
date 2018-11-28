<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="model.webUser.*" %>
<%@page language="java" import="view.WebUserView" %>

<%@page language="java" import="com.google.gson.*" %>



<%

    // This is the object we get from the GSON library.
    // This object knows how to convert betweeb these two formats: 
    //    POJO (plain old java object) 
    //    JSON (JavaScript Object notation)
    Gson gson = new Gson();

    DbConn dbc = new DbConn();
    StringDataUser errorMsgs = new StringDataUser();

    String id = request.getParameter("deleteId");
    if (id == null) {
        errorMsgs.errorMsg = "Cannot insert -- no data was received";
        System.out.println(errorMsgs.errorMsg);
    } else {
        System.out.println("id is " + id);
        errorMsgs.errorMsg = dbc.getErr();
        if (errorMsgs.errorMsg.length() == 0) { // means db connection is ok
            System.out.println("deleteSessionAPI.jsp ready to delete");
            // this method takes the user's input data as input and outputs an error message object (with a successMsg or erroMsg depending on result).
            errorMsgs = WebUserView.deleteSessionAPI(dbc, id); // this is the form level message
        }
    }

    out.print(gson.toJson(errorMsgs).trim());
    dbc.close();
%>