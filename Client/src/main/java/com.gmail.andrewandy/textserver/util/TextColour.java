package com.gmail.andrewandy.textserver.util;

import com.sun.istack.internal.NotNull;

public enum TextColour {

    BLUE('b'), RED('c'), YELLOW('e'), GREEN('a'), MAGENTA('d'),
    TextColour(char code) {

    }



    /**
     * Translate the ANSI colour codes from a char.
     *
     * @param code   The colour code something like {@literal &}
     * @param string The string to translate
     * @return The colourised string.
     */
    @NotNull
    static String translateColourCode(char code, String string) {

        char[] b = string.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == code && "AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) { //This checks if the second part of the message
                //After the char is either A-F, a-f, K-O, k-o or R, r.
                b[i] = '\u00A7'; //The '&' symbol.
                switch (b[i+1]) {
                    case 'a':
                        String coloured = string.substring(0, i) + string.substring(i).replace(code, GREEN);
                }
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b); //Return a string object from the char array.
    }

}
