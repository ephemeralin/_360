package com.ephemeralin.z360.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * The type Connector mysql test.
 */
public class ConnectorMySqlTest {

    /**
     * Gets connection test.
     */
    @Test
    public void getConnectionTest() {
        final ConnectorMySql connectorMySql = new ConnectorMySql(new PropertiesStorage("/properties.properties"));
        final Connection connection = connectorMySql.getConnection();
        try {
            assertTrue("DB connection failed!", connection.isValid(10));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}