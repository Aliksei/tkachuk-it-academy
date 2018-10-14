package by.itacademy.connector;

import by.itacademy.exceptions.DaoLayerException;
import by.itacademy.utils.SqlQueryManager;
import by.itacademy.exceptions.DatabaseConnectionException;
import by.itacademy.utils.SqlQueryPrinter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.*;

import static by.itacademy.utils.DatabaseProperties.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostgresConnector {

    private final static Logger LOGGER = Logger.getLogger(PostgresConnector.class);

    static {
        final String initScript = SqlQueryManager.getSqlQuery(DATABASE_INIT_SCRIPT);
        try {
            LOGGER.info("-------------------  Running [Database Init Script]  -------------------\n");
            Class.forName("org.postgresql.Driver");
            PreparedStatement preparedStatement = getDefaultConnection().prepareStatement(initScript);
            SqlQueryPrinter.printQuery(preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("Database connection passed successfully");
        } catch (Exception e) {
            throw new DaoLayerException("Can't init database demo table");
        }
    }

    public static void main(String[] args) {

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
