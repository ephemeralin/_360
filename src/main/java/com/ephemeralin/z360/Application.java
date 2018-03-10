package com.ephemeralin.z360;

import com.ephemeralin.z360.meduza.RssGrabber;

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
    }
}
