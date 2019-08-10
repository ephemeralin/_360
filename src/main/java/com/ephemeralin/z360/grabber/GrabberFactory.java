package com.ephemeralin.z360.grabber;

import com.ephemeralin.z360.model.Source;

public class GrabberFactory {

    public IGrabber getGrabber(Source source) {
         switch (source) {
             case vesti:
                 return new VestiGrabber();
             case meduza:
                 return new MeduzaGrabber();
             case rentv:
                 return new RentvGrabber();
             default:
                 return null;
         }
    }
}
