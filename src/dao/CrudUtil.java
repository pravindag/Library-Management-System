
package dao;

import db.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class CrudUtil {
    
    public static PreparedStatement getPreparedStatement(String sql, Object... args) throws  Exception{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        if(args != null){
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i+1, args[i]);
            }
        }
        return statement;
    }
    
    public static boolean executeUpdate(String sql, Object... agrs) throws Exception{
        return getPreparedStatement(sql, agrs).executeUpdate() > 0;
    }
    
    public static ResultSet executeQuery(String sql, Object... agrs) throws Exception{
        return getPreparedStatement(sql, agrs).executeQuery();
    }
}
