package com.ephemeralin.z360.db;

import com.ephemeralin.z360.meduza.Item;
import com.ephemeralin.z360.model.ItemDAO;
import com.ephemeralin.z360.util.ConnectorMySql;
import com.ephemeralin.z360.util.PropertiesStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * The type Item my sql dao.
 */
public class ItemMySqlDAO implements ItemDAO {

    /**
     * Logger instance.
     */
    private Logger log;

    /**
     * Database connector.
     */
    private ConnectorMySql databaseConnector;

    /**
     * Instantiates a new Item my sql dao.
     *
     * @param propertiesFileName the properties file name
     */
    public ItemMySqlDAO(String propertiesFileName) {
        this.databaseConnector = new ConnectorMySql(PropertiesStorage.getInstance(propertiesFileName));
        this.log = LogManager.getLogger(this.getClass());
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public List<Item> findByLink() {
        return null;
    }

    @Override
    public List<Item> findByTitle() {
        return null;
    }

    @Override
    public boolean insertItem(Item item) {
        String sql = "INSERT INTO meduza_news"
                    + " (link, description, pubDateString, pubDate, createdDate, full_text)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isAdded = false;
        try {
            String guid = UUID.randomUUID().toString();
            conn = databaseConnector.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getLink());
            pstmt.setString(2, item.getDescription());
            pstmt.setString(3, item.getPubDateString());
            pstmt.setLong(4, item.getPubDate());
            pstmt.setLong(5, item.getCreatedDate());
            pstmt.setString(6, item.getFullText());
            pstmt.execute();
            isAdded = true;
            log.info(String.format("Item with GUID %s put to the DB", guid));
        } catch (SQLException e) {
            log.error(String.format("SQL Error to put Item with link %s to the DB", item.getLink()), e);
        } catch (Exception e) {
            log.error(String.format("Unknown Error to put Item with link %s to the DB", item.getLink()), e);
        } finally {
            closeSqlResources(conn, pstmt, rs);
        }
        return isAdded;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(Item item) {
        return false;
    }

    /**
     * Close all SQL connection resources.
     * @param conn Connection
     * @param pstmt Prepared Statement
     * @param rs Result Set
     */
    private void closeSqlResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
    }
}
