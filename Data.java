package ARC;
/**
 * Mounika Alluri : June 2018
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Data {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/app_reviews?useUnicode=true&useJDB..." +
            "CCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"; //replace this with your db

    //replace with your corresponding credentials
    static final String USER = "root";
    static final String PASS = "password";

    public static ArrayList<Row> dataRows = new ArrayList<Row>();
    public String content="";

    public void init() {

        Connection conn = null;
        Statement stmt = null;
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM review";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String appName = rs.getString("app_name");
                String versionNum = rs.getString("version");
                String userName = rs.getString("userid");
                String date = rs.getString("date");
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String text = rs.getString("text");
                int infoGiv = rs.getInt("has_information_giving");
                int infoSeek = rs.getInt("has_information_seeking");
                int featureReq = rs.getInt("has_feature_Request");
                int bugReport = rs.getInt("has_bug_report");
                int sentScore = rs.getInt("sentiment_score");
                Row r = new Row(id, appName, versionNum, userName, date, rating, title, text, infoGiv, infoSeek, featureReq, bugReport, sentScore);
                content+=text;
                dataRows.add(r);
            }

            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        } //end try

    }

}

