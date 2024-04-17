package io;

import utils.Utils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Permet d'écrire des bits dans un fichier en les découpants en octets
 * S'occupe des exceptions
 * Les octets sont grâce à {@link FileOutputStream#write(int)}
 * Les bits à écrire sont stockés dans une chaîne de caractères et dès que la chaîne contient au moins 8 bits,
 * les n * 8 premiers bits sont écrits
 * @see WriteFile pour une écriture plus rapide destinée aux fichiers classiques
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class BinaryWriter {
    /**
     * Ecrivain
     */
    private FileOutputStream writer;

    /**
     * Bits à écrire
     */
    private StringBuilder content;

    /**
     * Constructeur
     * @param path chemin du fichier
     */
    public BinaryWriter(String path) {
        try {
            this.writer = new FileOutputStream(path);
            this.content = new StringBuilder();
        } catch (IOException e) {
            System.out.println("Error occurs while opening the file : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Ajoute une suite binaire à écrire
     * @param binary liste de 0 et de 1 à écrire
     */
    public void write(String binary) {
        this.content.append(binary);
        this.tryWrite();
    }

    /**
     * Essaye d'écrire autant de bits que possible si la chaîne contient au moins 8 bits
     * Ecrit 8 bits par 8 bits tant que la chaîne contient assez de bits
     * Supprime les bits écrits de la chaîne
     */
    private void tryWrite() {
        try {
            while(this.content.length() >= Byte.SIZE) {
                this.writer.write((byte) Utils.binaryStringToChar( this.content.substring(0, Byte.SIZE) ));
                this.content.delete(0, Byte.SIZE);
            }
        } catch (IOException e) {
            System.out.println("Error occurs while writing : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Ecrit le reste de la chaîne
     * Complète avec des 0 à la fin pour faire une suite parfaitement multiple de 8
     * @see Utils#completeBinaryStringAfter8(StringBuilder)
     */
    public void flush() {
        Utils.completeBinaryStringAfter8(this.content);
        this.tryWrite();
    }

    /**
     * Termine l'écriture
     * Flush {@link BinaryWriter#flush()} avant la fermeture
     */
    public void close() {
        this.flush();

        try {
            this.writer.close();
        } catch (IOException e) {
            System.out.println("Error occurs while closing : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
