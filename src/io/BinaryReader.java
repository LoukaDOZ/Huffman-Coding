package io;

import utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Permet de lire des bits dans un fichier découpé en octets
 * S'occupe des exceptions
 * Les octets sont lus sous forme d'octets {@link FileInputStream#read()} et transformés en chaîne de bits
 * Lorsque le nombre bits demandés à être lu sont supérieur à ce que on a déjà récupéré, on continue la lecture du fichier
 * @see ReadFile pour une lecture plus rapide destinée aux fichiers classiques
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class BinaryReader {
    /**
     * Lecteur
     */
    private FileInputStream reader;

    /**
     * Chaîne de bits récupérée
     */
    private StringBuilder content;

    /**
     * Constructeur
     * @param path chemin du fichier
     */
    public BinaryReader(String path) {
        try {
            this.reader = new FileInputStream(path);
            this.content = new StringBuilder();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurs while opening the file : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Permet d'obtenir 1 bit du fichier
     * @return true pour 1, false pour 0
     * @throws IllegalStateException si le fichier ne contient pas assez de bits pour compléter la demande
     */
    public boolean readBit() {
        this.tryRead(1);

        if(this.content.length() < 1)
            throw new IllegalStateException("Asked 1 but length is " + this.content.length());

        char c = this.content.charAt(0);
        this.content.deleteCharAt(0);

        return c != '0';
    }

    /**
     * Permet d'obtenir un caractère du fichier (16 bits)
     * @return une chaîne de 0 et de 1 représentant le caractère
     * @throws IllegalStateException si le fichier ne contient pas assez de bits pour compléter la demande
     */
    public String readChar() {
        this.tryRead(Character.SIZE);

        if(this.content.length() < Character.SIZE)
            throw new IllegalStateException("Asked " + Character.SIZE + " but length is " + this.content.length());

        String binary = this.content.substring(0, Character.SIZE);
        this.content.delete(0, Character.SIZE);

        return binary;
    }

    /**
     * Récupère suffisament d'octets du fichier pour compléter la quantité demandée
     * Ne récupère rien si on à déjà assez
     * @param quantity nombre de bits à obtenir
     */
    private void tryRead(int quantity) {
        int c;

        try {
            while (this.content.length() < quantity && (c = this.reader.read()) >= 0) {
                StringBuilder sb = new StringBuilder(Utils.charToBinaryString((char) c));

                Utils.completeBinaryStringBefore8(sb);
                this.content.append(sb);
            }
        } catch (IOException e) {
            System.out.println("Error occurs while reading : " + e.getMessage());
            e.printStackTrace();
            this.close();
        }
    }

    /**
     * Retourne le reste des bits lus
     * Ne lis pas tout le reste du fichier
     * @return le reste des bits déjà lus
     */
    public String flush() {
        String binary = this.content.toString();
        this.content.delete(0, this.content.length());

        return binary;
    }

    /**
     * Termine la lecture
     * Retourne le reste des bits déjà lus {@link BinaryReader#flush()}
     * @return le reste des bits déjà lus
     */
    public String close() {
        String binary = this.flush();

        try {
            this.reader.close();
        } catch (IOException e) {
            System.out.println("Error occurs while closing : " + e.getMessage());
            e.printStackTrace();
        }

        return binary;
    }
}
