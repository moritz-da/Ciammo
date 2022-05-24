package de.hdm_stuttgart.mi.Helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class delivers useful methods for Ciammo.
 */
public final class Utilities {

    private static final Logger log = LogManager.getLogger(Utilities.class);

    /**
     * uncalled constructor
     */
    private Utilities() {
        //not called
    }

    /**
     * This method delivers word with uppercase first letter.
     *
     * @param word given word
     * @return if at least length 1, word with uppercase first letter, null otherwise
     */
    public static String firstLetterUpperCase(String word) {
        log.debug("firstLetterUpperCase method started");
        String s = word.trim().toLowerCase();
        if (word.length() >= 2) {
            char firstLetter = s.charAt(0);
            firstLetter = Character.toUpperCase(firstLetter);
            String restOfWord = s.substring(1);
            return firstLetter + restOfWord;
        }
        else if (word.length() == 1) {
            return Character.toString(Character.toUpperCase(s.charAt(0)));
        }
        return null;
    }

    /**
     * This method checks password conditions.
     *
     * @param password given password
     * @return true, if password fits password conditions, false otherwise
     */
    public static boolean provePasswordConditions(String password) {
        log.debug("provePasswordConditions method started");
        if (password == null || password.length() < 5) {
            log.debug("provePasswordConditions method, Password not long enough. Use min 8 characters!");
            return false;
        }
        int counterSmallLetter = 0;
        int counterBigLetter = 0;
        int counterNumber = 0;
        int counterSpecialCharacter = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                counterBigLetter++;
            }
            if (Character.isLowerCase(c)) {
                counterSmallLetter++;
            }
            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                counterNumber++;
            }
            if (c == '-' || c == '_' || c == '#' || c == '?' || c == '!' || c == '&' || c == '+' || c == '*') {
                counterSpecialCharacter++;
            }
        }

        if (counterBigLetter == 0) {
            log.debug("provePasswordConditions method, password needs a big letter");
            return false;
        }
        if (counterSmallLetter == 0) {
            log.debug("provePasswordConditions method, password needs a small letter");
            return false;
        }
        if (counterNumber == 0) {
            log.debug("provePasswordConditions method, password needs a number");
            return false;
        }
        if (counterSpecialCharacter == 0) {
            log.debug("provePasswordConditions method, password needs a special character");
            return false;
        }
        return true;
    }
}
