package com.ephemeralin.z360;

import com.ephemeralin.z360.grabber.IGrabber;
import com.ephemeralin.z360.grabber.VestiGrabber;
import com.ephemeralin.z360.model.Item;
import com.ephemeralin.z360.stemming.Stemmer;

import java.io.IOException;
import java.util.List;

/**
 * The Application.
 */
public class Application2 {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main2(String[] args) {
//        final ItemMySqlDAO dao = new ItemMySqlDAO("/properties.properties");
        IGrabber grabber = new VestiGrabber();
        final List<Item> items = grabber.getData();
        for (Item item : items) {
            int a = 1;
            System.out.println(item);
            try {
                System.out.println(Stemmer.getInstance().stem(item.getFullText()));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            break;
            //dao.insertItem(item);
        }
    }
}
