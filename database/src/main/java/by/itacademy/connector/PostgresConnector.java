package by.itacademy.connector;

import by.itacademy.utils.SqlQueryManager;
import by.itacademy.exceptions.DatabaseConnectionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;

import static by.itacademy.utils.DatabaseProperties.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostgresConnector {

    static {
        final String initScript = SqlQueryManager.getSqlQuery(DATABASE_INIT_SCRIPT);
        try {
            PreparedStatement statement = getDefaultConnection().prepareStatement(initScript);
            statement.executeUpdate() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getDefaultConnection(){
        return getConnection(DATABASE_BASE_URL, BASE_USER, BASE_USER_PASSWORD);
    }

    public static Connection getConnection(final String url, final String user, final String password){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
           throw new DatabaseConnectionException(e.getMessage(), e.getCause());
        }
    }

}
