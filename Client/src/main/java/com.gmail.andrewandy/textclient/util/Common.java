package com.gmail.andrewandy.textclient.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Common {

    public static String colourise(String string) {
        return TextColour.translateColourCode('&', string);
    }

    public static void log (Level level, String message) {
       Logger.getGlobal().log(level, colourise(message + "&r"));
    }

}
