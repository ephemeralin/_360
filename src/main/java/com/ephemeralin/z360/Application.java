package com.ephemeralin.z360;

import com.ephemeralin.z360.db.ItemMySqlDAO;
import com.ephemeralin.z360.meduza.Item;
import com.ephemeralin.z360.meduza.RssGrabber;

import java.util.List;

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
        final ItemMySqlDAO dao = new ItemMySqlDAO("/properties.properties");
        RssGrabber rssGrabber = new RssGrabber();
        final List<Item> items = rssGrabber.grab();
        for (Item item : items) {
            dao.insertItem(item);
        }
    }
}
