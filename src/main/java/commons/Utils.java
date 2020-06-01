/* 
author @P6
*/

package commons;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Utils {

    public String getDBConnection(String userName, String password, String dbName, String query, String fileName)
            throws Exception {
        String result = null;
      
        try (Connection con = DriverManager.getConnection(dbName, userName, password)) {
            try (Statement selectStmt = con.createStatement()) {

                try (ResultSet rs = selectStmt.executeQuery(query)) {
                 
                    while (rs.next()) {
                     
                        LocalDateTime now = LocalDateTime.now();
                        int year = now.getYear();
                        int month = now.getMonthValue();
                        int day = now.getDayOfMonth();
                        int hour = now.getHour();
                        int minute = now.getMinute();
                        int second = now.getSecond();
                        String timestamp= Integer.toString(year)+"_"+Integer.toString(month)+"_"+Integer.toString(day)+"_"+Integer.toString(hour)+"_"+Integer.toString(minute)+"_"+Integer.toString(second);
                        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(rs,
                                new ResultSetToExcel.FormatType[] { ResultSetToExcel.FormatType.TEXT,
                                        ResultSetToExcel.FormatType.TEXT, ResultSetToExcel.FormatType.TEXT,ResultSetToExcel.FormatType.TEXT},
                                "Union Query");
                        resultSetToExcel.generate(new File(
                                System.getProperty("user.dir") + File.separator +"test-output"+File.separator+ fileName + timestamp + ".xls"));

                    }
                  
                }
            }

        } catch (SQLException | NullPointerException e) {

            e.printStackTrace();
        }
        return result;
    }

}