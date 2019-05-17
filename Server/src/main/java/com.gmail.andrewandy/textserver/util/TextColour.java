package com.gmail.andrewandy.textserver.util;


enum TextColour {

    BLUE('b', "\u001b[34m"), RED('c', "\u001b[31m"), YELLOW('e', "\u001b[33m"), GREEN('a', "\u001b[32m"), MAGENTA('d', "\u001b[35m"), WHITE('f', "\u001b[37m"), BLACK('g', "\u001b[30m"), RESET('r', "\u001b[0m");

    private String ansi;
    private char code;
    TextColour(char code, String ansiString) {
        setAnsi(ansiString);
        setCode(code);
    }

    public static String translateColourCode(char code, String string) {
        char[] b = string.toCharArray();
        String c = new String(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == code && "AaBbCcDdEeFfGgRr".indexOf(b[i + 1]) > -1) { //This checks if the second part of the message
                //After the char is either A-G or a-g or R-r
                for (TextColour colour : values()) {
                    if (colour.getCode() != b[i + 1]) {
                        continue;
                    }
                    b[i] = Character.MAX_VALUE;
                    b[i + 1] = Character.MAX_VALUE;
                    c = new String(b);
                    c = c.substring(0, i) + colour.getAnsi() + c.substring(i + 1);
                    System.out.println("[" + i + "]" + " " + c);
                }
            }
        }
        return c;
    }

    public String getAnsi() {
        return ansi;
    }

    private void setAnsi(String ansiString) {
        ansi = ansiString;
    }

    public char getCode() {
        return code;
    }

    private void setCode(char code) {
        this.code = code;
    }
}
