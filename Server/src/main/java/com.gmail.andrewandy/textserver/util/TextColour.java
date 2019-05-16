package com.gmail.andrewandy.textserver.util;


enum TextColour {

    BLUE('b', "\u001b[34m"), RED('c', "\u001b[31m"), YELLOW('e', "\u001b[33m"), GREEN('a', "\u001b[32m"), MAGENTA('d', "\u001b[35m"), WHITE('f', "\u001b[37m"), BLACK('g', "\u001b[30m"), RESET('r', "\u001b[0m");

    TextColour(char code, String ansiString) {
        setAnsi(ansiString);
        setCode(code);
    }

    private String ansi;
    private char code;

    public static String translateColourCode(char code, String string) {
        char[] chars = string.toCharArray();
        String stringFromArray = new String(chars);
        for (int index = 0; index < chars.length - 1; index++) {
            if (chars[index] == code && "AaBbCcDdEeFfGgRr".indexOf(chars[index + 1]) > -1) { //This checks if the second part of the message
                //After the char is either A-G or a-g or R-r
                for (TextColour colour : values()) {
                    if (colour.getCode() != chars[index + 1]) {
                        continue;
                    }
                    chars[index] =  Character.MAX_VALUE;
                    chars[index+1] = Character.MAX_VALUE;
                    stringFromArray = new String(chars);
                    stringFromArray = stringFromArray.substring(0, index) + colour.getAnsi() + stringFromArray.substring(index + 1);
                    System.out.println("[" + index + "]" + " " + stringFromArray);
                }
            }
        }
        return stringFromArray;
    }


    private void setAnsi(String ansiString) {
        ansi = ansiString;
    }

    public String getAnsi() {
        return ansi;
    }

    public char getCode() {
        return code;
    }

    private void setCode(char code) {
        this.code = code;
    }
}
