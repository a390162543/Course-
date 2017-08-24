package cn.zhsite.json.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JSONUtil {

    public static String toJSON(ResultSet rs){
        JSONArray array = new JSONArray();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName =metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.put(jsonObj);
            }
            return array.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
