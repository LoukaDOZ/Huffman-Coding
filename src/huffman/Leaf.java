package huffman;

import utils.Utils;

/**
 * Feuille de l'arbre de Huffman
 * @see TreeElement
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class Leaf extends TreeElement {
    /**
     * Constructeur
     * @param value valeur
     */
    public Leaf(char value) {
        super(value);
    }

    /**
     * Constructeur
     * @param value valeur
     * @param frequency fréquence
     */
    public Leaf(char value, int frequency) {
        super(value, frequency);
    }

    /**
     * Retourne la valeur binaire d'un caractère
     * @param c caractère
     * @return une chaine de 0 et 1 correspondant à la valeur binaire de c si le caractère est présent, sinon une chaine vide
     */
    @Override
    public String getCharCode(char c) {
        return "";
    }

    /**
     * Converti en chaîne de caractères
     * @return la feuille sous forme de chaîne de cractères
     */
    @Override
    public String toString() {
        return "{ f = " + this.getFrequency() + ", v = '" + Utils.literalChar(this.getValue()) + "' }";
    }
}
