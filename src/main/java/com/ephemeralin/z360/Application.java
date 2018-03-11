package com.ephemeralin.z360;

import com.ephemeralin.z360.meduza.RssGrabber;
import com.ephemeralin.z360.util.ConnectorMySql;
import com.ephemeralin.z360.util.PropertiesStorage;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Application.
 */
public class Application {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        RssGrabber rssGrabber = new RssGrabber();
        rssGrabber.grab();

        final ConnectorMySql connector = new ConnectorMySql(new PropertiesStorage("/properties.properties"));
        final Connection connection = connector.getConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
