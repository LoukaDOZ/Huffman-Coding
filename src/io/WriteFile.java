package io;

import java.io.*;

/**
 * Permet d'écrire un fichier
 * S'occupe des exceptions
 * @see BinaryReader pour une lecture plus lente mais destinée aux fichiers compressés
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class WriteFile {
    /**
     * Ecrivain
     */
    private BufferedWriter writer;

    /**
     * Constructeur
     * @param path chemin du fichier
     */
    public WriteFile(String path) {
        try {
            this.writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            System.out.println("Error occurs while opening the file : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Ajoute un saut de ligne au fichier
     */
    public void newLine() {
        try {
            this.writer.newLine();
        } catch (IOException e) {
            System.out.println("Error occurs while writing : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    public void write(char c) {
        try {
            this.writer.write(c);
        } catch (IOException e) {
            System.out.println("Error occurs while writing : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Termine la lecture
     */
    public void close() {
        try {
            this.writer.close();
        } catch (IOException e) {
            System.out.println("Error occurs while closing : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
