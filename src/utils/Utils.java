package utils;

/**
 * Boîte à outils
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class Utils {
    /**
     * Transfome un caractère en chaîne de bits
     * @param c caractère
     * @return une chaîne de bit représentant le caractère
     */
    public static String charToBinaryString(char c) {
        return Integer.toBinaryString(c);
    }

    /**
     * Transfome une chaîne de bits en caractère
     * @param s chaîne de bits
     * @return caractère représenté par la chaîne de bits
     */
    public static char binaryStringToChar(String s) {
        return (char) Integer.parseInt(s, 2);
    }

    /**
     * Ajoute n 0 avant afin de faire une chaîne de bits multiple de 8
     * @param s chaîne de bits
     */
    public static void completeBinaryStringBefore8(StringBuilder s) {
        //Si la chaîne n'est pas multiple de 8, completer en rajoutant des 0 au début
        while (s.length() % Byte.SIZE != 0)
            s.insert(0, "0");
    }

    /**
     * Ajoute n 0 avant afin de faire une chaîne de bits multiple de 16
     * @param s chaîne de bits
     */
    public static void completeBinaryStringBefore16(StringBuilder s) {
        //Si la chaîne n'est pas multiple de 16, completer en rajoutant des 0 au début
        while (s.length() % Character.SIZE != 0)
            s.insert(0, "0");
    }

    /**
     * Ajoute n 0 après afin de faire une chaîne de bits multiple de 8
     * @param s chaîne de bits
     */
    public static void completeBinaryStringAfter8(StringBuilder s) {
        //Si la chaîne n'est pas multiple de 8, completer en rajoutant des 0 à la fin
        while (s.length() % Byte.SIZE != 0)
            s.append("0");
    }

    /**
     * Ajoute n 0 après afin de faire une chaîne de bits multiple de 16
     * @param s chaîne de bits
     */
    public static void completeBinaryStringAfter16(StringBuilder s) {
        //Si la chaîne n'est pas multiple de 16, completer en rajoutant des 0 à la fin
        while (s.length() % Character.SIZE != 0)
            s.append("0");
    }

    /**
     * Affiche littéralement un caractère spécial
     * @param c le caractère
     * @return le caractère écrit littéralement ou le caractère lui même si ce n'est pas un caractère spécial
     */
    public static String literalChar(char c) {
        switch (c) {
            case '\n' :
                return "\\n";
            case '\r' :
                return "\\r";
            case '\t' :
                return "\\t";
            case '\b' :
                return "\\b";
            case '\f' :
                return "\\f";
            case '\0' :
                return "\\0";
            default :
                return String.valueOf(c);
        }
    }
}
