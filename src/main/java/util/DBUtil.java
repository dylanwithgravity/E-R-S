package util;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static BasicDataSource connectionPool = new BasicDataSource();

    static {
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("database.properties");
            Properties properties = new Properties();
            properties.load(input);
            connectionPool.setDriverClassName(properties.getProperty("driver"));
            connectionPool.setUrl(properties.getProperty("url"));
            connectionPool.setUsername(properties.getProperty("username"));
            connectionPool.setPassword(properties.getProperty("password"));
            connectionPool.setDefaultAutoCommit(false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public static Connection getInstance() {

        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
