package io;

import java.io.*;

/**
 * Permet de lire un fichier
 * S'occupe des exceptions
 * @author louka DOZ, Wissam AIT KHEDDACHE
 * @see BinaryWriter pour une écriture plus lente mais destinée aux fichiers compressés
 */
public class ReadFile {
    /**
     * Lecteur
     */
    private BufferedReader reader;

    /**
     * Constructeur
     * @param path chemin du fichier
     */
    public ReadFile(String path) {
        try {
            this.reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Error occurs while opening the file : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Lit un caractère
     * @return le caractère ou null si la fin de fichier est atteinte
     */
    public Character readChar() {
        int i = -1;

        try {
            i = this.reader.read();
        } catch (IOException e) {
            System.out.println("Error occurs while reading : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }

        if(i == -1)
            return null;

        return (char) i;
    }

    /**
     * Lit tout le fichier caractère par caractère
     * @see ReadFile#readChar()
     * @return le contenu du fichier, une chaîne vide si la fin de fichier est atteinte
     */
    public String readAllChars() {
        StringBuilder s = new StringBuilder();
        int c;

        while ((c = this.readChar()) >= 0)
            s.append((char) c);

        return s.toString();
    }

    /**
     * Termine la lecture
     */
    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            System.out.println("Error occurs while closing : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
